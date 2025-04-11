package org.anttijuustila.drawingdemo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import org.anttijuustila.drawingdemo.model.DrawingDocument;
import org.anttijuustila.drawingdemo.model.DrawingShape;

public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

	private DrawingDocument document;

	private boolean isDrawing = false;

	public DrawingPanel(final DrawingDocument document) {
		super();
		setBackground(Color.WHITE);
		this.document = document;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {        
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		for (final DrawingShape shape : document.getShapes()) {
			shape.draw(g2);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		isDrawing = true;
		document.handleMouseDown(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isDrawing) {			
			isDrawing = false;
			document.handleMouseUp(e.getPoint());
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (isDrawing) {
			Graphics2D g = (Graphics2D)getGraphics();
			DrawingShape shape = document.getCurrentShape();
			if (shape != null) {		
				g.setXORMode(getBackground());
				shape.draw(g);
				document.handleMouseMove(e.getPoint());
				shape = document.getCurrentShape();
				shape.draw(g);
			}
			g.dispose();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1200, 1000);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(800, 600);
	}

	
}
