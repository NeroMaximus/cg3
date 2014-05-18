package ru.nsu.Filters;

import java.awt.image.BufferedImage;
import ru.nsu.MyRGB;

/**
 * Created by nero on 5/10/14.
 */
public class FilterNegative extends Filter {
    static public MyRGB[][] applyFilter( MyRGB[][] array){
        MyRGB[][] new_array = new MyRGB[256][256];
        for ( int i = 0; i<256; i++)
            for (int j = 0; j< 256; j++) {
                new_array[j][i] = new MyRGB();
                new_array[j][i].setRed(255 - array[j][i].getRed());
                new_array[j][i].setGreen(255 - array[j][i].getGreen());
                new_array[j][i].setBlue(255 - array[j][i].getBlue());
            }
        return new_array;
    }
}
