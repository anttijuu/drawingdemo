package org.anttijuustila.drawingdemo.view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.anttijuustila.drawingdemo.controller.DocumentObserver;
import org.anttijuustila.drawingdemo.controller.DrawingDocument;

public class StatusPanel extends JPanel implements DocumentObserver {

	private JLabel statusLabel = new JLabel("Lines: 0, Ellipses: 0, Rectangles: 0");
	private DrawingDocument document;

	public StatusPanel(DrawingDocument document) {
		super();
		this.document = document;
		this.document.addObserver(this);
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		add(statusLabel);
	}

	@Override
	public void documentChanged() {
		final DrawingDocument.StatsRecord stats = document.getStats();
		final String text = String.format("Lines: %d, Ellipses: %d, Rectangles: %d", stats.lineCount, stats.ellipseCount, stats.rectangleCount);
		statusLabel.setText(text);
	}

	@Override
	public void selectionChanged(int whichSelected) {
		// Nada
	}
}
