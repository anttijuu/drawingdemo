package org.anttijuustila.drawingdemo;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.anttijuustila.drawingdemo.model.DrawingDocument;
import org.anttijuustila.drawingdemo.view.DrawingPanel;

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
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container rootPane = mainFrame.getContentPane();
        DrawingPanel drawingPanel = new DrawingPanel(document);
        rootPane.add(drawingPanel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        mainFrame.setPreferredSize(new Dimension((screenSize.width/2), screenSize.height / 2));
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
