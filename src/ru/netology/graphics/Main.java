package ru.netology.graphics;
import ru.netology.graphics.image.GraphicsConverter;
import ru.netology.graphics.server.GServer;


public class Main {
    public static void main(String[] args) throws Exception {
        GraphicsConverter converter = new GraphicsConverter(); // Создайте тут объект вашего класса конвертера
        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); //



    }
}
