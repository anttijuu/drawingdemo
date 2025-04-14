package org.anttijuustila.drawingdemo.view;

import javax.swing.AbstractListModel;

import org.anttijuustila.drawingdemo.controller.DocumentObserver;
import org.anttijuustila.drawingdemo.controller.DrawingDocument;
import org.anttijuustila.drawingdemo.model.DrawingShape;

public class DrawingElementListModel extends AbstractListModel<DrawingShape> implements DocumentObserver {

	private DrawingDocument document;

	public DrawingElementListModel(final DrawingDocument document) {
		this.document = document;
		document.addObserver(this);
	}

	@Override
	public int getSize() {
		return document.size();
	}

	@Override
	public DrawingShape getElementAt(int index) {
		return document.get(index);
	}

	@Override
	public void documentChanged() {
		this.fireContentsChanged(this, 0, getSize());
	}

	@Override
	public void selectionChanged(int whichSelected) {
		// empty
	}

}
