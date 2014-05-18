package ru.nsu.Filters.OrderedDithering;

import ru.nsu.Filters.Filter;
import ru.nsu.MyRGB;

/**
 * Created by nero on 5/18/14.
 */
public class FilterOrderedDithering extends Filter {
    private static int size = 3;
    static final double[][] matrix = new double[size][size];

    static {
        matrix[0][0] = 3;
        matrix[0][1] = 7;
        matrix[0][2] = 4;

        matrix[1][0] = 2;
        matrix[1][1] = 1;
        matrix[1][2] = 6;

        matrix[2][0] = 2;
        matrix[2][1] = 8;
        matrix[2][2] = 5;

//        matrix[0][0] = 1;
//        matrix[0][1] = 9;
//        matrix[0][2] = 3;
//        matrix[0][3] = 11;
//
//        matrix[1][0] = 13;
//        matrix[1][1] = 5;
//        matrix[1][2] = 15;
//        matrix[1][3] = 7;
//
//        matrix[2][0] = 4;
//        matrix[2][1] = 12;
//        matrix[2][2] = 2;
//        matrix[2][3] = 10;
//
//        matrix[3][0] = 16;
//        matrix[3][1] = 8;
//        matrix[3][2] = 14;
//        matrix[3][3] = 6;
    }

    private static int red_param;
    private static int green_param;
    private static int blue_param;

    static public MyRGB[][] applyFilter(MyRGB[][] array, int red_value, int green_value, int blue_value){
        red_param = red_value;
        green_param = green_value;
        blue_param = blue_value;
        MyRGB[][] new_array = new MyRGB[256][256];
        for ( int x= 0; x < 256; x++)
            for ( int y = 0; y < 256; y++) {
                new_array[x][y] = new MyRGB();
                new_array[x][y].setRed( findClosestPaletteColorForRed((int) (array[x][y].getRed() + matrix[x % size][y % size]/10)));
                new_array[x][y].setGreen(findClosestPaletteColorForGreen((int) (array[x][y].getGreen() + matrix[x % size][y % size] / 10)));
                new_array[x][y].setBlue(findClosestPaletteColorForBlue((int) (array[x][y].getBlue() + matrix[x % size][y % size] / 10)));
            }

        return new_array;
    }

    private static int findClosestPaletteColorForRed(int color) {
        if (color < 0)
            color = 0;
        if (color > 255)
            color = 255;
        int one = 256/ red_param;
        if (color%one < one/2){
            if (color/one != 0) {
            return one * (color/one)-1;
        } else {
            return one * (color/one);
        }        }
        else {
            return one * (color/one+1)-1;
        }
    }
    private static int findClosestPaletteColorForGreen(int color) {
        if (color < 0)
            color = 0;
        if (color > 255)
            color = 255;
        int one = 256/ green_param;
        if (color%one < one/2){
            if (color/one != 0) {
//                System.out.println("#"+(one * (color/one)-1));
                return one * (color/one)-1;
            } else {
//                System.out.println("#"+(one * (color/one)));
                return one * (color/one);
            }
        }
        else {
            return one * (color/one+1)-1;
        }
    }
    private static int findClosestPaletteColorForBlue(int color) {
        if (color < 0)
            color = 0;
        if (color > 255)
            color = 255;
//        System.out.print("#"+color + " "+ blue_param);
        int one = 256/ blue_param;
        if (color%one < one/2){
            if (color/one != 0) {
//                System.out.println("#"+(one * (color/one)-1));
                return one * (color/one)-1;
            } else {
//                System.out.println("#"+(one * (color/one)));
                return one * (color/one);
            }
        }
        else {
//            System.out.println("#"+(one * (color/one+1)));
            return one * (color/one+1)-1;
        }
    }
}
