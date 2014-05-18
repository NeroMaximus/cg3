package ru.nsu.Filters;

import ru.nsu.MyRGB;

import java.awt.image.BufferedImage;

/**
 * Created by nero on 5/10/14.
 */
public abstract class Filter {
    public static MyRGB[][] applyFilter(MyRGB[][] array, int red_value, int green_value, int blue_value){
        return null;
    }

    public static MyRGB[][] strollByMatrix( MyRGB[][] array, double[][] matrix ){
        MyRGB[][] extended_array;
        MyRGB[][] new_array = new MyRGB[256][256];
        for ( int x= 0; x < 256; x++)
            for ( int y = 0; y < 256; y++)
                new_array[x][y] = new MyRGB();

        int diameter = matrix[0].length-1;
        int semidiameter = diameter/2;
        double div = 0;
        for (int i = 0; i < diameter+1; i++)
            for ( int j = 0; j < diameter+1; j++)
                div += matrix[i][j];

        System.out.println("DIV="+div);


        extended_array = fillExtendedArray( array, diameter+array[0].length );


        for ( int i = 0; i < 256; i++)
            for (int j = 0; j< 256; j++) {

                double new_red = 0;
                double new_green = 0;
                double new_blue = 0;

                for ( int a = -semidiameter; a < semidiameter + 1; a++)
                    for ( int b = -semidiameter; b < semidiameter + 1; b++) {
                        new_red += matrix[semidiameter+a][semidiameter+b] * extended_array[i+a+semidiameter][j+b+semidiameter].getRed();
                        new_green += matrix[semidiameter+a][semidiameter+b] * extended_array[i+a+semidiameter][j+b+semidiameter].getGreen();
                        new_blue += matrix[semidiameter+a][semidiameter+b] * extended_array[i+a+semidiameter][j+b+semidiameter].getBlue();
                    }

                new_red /= div;
                new_green /= div;
                new_blue /= div;

       //         System.out.println(new_red+" "+new_green+" "+new_blue);

                new_array[i][j].setRed((int) new_red);
                new_array[i][j].setGreen((int) new_green);
                new_array[i][j].setBlue((int) new_blue);
            }
        return new_array;
    }


    private static MyRGB[][] fillExtendedArray( MyRGB[][] array, int diameter) {
        MyRGB[][] extended_array = new MyRGB[diameter][diameter];
        for ( int x= 0; x < diameter; x++)
            for ( int y = 0; y < diameter; y++)
                extended_array[x][y] = new MyRGB();

        int additional_semidiameter = (diameter -256)/2;
        System.out.println("AS="+additional_semidiameter);


        for ( int i = 0; i < 256; i++)
            for (int j = 0; j < 256; j++) {
                extended_array[i+additional_semidiameter][j+additional_semidiameter].setRed(array[i][j].getRed());
                extended_array[i+additional_semidiameter][j+additional_semidiameter].setGreen(array[i][j].getGreen());
                extended_array[i+additional_semidiameter][j+additional_semidiameter].setBlue(array[i][j].getBlue());
            }


        for ( int i = 0; i < 256; i++){
            for (int k = 0; k < additional_semidiameter; k++){
                extended_array[i+additional_semidiameter][k].setRed( array[i][0].getRed() );
                extended_array[i+additional_semidiameter][k].setGreen( array[i][0].getGreen() );
                extended_array[i+additional_semidiameter][k].setBlue( array[i][0].getBlue() );

                extended_array[i+additional_semidiameter][diameter-1-k].setRed(array[i][0].getRed());
                extended_array[i+additional_semidiameter][diameter-1-k].setGreen(array[i][0].getGreen());
                extended_array[i+additional_semidiameter][diameter-1-k].setBlue(array[i][0].getBlue());


                extended_array[k][i+additional_semidiameter].setRed( array[0][i].getRed() );
                extended_array[k][i+additional_semidiameter].setGreen( array[0][i].getGreen() );
                extended_array[k][i+additional_semidiameter].setBlue( array[0][i].getBlue() );

                extended_array[diameter-1-k][i+additional_semidiameter].setRed( array[0][i].getRed() );
                extended_array[diameter-1-k][i+additional_semidiameter].setGreen( array[0][i].getGreen() );
                extended_array[diameter-1-k][i+additional_semidiameter].setBlue( array[0][i].getBlue() );
            }
        }


        for ( int i = 0; i < additional_semidiameter; i++)
            for ( int j = 0; j < additional_semidiameter; j++){
                extended_array[i][j].setRed(array[0][0].getRed());
                extended_array[i][j].setGreen(array[0][0].getGreen());
                extended_array[i][j].setBlue(array[0][0].getBlue());

                extended_array[diameter-1-i][j].setRed( array[255][0].getRed() );
                extended_array[diameter-1-i][j].setGreen(array[255][0].getGreen());
                extended_array[diameter-1-i][j].setBlue( array[255][0].getBlue() );

                extended_array[i][diameter-1-j].setRed( array[0][255].getRed() );
                extended_array[i][diameter-1-j].setGreen(array[0][255].getGreen());
                extended_array[i][diameter-1-j].setBlue(array[0][255].getBlue());

                extended_array[diameter-1-i][diameter-1-j].setRed( array[255][255].getRed() );
                extended_array[diameter-1-i][diameter-1-j].setGreen(array[255][255].getGreen());
                extended_array[diameter-1-i][diameter-1-j].setBlue( array[255][255].getBlue() );
            }


        return extended_array;
    }
}
