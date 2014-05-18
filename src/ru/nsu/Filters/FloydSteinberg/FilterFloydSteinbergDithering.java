package ru.nsu.Filters.FloydSteinberg;

import ru.nsu.Filters.Filter;
import ru.nsu.MyRGB;

/**
 * Created by nero on 5/13/14.
 */
public class FilterFloydSteinbergDithering extends Filter {

    static int red_param;
    static int green_param;
    static int blue_param;


    static public MyRGB[][] applyFilter( MyRGB[][] array, int red_value, int green_value, int blue_value){
        MyRGB[][] new_array = new MyRGB[256][256];

        red_param = red_value;
        green_param = green_value;
        blue_param = blue_value;
        int red_oldpixel, green_oldpixel, blue_oldpixel;
        int red_newpixel, green_newpixel, blue_newpixel;
        int quant_error;

        for ( int x= 0; x < 256; x++)
            for ( int y = 0; y < 256; y++)
                new_array[x][y] = new MyRGB(array[x][y]);

        for ( int x= 0; x < 256; x++)
            for ( int y = 0; y < 256; y++) {

                red_oldpixel = array[x][y].getRed();
                red_newpixel = find_closest_palette_color_for_red(red_oldpixel);

                new_array[x][y].setRed(red_newpixel);
                quant_error = red_oldpixel - red_newpixel;
                if ( x+1 < 256 )new_array[x+1][y  ].setRed((int) (new_array[x+1][y  ].getRed() + quant_error * 7.0/16));
                if ( x-1 > 0 && y+1 < 256 )new_array[x-1][y+1].setRed((int) (new_array[x-1][y+1].getRed() + quant_error * 3.0/16));
                if ( y+1 < 256 )new_array[x  ][y+1].setRed((int) (new_array[x  ][y+1].getRed() + quant_error * 5.0/16));
                if ( x+1 < 256 && y+1 < 256)new_array[x+1][y+1].setRed((int) (new_array[x+1][y+1].getRed() + quant_error * 1.0/16));



                green_oldpixel = new_array[x][y].getGreen();
                green_newpixel = find_closest_palette_color_for_green(green_oldpixel);
                System.out.println("old=" + green_oldpixel +" new "+green_newpixel+"_"+green_param);
                new_array[x][y].setGreen(green_newpixel);
                quant_error = green_oldpixel - green_newpixel;
                if ( x+1 < 256 )new_array[x+1][y  ].setGreen(new_array[x+1][y  ].getGreen() + quant_error * 7/16);
                if ( x-1 > 0 && y+1 < 256 )new_array[x-1][y+1].setGreen(new_array[x-1][y+1].getGreen() + quant_error * 3/16);
                if ( y+1 < 256 )new_array[x  ][y+1].setGreen(new_array[x  ][y+1].getGreen() + quant_error * 5/16);
                if ( x+1 < 256 && y+1 < 256)new_array[x+1][y+1].setGreen(new_array[x+1][y+1].getGreen() + quant_error * 1/16);



                blue_oldpixel = new_array[x][y].getBlue();
                blue_newpixel = find_closest_palette_color_for_blue(blue_oldpixel);
                new_array[x][y].setBlue(blue_newpixel);
                quant_error = blue_oldpixel - blue_newpixel;
                if ( x+1 < 256 )new_array[x+1][y  ].setBlue(new_array[x+1][y  ].getBlue() + quant_error * 7/16);
                if ( x-1 > 0 && y+1 < 256 )new_array[x-1][y+1].setBlue(new_array[x-1][y+1].getBlue() + quant_error * 3/16);
                if ( y+1 < 256 )new_array[x  ][y+1].setBlue(new_array[x  ][y+1].getBlue() + quant_error * 5/16);
                if ( x+1 < 256 && y+1 < 256)new_array[x+1][y+1].setBlue(new_array[x+1][y+1].getBlue() + quant_error * 1/16);
            }
        return new_array;
    }

    private static int find_closest_palette_color_for_red(int color) {
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

    private static int find_closest_palette_color_for_green(int color) {
        if (color < 0)
            color = 0;
        if (color > 255)
            color = 255;
        int one = 256/ green_param;
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
    private static int find_closest_palette_color_for_blue(int color) {
        if (color < 0)
            color = 0;
        if (color > 255)
            color = 255;
        int one = 256/ blue_param;
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
}