package app;

import java.util.ArrayList;
import java.util.List;

public class Triangle {

    public static void main(String[] args) {
        final int ARGS_COUNT = 3;
        if (args.length != ARGS_COUNT) {
            System.out.println("Unknown error");
            return;
        }

        List<Float> intArgs = new ArrayList<Float>();
        for (String arg : args) {
            float side;
            try {
                side = Float.parseFloat(arg);
                String argToStr = Float.toString(side);
                if (argToStr.equals("Infinity")) {
                    System.out.println("Unknown error");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Unknown error");
                return;
            }

            if (side <= 0) {
                System.out.println("Not a triangle");
                return;
            }

            intArgs.add(side);
        }

        float a = intArgs.get(0);
        float b = intArgs.get(1);
        float c = intArgs.get(2);

        if (c >= a + b || b >= a + c || a >= c + b) {
            System.out.println("Not a triangle");
            return;
        }

        if (a == b && a == c) {
            System.out.println("Equilateral triangle");
            return;
        }

        if (IsIsosceles(a, b, c) || IsIsosceles(c, b, a) || IsIsosceles(b, a, c)) {
            System.out.println("Isosceles triangle");
            return;
        }

        System.out.println("Regular triangle");
    }

    private static boolean IsIsosceles(float a, float b, float c) {
        return a == b || a == c;
    }
}
