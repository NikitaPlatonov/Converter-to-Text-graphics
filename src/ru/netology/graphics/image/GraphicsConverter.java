package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GraphicsConverter implements TextGraphicsConverter {
    TextColorSchema colorSchema = new ColorSchema();
    private int maxWidth;
    private int maxHeight;
    private double maxRatio;
    private Image scaledImage;
    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int newWidth = img.getWidth();
        int newHeight = img.getHeight();
        if ((double) newWidth / (double) newHeight > maxRatio || (double) newHeight / (double) newWidth > maxRatio) {
            double ratio1 = (double) newWidth / (double) newHeight;
            double ratio2 = (double) newHeight / (double) newWidth;
            if (ratio1 > ratio2) {
                throw new BadImageSizeException(ratio1, maxRatio);
            } else {
                throw new BadImageSizeException(ratio2, maxRatio);
            }
        }
        if (newHeight > maxHeight || newWidth > maxWidth) {
            while (true) {
                if (newHeight > maxHeight && newHeight > newWidth) {
                    double ratio = ((double) newHeight / (double) newWidth);
                    double width = (double) newWidth / ratio;
                    double height = (double) newHeight / ratio;
                    newWidth = (int) Math.round(width);
                    newHeight = (int) Math.round(height);
                } else if (newWidth > newHeight && newHeight > maxHeight) {
                    double ratio = ((double) newWidth / (double) newHeight);
                    double width = (double) newWidth / ratio;
                    double height = (double) newHeight / ratio;
                    newWidth = (int) Math.round(width);
                    newHeight = (int) Math.round(height);
                }
                if (newWidth > maxWidth && newWidth > newHeight) {
                    double ratio = ((double) newWidth / (double) newHeight);
                    double width = (double) newWidth / ratio;
                    double height = (double) newHeight / ratio;
                    newWidth = (int) Math.round(width);
                    newHeight = (int) Math.round(height);
                } else if (newHeight > newWidth && newWidth > maxWidth) {
                    double ratio = ((double) newHeight / (double) newWidth);
                    double width = (double) newWidth / ratio;
                    double height = (double) newHeight / ratio;
                    newWidth = (int) Math.round(width);
                    newHeight = (int) Math.round(height);
                }
                if (newWidth > maxWidth || newHeight > maxHeight) {
                    continue;
                } else {
                    scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
                    break;
                }
            }
        }
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        ImageIO.write(bwImg, "png", new File("out.png"));
        WritableRaster bwRaster = bwImg.getRaster();
        StringBuilder textGraphics = new StringBuilder();
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                int color = bwRaster.getPixel(j, i, new int[3])[0];
                char c = colorSchema.convert(color);
                textGraphics.append(c);
                textGraphics.append(c);
            }
            textGraphics.append("\n");
        }
        return textGraphics.toString();
    }

    public void setMaxWidth(int Width) {
        this.maxWidth = Width;
    }

    public void setMaxHeight(int Height) {
        this.maxHeight = Height;
    }

    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    public void setTextColorSchema(TextColorSchema colorSchema) {
        this.colorSchema = colorSchema;
    }
}

