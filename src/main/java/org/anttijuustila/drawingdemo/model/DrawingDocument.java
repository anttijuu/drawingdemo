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
import java.util.concurrent.ThreadLocalRandom;

public class DrawingDocument {
	
	enum ToolType {
		LINE,
		RECTANGLE,
		ELLIPSE,
	}
	final ToolType [] tools = { ToolType.LINE, ToolType.RECTANGLE, ToolType.ELLIPSE };
	final Color [] randomColors = { Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE };
	final int [] randomLineWidths = { 1, 2, 3, 4, 5, 6 };

	private List<DrawingShape> shapes = new ArrayList<>();
	private ToolType currentTool = ToolType.LINE;

	private int currentLineWidth = 1;
	private Color currentColor = Color.BLACK;
	private Color currentFillColor = null;

	private Point2D.Float origin = null;
	private Point2D.Float firstPoint = null;
	private Point2D.Float secondPoint = null;

	public List<DrawingShape> getShapes() {
		return shapes;
	}

	public DrawingShape getCurrentShape() {
		return currentShape;
	}

	private DrawingShape currentShape = null;
	
	public void handleMouseDown(final Point point) {
		firstPoint = new Point2D.Float(point.x, point.y);
		origin = firstPoint;
		secondPoint = new Point2D.Float(point.x, point.y);
		currentShape = createShape();
	}

	public void handleMouseMove(final Point point) {
		secondPoint = new Point2D.Float(point.x, point.y);
		currentShape = createShape();
	}

	public void handleMouseUp(final Point point) {
		secondPoint = new Point2D.Float(point.x, point.y);
		// if (currentTool != ToolType.LINE) {
		// 	normalizePoints();
		// }
		addShape(createShape());
		currentShape = null;
		firstPoint = null;
		secondPoint = null;
		origin = null;
		randomizeDrawing();
	}

	private void randomizeDrawing() {
		currentTool = tools[ThreadLocalRandom.current().nextInt(tools.length)];
		currentLineWidth = randomLineWidths[ThreadLocalRandom.current().nextInt(randomLineWidths.length)];
		currentColor = randomColors[ThreadLocalRandom.current().nextInt(randomColors.length)];
		currentFillColor = randomColors[ThreadLocalRandom.current().nextInt(randomColors.length)];
	}

	private void addShape(final DrawingShape shape) {
		shapes.add(shape);
	}

	public DrawingShape createShape() {
		if (firstPoint == null || secondPoint == null) {
			return null;
		}
		Shape shape = shapeFromToolType();
		if (shape != null) {
			DrawingShape newShape = new DrawingShape(shape, new BasicStroke(currentLineWidth), currentColor, currentFillColor);
			return newShape;	
		}
		return null;
	}

	private Shape shapeFromToolType() {
		normalizePoints();
		switch (currentTool) {
			case ELLIPSE:
				return new Ellipse2D.Float(firstPoint.x, firstPoint.y, Math.abs(secondPoint.x-firstPoint.x), Math.abs(secondPoint.y-firstPoint.y));
			case LINE:
				return new Line2D.Float(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);				
			case RECTANGLE:
				return new Rectangle2D.Float(firstPoint.x, firstPoint.y, Math.abs(secondPoint.x-firstPoint.x), Math.abs(secondPoint.y-firstPoint.y));
			default:
				return null;
		}
	}

	private void normalizePoints() {
		if (currentTool != ToolType.LINE && (secondPoint.x < origin.x || secondPoint.y < origin.y)) {
			if (secondPoint.x < origin.x && secondPoint.y < origin.y) {
				firstPoint = secondPoint;
				secondPoint = origin;
			} else {
				firstPoint = new Point2D.Float(Math.min(origin.x, secondPoint.x), Math.min(origin.y, secondPoint.y));
				secondPoint = new Point2D.Float(Math.max(origin.x, secondPoint.x), Math.max(origin.y, secondPoint.y));
			}
		}
	}

}
