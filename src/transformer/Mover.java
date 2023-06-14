package transformer;

import shapes.TShape;

import java.awt.*;

public class Mover extends Transformer{

    public Mover(TShape selectedShape) {
        super(selectedShape);
    }

    @Override
    public void prepareTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.prepareMoving(graphics2D, x, y);
    }

    @Override
    public void keepTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.draw(graphics2D);
        this.selectedShape.keepMoving(graphics2D, x, y);
        this.selectedShape.draw(graphics2D);
    }

    @Override
    public void finishTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.finishMoving(x, y);
    }
}
