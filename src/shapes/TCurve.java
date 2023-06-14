package shapes;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class TCurve extends TShape {

    public TCurve(){
        this.shape = new Path2D.Double();
    }

    @Override
    public TShape clone() {
        return new TCurve();
    }

    public void prepareDrawing(int x, int y) {
        Path2D curve = (Path2D.Double)this.shape;
        curve.moveTo(x, y);
    }

    @Override
    public void keepDrawing(int x, int y) {
        Path2D curve = (Path2D.Double)this.shape;
        curve.lineTo(x, y);
    }
}