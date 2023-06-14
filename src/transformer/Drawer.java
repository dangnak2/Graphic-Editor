package transformer;

import global.Constants;
import shapes.TSelection;
import shapes.TShape;

import java.awt.*;
import java.util.Vector;
import global.Constants.ETools;

public class Drawer extends Transformer{

    public Drawer(TShape selectedShape) {
        super(selectedShape);
    }

    @Override
    public void prepareTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.prepareDrawing(x, y);
    }

    @Override
    public void keepTransforming(int x, int y, Graphics2D graphics2D) {
        this.selectedShape.draw(graphics2D);
        this.selectedShape.keepDrawing(x, y);
        this.selectedShape.draw(graphics2D);
    }

    @Override
    public void finishTransforming(int x, int y, Graphics2D graphics2D) {

    }
}
