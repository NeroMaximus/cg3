package ru.nsu;

import ru.nsu.Filters.FilterBlackAndWhite;
import ru.nsu.Filters.FilterBlur;
import ru.nsu.Filters.FilterGaussianBlur;
import ru.nsu.Filters.FilterNegative;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by nero on 4/20/14.
 */
public class MainFrame extends JFrame{
    File file;
    InputStream inputStream;
    BMPParser parser;
    SecondWindow secondWindow = new SecondWindow();
    FirstWindow firstWindow  = new FirstWindow();
    ThirdWindow thirdWindow = new ThirdWindow();



    public MainFrame(String title, BMPParser parser) {
        super(title);
        this.parser = parser;
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 400));
        setMaximumSize(new Dimension(800, 400));

        setSize(new Dimension(800, 400));

        add( getMyTriplePane());

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel getMyTriplePane() {
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(0, 3);
        panel.setLayout(layout);
        panel.add(getFirstPane());
        panel.add(getSecondPane());
        panel.add( getThirdPane() );
        return panel;
    }

    private Component getThirdPane() {
        JPanel thirdPane = new JPanel();
        thirdPane.setLayout( new BoxLayout( thirdPane , BoxLayout.Y_AXIS));

        thirdPane.setBackground( Color.cyan );
        thirdPane.add(new JButton("Crop"));
        thirdPane.add( thirdWindow );

        secondWindow.thirdWindow = thirdWindow;

        return thirdPane;
    }

    private Component getSecondPane() {
        JPanel secondPane = new JPanel();
        JMenuBar menuBar = new JMenuBar();
        JPanel nested_pane = new JPanel();
        JMenu applyFilter = new JMenu("apply filter");
        JMenuItem negative = new JMenuItem("negative");
        JMenuItem matrix_blur = new JMenuItem("blur");
        JMenuItem gaussian_blur = new JMenuItem("gaussian blur");
        JMenuItem black_and_white = new JMenuItem("black and white");
        JMenuItem watercolor = new JMenuItem("watercolor");

        gaussian_blur.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thirdWindow.setArray(FilterGaussianBlur.applyFilter(secondWindow.getArray()));
                thirdWindow.repaint();
            }
        });


        negative.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("NEGATIVE!");
                thirdWindow.setArray(FilterNegative.applyFilter(secondWindow.getArray()));
                thirdWindow.repaint();
            }
        });
        black_and_white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int threshold = 0;//TODO
                boolean inversion = false;//TODO
                thirdWindow.setArray(FilterBlackAndWhite.applyFilter( secondWindow.getArray(), threshold, inversion));
                thirdWindow.repaint();
            }
        });
        matrix_blur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thirdWindow.setArray( FilterBlur.applyFilter( secondWindow.getArray()) );
                thirdWindow.repaint();
            }
        });


        secondPane.setLayout( new BorderLayout());//( new BoxLayout( secondPane , BoxLayout.Y_AXIS));

        secondPane.setBackground( Color.white);
        applyFilter.add(negative);
        applyFilter.add(matrix_blur);
        applyFilter.add(black_and_white);
        applyFilter.add(watercolor);
        applyFilter.add(gaussian_blur);

        menuBar.add(applyFilter);
        secondPane.add(menuBar, BorderLayout.NORTH);
        nested_pane.setLayout(new BoxLayout(nested_pane, BoxLayout.Y_AXIS));
        nested_pane.add(secondWindow);
        secondPane.add( nested_pane, BorderLayout.CENTER );
        firstWindow.secondWindow = secondWindow;
        return secondPane;
    }


    public Component getFirstPane() {
        final JPanel firstPane = new JPanel();
        firstPane.setLayout( new BoxLayout( firstPane , BoxLayout.Y_AXIS));
        JButton load = new JButton("Load");
        load.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog( firstPane );
                if (returnVal == JFileChooser.APPROVE_OPTION){
                    file = fc.getSelectedFile();
                    try {
                        inputStream = new FileInputStream(file);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

                    new Thread( new Runnable() {
                        @Override
                        public void run() {
                            parser.parse(inputStream);
                            firstWindow.array = parser.getArray();
                            System.out.println("DONE!");
                        }
                    }).run();
                firstWindow.repaint();
                }
            }
        });

        firstPane.add( load );
        firstPane.add( firstWindow );

        return firstPane;
    }


    private static void createAndShowGUI() {
        BMPParser parser = new BMPParser();
        MainFrame mainFrame = new MainFrame("ru/nsu/Filters", parser);
      //  mainFrame.setLocationRelativeTo(null);
    }

    public static void main( String[] args){
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
