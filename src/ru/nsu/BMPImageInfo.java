package ru.nsu;

/**
 * Created by nero on 5/4/14.
 */


//version CORE
public class BMPImageInfo {
    private  int offBits;
    private int fileSize;
    int height;
    int bitCount;
    int planes;
    private int bitMapInfoSize;
    private int width;
    private int compression;
    private int sizeImage;
    private int XPelsPerMeter;
    private int YPelsPerMeter;
    private int clrUsed;
    private int clrImportant;

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setOffBits(int offBits) {
        this.offBits = offBits;
    }

    public int getOffBits() {
        return offBits;
    }

    public void setBitMapInfoSize(int bitMapInfoSize) {
        this.bitMapInfoSize = bitMapInfoSize;
    }

    public int getBitMapInfoSize() {
        return bitMapInfoSize;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setBitCount(int bitCount) {
        this.bitCount = bitCount;
    }

    public int getBitCount() {
        return bitCount;
    }

    public void setPlanes(int planes) {
        this.planes = planes;
    }

    public int getPlanes() {
        return planes;
    }

    public void setCompression(int compression) {
        this.compression = compression;
    }

    public int getCompression() {
        return compression;
    }

    public void setSizeImage(int sizeImage) {
        this.sizeImage = sizeImage;
    }

    public int getSizeImage() {
        return sizeImage;
    }

    public void setXPelsPerMeter(int XPelsPerMeter) {
        this.XPelsPerMeter = XPelsPerMeter;
    }

    public int getXPelsPerMeter() {
        return XPelsPerMeter;
    }

    public void setYPelsPerMeter(int YPelsPerMeter) {
        this.YPelsPerMeter = YPelsPerMeter;
    }

    public int getYPelsPerMeter() {
        return YPelsPerMeter;
    }

    public void setClrUsed(int clrUsed) {
        this.clrUsed = clrUsed;
    }

    public int getClrUsed() {
        return clrUsed;
    }

    public void setClrImportant(int clrImportant) {
        this.clrImportant = clrImportant;
    }

    public int getClrImportant() {
        return clrImportant;
    }
}
