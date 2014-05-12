package ru.nsu;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nero on 5/3/14.
 */
public class BMPParser {
    BMPImageInfo imageInfo = null;
    BufferedImage bufferedImage = null;
    int offBits;
    int fileSize;
    MyRGB[][] array;
    InputStream inputStream;



    public void parse(InputStream inputStream) {
        this.inputStream = inputStream;
        readBITMAPFILEHEADER();
        readBITMAPINFO();

        array = new MyRGB[imageInfo.getWidth()][imageInfo.getHeight()];

        for ( int i = 0; i < imageInfo.getWidth(); i++)
            for ( int j = 0; j < imageInfo.getHeight(); j++) {
                array[i][j] = new MyRGB();
            }

        readData();
        System.out.println("parser done his job!");
    }


    private void readBITMAPFILEHEADER() {
        int readBytesCount;
        byte[] header = new byte[14];
        try {
            readBytesCount = inputStream.read(header);
            if ( readBytesCount < 14) throw new IOException();//TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
        //1,440,054
        System.out.println("size = " + ( fileSize = calculateFileSize(header[5], header[4], header[3], header[2]) ) );
        System.out.println("offBits = " + (offBits = calculateFileSize(header[13], header[12], header[11], header[10])));
    }


    private void readBITMAPINFO() {
        String structureType = recognizeStructureVersion();

        if (structureType.equals("CORE")){
            //TODO
        } else if (structureType.equals("3")){
            imageInfo = new BMPImageInfoV3();
            imageInfo.setBitMapInfoSize(32);
            readInfoV3();

        } else if (structureType.equals("4")){
            readInfoV3();
            //TODO
        } else if (structureType.equals("5")){
            readInfoV3();
            //TODO
        }
    }

    private void readInfoV3() {
        imageInfo.setFileSize(fileSize);
        imageInfo.setOffBits(offBits);

        int readBytesCount;
        byte[] buffer = new byte[36];
        try {
            readBytesCount = inputStream.read(buffer);
            if ( readBytesCount < 36) throw new IOException();//TODO
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageInfo.setWidth        (calculateFileSize(buffer[3], buffer[2], buffer[1], buffer[0])); //LONG!
        imageInfo.setHeight(calculateFileSize(buffer[7], buffer[6], buffer[5], buffer[4])); //LONG!
        imageInfo.setPlanes(calculateFileSize(buffer[9], buffer[8]));
        imageInfo.setBitCount(calculateFileSize(buffer[11], buffer[10]));
        imageInfo.setCompression(calculateFileSize(buffer[15], buffer[14], buffer[13], buffer[12]));
        imageInfo.setSizeImage(calculateFileSize(buffer[19], buffer[18], buffer[17], buffer[16]));
        imageInfo.setXPelsPerMeter(calculateFileSize(buffer[23], buffer[22], buffer[21], buffer[20])); //LONG!
        imageInfo.setYPelsPerMeter(calculateFileSize(buffer[27], buffer[26], buffer[25], buffer[24])); //LONG!
        imageInfo.setClrUsed(calculateFileSize(buffer[31], buffer[30], buffer[29], buffer[28]));
        imageInfo.setClrImportant(calculateFileSize(buffer[35], buffer[34], buffer[33], buffer[32]));
        System.out.println("bitcount = "+ imageInfo.getBitCount());
    }

    private String recognizeStructureVersion() {
        int readBytesCount;
        int structureSize;
        byte[] header = new byte[4];
        try {
            readBytesCount = inputStream.read(header);
            if ( readBytesCount < 4) throw new IOException();//TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("размер структуры с информационными полями:"+(structureSize = calculateFileSize(header[3], header[2], header[1], header[0])));

        switch (structureSize){
            case 12:
                return "CORE";
            case 40:
                return "3";
            case 108:
                return "4";
            case 124:
                return "5";
            default:
                throw new RuntimeException("Undefined struct type");//TODO
        }
    }








    private void readData() {
        byte[] buffer = new byte[3];//TODO: do larger
        int readBytesCount;
        int x = -1, y = imageInfo.getHeight() - 1;

        while (true){
            try {
                readBytesCount = inputStream.read(buffer);
                if (readBytesCount == -1) {
                    break;
                }
                x++;
                if (x >= imageInfo.getWidth()){
                    x = 0;
                    y--;
                    if (y < 0)
                        break;
                }
                array[x][y].setRed( buffer[2] );// System.out.print( buffer[2]);
                array[x][y].setGreen( buffer[1] );// System.out.print( " "+buffer[1]);
                array[x][y].setBlue( buffer[0] );// System.out.println(" "+buffer[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int calculateFileSize(byte b, byte b1, byte b2) {
        return b*65536 + b1*256 + b2;
    }


    private int calculateFileSize(byte b, byte b1, byte b2, byte b3) {
        int size = b*16777216 + b1*65536 + b2*256 + b3;
        return size;
    }
    private int calculateFileSize(byte b, byte b1) {
        int size = b*256 + b1;
        return size;
    }

    public MyRGB[][] getArray() {
        return array;
    }
}
