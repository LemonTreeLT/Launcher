package com.lemontree.launcher.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.stream.IntStream;

public class ImageUtils {
    public static Image White(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        IntStream.range(0, height).parallel().forEach(y -> IntStream.range(0, width).forEach(x -> {
            Color color = pixelReader.getColor(x, y);
            if(!color.equals(Color.TRANSPARENT)) pixelWriter.setColor(x, y, Color.WHITE);
            else pixelWriter.setColor(x, y, color);
        }));

        return writableImage;
    }
}
