package transformer;

import shapes.TAnchors.EAnchors;
import shapes.TSelection;
import shapes.TShape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Resizer extends Transformer{

    public Resizer(TShape selectedShape) {
        super(selectedShape);
    }

    @Override
    public void prepareTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.prepareResizing(x, y);
    }

    @Override
    public void keepTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.keepResizing(x, y, graphics2D);
    }

    @Override
    public void finishTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.finishResizing(x, y);
    }
}
