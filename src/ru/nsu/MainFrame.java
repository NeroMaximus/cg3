package ru.nsu;

import ru.nsu.Filters.*;
import ru.nsu.Filters.BlackAndWhite.BnWOptionPanel;
import ru.nsu.Filters.FloydSteinberg.FSOptionPanel;
import ru.nsu.Filters.FloydSteinberg.FilterFloydSteinbergDithering;
import ru.nsu.Filters.OrderedDithering.ODOptionPanel;
import ru.nsu.Filters.RobertsOperator.ROOptionPanel;
import ru.nsu.Filters.SobelOperator.SOOptionPanel;

import javax.jnlp.FileContents;
import javax.jnlp.FileSaveService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by nero on 4/20/14.
 */
public class MainFrame extends JFrame implements ActionListener {
    private final MainFrame mainFrame;
    File file;
    InputStream inputStream;
    BMPParser parser;
    SecondWindow secondWindow = new SecondWindow();
    FirstWindow firstWindow  = new FirstWindow();
    ThirdWindow thirdWindow = new ThirdWindow();
    JButton saveButton;


    public MainFrame(String title, BMPParser parser) {
        super(title);
        this.parser = parser;
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 500));

        setSize(new Dimension(800, 400));

        add( getMyTriplePane());

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        mainFrame = this;

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
        JMenuBar menuBar = new JMenuBar();
        menuBar.setMinimumSize( new Dimension(60, 100));
        JPanel thirdPane = new JPanel();
        saveButton = new JButton("save");
        saveButton.addActionListener( this );
//        thirdPane.setLayout( new BoxLayout( thirdPane , BoxLayout.Y_AXIS));
        thirdPane.setLayout(new BorderLayout());

        menuBar.add(saveButton);

        thirdPane.add(menuBar, BorderLayout.NORTH);
        JPanel nested_pane = new JPanel();
        nested_pane.setLayout(new BoxLayout(nested_pane, BoxLayout.Y_AXIS));
        nested_pane.add(thirdWindow);
        thirdPane.add(nested_pane, BorderLayout.CENTER);

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
        JMenuItem graysclare = new JMenuItem("Graysclare");
        JMenuItem gaussian_blur = new JMenuItem("gaussian blur");
        JMenuItem black_and_white = new JMenuItem("black and white");
        JMenuItem watercolor = new JMenuItem("watercolor");
        JMenuItem roberts_operator = new JMenuItem("Roberts operator");
        JMenuItem sobel_operator = new JMenuItem("Sobel operator");
        JMenuItem stamp = new JMenuItem("stamp");
        JMenuItem zoom = new JMenuItem("2x zoom");
        JMenuItem orderedDithering = new JMenuItem("ordered dithering");
        JMenuItem floydSteinberg_dithering = new JMenuItem("Floyd-Steinberg dithering");

        orderedDithering.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ODOptionPanel odOptionPanel = new ODOptionPanel(mainFrame, secondWindow.getArray(), thirdWindow);
            }
        });

        zoom.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thirdWindow.setArray(FilterZoom.applyFilter(secondWindow.getArray()));
                thirdWindow.repaint();
            }
        });

        watercolor.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thirdWindow.setArray(FilterAqua.applyFilter(secondWindow.getArray()));
                thirdWindow.repaint();
            }
        });


        stamp.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thirdWindow.setArray(FilterStamp.applyFilter(secondWindow.getArray()));
                thirdWindow.repaint();
            }
        });


        sobel_operator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SOOptionPanel optionPanel = new SOOptionPanel(mainFrame, secondWindow.getArray(), thirdWindow);
            }
        });

        roberts_operator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ROOptionPanel optionPanel = new ROOptionPanel(mainFrame, secondWindow.getArray(), thirdWindow);
            }
        });

        floydSteinberg_dithering.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FSOptionPanel odOptionPanel = new FSOptionPanel(mainFrame, secondWindow.getArray(), thirdWindow);
            }
        });

        graysclare.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thirdWindow.setArray(FilterGrayscale.applyFilter(secondWindow.getArray()));
                thirdWindow.repaint();
            }
        });

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
                thirdWindow.setArray(FilterNegative.applyFilter(secondWindow.getArray()));
                thirdWindow.repaint();
            }
        });
        black_and_white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BnWOptionPanel optionPanel = new BnWOptionPanel(mainFrame, secondWindow.getArray(), thirdWindow);
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
        applyFilter.add(graysclare);
        applyFilter.add(roberts_operator);
        applyFilter.add(sobel_operator);
        applyFilter.add(stamp);
        applyFilter.add(floydSteinberg_dithering);
        applyFilter.add(zoom);
        applyFilter.add(orderedDithering);

        menuBar.add(applyFilter);
        secondPane.add(menuBar, BorderLayout.NORTH);
        nested_pane.setLayout(new BoxLayout(nested_pane, BoxLayout.Y_AXIS));
        nested_pane.add(secondWindow);
        secondPane.add( nested_pane, BorderLayout.CENTER );
        firstWindow.setSecondWindow(secondWindow);
        return secondPane;
    }


    public Component getFirstPane() {
        final JPanel firstPane = new JPanel();
        firstPane.setLayout( new BoxLayout( firstPane , BoxLayout.Y_AXIS));
        JButton load = new JButton("load image");
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
                            firstWindow.setArray( parser.getArray());
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            final JFileChooser c = new JFileChooser();
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(mainFrame);
            if (rVal == JFileChooser.APPROVE_OPTION) {
//
//                new Thread( new Runnable() {
//                    @Override
//                    public void run() {
                        Saver.save(c.getSelectedFile(), thirdWindow.getArray(), parser.getImageInfo());
//                    }
//                }).run();
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {

            }
//            FileSaveService fss = null;
//            FileContents fileContents = null;
//            ByteArrayInputStream is = new ByteArrayInputStream(
//                    (new String("Saved by JWSFileChooserDemo").getBytes()));
//            //XXX YIKES! If they select an
//            //XXX existing file, this will
//            //XXX overwrite that file.
//            System.out.println("action performed");
//
//
//            try {
//                fss = (FileSaveService) ServiceManager.
//                        lookup("");
//            } catch (UnavailableServiceException exc) { }
//
//            if (fss != null) {
//                try {
//                    fileContents = fss.saveFileDialog(null,
//                            null,
//                            is,
//                            "FILENAME.txt");
//                } catch (Exception exc) {}
//            }
//
//            if (fileContents != null) {
//            } else {
//            }
        }
    }
}
