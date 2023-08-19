package com.qr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

/**
 * 
 * @author sarvesh.lakhera
 *
 */
public class GenerateQrWithRoundShape {
  public static void main(String[] args) {
    try {
      generateQRCodeImage("https://www.google.com", 300, 300, "./MyQRCode.png");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void generateQRCodeImage(String text, int width, int height, String filePath)
      throws WriterException, IOException {
    String pathToStore = "D:\\Project\\Parivesh\\Files\\QRCodeGenerated.jpg";

    final Map<EncodeHintType, Object> encodingHints = new HashMap<EncodeHintType, Object>();
    encodingHints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    QRCode code = Encoder.encode(text, ErrorCorrectionLevel.H, encodingHints);
    BufferedImage image = renderQRImage(code, width, height, 4);

    ImageIO.write(image, "png", new File(pathToStore));
  }

  private static BufferedImage renderQRImage(QRCode code, int width, int height, int quietZone) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = image.createGraphics();

    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.setBackground(Color.white);
    graphics.clearRect(0, 0, width, height);
    graphics.setColor(Color.green);

    ByteMatrix input = code.getMatrix();
    if (input == null) {
      throw new IllegalStateException();
    }
    int inputWidth = input.getWidth();
    int inputHeight = input.getHeight();
    int qrWidth = inputWidth + (quietZone * 2);
    int qrHeight = inputHeight + (quietZone * 2);
    int outputWidth = Math.max(width, qrWidth);
    int outputHeight = Math.max(height, qrHeight);

    int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
    int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
    int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
    final int FINDER_PATTERN_SIZE = 7;
    final float CIRCLE_SCALE_DOWN_FACTOR = 21f / 30f;
    int circleSize = (int) (multiple * CIRCLE_SCALE_DOWN_FACTOR);

    for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
      for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
        if (input.get(inputX, inputY) == 1) {
          if (!(inputX <= FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE
              || inputX >= inputWidth - FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE
              || inputX <= FINDER_PATTERN_SIZE && inputY >= inputHeight - FINDER_PATTERN_SIZE)) {
            graphics.fillOval(outputX, outputY, circleSize, circleSize);
          }
        }
      }
    }

    int circleDiameter = multiple * FINDER_PATTERN_SIZE;
    drawFinderPatternCircleStyle(graphics, leftPadding, topPadding, circleDiameter);
    drawFinderPatternCircleStyle(graphics, leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding,
        circleDiameter);
    drawFinderPatternCircleStyle(graphics, leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple,
        circleDiameter);

    return image;
  }

  private static void drawFinderPatternCircleStyle(Graphics2D graphics, int x, int y, int circleDiameter) {
    final int WHITE_CIRCLE_DIAMETER = circleDiameter * 5 / 7;
    final int WHITE_CIRCLE_OFFSET = circleDiameter / 7;
    final int MIDDLE_DOT_DIAMETER = circleDiameter * 3 / 7;
    final int MIDDLE_DOT_OFFSET = circleDiameter * 2 / 7;

    graphics.setColor(Color.black);
    graphics.fillOval(x, y, circleDiameter, circleDiameter);
    graphics.setColor(Color.white);
    graphics.fillOval(x + WHITE_CIRCLE_OFFSET, y + WHITE_CIRCLE_OFFSET, WHITE_CIRCLE_DIAMETER, WHITE_CIRCLE_DIAMETER);
    graphics.setColor(Color.black);
    graphics.fillOval(x + MIDDLE_DOT_OFFSET, y + MIDDLE_DOT_OFFSET, MIDDLE_DOT_DIAMETER, MIDDLE_DOT_DIAMETER);
  }
}
