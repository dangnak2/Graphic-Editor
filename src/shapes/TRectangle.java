package shapes;

import java.awt.*;

public class TRectangle extends TShape {

    public TRectangle(){
        this.shape = new Rectangle();
    }

    public TShape clone(){
        return new TRectangle();
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