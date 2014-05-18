package ru.nsu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by nero on 5/18/14.
 */
public class Saver {
    public static void save(File selectedFile, MyRGB[][] array, BMPImageInfo imageInfo) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(selectedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            out.write( imageInfo.getBytes() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        for ( int j = 255; j >=0 ; j--)
        for (int i = 0; i <= 255; i++){
                try {
                    out.write( (byte)array[i][j].getBlue() );
                    out.write( (byte)array[i][j].getGreen() );
                    out.write( (byte)array[i][j].getRed() );
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
