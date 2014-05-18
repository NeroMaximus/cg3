package ru.nsu.Filters.RobertsOperator;

import ru.nsu.Filters.Filter;
import ru.nsu.Filters.FilterGrayscale;
import ru.nsu.MyRGB;

/**
 * Created by nero on 5/15/14.
 */
public class FilterRobertsOperator extends Filter {
    static public MyRGB[][] applyFilter( MyRGB[][] array, int board){
        MyRGB[][] new_array = FilterGrayscale.applyFilter(array);
        MyRGB[][] new_new_array = new MyRGB[256][256];

        for ( int i = 0; i<256; i++)
            for (int j = 0; j< 256; j++)
                new_new_array[i][j] = new MyRGB();//TODO


                int G;
        int Gx;
        int Gy;

        for ( int i = 0; i<255; i++)
            for (int j = 0; j< 255; j++) {

                Gx = new_array[i+1][j+1].getBlue() - new_array[i][j].getBlue();
                Gy = new_array[i][j+1].getBlue() - new_array[i+1][j].getBlue();
                G = (int) Math.sqrt( Gx * Gx + Gy * Gy);

                if ( G > board ) {
                    new_new_array[i][j].setRed(255);
                    new_new_array[i][j].setGreen(255);
                    new_new_array[i][j].setBlue(255);
                }
            }
        return new_new_array;
    }
}
