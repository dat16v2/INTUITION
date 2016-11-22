package kea.intuition;

import javafx.scene.text.Font;

public class Fonts {
    public static Font OpenSansRegular;
    public static Font OpenSansLight;
    public static Font OpenSansSemiBold;
    public static Font OpenSansBold;

    public static void Init() {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        OpenSansRegular = Font.loadFont(contextClassLoader.getResourceAsStream("Open_Sans/OpenSans-Regular.ttf"), 400);
        OpenSansLight = Font.loadFont(contextClassLoader.getResourceAsStream("Open_Sans/OpenSans-Light.ttf"), 300);
        OpenSansSemiBold = Font.loadFont(contextClassLoader.getResourceAsStream("Open_Sans/OpenSans-Semibold.ttf"), 600);
        OpenSansBold = Font.loadFont(contextClassLoader.getResourceAsStream("Open_Sans/OpenSans-Bold.ttf"), 700);
    }
}