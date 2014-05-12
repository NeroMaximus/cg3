package ru.nsu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nero on 4/25/14.
 */
public class SecondWindow extends JPanel {
    BufferedImage bufferedImage = null;
    public ThirdWindow thirdWindow = null;
    private MyRGB[][] array = null;
    private int width;
    private int height;

    SecondWindow(){
        super();
        setSize( new Dimension(256,256) );
        setMaximumSize( new Dimension(256,256));
        setMinimumSize( new Dimension(256,256));
        setBackground( Color.green );
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        if(array!=null){
            height = array[0].length;
            width = array.length;
            bufferedImage = new BufferedImage( width, height , BufferedImage.TYPE_INT_RGB);

            for ( int x = 0; x<width; x++)
                for ( int y = 0; y< height; y++ ) {
                    bufferedImage.setRGB( x, y, calculateFileSize(array[x][y].getRed(), array[x][y].getGreen(), array[x][y].getBlue()));
                }

            graphics2D.drawImage(bufferedImage, 0, 0, 256, 256, null);
            thirdWindow.repaint();
        }
    }

    private int calculateFileSize(int red, int green, int blue) {
        return red*65536 + green*256 + blue;
    }

    public void setArray(MyRGB[][] array) {
        this.array = array;
    }

    public MyRGB[][] getArray() {
        return array;
    }
}
