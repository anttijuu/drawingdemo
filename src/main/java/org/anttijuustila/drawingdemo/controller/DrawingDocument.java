package org.anttijuustila.drawingdemo.controller;

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

import org.anttijuustila.drawingdemo.model.DrawingShape;

public class DrawingDocument {
	
	public class StatsRecord {
		public int lineCount = 0;
		public int rectangleCount = 0;
		public int ellipseCount = 0;
	}

	enum ToolType {
		LINE,
		RECTANGLE,
		ELLIPSE,
	}
	final ToolType [] tools = { ToolType.LINE, ToolType.RECTANGLE, ToolType.ELLIPSE };
	final Color [] randomColors = { Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE };
	final int [] randomLineWidths = { 1, 2, 3, 4, 5, 6 };

	private List<DrawingShape> shapes = new ArrayList<>();
	private StatsRecord stats = new StatsRecord();

	private ToolType currentTool = ToolType.LINE;

	private int currentLineWidth = 1;
	private Color currentColor = Color.BLACK;
	private Color currentFillColor = null;

	private Point2D.Float origin = null;
	private Point2D.Float firstPoint = null;
	private Point2D.Float secondPoint = null;

	private DrawingShape currentlyDrawnShape = null;
	private int currentlySelectedIndex = -1;

	private List<DocumentObserver> observers = new ArrayList<>();

	public void addObserver(final DocumentObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void removeObserver(final DocumentObserver observer) {
		observers.remove(observer);
	}

	enum ChangeEvent {
		CONTAINER_CHANGED,
		SELECTION_CHANGED,
	}

	private void notifyObservers(ChangeEvent event, int parameter) {
		switch (event) {
			case CONTAINER_CHANGED:
				for (final DocumentObserver observer : observers) {
					observer.documentChanged();
				}
				break;
			case SELECTION_CHANGED:
				for (final DocumentObserver observer : observers) {
					observer.selectionChanged(parameter);
				}
				break;
		}
	}

	public List<DrawingShape> getShapes() {
		return shapes;
	}

	public StatsRecord getStats() {
		return stats;
	}

	public DrawingShape getCurrentlyDrawnShape() {
		return currentlyDrawnShape;
	}
	
	public void handleMouseDown(final Point point) {
		firstPoint = new Point2D.Float(point.x, point.y);
		origin = firstPoint;
		secondPoint = new Point2D.Float(point.x, point.y);
		currentlyDrawnShape = createShape();
	}

	public void handleMouseMove(final Point point) {
		secondPoint = new Point2D.Float(point.x, point.y);
		currentlyDrawnShape = createShape();
	}

	public void handleMouseUp(final Point point) {
		secondPoint = new Point2D.Float(point.x, point.y);
		addShape(createShape());
		currentlyDrawnShape = null;
		firstPoint = null;
		secondPoint = null;
		origin = null;
		randomizeDrawing();
		notifyObservers(ChangeEvent.CONTAINER_CHANGED, shapes.size() - 1);
	}

	private void randomizeDrawing() {
		currentTool = tools[ThreadLocalRandom.current().nextInt(tools.length)];
		currentLineWidth = randomLineWidths[ThreadLocalRandom.current().nextInt(randomLineWidths.length)];
		currentColor = randomColors[ThreadLocalRandom.current().nextInt(randomColors.length)];
		currentFillColor = randomColors[ThreadLocalRandom.current().nextInt(randomColors.length)];
	}

	private void addShape(final DrawingShape shape) {
		shapes.add(shape);
		switch (currentTool) {
			case ELLIPSE:
				stats.ellipseCount++;
				break;
			case LINE:
				stats.lineCount++;
				break;
			case RECTANGLE:
				stats.rectangleCount++;
				break;
			default:
				break;
		}
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
		// Normalizes the points so that firstPoint is always to up and left of
		// the second point, otherwise the drawing is not correctly done.
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

	public int size() {
		return shapes.size();
	}

	public DrawingShape get(int index) {
		return shapes.get(index);
	}

	public void setSelectedShape(int whichSelected) {
		currentlySelectedIndex = whichSelected;
		notifyObservers(ChangeEvent.SELECTION_CHANGED, currentlySelectedIndex);
	}

	public void removeShape(int selectedIndex) {
		final DrawingShape toRemove = shapes.get(selectedIndex);
		final Shape shape = toRemove.getShape();
		if (shape instanceof Line2D) {
			stats.lineCount--;
		} else if (shape instanceof Ellipse2D) {
			stats.ellipseCount--;
		} else if (shape instanceof Rectangle2D) {
			stats.rectangleCount--;
		}
		shapes.remove(selectedIndex);
		notifyObservers(ChangeEvent.CONTAINER_CHANGED, selectedIndex);
	}

}
