package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema {
    private static final char [] Schema = {'#', '$', '@','%', '*', '+', '-', '\''};
    public char convert(int color) {
        return Schema [color / 32];
    }
}
