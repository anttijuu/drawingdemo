package org.anttijuustila.drawingdemo.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class DrawingDocument {
	
	enum ToolType {
		SELECTION,
		POINT,
		LINE,
		RECTANGLE,
		ELLIPSE,
	}

	private List<DrawingShape> shapes = new ArrayList<>();
	private Shape selectedShape = null;
	private ToolType currentTool = ToolType.LINE;

	private int currentLineWidth = 1;
	private Color currentColor = Color.BLACK;
	private Color currentFillColor = null;

	private Point2D.Float start = null;
	private Point2D.Float end = null;

	public List<DrawingShape> getShapes() {
		return shapes;
	}

	private DrawingShape currentShape = null;
	
	public void handleMouseDown(final Point point) {
		start = new Point2D.Float(point.x, point.y);
	}

	public void handleMouseUp(final Point point) {
		end = new Point2D.Float(point.x, point.y);
		if (end.x < start.x) {
			float tmp = start.x;
			start.x = end.x;
			end.x = tmp;
		}
		if (end.y < start.y) {
			float tmp = start.y;
			start.y = end.y;
			end.y = tmp;
		}
		selectedShape = null;
		for (final DrawingShape shape : shapes) {
			if (shape.contains(point)) {
				selectedShape = shape;
				break;
			}
		}
		if (selectedShape == null) {
			addShape(createShape());
		}
		start = null;
		end = null;
	}

	private void addShape(final DrawingShape shape) {
		shapes.add(0, shape);
	}

	public DrawingShape createShape() {
		if (start == null || end == null) {
			return null;
		}
		// Normalize the coordinates so that start is always
		// the upper left coordinate.
		Shape shape = shapeFromToolType();
		if (shape != null) {
			DrawingShape newShape = new DrawingShape(shape, new BasicStroke(currentLineWidth), currentColor, currentFillColor);
			return newShape;	
		}
		return null;
	}

	private Shape shapeFromToolType() {
		switch (currentTool) {
			case SELECTION:
				return null;
			case POINT:
				return new Ellipse2D.Float(start.x, start.y, currentLineWidth, currentLineWidth);
			case ELLIPSE:
				return new Ellipse2D.Float(start.x, start.y, end.x-start.x, end.y-start.y);
			case LINE:
				return new Line2D.Float(start.x, start.y, end.x, end.y);				
			case RECTANGLE:
				return new Rectangle2D.Float(start.x, start.y, end.x-start.x, end.y-start.y);
		}
		return null;
	}

}
