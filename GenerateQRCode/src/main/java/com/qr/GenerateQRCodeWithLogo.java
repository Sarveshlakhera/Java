package com.qr;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.google.common.collect.ImmutableMap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 
 * @author sarvesh.lakhera
 *
 */
public class GenerateQRCodeWithLogo {
  public static void main(String[] args) throws Exception {

    String content = "https://trekcodes.blogspot.com/";
    String pathToStore = "D:\\Project\\Parivesh\\Files\\QRCodeGenerated.jpg";
    final int GREEN = 0xFF49be25;

    BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 400, 400,
        ImmutableMap.of(com.google.zxing.EncodeHintType.MARGIN, 0));
    MatrixToImageConfig imageConfig = new MatrixToImageConfig(GREEN , MatrixToImageConfig.WHITE);

    BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, imageConfig);

    // Getting logo image
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream is = classloader.getResourceAsStream("sample-logo/Parivesh.png");

    BufferedImage logoImage = ImageIO.read(is);
    int finalImageHeight = qrImage.getHeight() - logoImage.getHeight();
    int finalImageWidth = qrImage.getWidth() - logoImage.getWidth();

    // Merging both images
    BufferedImage finalImage = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = (Graphics2D) finalImage.getGraphics();
    graphics.drawImage(qrImage, 0, 0, null);
    graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    graphics.drawImage(logoImage, (int) Math.round(finalImageWidth * 2 - 70),
        (int) Math.round(finalImageHeight * 2 - 30), finalImageWidth / 2, finalImageWidth / 2, null);

    ImageIO.write(finalImage, "png", new File(pathToStore));
    System.out.println("QR Code with Logo Generated Successfully");

  }
}
