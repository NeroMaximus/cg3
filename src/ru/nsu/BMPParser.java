package ru.nsu;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nero on 5/3/14.
 */
public class BMPParser {
    BMPImageInfo imageInfo = null;
    MyRGB[][] array;
    InputStream inputStream;
    int offBits;
    int fileSize;
    byte[] header;
    byte[] header2;


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
    }


    private void readBITMAPFILEHEADER() {
        int readBytesCount;
        header = new byte[14];

        try {
            readBytesCount = inputStream.read(header);
            if ( readBytesCount < 14) throw new IOException();//TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (header[1] != 77 || header[0] != 66) {
            throw new Error("NBF");//TODO
        }

        System.out.println("size = " + ( fileSize = assembleNumber(header[5], header[4], header[3], header[2]) ) );
        System.out.println("offBits = " + (offBits = assembleNumber(header[13], header[12], header[11], header[10])));
    }


    private void readBITMAPINFO() {
        String structureType = recognizeStructureVersion();

        if (structureType.equals("CORE")){
            //TODO
        } else if (structureType.equals("3")){
            imageInfo = new BMPImageInfoV3();
            imageInfo.addBytes(header);
            imageInfo.addBytes( header2 );
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

        byte[] new_bytes = buffer.clone();
        new_bytes[3] = 0;
        new_bytes[2] = 0;
        new_bytes[1] = 1;
        new_bytes[0] = 0;

        new_bytes[7] = 0;
        new_bytes[6] = 0;
        new_bytes[5] = 1;
        new_bytes[4] = 0;
        imageInfo.addBytes( new_bytes );

        imageInfo.setWidth        (assembleNumber(buffer[3], buffer[2], buffer[1], buffer[0])); //LONG!
        imageInfo.setHeight(assembleNumber(buffer[7], buffer[6], buffer[5], buffer[4]));
        imageInfo.setPlanes(calculateFileSize(buffer[9], buffer[8]));
        imageInfo.setBitCount(calculateFileSize(buffer[11], buffer[10]));
        imageInfo.setCompression(assembleNumber(buffer[15], buffer[14], buffer[13], buffer[12]));
        imageInfo.setSizeImage(assembleNumber(buffer[19], buffer[18], buffer[17], buffer[16]));
        imageInfo.setXPelsPerMeter(assembleNumber(buffer[23], buffer[22], buffer[21], buffer[20])); //LONG!
        imageInfo.setYPelsPerMeter(assembleNumber(buffer[27], buffer[26], buffer[25], buffer[24])); //LONG!
        imageInfo.setClrUsed(assembleNumber(buffer[31], buffer[30], buffer[29], buffer[28]));
        imageInfo.setClrImportant(assembleNumber(buffer[35], buffer[34], buffer[33], buffer[32]));
        System.out.println("bitcount = "+ imageInfo.getBitCount());
    }

    private String recognizeStructureVersion() {
        int readBytesCount;
        int structureSize;
        header2 = new byte[4];
        try {
            readBytesCount = inputStream.read(header2);
            if ( readBytesCount < 4) throw new IOException();//TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
        structureSize = assembleNumber(header2[3], header2[2], header2[1], header2[0]);

        switch (structureSize){
            case 12:
                System.out.println("Type of infostructure: CORE(12)");
                return "CORE";
            case 40:
                System.out.println("Type of infostructure: 3(40)");
                return "3";
            case 108:
                System.out.println("Type of infostructure: 4(108)");
                return "4";
            case 124:
                System.out.println("Type of infostructure: 5(124)");
                return "5";
            default:
                throw new RuntimeException("Undefined struct type");//TODO
        }
    }




    private void readData() {
        byte[] buffer = new byte[3];//TODO: do larger
        int readBytesCount;

        int x = 0, y = imageInfo.getHeight() - 1;
        while (true){
            try {
                readBytesCount = inputStream.read(buffer);
                if (readBytesCount == -1) {
                    break;
                }
                if (readBytesCount < 3){
                    //TODO
                }

                array[x][y].setRed( buffer[2] );// System.out.print( buffer[2]);
                array[x][y].setGreen( buffer[1] );// System.out.print( " "+buffer[1]);
                array[x][y].setBlue( buffer[0] );// System.out.println(" "+buffer[0]);

                x++;
                if (x >= imageInfo.getWidth()){
                    byte[] garbage = new byte[calculateRest()];
                    inputStream.read( garbage );
                    x = 0;
                    y--;
                    if (y < 0)
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int calculateRest() {
        return (4 - imageInfo.getWidth() * 3 % 4) % 4;
    }


    private int assembleNumber(int b, int b1, int b2, int b3) {
        int size = 0;

        if (b < 0)
            size += (256+b)*16777216;
        else
            size += b*16777216;

        if (b1 < 0)
            size += (256+b1)*65536;
        else
            size += b1*65536;

        if (b2 < 0)
            size += (256+b2)*256;
        else
            size += b2*256;

        if (b3 < 0)
            size += 256+b3;
        else
            size += b3;


        return size;
    }
    private int calculateFileSize(int b, int b1) {
        int size = 0;

        if (b < 0)
            size += (256+b)*256;
        else
            size += b*256;

        if (b1 < 0)
            size += 256+b1;
        else
            size += b1;

        return size;
    }

    public MyRGB[][] getArray() {
        return array;
    }

    public BMPImageInfo getImageInfo() {
        return imageInfo;
    }
}
