package org.anttijuustila.drawingdemo;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.anttijuustila.drawingdemo.controller.DrawingDocument;
import org.anttijuustila.drawingdemo.view.DrawingPanel;
import org.anttijuustila.drawingdemo.view.ListPanel;

/**
 * Hello world!
 *
 */
public class DrawingApp 
{
    public static void main( String[] args )
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DrawingApp().launch();
            }
        });        
    }

    private DrawingDocument document = new DrawingDocument();

    protected void launch() {
        JFrame mainFrame = new JFrame("Drawing App");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        Container rootPane = mainFrame.getContentPane();
        rootPane.setLayout(new GridLayout(1,2));
        DrawingPanel drawingPanel = new DrawingPanel(document);
        rootPane.add(drawingPanel, 0,0);

        ListPanel listPanel = new ListPanel(document);
        rootPane.add(listPanel, 0, 1);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        mainFrame.setMinimumSize(new Dimension((screenSize.width / 3), screenSize.height / 3));
        mainFrame.setPreferredSize(new Dimension((screenSize.width/3 * 2), screenSize.height / 3 * 2));
        mainFrame.setSize(new Dimension((screenSize.width/3 * 2), screenSize.height / 3 * 2));
        int top = (screenSize.height / 2) - (mainFrame.getHeight() / 2);
        int left = (screenSize.width / 2) - (mainFrame.getWidth() / 2);
        mainFrame.setLocation(new Point(left, top));

        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.setVisible(true);
    }
}
