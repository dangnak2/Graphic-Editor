package shapes;

import java.awt.*;

public class TPolygon extends TShape {

    public TPolygon(){
        this.shape = new Polygon();
    }

    @Override
    public TShape clone() {
        return new TPolygon();
    }

    public void prepareDrawing(int x, int y) {
        this.addPoint(x, y);
        this.addPoint(x, y);
    }

    @Override
    public void addPoint(int x, int y) {
        Polygon polygon = (Polygon) this.shape;
        polygon.addPoint(x, y);
    }

    @Override
    public void keepDrawing(int x, int y) {
        Polygon polygon = (Polygon) this.shape;
        polygon.xpoints[polygon.npoints - 1] = x;
        polygon.ypoints[polygon.npoints - 1] = y;
    }
}
