package com.bura.opengl.util;

public class MathUtils {

    public static double getAngle(float centerX, float centerY, float touchX, float touchY) {
        return Math.atan2(centerY - touchY, centerX - touchX);
    }

    public static double getLength(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
