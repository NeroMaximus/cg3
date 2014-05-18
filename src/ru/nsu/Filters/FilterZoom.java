package ru.nsu.Filters;

import ru.nsu.MyRGB;

/**
 * Created by nero on 5/12/14.
 */
public class FilterZoom extends Filter {

    static public MyRGB[][] applyFilter( MyRGB[][] array){
        MyRGB[][] new_array;
        int zoomCenterY = 128;
        int zoomCenterX = 128;
        new_array = zoom( array, zoomCenterX, zoomCenterY );

        new_array = FilterGaussianBlur.applyFilter( new_array );
        return new_array;
    }

    private static MyRGB[][] zoom(MyRGB[][] array, int zoomCenterX, int zoomCenterY) {

        if ( zoomCenterX < 63 || zoomCenterX > 191 || zoomCenterY < 63 || zoomCenterY > 191 )
            throw new Error("Bad zoomCenterX or zoomCenterY");

        MyRGB[][] zoomedArray = new MyRGB[256][256];
        for ( int x= 0; x < 256; x++)
            for ( int y = 0; y < 256; y++)
                zoomedArray[x][y] = new MyRGB();

        for ( int i = 0; i < 128; i++)
            for (int j = 0; j< 128; j++) {
                zoomedArray[2*i][2*j].setRed(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getRed());
                zoomedArray[2*i+1][2*j].setRed(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getRed());
                zoomedArray[2*i][2*j+1].setRed(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getRed());
                zoomedArray[2*i+1][2*j+1].setRed(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getRed());

                zoomedArray[2*i][2*j].setGreen(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getGreen());
                zoomedArray[2*i+1][2*j].setGreen(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getGreen());
                zoomedArray[2*i][2*j+1].setGreen(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getGreen());
                zoomedArray[2*i+1][2*j+1].setGreen(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getGreen());

                zoomedArray[2*i][2*j].setBlue(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getBlue());
                zoomedArray[2*i+1][2*j].setBlue(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getBlue());
                zoomedArray[2*i][2*j+1].setBlue(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getBlue());
                zoomedArray[2*i+1][2*j+1].setBlue(array[i+zoomCenterX - 64][j+zoomCenterY - 64].getBlue());
            }

        return zoomedArray;
    }


}
