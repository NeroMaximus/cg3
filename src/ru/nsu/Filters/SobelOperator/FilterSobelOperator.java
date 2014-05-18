package ru.nsu.Filters.SobelOperator;

import ru.nsu.Filters.Filter;
import ru.nsu.Filters.FilterBlur;
import ru.nsu.Filters.FilterGrayscale;
import ru.nsu.MyRGB;

/**
 * Created by nero on 5/15/14.
 */
public class FilterSobelOperator extends Filter {
    static public MyRGB[][] applyFilter( MyRGB[][] array, int board){
        MyRGB[][] new_array = FilterGrayscale.applyFilter(array);
        MyRGB[][] extended_array = new MyRGB[260][260];
        for ( int x= 0; x < 260; x++)
            for ( int y = 0; y < 260; y++) {
                extended_array[x][y] = new MyRGB();

                if ( x < 256 && y < 256)
                    new_array[x][y] = new MyRGB();
            }

        FilterBlur.fillExtendedArray(extended_array, FilterGrayscale.applyFilter(array));

        int G;
        int Gx;
        int Gy;

        for ( int i = 1; i<256; i++)
            for (int j = 1; j< 256; j++) {

                Gx = extended_array[i-1][j+1].getBlue() + 2 * extended_array[i][j+1].getBlue() + extended_array[i+1][j+1].getBlue()
                        - extended_array[i-1][j-1].getBlue() - 2 * extended_array[i][j-1].getBlue() - extended_array[i+1][j-1].getBlue();
                Gy = extended_array[i+1][j-1].getBlue() + 2 * extended_array[i+1][j].getBlue() + extended_array[i+1][j+1].getBlue()
                        - extended_array[i-1][j-1].getBlue() - 2 * extended_array[i-1][j].getBlue() - extended_array[i-1][j+1].getBlue();
                G = (int) Math.sqrt( Gx * Gx + Gy * Gy);

                if ( G > board ) {
                    new_array[i][j].setRed(255);
                    new_array[i][j].setGreen(255);
                    new_array[i][j].setBlue(255);
                }
            }
        return new_array;
    }
}
