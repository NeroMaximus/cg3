package ru.nsu.Filters;

import ru.nsu.MyRGB;

import java.awt.image.BufferedImage;

/**
 * Created by nero on 5/10/14.
 */
public class FilterBlackAndWhite extends Filter {
    static public MyRGB[][] applyFilter(MyRGB[][] array, int threshold, boolean inversion){
        MyRGB[][] new_array = new MyRGB[256][256];
        for ( int x= 0; x < 256; x++)
            for ( int y = 0; y < 256; y++) {
                new_array[x][y] = new MyRGB();
                //if ( array[x][y].getRed() )
            }

        return new_array;
    }
}
