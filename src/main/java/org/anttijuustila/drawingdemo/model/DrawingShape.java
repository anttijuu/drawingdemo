package org.anttijuustila.drawingdemo.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

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

	public void draw(Graphics2D g, boolean drawAsSelected) {
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
		if (drawAsSelected) {
			PathIterator iterator = shape.getPathIterator(null);
			float coords [] = new float[6];
			while (!iterator.isDone()) {
				iterator.currentSegment(coords);
				g.fillRect((int)coords[0]- 4, (int)coords[1] - 4, 8, 8);
				iterator.next();
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (shape.getClass() == Ellipse2D.Float.class) {
			builder.append("Ellipse");
		} else if (shape.getClass() == Rectangle2D.Float.class) {
			builder.append("Rectangle");
		} else if (shape.getClass() == Line2D.Float.class) {
			builder.append("Line");
		} else {
			builder.append(shape.getClass().toString());
		}
		builder.append( " with line color ");
		builder.append(lineColor.toString());
		if (fillColor != null) {
			builder.append(" and ");
			builder.append(fillColor.toString());
			builder.append(" fill color");
		}
		return builder.toString();
	}

}
