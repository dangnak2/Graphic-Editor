package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class TOval extends TShape {

    public TOval(){
        this.shape = new Ellipse2D.Double();
    }

    @Override
    public TShape clone() {
        return new TOval();
    }

    public void prepareDrawing(int x, int y) {
        Ellipse2D ellipse = (Ellipse2D.Double)this.shape;
        ellipse.setFrame(x, y, 0, 0);
    }

    public void keepDrawing(int x, int y) {
        Ellipse2D ellipse = (Ellipse2D.Double)this.shape;
        ellipse.setFrame(ellipse.getX(), ellipse.getY(), x - ellipse.getX(), y - ellipse.getY());
    }

}