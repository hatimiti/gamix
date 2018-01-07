package com.github.hatimiti.gamix.base.gui.swing.support;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * ウィンドウ内領域でマウスドラッグ可能にするListener
 * @author hatimiti
 *
 */
public class WindowMouseDraggableListener
		implements MouseListener, MouseMotionListener {

	private Window frame;
	private Point startDrag;
	private Point startPos;

	public WindowMouseDraggableListener(Window frame) {
		this.frame = frame;

		// リスナー登録
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		startDrag = getScreenLocation(e);
		startPos = frame.getLocation();
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {
		Point cursor = getScreenLocation(e);
		int xdiff = cursor.x - startDrag.x;
		int ydiff = cursor.y - startDrag.y;
		frame.setLocation(startPos.x+xdiff, startPos.y+ydiff);
	}

	private Point getScreenLocation(MouseEvent e) {
		Point p1 = e.getPoint();
		Point p2 = frame.getLocationOnScreen();
		return new Point(p1.x+p2.x, p1.y+p2.y);
	}

}