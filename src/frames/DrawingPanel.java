package frames;

import global.Constants.ETools;
import global.Constants.ETransformationStyle;
import shapes.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;
import shapes.TAnchors.EAnchors;
import transformer.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class DrawingPanel extends JPanel {
	//attribute
	private static final long serialVersionUID = 1L;

	// component
	private Vector<TShape> shapes;

	// associated attribute
	private ETools selectedTool;
	private TShape selectedShape;
	private TShape currentShape;
	private Transformer transformer;

	// working variable
	private boolean updated;
	private enum EDrawingState{
		eIdle,
		e2PointDrawing,
		eNPointDrawing,
		eMoving,
		eRotating, eMultipleSelection;
	}
	private EDrawingState eDrawingState;
	private Image bufferImg;
	private Graphics2D bufferG;

	public DrawingPanel() {
		// attribute
		this.setBackground(Color.WHITE);
		this.eDrawingState = EDrawingState.eIdle;
		this.updated = false;

		//component
		this.shapes = new Vector<TShape>();

		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
	}

	public boolean isUpdated() {
		return this.updated;
	}
	public void setUpdated(boolean updated){
		this.updated = updated;
	}

	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes){
		this.shapes = (Vector<TShape>) shapes;
		this.repaint();
	}

	public Object getShapes(){
		return this.shapes;
	}

	public void setSelectedTool(ETools selectedTool){
		this.selectedTool = selectedTool;
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		for(TShape shape : this.shapes){
			shape.draw((Graphics2D)graphics);
		}
	}

	public void clearShapes(){
		this.shapes.clear();
		this.currentShape = null;
		this.repaint();
	}

	private void prepareDrawingBuffer(int x, int y){
		Graphics2D graphics2D = (Graphics2D)this.getGraphics();
		bufferImg = createImage(WIDTH, HEIGHT);
		bufferG = (Graphics2D) bufferImg.getGraphics();
		bufferG.setXORMode(this.getBackground());
		this.transformer.prepareTransforming(x, y, bufferG);
	}

	private void keepDrawingBuffer(int x, int y){
		if(!(this.transformer instanceof Drawer)){
			repaint();
		}
		Graphics2D graphics2D = (Graphics2D)this.getGraphics();
		this.transformer.keepTransforming(x, y, bufferG);
		graphics2D.drawImage(bufferImg, 0, 0, null);
	}

	private void prepareTransforming(int x, int y){
		if(selectedTool == ETools.eSelection){
			currentShape = onShape(x, y);
			if(currentShape != null){
				EAnchors eAnchor = currentShape.getSelectedAnchor();
				if(eAnchor == EAnchors.eMove){
					this.transformer = new Mover(this.currentShape);
				} else if(eAnchor == EAnchors.eRR){
					this.transformer = new Rotater(this.currentShape);
				} else{
					this.transformer = new Resizer(this.currentShape);
				}
			} else{
				this.currentShape = this.selectedTool.newShape();
				this.transformer = new Drawer(this.currentShape);
			}
		} else {
			this.currentShape = this.selectedTool.newShape();
			this.transformer = new Drawer(this.currentShape);
		}

		Graphics2D graphics2D = (Graphics2D)this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.prepareDrawingBuffer(x, y);
	}

	private void keepTransforming(int x, int y){
		Graphics2D graphics2D = (Graphics2D)this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		// erase
		this.transformer.keepTransforming(x, y, graphics2D);
		this.keepDrawingBuffer(x, y);
	}

	private void finishTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D)this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		if (this.selectedShape!=null) {
			this.selectedShape.setSelected(false);
		}

		if (!(this.currentShape instanceof TSelection)) {
			this.transformer.finishTransforming(x, y, graphics2D);
			this.shapes.add(this.currentShape);
			this.selectedShape = this.currentShape;
			this.selectedShape.setSelected(true);
//			this.selectedShape.draw((Graphics2D) this.getGraphics());
			this.setUpdated(true);
		}
		repaint();
	}

	private void continueTransforming(int x, int y){
		Graphics2D graphics2D = (Graphics2D)this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.transformer.continueTransforming(x, y, graphics2D);

	}
	// 좌표가 도형 위에 위치하고 있는지 검사하는 메서드
	private TShape onShape(int x, int y){
		for(TShape shape : this.shapes){
			if(shape.contains(x, y)){
				return shape;
			}
		}
		return null;
	}

	private void changeSelection(int x, int y) {
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		this.repaint();
		// draw anchors
		this.selectedShape = this.onShape(x, y);
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(true);
			this.selectedShape.draw((Graphics2D) this.getGraphics());
		}
	}

	private void changeCursor(int x, int y) {
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);

		if(this.selectedTool == ETools.eSelection){
			cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			this.currentShape = this.onShape(x, y);

			if(this.currentShape != null){
				cursor = new Cursor(Cursor.MOVE_CURSOR);
				if(this.currentShape.isSelected()){
					EAnchors eAnchors = this.currentShape.getSelectedAnchor();
					switch (eAnchors){
						case eNW: cursor = new Cursor(Cursor.NW_RESIZE_CURSOR); break;
						case eWW: cursor = new Cursor(Cursor.W_RESIZE_CURSOR); break;
						case eSW: cursor = new Cursor(Cursor.SW_RESIZE_CURSOR); break;
						case eSS: cursor = new Cursor(Cursor.S_RESIZE_CURSOR); break;
						case eSE: cursor = new Cursor(Cursor.SE_RESIZE_CURSOR); break;
						case eEE: cursor = new Cursor(Cursor.E_RESIZE_CURSOR); break;
						case eNE: cursor = new Cursor(Cursor.NE_RESIZE_CURSOR); break;
						case eNN: cursor = new Cursor(Cursor.N_RESIZE_CURSOR); break;
						case eRR: cursor = new Cursor(Cursor.HAND_CURSOR);; break;
						default: break;
					}
				}
			}
		}
		this.setCursor(cursor);
	}

	private class MouseHandler implements MouseInputListener, MouseMotionListener, MouseWheelListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1){
				if(e.getClickCount() == 1){
					lButtonClick(e);
				}else if(e.getClickCount() == 2){
					lButton2Click(e);
				}
			}
		}
		private void lButtonClick(MouseEvent e) {
			if(eDrawingState == EDrawingState.eIdle){
				changeSelection(e.getX(), e.getY());
				if (selectedTool == ETools.ePolygon) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointDrawing;
				}
			} else if(eDrawingState == EDrawingState.eNPointDrawing){
				continueTransforming(e.getX(), e.getY());
			}
		}
		private void lButton2Click(MouseEvent e) {
			if(eDrawingState == EDrawingState.eNPointDrawing){
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(eDrawingState == EDrawingState.eIdle){
				if(selectedTool.getTransformationStyle() == ETransformationStyle.e2PTransformation){
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointDrawing;
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(eDrawingState == EDrawingState.eNPointDrawing){
				keepTransforming(e.getX(), e.getY());
			} else if(eDrawingState == EDrawingState.eIdle){
				changeCursor(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(eDrawingState == EDrawingState.e2PointDrawing){
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(eDrawingState == EDrawingState.e2PointDrawing){
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
		}
	}
}
