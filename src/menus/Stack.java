package menus;

import shapes.TShape;

import java.util.Vector;

public class Stack {

  private static final int maxIndex = 10;
  private int bottomIndex;
  private int topIndex;
  private int size;
  private Vector<Vector<TShape>> elements;

  public Stack() {
    this.bottomIndex = 0;
    this.topIndex = 0;
    this.size = 0;
    this.elements = new Vector<Vector<TShape>>();
    for (int i = 0; i < maxIndex; i++) {
      this.elements.add(new Vector<TShape>());
    }
  }

  public void push(Vector<TShape> shapes) {
    this.topIndex++;
    this.elements.set(this.topIndex % maxIndex, shapes);

    if (this.topIndex - this.bottomIndex == maxIndex) {
      this.bottomIndex++;
    } else {
      this.size++;
    }
  }

  public Vector<TShape> undo() {
    Vector<TShape> undoElement = null;
    if (this.topIndex >= this.bottomIndex) {
      this.topIndex--;
      undoElement = this.elements.get(this.topIndex % maxIndex);
    }
    return undoElement;

  }

  public boolean canUndo() {
    if (this.topIndex < 1) {
      return false;
    }
    return true;
  }

  public boolean canRedo() {
    if (this.topIndex - this.bottomIndex < this.size) {
      return true;
    }
    return false;
  }

  public Vector<TShape> redo() {
    Vector<TShape> redoElement = null;

    if (this.topIndex - this.bottomIndex < this.size) {
      this.topIndex++;
      redoElement = this.elements.get(this.topIndex % maxIndex);
    }
    return redoElement;
  }
}
