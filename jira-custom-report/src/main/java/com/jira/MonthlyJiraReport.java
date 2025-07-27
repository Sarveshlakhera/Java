package com.jira;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author sarvesh.lakhera
 *
 */
public class MonthlyJiraReport {
  private static final String JIRA_URL = "https://{domain}.atlassian.net";
  private static final String API_TOKEN = "******";
  private static final String USER_EMAIL = "abc@example.com";
  private static final String PROJECT_KEY = "abc"; // Define the project you want to report on

  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the year: ");
    int year = scanner.nextInt();

    // Get month input from user
    System.out.print("Enter the month (1-12): ");
    int month = scanner.nextInt();

    // Validate month input
    if (month < 1 || month > 12) {
      System.out.println("Invalid month. Please enter a value between 1 and 12.");
      scanner.close();
      return;
    }

    // Get start day input from user
    System.out.print("Enter the start day: ");
    int startDay = scanner.nextInt();

    // Get end day input from user
    System.out.print("Enter the end day: ");
    int endDay = scanner.nextInt();

    System.out.print("Enter the Resource name: ");
    String resourceName = scanner.next();

    // Validate day inputs
    if (startDay < 1 || endDay < 1 || startDay > endDay || endDay > getDaysInMonth(month)) {
      System.out.println(
          "ERROR:: Invalid input. Please ensure days are valid for the month and start day is less than or equal to end day.");
    } else if (resourceName == null || resourceName.isEmpty()) {
      System.out.println("ERROR:: Resource name cannot be empty");

    } else {
      String monthStr = month + "";
      String startDayStr = startDay + "";
      String endDayStr = endDay + "";

      if (month < 10)
        monthStr = "0" + month;

      if (startDay < 10)
        startDayStr = "0" + startDay;

      if (endDay < 10)
        endDayStr = "0" + endDay;

      String startDate = year + "/" + monthStr + "/" + startDayStr;
      String endDate = year + "/" + monthStr + "/" + endDayStr;
      LocalDate startOfMonth = LocalDate.of(year, month, startDay);
      LocalDate endOfMonth = LocalDate.of(year, month, endDay);
      System.out.println("Please wait while the report is generated.....");
      generateReport(startDate, endDate, startOfMonth, endOfMonth, resourceName);
    }

    // Close the scanner to prevent resource leaks
    scanner.close();
  }

  private static int getDaysInMonth(int month) {
    switch (month) {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:
      return 31; // Months with 31 days
    case 4:
    case 6:
    case 9:
    case 11:
      return 30; // Months with 30 days
    case 2:
      return 28; // Simplification, non-leap year
    default:
      return 0; // Should never reach here
    }
  }

  private static void generateReport(String startDate, String endDate, LocalDate startOfMonth, LocalDate endOfMonth,
      String resourceName) {
    try {
      Map<String, Map<String, Double>> report = new HashMap<>();
      JSONArray issues = getIssues(startDate, endDate);

      for (int i = 0; i < issues.length(); i++) {
        String issueId = issues.getJSONObject(i).getString("key");
        JSONArray worklogs = getWorklogs(issueId, startOfMonth, endOfMonth);

        for (int j = 0; j < worklogs.length(); j++) {
          JSONObject worklog = worklogs.getJSONObject(j);
          String author = worklog.getJSONObject("author").getString("displayName");
          if (resourceName.equalsIgnoreCase(author) || author.contains(resourceName)) {
            long timeSpentSeconds = worklog.getLong("timeSpentSeconds");
            String workDate = worklog.getString("started").substring(0, 10); // Get date in format
            // YYYY-MM-DD

            report.putIfAbsent(author, new HashMap<>());
            String key = String.format("%s (%s)", issueId, workDate); // Unique key for ticket and date
            report.get(author).put(key, report.get(author).getOrDefault(key, 0.0) + (timeSpentSeconds / 3600.0)); // Convert
            // seconds
            // to hours
          }
        }
      }
      if (report.isEmpty()) {
        System.out
            .println("ERROR:: Either the resource name is incorrect or there is no worklog for the given resource");
      } else {
        writeReportToExcel(report, startOfMonth, endOfMonth);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static JSONArray getIssues(String startDateStr, String endDateStr) throws IOException {
    String jql = String.format("project = %s AND worklogDate >= \"%s\" AND worklogDate <= \"%s\"", PROJECT_KEY,
        startDateStr, endDateStr);

    String encodedJql = URLEncoder.encode(jql, StandardCharsets.UTF_8.toString());
    String url = JIRA_URL + "/rest/api/3/search?jql=" + encodedJql;

    return sendGetRequest(url).getJSONArray("issues");
  }

  private static JSONArray getWorklogs(String issueId, LocalDate startOfMonth, LocalDate endOfMonth)
      throws IOException {
    String url = JIRA_URL + "/rest/api/3/issue/" + issueId + "/worklog";
    JSONArray worklogs = sendGetRequest(url).getJSONArray("worklogs");

    // Filter for the current month only
    JSONArray filteredWorklogs = new JSONArray();

    for (int i = 0; i < worklogs.length(); i++) {
      JSONObject worklog = worklogs.getJSONObject(i);
      LocalDate workDate = LocalDate.parse(worklog.getString("started").substring(0, 10)); // YYYY-MM-DD
      if (!workDate.isBefore(startOfMonth) && !workDate.isAfter(endOfMonth)) {
        filteredWorklogs.put(worklog);
      }
    }

    return filteredWorklogs;
  }

  private static JSONObject sendGetRequest(String url) throws IOException {
    CloseableHttpClient client = HttpClients.createDefault();
    HttpGet request = new HttpGet(url);
    String auth = USER_EMAIL + ":" + API_TOKEN;
    String encoding = java.util.Base64.getEncoder().encodeToString(auth.getBytes());

    request.setHeader("Authorization", "Basic " + encoding);
    request.setHeader("Content-Type", "application/json");

    HttpResponse response = client.execute(request);
    String json = EntityUtils.toString(response.getEntity());
    client.close();

    return new JSONObject(json);
  }

  private static void writeReportToExcel(Map<String, Map<String, Double>> report, LocalDate startDate,
      LocalDate endDate) {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Monthly Time Report");

    Row headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("Resource");
    headerRow.createCell(1).setCellValue("JIRA Ticket (Date)");
    headerRow.createCell(2).setCellValue("Date");
    headerRow.createCell(3).setCellValue("Hours Worked");

    // Fill data rows
    int rowIndex = 1;
    for (Map.Entry<String, Map<String, Double>> entry : report.entrySet()) {
      String resource = entry.getKey();

      for (Map.Entry<String, Double> ticketEntry : entry.getValue().entrySet()) {
        Row row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue(resource);
        row.createCell(1).setCellValue(ticketEntry.getKey());
        String dateDtr = "";
        int start = ticketEntry.getKey().indexOf('(');
        int end = ticketEntry.getKey().indexOf(')');
        if (start != -1 && end != -1 && start < end)
          dateDtr = ticketEntry.getKey().substring(start + 1, end);
        row.createCell(2).setCellValue(dateDtr);
        row.createCell(3).setCellValue(ticketEntry.getValue());
      }
    }

    try (FileOutputStream fileOut = new FileOutputStream(
        "Monthly_Time_Report_" + System.currentTimeMillis() + ".xlsx")) {
      workbook.write(fileOut);
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      workbook.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("SUCCESS:: Report generated successfully.");
  }
}
