package ru.nsu.Filters;

import ru.nsu.MyRGB;

/**
 * Created by nero on 5/13/14.
 */
public class FilterGrayscale extends Filter {
    static public MyRGB[][] applyFilter(MyRGB[][] array){
        MyRGB[][] new_array = new MyRGB[256][256];
        int value;
        for ( int i = 0; i<256; i++)
            for (int j = 0; j< 256; j++) {
                value = (array[i][j].getRed() + array[i][j].getGreen() + array[i][j].getBlue())/3;
                new_array[i][j] = new MyRGB();
                new_array[i][j].setRed( value );
                new_array[i][j].setGreen(value);
                new_array[i][j].setBlue(value);
            }
                return new_array;
    }
}
