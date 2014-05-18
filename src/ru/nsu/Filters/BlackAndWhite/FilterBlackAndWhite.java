package ru.nsu.Filters.BlackAndWhite;

import ru.nsu.Filters.Filter;
import ru.nsu.Filters.FilterGrayscale;
import ru.nsu.MyRGB;

import java.awt.image.BufferedImage;

/**
 * Created by nero on 5/10/14.
 */
public class FilterBlackAndWhite extends Filter {
    static public MyRGB[][] applyFilter(MyRGB[][] array, int threshold, boolean inversion){
        MyRGB[][] new_array = FilterGrayscale.applyFilter(array);
        for ( int x= 0; x < 256; x++)
            for ( int y = 0; y < 256; y++) {
                if (new_array[x][y].getRed() > threshold) {
                    new_array[x][y].setRed(255);
                    new_array[x][y].setGreen(255);
                    new_array[x][y].setBlue(255);
                } else {
                    new_array[x][y].setRed(0);
                    new_array[x][y].setGreen(0);
                    new_array[x][y].setBlue(0);
                }
            }

        return new_array;
    }
}
