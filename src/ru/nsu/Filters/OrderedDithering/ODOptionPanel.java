package ru.nsu.Filters.OrderedDithering;

import ru.nsu.MyRGB;
import ru.nsu.ThirdWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by nero on 5/11/14.
 */
public class ODOptionPanel extends JFrame {
    private int lower_limit = 0;
    private int upper_limit = 64;
    private int init_limit = 3;
    private int red_value = init_limit;
    private int green_value = init_limit;
    private int blue_value = init_limit;

    private boolean inversion = false;
    private ThirdWindow thirdWindow;
    private JSlider slider;
    private JSlider sliderRed;
    private JSlider sliderGreen;
    private JSlider sliderBlue;
    private Preview preview;
    private MyRGB[][] array = null;
    private JPanel okAndCancel;
    private JButton apply;
    private JButton cancel;
    private ThirdWindow tw;
    private JFrame me;

    public ODOptionPanel(Component component, MyRGB[][] ar, ThirdWindow thirdWindow){
        super("Options");
        setMinimumSize( new Dimension( 400, 500));
        setMaximumSize(new Dimension(400, 500));
        setLocationRelativeTo(component);
        setLayout( new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        me = this;
        apply = new JButton("Apply");
        apply.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tw.setArray( FilterOrderedDithering.applyFilter(array, red_value, green_value, blue_value ));
                tw.repaint();
                me.dispose();
            }
        });
        cancel = new JButton("Cancel");
        cancel.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                me.dispose();
            }
        });
        this.array = ar;
        this.tw = thirdWindow;
        preview = new Preview();
        okAndCancel = new JPanel();
        okAndCancel.setMaximumSize( new Dimension(200,50));
        okAndCancel.setLayout( new GridLayout(1,2));
        okAndCancel.add( apply );
        okAndCancel.add( cancel );


        slider = new JSlider( JSlider.HORIZONTAL, lower_limit, upper_limit, init_limit);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(50);
        slider.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()){
                    red_value = source.getValue();
                    green_value = red_value;
                    blue_value = red_value;
                    sliderRed.setValue( red_value );
                    sliderGreen.setValue( green_value );
                    sliderBlue.setValue( blue_value );
//            System.out.println(source.);
                    preview.repaint();

                }
            }
        });

        sliderRed = new JSlider( JSlider.HORIZONTAL, lower_limit, upper_limit, init_limit);
        sliderRed.setPaintTicks(true);
        sliderRed.setPaintLabels(true);
        sliderRed.setMajorTickSpacing(50);
        sliderRed.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()){
                    red_value = source.getValue();
//            System.out.println(source.);
                    preview.repaint();

                }
            }
        });

        sliderGreen = new JSlider( JSlider.HORIZONTAL, lower_limit, upper_limit, init_limit);
        sliderGreen.setPaintTicks(true);
        sliderGreen.setPaintLabels(true);
        sliderGreen.setMajorTickSpacing(50);
        sliderGreen.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()){
                    green_value = source.getValue();
//            System.out.println(source.);
                    preview.repaint();

                }

            }
        });

        sliderBlue = new JSlider( JSlider.HORIZONTAL, lower_limit, upper_limit, init_limit);
        sliderBlue.setPaintTicks(true);
        sliderBlue.setPaintLabels(true);
        sliderBlue.setMajorTickSpacing(50);
        sliderBlue.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()){
                    blue_value = source.getValue();
//            System.out.println(source.);
                    preview.repaint();
                }
            }
        });

        add(preview);
        add(new JLabel("BITS BER COLOR"));
        add(new JLabel("Red:"));
        add(sliderRed);
        add(new JLabel("Green:"));
        add(sliderGreen);
        add(new JLabel("Blue"));
        add(sliderBlue);
        add(new JLabel("RGB:"));
        add(slider);

        add(okAndCancel);
        setVisible(true);
        pack();
    }


    class Preview extends JPanel{

        MyRGB[][] new_array;
        BufferedImage bufferedImage = new BufferedImage(256,256, BufferedImage.TYPE_INT_RGB);

        Preview(){
            super();
            setSize( new Dimension(256,256) );
            setMaximumSize( new Dimension(256,256));
            setMinimumSize( new Dimension(256,256));
            setBackground( Color.black );
        }

        public void paintComponent(Graphics graphics){
            super.paintComponent(graphics);

            Graphics2D graphics2D = (Graphics2D) graphics;

            new_array = FilterOrderedDithering.applyFilter( array, red_value, green_value, blue_value);

            for ( int j = 0; j < 256; j++)
                for ( int i = 0; i < 256; i++){
                    bufferedImage.setRGB(  i, j, assembleRGB( new_array[i][j].getRed(), new_array[i][j].getGreen(), new_array[i][j].getBlue()));
                }

            graphics2D.drawImage(bufferedImage, 0, 0, 256, 256, null);
        }

        int assembleRGB( int red, int green, int blue) {
            return red*65536 + green*256 + blue;
        }
    }

}
