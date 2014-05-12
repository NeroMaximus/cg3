package ru.nsu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * Created by nero on 4/25/14.
 */
public class FirstWindow extends JPanel implements MouseMotionListener {
    int width;
    int height;
    int aspectSides = 0;
    int borders;
    SecondWindow secondWindow = null;
    BufferedImage bufferedImage = null;

    int centerX = 140;
    int centerY = 140;
    int frameLenght;
    public MyRGB[][] array = null;

    FirstWindow(){
        super();
        setSize( new Dimension(256,256) );
        setMaximumSize( new Dimension(256,256));
        setMinimumSize( new Dimension(256,256));
        setBackground( Color.blue );

        addMouseMotionListener(this);
    }



    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        if( array!=null ){
            height = array[0].length;
            width = array.length;
        bufferedImage = new BufferedImage( width, height , BufferedImage.TYPE_INT_RGB);

        for ( int x = 0; x<width; x++)
            for ( int y = 0; y< height; y++ ) {
                bufferedImage.setRGB( x, y, calculateFileSize(array[x][y].getRed(), array[x][y].getGreen(), array[x][y].getBlue()));
            }


        if (bufferedImage != null){
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();

            if ( height > width) {
                aspectSides = 1;
                frameLenght = 256*256/height;
                borders = (256 - 256 * width / height)/2;
                graphics2D.drawImage(bufferedImage, (256 - 256 * width / height) / 2, 0, 256 - (256 - 256 * width / height), 256, null);

            } else if (height < width) {
                aspectSides = 2;
                frameLenght = 256*256/width;
                borders = (256 - 256 * height / width)/2;
                graphics2D.drawImage(bufferedImage, 0, (256 - 256 * height / width) / 2, 256, 256 - (256 - 256 * height / width), null);
                graphics2D.setColor( Color.yellow);
                graphics2D.drawRect( centerX - frameLenght/2, centerY - frameLenght/2, frameLenght, frameLenght);
            } else {
                aspectSides = 0;
                frameLenght = 256*256/width;
                borders = (256 - 256 * width / height)/2;
                graphics2D.drawImage(bufferedImage, 0, 0, 256, 256, null);//(bufferedImage, null, 0, 0);//(bufferedImage, null, 600, 800);
            }
            //repaintSecondWindow();
        }}
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch ( aspectSides ){
            case 1:
                if (e.getX() > borders + frameLenght/2 && e.getX() < 256 - frameLenght/2 - borders) {
                    centerX = e.getX();
                 //   System.out.println("Center at x = "+e.getX()+ "with framel = "+ frameLenght);
                }
                if (e.getY() > frameLenght/2 && e.getY() < 256 - frameLenght/2) {
                    centerY = e.getY();
                }
                break;
            case 2:
                if (e.getX() > frameLenght/2 && e.getX() < 256 - frameLenght/2) {
                    centerX = e.getX();
                }
                if (e.getY() > borders + frameLenght/2 && e.getY() < 256 - frameLenght/2 - borders) {
                    centerY = e.getY();
                }

//                if (e.getX() -frameLenght/2 >= 0 && e.getX() + frameLenght/2 < width &&
//                        e.getY() - frameLenght >=0 && e.getY() + frameLenght/2 < height) {
                    repaint();
                    repaintSecondWindow( centerX - frameLenght/2, centerY - frameLenght/2 );
//                }

                break;
            case 0:
                if (e.getX() > frameLenght/2 && e.getX() < 256 - frameLenght/2) {
                    centerX = e.getX();
              //      System.out.println("Center at x = "+e.getX()+ "with framel = "+ frameLenght);
                }
                if (e.getY() > frameLenght/2 && e.getY() < 256 - frameLenght/2) {
                    centerY = e.getY();
              //      System.out.println("Center at y = "+e.getY()+" with framel = "+ frameLenght);
                }
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    void repaintSecondWindow(int x, int y) {
        if (secondWindow == null)
            return;

        if (array != null){
            MyRGB[][] new_array = getSubArray(array, x, y, 256, 256);
            secondWindow.setArray( new_array );
            secondWindow.repaint();
        }
    }

    private MyRGB[][] getSubArray(MyRGB[][] array, int x, int y, int w, int h) {
        MyRGB[][] subArray = new MyRGB[h][w];

        for ( int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                subArray[i][j] = new MyRGB();
                MyRGB a = array[ i+x*width/256][j+y*height/256];
                subArray[i][j].setRed( a.getRed());
                subArray[i][j].setGreen(a.getGreen());
                subArray[i][j].setBlue( a.getBlue() );
            }
        return subArray;
    }

    private int calculateFileSize(int b, int b1, int b2) {
        return b*65536 + b1*256 + b2;
    }
}
