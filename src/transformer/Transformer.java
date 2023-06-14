package transformer;

import shapes.TShape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Vector;

public abstract class Transformer {

    protected TShape selectedShape;

    public Transformer(TShape selectedShape) {
        this.selectedShape = selectedShape;
    }

    public abstract void prepareTransforming(int x, int y, Graphics2D graphics2D);
    public abstract void keepTransforming(int x, int y, Graphics2D graphics2D);
    public abstract void finishTransforming(int x, int y, Graphics2D graphics2D);

    public void continueTransforming(int x, int y, Graphics2D graphics2D){
        this.selectedShape.addPoint(x, y);
    }

}
