package ru.nsu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nero on 4/25/14.
 */
public class ThirdWindow extends JPanel {

    MyRGB[][] array = null;
    BufferedImage bufferedImage = new BufferedImage(256,256, BufferedImage.TYPE_INT_RGB);

    public ThirdWindow(){
        super();
        setSize( new Dimension(256,256) );
        setMaximumSize( new Dimension(256,256));
        setMinimumSize( new Dimension(256,256));
        setBackground( Color.red );
    }

    public void paintComponent(Graphics graphics){
        if (array == null)
            return;

        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        for ( int j = 0; j < 256; j++)
            for ( int i = 0; i < 256; i++){
                bufferedImage.setRGB( i, j, assembleRGB( array[i][j].getRed(), array[i][j].getGreen(), array[i][j].getBlue()));
            }

        graphics2D.drawImage(bufferedImage, 0, 0, 256, 256, null);
    }

    static int assembleRGB( int red, int green, int blue) {
        return red*65536 + green*256 + blue;
    }

    public void setArray(MyRGB[][] array) {
        this.array = array;
    }

    public MyRGB[][] getArray() {
        return array;
    }
}
