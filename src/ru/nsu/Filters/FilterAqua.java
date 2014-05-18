package ru.nsu.Filters;

import ru.nsu.MyRGB;
import sun.security.x509.AVA;

import java.math.BigInteger;

/**
 * Created by nero on 5/12/14.
 */
public class FilterAqua extends Filter {

    static public MyRGB[][] applyFilter( MyRGB[][] array){
        MyRGB[][] new_array;

        MyRGB[][] extended_array = new MyRGB[258][258];
        for ( int x= 0; x < 258; x++)
            for ( int y = 0; y < 258; y++)
                extended_array[x][y] = new MyRGB();

        FilterBlur.fillExtendedArray(extended_array, array);
        new_array = median( extended_array );

        FilterBlur.fillExtendedArray(extended_array, new_array);
        definition( extended_array, new_array);

        return new_array;
    }

    private static void definition(MyRGB[][] arrayForDefinition, MyRGB[][] array) {
        int new_red;
        int new_green;
        int new_blue;

        for ( int x= 1; x < 257; x++)
            for ( int y = 1; y < 257; y++) {
                new_red =  - arrayForDefinition[x-1][y-1].getRed() - arrayForDefinition[x][y-1].getRed()   - arrayForDefinition[x+1][y-1].getRed()
                           - arrayForDefinition[x-1][y].getRed()   + 9*arrayForDefinition[x][y].getRed() - arrayForDefinition[x+1][y].getRed()
                           - arrayForDefinition[x-1][y+1].getRed() - arrayForDefinition[x][y+1].getRed()   - arrayForDefinition[x+1][y+1].getRed();
                new_red /= 1;

                new_green = - arrayForDefinition[x-1][y-1].getGreen() - arrayForDefinition[x][y-1].getGreen() - arrayForDefinition[x+1][y-1].getGreen()
                            - arrayForDefinition[x-1][y].getGreen()   +9*arrayForDefinition[x][y].getGreen() - arrayForDefinition[x+1][y].getGreen()
                            - arrayForDefinition[x-1][y+1].getGreen() - arrayForDefinition[x][y+1].getGreen() - arrayForDefinition[x+1][y+1].getGreen();
                new_green /= 1;

                new_blue =  - arrayForDefinition[x-1][y-1].getBlue() - arrayForDefinition[x][y-1].getBlue() - arrayForDefinition[x+1][y-1].getBlue()
                            - arrayForDefinition[x-1][y].getBlue()   + 9*arrayForDefinition[x][y].getBlue() - arrayForDefinition[x+1][y].getBlue()
                            - arrayForDefinition[x-1][y+1].getBlue() - arrayForDefinition[x][y+1].getBlue() - arrayForDefinition[x+1][y+1].getBlue();
                new_blue /= 1;



                if ( new_red < 0)
                    new_red = 0;
                if(new_red > 255){
                    new_red = 255;
                }

                if ( new_green < 0)
                    new_green = 0;
                if(new_green > 255){
                    new_green = 255;
                }

                if ( new_blue < 0)
                    new_blue = 0;
                if(new_blue > 255){
                    new_blue = 255;
                }
                array[x-1][y-1].setRed(new_red);
                array[x-1][y-1].setGreen(new_green);
                array[x-1][y-1].setBlue(new_blue);
            }
    }

    private static MyRGB[][] median(MyRGB[][] new_array) {
        int new_red;
        int new_green;
        int new_blue;

        MyRGB[][] w_array = new MyRGB[256][256];
        for ( int x= 0; x < 256; x++)
            for ( int y = 0; y < 256; y++)
                w_array[x][y] = new MyRGB();



        for ( int x= 1; x < 257; x++)
            for ( int y = 1; y < 257; y++) {
                new_red = getAv( new int[]{new_array[x-1][y-1].getRed(), new_array[x][y-1].getRed(), new_array[x+1][y-1].getRed(),
                        new_array[x-1][y].getRed(), new_array[x][y].getRed(), new_array[x+1][y].getRed(),
                        new_array[x-1][y+1].getRed(), new_array[x][y+1].getRed(), new_array[x+1][y+1].getRed()}, 9);

                new_green = getAv( new int[]{new_array[x-1][y-1].getGreen(), new_array[x][y-1].getGreen(), new_array[x+1][y-1].getGreen(),
                        new_array[x-1][y].getGreen(), new_array[x][y].getGreen(), new_array[x+1][y].getGreen(),
                        new_array[x-1][y+1].getGreen(), new_array[x][y+1].getGreen(), new_array[x+1][y+1].getGreen()},9);


                new_blue = getAv( new int[]{new_array[x-1][y-1].getBlue(), new_array[x][y-1].getBlue(), new_array[x+1][y-1].getBlue(),
                        new_array[x-1][y].getBlue(), new_array[x][y].getBlue(), new_array[x+1][y].getBlue(),
                        new_array[x-1][y+1].getBlue(), new_array[x][y+1].getBlue(), new_array[x+1][y+1].getBlue()}, 9);

                w_array[x-1][y-1].setRed(new_red);
                w_array[x-1][y-1].setGreen(new_green);
                w_array[x-1][y-1].setBlue(new_blue);
            }

        return w_array;
    }

    private static int getAv( int[] a, int n) {
        int f;
        int min;
        int temp;

        for (int j = 0; j < n; j++)
        {
            f = 0;
            min = j;
            for(int i = j; i < (n - j - 1); i++)
            {
                if (a[i] > a[i + 1])
                {
                    temp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = temp;
                    f = 1;
                }
                if (a[i] < a[min])
                    min = i;
            }
            if (f == 0)
                break;
            if (min != j)
            {
                temp = a[j];
                a[j] = a[min];
                a[min] = temp;
            }
        }

        return a[n/2];
    }


    private static int MyRound(double value) {
        int int_value;
        if (value%1 < 0.5) int_value = (int) value;
        else int_value = (int) (value + 1);

        return int_value;
    }

}
