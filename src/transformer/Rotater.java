package transformer;

import shapes.TShape;

import java.awt.*;

public class Rotater extends Transformer{

    public Rotater(TShape selectedShape) {
        super(selectedShape);
    }

    @Override
    public void prepareTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.prepareRotation(x, y);
    }

    @Override
    public void keepTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.keepRotation(x, y, graphics2D);
    }

    @Override
    public void finishTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.finishRotation(x, y);
    }
}
