package org.anttijuustila.drawingdemo.controller;

public interface DocumentObserver {
	void documentChanged();
	void selectionChanged(int whichSelected);
}
