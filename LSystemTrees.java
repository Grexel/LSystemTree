/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsystemtrees;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jeff
 */
public class LSystemTrees extends JFrame implements ActionListener{
    private static final int IMAGE_WIDTH = 800;
    private static final int IMAGE_HEIGHT = 600;
    final JFileChooser fc = new JFileChooser();
    public JLabel drawingLabel;
    public JScrollPane imageScroll;
    public JPanel mainPanel,imagePanel;
    public JButton newPlantButton;
    public JTextField iterationTF;
    public JLabel iterationLbl;
    public JButton redoPlantButton;
    public BufferedImage drawingImage;
    public Graphics2D imageGraphics;
    public JButton saveImageButton;
    public LSystemPlant plant;
    
    public LSystemTrees(String title){
        super(title);
        fc.addChoosableFileFilter(new FileNameExtensionFilter(".png","png"));
        fc.setAcceptAllFileFilterUsed(false);
       
        
        plant = new LSystemPlant();
        mainPanel = new JPanel();
        drawingImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        imageGraphics = drawingImage.createGraphics();
        drawingLabel = new JLabel(new ImageIcon(drawingImage));
        imagePanel = new JPanel();
        //imagePanel.setPreferredSize(new Dimension(800,600));
        imagePanel.add(drawingLabel);
        
        imageScroll = new JScrollPane(imagePanel);
        //imageScroll.setPreferredSize((new Dimension(800,600)));
        imageScroll.setAutoscrolls(true);
        
        newPlantButton = new JButton("Random Plant");
        newPlantButton.addActionListener(this);
        redoPlantButton = new JButton("Redo Plant");
        redoPlantButton.addActionListener(this);
        saveImageButton = new JButton("Save Image");
        saveImageButton.addActionListener(this);
        iterationTF = new JTextField(10);
        iterationLbl = new JLabel("# of Iterations");
        mainPanel.setOpaque(true);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.0;
        c.gridwidth = 7;
        c.gridheight = 7;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(imageScroll, c);
        c.gridwidth = 1;
        c.gridheight = 1;
        
        c.weightx = 0.0;
        c.gridx = 8;
        c.gridy = 6;
        mainPanel.add(newPlantButton, c);
        c.weightx = 0.0;
        c.gridx = 8;
        c.gridy = 1;
        mainPanel.add(iterationLbl, c);
        c.gridx = 8;
        c.gridy = 2;
        mainPanel.add(iterationTF, c);
        c.gridx = 8;
        c.gridy = 3;
        mainPanel.add(redoPlantButton, c);
        c.gridx = 8;
        c.gridy = 5;
        mainPanel.add(saveImageButton, c);
        
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        imageGraphics.addRenderingHints(hints);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newPlantButton){
            plant = new LSystemPlant();
            //System.out.println(plant.finalGrowth);
            //draw on display
            drawPlantOnScreen();
        }
        if(e.getSource() == redoPlantButton){
            plant.grow(Integer.parseInt(iterationTF.getText()));
            plant.parseGenetics();
            //System.out.println(plant.finalGrowth);
            //draw on display
            drawPlantOnScreen();
        }
        if (e.getSource() == saveImageButton) {
            int returnVal = fc.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File outputFile = fc.getSelectedFile();
                //This is where a real application would open the file.try {
                // retrieve imageFile file = chooser.getSelectedFile();
                String fname = outputFile.getAbsolutePath();

                if(!fname.endsWith(".png") ) {
                    outputFile = new File(fname + ".png");
                }
                try{
                    BufferedImage bi = drawingImage;
                    ImageIO.write(drawingImage, "png", outputFile);
                } catch (IOException p) {
                    System.out.println("Couldnt save the image");
                }
            }
        }
    }
    
    public void drawPlantOnScreen(){
        double xL = 0,xR = 0,yT = 0,yB = 0;
        for(Line2D.Double line : plant.branches){
            if(line.x1 < xL) xL = line.x1;
            if(line.x2 < xL) xL = line.x2;
            if(line.x1 > xR) xR = line.x1;
            if(line.x2 > xR) xR = line.x2;
            if(line.y1 < yT) yT = line.y1;
            if(line.y2 < yT) yT = line.y2;
            if(line.y1 > yB) yB = line.y1;
            if(line.y2 > yB) yB = line.y2;
        }
        Rectangle2D.Double rect = new Rectangle2D.Double(xL,yT,xR - xL,yB - yT);
        drawingImage = new BufferedImage((int)rect.width, (int)rect.height,
                BufferedImage.TYPE_INT_ARGB);
        drawingLabel.removeAll();
        drawingLabel.setIcon(new ImageIcon(drawingImage));
        imagePanel.setPreferredSize(new Dimension(drawingImage.getWidth(),drawingImage.getHeight()));
        imageGraphics = drawingImage.createGraphics();
        imageGraphics.setColor(Color.BLACK);
        imageGraphics.clearRect(0, 0, drawingImage.getWidth(), drawingImage.getHeight());
        imageGraphics.setColor(Color.RED);
        imageGraphics.setStroke(new BasicStroke(2));
        for(Line2D.Double line : plant.branches){
            imageGraphics.drawLine((int)(line.x1-xL), (int)(line.y1-yT ), (int)(line.x2-xL), (int)(line.y2-yT));
        }
        
        repaint();
    }
    private static void createAndShowGUI() {
        //Create and set up the window.
        LSystemTrees frame = new LSystemTrees("L-System Trees");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(frame.mainPanel);

        //Display the window.
        frame.pack();
        frame.setLocation(100, 100);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    
}
