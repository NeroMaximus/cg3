package ru.nsu.Filters.SobelOperator;

import ru.nsu.Filters.BlackAndWhite.FilterBlackAndWhite;
import ru.nsu.Filters.OrderedDithering.FilterOrderedDithering;
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
 * Created by nero on 5/10/14.
 */
public class SOOptionPanel extends JFrame implements ChangeListener {
    private int lower_limit = 0;
    private int upper_limit = 200;
    private int init_limit = 90;
    private int current_value = init_limit;
    private boolean inversion = false;
    private ThirdWindow thirdWindow;
    private JSlider slider;
    private Preview preview;
    private MyRGB[][] array = null;
    private JPanel okAndCancel;
    private JButton apply;
    private JButton cancel;
    private ThirdWindow tw;
    private JFrame me;

    public SOOptionPanel(Component component, MyRGB[][] ar, ThirdWindow thirdWindow){
        super("Options");
        setMinimumSize( new Dimension( 400, 400));
        setMaximumSize(new Dimension(400, 400));
        setLocationRelativeTo(component);
        setLayout( new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        me = this;
        apply = new JButton("Apply");
        apply.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tw.setArray( FilterSobelOperator.applyFilter(array, current_value));
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
        slider.addChangeListener(this);
        add(preview);
        add(slider);
        add(okAndCancel);
        setVisible(true);
        pack();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()){
            current_value = source.getValue();
            preview.repaint();

        }

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
            System.out.println("repaint "+current_value);
            super.paintComponent(graphics);

            Graphics2D graphics2D = (Graphics2D) graphics;

            new_array = FilterSobelOperator.applyFilter(array, current_value);

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
