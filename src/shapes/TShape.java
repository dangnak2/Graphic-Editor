package shapes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;
import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable {

    // attribute
    private static final long serialVersionUID = 1L;
    // component
    protected Shape shape;
    private final TAnchors anchors;
    // working
    private boolean bSelected;
    private int px, py;
    private double cx, cy;
    private double xScale, yScale;
    private AffineTransform affineTransform;

    // setters and getters
    public EAnchors getSelectedAnchor() {
        return this.anchors.geteSelectedAnchor();
    }

    public boolean isSelected() {
        return bSelected;
    }
    public void setSelected(boolean bSelected) {
        this.bSelected = bSelected;
    }

    // constructors
    public TShape() {
        this.bSelected = false;
        this.anchors = new TAnchors();
        this.affineTransform = new AffineTransform();
    }
    public abstract TShape clone();

    // methods
    public abstract void prepareDrawing(int x, int y);
    public abstract void keepDrawing(int x, int y);
    public void addPoint(int x, int y){}

    public boolean contains(int x, int y){
        Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
        if(this.bSelected){
            if(this.anchors.contains(x,y)){
                return true;
            }
        }
        if(transformedShape.contains(x, y)){
            this.anchors.seteSelectedAnchor(EAnchors.eMove);
            return true;
        }
        return false;
    }

    public void prepareMoving(Graphics2D graphics2D, int x, int y){
        this.px = x;
        this.py = y;
    }

    public void keepMoving(Graphics2D graphics2D, int x, int y){
        int dw = x - this.px;
        int dh = y - this.py;

        this.affineTransform = new AffineTransform();
        this.affineTransform.setToIdentity();
        this.affineTransform.translate(dw, dh);
        this.shape = this.affineTransform.createTransformedShape(this.shape);
        this.px = x;
        this.py = y;
    }

    public void finishMoving(int x, int y) {
        this.shape = this.affineTransform.createTransformedShape(this.shape);
        this.affineTransform.setToIdentity();
    }

//  0523
    public void prepareResizing(int x, int y) {
        this.px = x;
        this.py = y;
        Point2D resizeAnchorPoint = this.anchors.getResizeAnchorPoint();
        this.cx = resizeAnchorPoint.getX();
        this.cy = resizeAnchorPoint.getY();
    }

    public void keepResizing(int x, int y, Graphics2D graphics2D) {
        this.getResizeScale(x, y, graphics2D);
        this.px = x;
        this.py = y;
    }

    public void finishResizing(int x, int y) {
        this.shape = this.affineTransform.createTransformedShape(this.shape);
        this.affineTransform.setToIdentity();
    }

    public void prepareRotation(int x, int y) {
        this.px = x;
        this.py = y;
    }

    public void keepRotation(int x, int y, Graphics2D graphics2D) {

        double centerX = this.shape.getBounds().getCenterX();
        double centerY = this.shape.getBounds().getCenterY();

        double startAngle = Math.toDegrees(
                Math.atan2(centerX-this.px, centerY - this.py));
        double endAngle = Math.toDegrees(
                Math.atan2(centerX-x, centerY - y));
        double rotationAngle = startAngle - endAngle;

        if(rotationAngle < 0){
            rotationAngle += 360;
        }

        this.affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(rotationAngle), centerX, centerY);
    }

    public void finishRotation(int x, int y) {
        this.shape = this.affineTransform.createTransformedShape(this.shape);
        this.affineTransform.setToIdentity();
    }

    private void getResizeScale(int x, int y, Graphics2D graphics2D) {

        double w1 = px - cx;
        double w2 = x - cx;
        double h1 = py - cy;
        double h2 = y - cy;

        switch (anchors.geteResizeAnchor()) {
            case eNW:
            case eSW:
            case eSE:
            case eNE:
                xScale = w2 / w1;
                yScale = h2 / h1;
                break;
            case eWW:
            case eEE:
                xScale = w2 / w1;
                yScale = 1;
                break;
            case eSS:
            case eNN:
                xScale = 1;
                yScale = h2 / h1;
                break;
            default:
                break;
        }

        this.affineTransform = new AffineTransform();
        this.affineTransform.translate(cx, cy);
        this.affineTransform.scale(this.xScale, this.yScale);
        this.affineTransform.translate(-cx, -cy);
        this.shape = this.affineTransform.createTransformedShape(this.shape);

    }

    public void draw(Graphics2D graphics2D) {
        Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
        graphics2D.draw(transformedShape);
        if (this.bSelected) {
            this.anchors.draw(graphics2D, transformedShape.getBounds());
        }
    }
}