package shapes;

import java.awt.*;

public class TSelection extends TShape {

    public TSelection(){
        this.shape = new Rectangle();
    }

    public TShape clone(){
        return new TSelection();
    }

    public void prepareDrawing(int x, int y) {
        Rectangle rectangle = (Rectangle) this.shape;
        rectangle.setFrame(x, y, 0, 0);
    }

    public void keepDrawing(int x, int y) {
        Rectangle rectangle = (Rectangle) this.shape;
        rectangle.setSize( x - rectangle.x, y - rectangle.y);
    }

}