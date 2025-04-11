package org.anttijuustila.drawingdemo.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DrawingShape implements Shape {

	private Shape shape;
	private Stroke stroke = new BasicStroke(1);
	private Color lineColor = Color.BLACK;
	private Color fillColor = null;

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
		g.setStroke(stroke);
		if (fillColor != null) {
			g.setColor(fillColor);
			g.fill(shape);
		} else {
			g.setColor(lineColor);
			g.draw(shape);
		}
		g.setColor(oldColor);
		g.setStroke(oldStroke);
	}
	
	@Override
	public Rectangle getBounds() {
		return shape.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return shape.getBounds2D();
	}

	@Override
	public boolean contains(double x, double y) {
		return shape.contains(x, y);
	}

	@Override
	public boolean contains(Point2D p) {
		return shape.contains(p);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return shape.intersects(x, y, w, h);
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		return intersects(r);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		return shape.contains(x, y, w, h);
	}

	@Override
	public boolean contains(Rectangle2D r) {
		return shape.contains(r);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return shape.getPathIterator(at);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return shape.getPathIterator(at, flatness);
	}

}
