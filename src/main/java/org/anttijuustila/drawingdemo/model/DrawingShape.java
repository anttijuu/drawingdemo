package org.anttijuustila.drawingdemo.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

public class DrawingShape {

	private Shape shape;
	private Stroke stroke = new BasicStroke(1);
	private Color lineColor = Color.BLACK;
	private Color fillColor = null;

	public Shape getShape() {
		return shape;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public DrawingShape(
		final Shape shape, 
		final Stroke stroke, 
		final Color lineColor, 
		final Color fillColor
		) {
			this.shape = shape;
			this.stroke = stroke;
			this.lineColor = lineColor;
			this.fillColor = fillColor;
		}

	public void draw(Graphics2D g) {
		Stroke oldStroke = g.getStroke();
		Color oldColor = g.getColor();
		if (fillColor != null) {
			g.setColor(fillColor);
			g.fill(shape);
		}
		g.setStroke(stroke);
		g.setColor(lineColor);
		g.draw(shape);
		g.setColor(oldColor);
		g.setStroke(oldStroke);
	}

}
