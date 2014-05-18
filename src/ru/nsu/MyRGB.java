package ru.nsu;

/**
 * Created by nero on 5/10/14.
 */
public class MyRGB {

    int red;
    int green;
    int blue;

    public MyRGB() {
    }

    public MyRGB(MyRGB myRGB) {
        this.red = myRGB.getRed();
        this.green = myRGB.getGreen();
        this.blue = myRGB.getBlue();
    }

    public void setRed(int red) {
        if (red >= 0)
            this.red = red;
        else
            this.red = 256 + red;
    }
    public void setBlue(int blue) {
        if (blue >= 0)
            this.blue = blue;
        else
            this.blue = 256 + blue;
    }
    public void setGreen(int green) {
        if (green >= 0)
            this.green = green;
        else
            this.green = 256 + green;
    }

    public int getRed() { return red; }
    public int getBlue() {
        return blue;
    }
    public int getGreen() { return green; }

}
