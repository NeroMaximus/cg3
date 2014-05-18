package ru.nsu.Filters;

import ru.nsu.MyRGB;

/**
 * Created by nero on 5/12/14.
 */
public class FilterStamp extends Filter {
        public static MyRGB[][] applyFilter(MyRGB[][] array){
            MyRGB[][] new_array = new MyRGB[256][256];
            for ( int x= 0; x < 256; x++)
                for ( int y = 0; y < 256; y++)
                    new_array[x][y] = new MyRGB();

            MyRGB[][] extended_array = new MyRGB[258][258];
            for ( int x= 0; x < 258; x++)
                for ( int y = 0; y < 258; y++)
                    extended_array[x][y] = new MyRGB();

            fillExtendedArray( extended_array, array );


            for ( int i = 1; i < 257; i++)
                for (int j = 1; j< 257; j++) {
                    double new_red = extended_array[i][j-1].getRed()
                            - extended_array[i-1][j].getRed() + extended_array[i+1][j].getRed()
                            - extended_array[i][j+1].getRed();

                    double new_green = extended_array[i][j-1].getGreen()
                            - extended_array[i-1][j].getGreen() + extended_array[i+1][j].getGreen()
                            - extended_array[i][j+1].getGreen();

                    double new_blue = extended_array[i][j-1].getBlue()
                            - extended_array[i-1][j].getBlue() + extended_array[i+1][j].getBlue()
                            - extended_array[i][j+1].getBlue();

                    new_red += 128;
                    new_green += 128;
                    new_blue += 128;

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


                    new_array[i-1][j-1].setRed(MyRound(new_red));
                    new_array[i-1][j-1].setGreen(MyRound(new_green));
                    new_array[i-1][j-1].setBlue(MyRound(new_blue));
                }
            return new_array;
        }

        private static int MyRound(double value) {
            int int_value;
            if (value%1 < 0.5) int_value = (int) value;
            else int_value = (int) (value + 1);

            return int_value;
        }

        public static void fillExtendedArray(MyRGB[][] extended_array, MyRGB[][] array) {
            for ( int i = 0; i < 256; i++)
                for (int j = 0; j < 256; j++) {
                    extended_array[i+1][j+1].setRed(array[i][j].getRed());
                    extended_array[i+1][j+1].setGreen(array[i][j].getGreen());
                    extended_array[i+1][j+1].setBlue(array[i][j].getBlue());
                }


            for ( int i = 0; i < 256; i++){
                extended_array[i+1][0].setRed( array[i][0].getRed() );
                extended_array[i+1][0].setGreen( array[i][0].getGreen() );
                extended_array[i+1][0].setBlue( array[i][0].getBlue() );
                extended_array[i+1][257].setRed( array[i][0].getRed() );
                extended_array[i+1][257].setGreen(array[i][0].getGreen());
                extended_array[i+1][257].setBlue(array[i][0].getBlue());
            }
            for ( int j = 0; j < 256; j++){
                extended_array[0][j+1].setRed( array[0][j].getRed() );
                extended_array[0][j+1].setGreen( array[0][j].getGreen() );
                extended_array[0][j+1].setBlue( array[0][j].getBlue() );
                extended_array[257][j+1].setRed( array[255][j].getRed() );
                extended_array[257][j+1].setGreen( array[255][j].getGreen() );
                extended_array[257][j+1].setBlue( array[255][j].getBlue() );
            }
            extended_array[0][0].setRed( array[0][0].getRed() );
            extended_array[0][0].setGreen(array[0][0].getGreen());
            extended_array[0][0].setBlue(array[0][0].getBlue());
            extended_array[0][257].setRed(array[0][255].getRed());
            extended_array[0][257].setGreen(array[0][255].getGreen());
            extended_array[0][257].setBlue(array[0][255].getBlue());

            extended_array[257][0].setRed( array[255][0].getRed() );
            extended_array[257][0].setGreen(array[255][0].getGreen());
            extended_array[257][0].setBlue( array[255][0].getBlue() );

            extended_array[257][257].setRed(array[255][255].getRed());
            extended_array[257][257].setGreen( array[255][255].getGreen() );
            extended_array[257][257].setBlue(array[255][255].getBlue());
        }
    }

