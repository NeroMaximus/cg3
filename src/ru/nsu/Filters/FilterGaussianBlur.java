package ru.nsu.Filters;

import com.sun.java.swing.plaf.windows.resources.windows;
import ru.nsu.MyRGB;

/**
 * Created by nero on 5/11/14.
 */
public class FilterGaussianBlur extends Filter {
    static final double[][] gauss_matrix = new double[5][5];

    static {
        gauss_matrix[0][0] = 0.000789;
        gauss_matrix[0][4] = 0.000789;
        gauss_matrix[4][0] = 0.000789;
        gauss_matrix[4][4] = 0.000789;

        gauss_matrix[0][1] = 0.006581;
        gauss_matrix[0][3] = 0.006581;
        gauss_matrix[1][0] = 0.006581;
        gauss_matrix[1][4] = 0.006581;
        gauss_matrix[3][0] = 0.006581;
        gauss_matrix[3][4] = 0.006581;
        gauss_matrix[4][1] = 0.006581;
        gauss_matrix[4][3] = 0.006581;

        gauss_matrix[2][0] = 0.013347;
        gauss_matrix[2][4] = 0.013347;
        gauss_matrix[0][2] = 0.013347;
        gauss_matrix[4][2] = 0.013347;

        gauss_matrix[1][1] = 0.054901;
        gauss_matrix[1][3] = 0.054901;
        gauss_matrix[3][1] = 0.054901;
        gauss_matrix[3][3] = 0.054901;

        gauss_matrix[1][2] = 0.111345;
        gauss_matrix[2][1] = 0.111345;
        gauss_matrix[3][2] = 0.111345;
        gauss_matrix[2][3] = 0.111345;

        gauss_matrix[2][2] = 0.225821;
    }

    static public MyRGB[][] applyFilter( MyRGB[][] array){
        MyRGB[][] new_array = new MyRGB[256][256];
        for ( int x= 0; x < 256; x++)
            for ( int y = 0; y < 256; y++)
                new_array[x][y] = new MyRGB();

        MyRGB[][] extended_array = new MyRGB[260][260];
        for ( int x= 0; x < 260; x++)
            for ( int y = 0; y < 260; y++)
                extended_array[x][y] = new MyRGB();

        fillExtendedArray( extended_array, array );


        for ( int i = 0; i < 256; i++)
            for (int j = 0; j< 256; j++) {

                double new_red = 0;
                double new_green = 0;
                double new_blue = 0;

                for ( int a = -2; a < 3; a++)
                    for ( int b = -2; b < 3; b++) {
                        new_red += gauss_matrix[2+a][2+b] * extended_array[i+a+2][j+b+2].getRed();
                        new_green += gauss_matrix[2+a][2+b] * extended_array[i+a+2][j+b+2].getGreen();
                        new_blue += gauss_matrix[2+a][2+b] * extended_array[i+a+2][j+b+2].getBlue();
                    }
                new_array[i][j].setRed((byte) new_red);
                new_array[i][j].setGreen((byte) new_green);
                new_array[i][j].setBlue((byte) new_blue);
            }


        return new_array;
    }


    private static void fillExtendedArray(MyRGB[][] extended_array, MyRGB[][] array) {
        for ( int i = 0; i < 256; i++)
            for (int j = 0; j < 256; j++) {
                extended_array[i+2][j+2].setRed(array[i][j].getRed());
                extended_array[i+2][j+2].setGreen(array[i][j].getGreen());
                extended_array[i+2][j+2].setBlue(array[i][j].getBlue());
            }


        for ( int i = 0; i < 256; i++){
            extended_array[i+2][0].setRed( array[i][0].getRed() );
            extended_array[i+2][0].setGreen( array[i][0].getGreen() );
            extended_array[i+2][0].setBlue( array[i][0].getBlue() );
            extended_array[i+2][1].setRed( array[i][0].getRed() );
            extended_array[i+2][1].setGreen( array[i][0].getGreen() );
            extended_array[i+2][1].setBlue( array[i][0].getBlue() );

            extended_array[i+2][259].setRed( array[i][0].getRed() );
            extended_array[i+2][259].setGreen(array[i][0].getGreen());
            extended_array[i+2][259].setBlue(array[i][0].getBlue());
            extended_array[i+2][258].setRed( array[i][0].getRed() );
            extended_array[i+2][258].setGreen(array[i][0].getGreen());
            extended_array[i+2][258].setBlue(array[i][0].getBlue());

        }
        for ( int j = 0; j < 256; j++){
            extended_array[0][j+2].setRed( array[0][j].getRed() );
            extended_array[0][j+2].setGreen( array[0][j].getGreen() );
            extended_array[0][j+2].setBlue( array[0][j].getBlue() );

            extended_array[1][j+2].setRed( array[0][j].getRed() );
            extended_array[1][j+2].setGreen( array[0][j].getGreen() );
            extended_array[1][j+2].setBlue( array[0][j].getBlue() );

            extended_array[258][j+2].setRed( array[255][j].getRed() );
            extended_array[258][j+2].setGreen( array[255][j].getGreen() );
            extended_array[258][j+2].setBlue( array[255][j].getBlue() );

            extended_array[259][j+2].setRed( array[255][j].getRed() );
            extended_array[259][j+2].setGreen( array[255][j].getGreen() );
            extended_array[259][j+2].setBlue( array[255][j].getBlue() );
        }

        extended_array[0][0].setRed( array[0][0].getRed() );
        extended_array[0][0].setGreen(array[0][0].getGreen());
        extended_array[0][0].setBlue(array[0][0].getBlue());
        extended_array[0][259].setRed(array[0][255].getRed());
        extended_array[0][259].setGreen(array[0][255].getGreen());
        extended_array[0][259].setBlue(array[0][255].getBlue());

        extended_array[259][0].setRed( array[255][0].getRed() );
        extended_array[259][0].setGreen(array[255][0].getGreen());
        extended_array[259][0].setBlue( array[255][0].getBlue() );

        extended_array[259][259].setRed(array[255][255].getRed());
        extended_array[259][259].setGreen( array[255][255].getGreen() );
        extended_array[259][259].setBlue(array[255][255].getBlue());



        extended_array[1][1].setRed(array[0][0].getRed());
        extended_array[1][1].setGreen(array[0][0].getGreen());
        extended_array[1][1].setBlue(array[0][0].getBlue());
        extended_array[1][258].setRed(array[0][255].getRed());
        extended_array[1][258].setGreen(array[0][255].getGreen());
        extended_array[1][258].setBlue(array[0][255].getBlue());

        extended_array[258][1].setRed(array[255][0].getRed());
        extended_array[258][1].setGreen(array[255][0].getGreen());
        extended_array[258][1].setBlue(array[255][0].getBlue());

        extended_array[258][258].setRed(array[255][255].getRed());
        extended_array[258][258].setGreen( array[255][255].getGreen() );
        extended_array[258][258].setBlue(array[255][255].getBlue());
    }
}



