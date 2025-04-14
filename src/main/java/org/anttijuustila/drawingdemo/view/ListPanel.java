package org.anttijuustila.drawingdemo.view;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.anttijuustila.drawingdemo.controller.DocumentObserver;
import org.anttijuustila.drawingdemo.controller.DrawingDocument;
import org.anttijuustila.drawingdemo.model.DrawingShape;

public class ListPanel extends JPanel implements DocumentObserver, ListSelectionListener, ListDataListener, KeyListener {

	private JList<DrawingShape> elementList;
	private DrawingDocument document;

	public ListPanel(final DrawingDocument document) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		DrawingElementListModel model = new DrawingElementListModel(document);
		elementList = new JList<>(model);		
		elementList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.document = document;
		elementList.addListSelectionListener(this);
		add(new JScrollPane(elementList));
		setFocusable(true);
		addKeyListener(this);
	}

	@Override
	public void documentChanged() {
		elementList.repaint();
	}

	private boolean selectionOriginatedFromHere = false;

	@Override
	public void selectionChanged(int whichSelected) {
		if (!selectionOriginatedFromHere) {
			elementList.setSelectedIndex(whichSelected);
		}
		selectionOriginatedFromHere = false;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			selectionOriginatedFromHere = true;
			document.setSelectedShape(elementList.getSelectedIndex());
			requestFocusInWindow();
		}
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
			if (elementList.getSelectedIndex() >= 0 && elementList.getSelectedIndex() < document.size()) {
				document.removeShape(elementList.getSelectedIndex());
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			int nextIndex = Math.max(elementList.getSelectedIndex() - 1, 0);
			elementList.setSelectedIndex(nextIndex);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			int nextIndex = Math.min(elementList.getSelectedIndex() + 1, document.size() - 1);
			elementList.setSelectedIndex(nextIndex);
		}
	}

}
