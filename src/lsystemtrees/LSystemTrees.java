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
    
    public JButton pickForegroundColor;
    public Color foregroundColor;
    public JButton pickBackgroundColor;
    public Color backgroundColor;
    public JButton pickBarkColor;
    public Color barkColor = Color.ORANGE;
    public JColorChooser colorChooser;
    
    public JLabel drawingLabel;
    public JScrollPane imageScroll;
    public JPanel mainPanel,imagePanel;
    public JButton newPlantButton;
    public JTextField iterationTF;
    public JLabel iterationLbl;
    public JTextField baseWidthTF;
    public JLabel baseWidthLbl;
    public JButton redoPlantButton;
    public BufferedImage drawingImage;
    public Graphics2D imageGraphics;
    public JButton saveImageButton;
    
    public LSystemPlant plant;
    public double maxStrokeWidth = 20;
    public LSystemTrees(String title){
        super(title);
        fc.addChoosableFileFilter(new FileNameExtensionFilter(".png","png"));
        fc.setAcceptAllFileFilterUsed(false);
        plant = new LSystemPlant();
        initializeSwingComponents();
        initializeLayout();
        
    }
    
    public void initializeSwingComponents(){
        mainPanel = new JPanel();
        drawingImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        imageGraphics = drawingImage.createGraphics();
        drawingLabel = new JLabel(new ImageIcon(drawingImage));
        imagePanel = new JPanel();
        imagePanel.add(drawingLabel);
        
        imageScroll = new JScrollPane(imagePanel);
        imageScroll.setAutoscrolls(true);
        
        newPlantButton = new JButton("Random Plant");
        newPlantButton.addActionListener(this);
        redoPlantButton = new JButton("Redo Plant");
        redoPlantButton.addActionListener(this);
        saveImageButton = new JButton("Save Image");
        saveImageButton.addActionListener(this);
        pickForegroundColor = new JButton("Foreground Color");
        pickForegroundColor.addActionListener(this);
        pickBackgroundColor = new JButton("Background Color");
        pickBackgroundColor.addActionListener(this);
        pickBarkColor = new JButton("Bark Color");
        pickBarkColor.addActionListener(this);
        foregroundColor = Color.WHITE;
        backgroundColor = Color.BLACK;
        pickForegroundColor.setBackground(foregroundColor);
        pickForegroundColor.setBackground(backgroundColor);
        colorChooser = new JColorChooser(foregroundColor);
        
        iterationTF = new JTextField(10);
        iterationTF.setText("5");
        iterationLbl = new JLabel("# of Iterations");
        
        baseWidthTF = new JTextField(10);
        baseWidthTF.setText("10");
        baseWidthLbl = new JLabel("Width of Trunk");
        mainPanel.setOpaque(true);
    }
    public void initializeLayout(){
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.0;
        c.gridwidth = 14;
        c.gridheight = 14;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(imageScroll, c);
        c.gridwidth = 2;
        c.gridheight = 1;
        
        c.weightx = 0.0;
        c.gridx = 15;
        c.gridy = 1;
        mainPanel.add(iterationLbl, c);
        c.gridx = 15;
        c.gridy = 2;
        mainPanel.add(iterationTF, c);
        c.gridx = 15;
        c.gridy = 3;
        mainPanel.add(baseWidthLbl, c);
        c.gridx = 15;
        c.gridy = 4;
        mainPanel.add(baseWidthTF, c);
        c.gridx = 15;
        c.gridy = 5;
        mainPanel.add(redoPlantButton, c);
        c.gridx = 15;
        c.gridy = 6;
        mainPanel.add(pickForegroundColor, c);
        c.gridx = 15;
        c.gridy = 7;
        mainPanel.add(pickBackgroundColor, c);
        c.gridx = 15;
        c.gridy = 8;
        mainPanel.add(pickBarkColor, c);
        c.gridx = 15;
        c.gridy = 9;
        mainPanel.add(saveImageButton, c);
        c.weightx = 0.0;
        c.gridx = 15;
        c.gridy = 10;
        mainPanel.add(newPlantButton, c);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newPlantButton){
            plant = new LSystemPlant();
            iterationTF.setText("5");
            baseWidthTF.setText("10");
            drawPlantOnScreen();
        }
        if(e.getSource() == redoPlantButton){
            plant.grow(Integer.parseInt(iterationTF.getText()));
            plant.parseGenetics();
            maxStrokeWidth = Integer.parseInt(baseWidthTF.getText());
            drawPlantOnScreen();
        }
        if(e.getSource() == pickForegroundColor){
            Color newColor = colorChooser.showDialog(this,"Pick foreground color.",foregroundColor);
            foregroundColor = newColor;
            pickForegroundColor.setBackground(newColor);
        }
        if(e.getSource() == pickBackgroundColor){
            Color newColor = colorChooser.showDialog(this,"Pick foreground color.",backgroundColor);
            backgroundColor = newColor;
            pickBackgroundColor.setBackground(newColor);
        }
        if(e.getSource() == pickBarkColor){
            Color newColor = colorChooser.showDialog(this,"Pick foreground color.",backgroundColor);
            barkColor = newColor;
            pickBarkColor.setBackground(newColor);
        }
        if (e.getSource() == saveImageButton) {
            int returnVal = fc.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File outputFile = fc.getSelectedFile();
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
        int maxBranchesFromRoot = 0;
        int maxBranchesFromTip = 0;
        //get the boundary for the plant
        for(LSystemBranch branch : plant.branches){
            maxBranchesFromRoot = (maxBranchesFromRoot < branch.branchesFromRoot())
                ? branch.branchesFromRoot() : maxBranchesFromRoot ;
            maxBranchesFromTip = (maxBranchesFromTip < branch.branchesFromTip())
                ? branch.branchesFromTip() : maxBranchesFromTip ;
            Line2D.Double line = branch.line();
            if(line.x1 < xL) xL = line.x1;
            if(line.x2 < xL) xL = line.x2;
            if(line.x1 > xR) xR = line.x1;
            if(line.x2 > xR) xR = line.x2;
            if(line.y1 < yT) yT = line.y1;
            if(line.y2 < yT) yT = line.y2;
            if(line.y1 > yB) yB = line.y1;
            if(line.y2 > yB) yB = line.y2;
        }
        
        //reset the image, drawinglabel, and graphics2D.
        Rectangle2D.Double rect = new Rectangle2D.Double(xL,yT,xR - xL,yB - yT);
        drawingImage = new BufferedImage((int)rect.width, (int)rect.height,
                BufferedImage.TYPE_INT_ARGB);
        drawingLabel.removeAll();
        drawingLabel.setIcon(new ImageIcon(drawingImage));
        imagePanel.setPreferredSize(new Dimension(drawingImage.getWidth(),drawingImage.getHeight()));
        imageGraphics = drawingImage.createGraphics();
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        imageGraphics.addRenderingHints(hints);
        
        //draw plant
        imageGraphics.setColor(backgroundColor);
        imageGraphics.fillRect(0, 0, drawingImage.getWidth(), drawingImage.getHeight());
        imageGraphics.setColor(foregroundColor);
        imageGraphics.setStroke(new BasicStroke(2));
        for(LSystemBranch branch : plant.branches){
            Line2D.Double line = branch.line();
            //make stroke taper off evenly towards the end
            double strokeWidth = (0.0 + maxStrokeWidth-1) * (1.0- ((double)branch.branchesFromRoot() / (double)maxBranchesFromRoot))
                    * ((double)branch.branchesFromTip() / (double)maxBranchesFromTip)  + 1;
            //xL and yT are offsets 
            imageGraphics.setStroke(new BasicStroke((int)strokeWidth + 3));
            imageGraphics.setColor(barkColor);
            imageGraphics.drawLine((int)(line.x1-xL), (int)(line.y1-yT ), (int)(line.x2-xL), (int)(line.y2-yT));
            imageGraphics.setStroke(new BasicStroke((int)strokeWidth));
            imageGraphics.setColor(foregroundColor);
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
