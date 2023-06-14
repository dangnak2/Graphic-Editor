package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class TAnchors implements Serializable {

    private final int WIDTH = 10;
    private final int HEIGHT = 10;

    public enum EAnchors {
        eNW, eWW, eSW, eSS, eSE, eEE, eNE, eNN, eRR, eMove;
    }

    private final Ellipse2D[] anchors;
    private  EAnchors eSelectedAnchor;
    // 대각선에 있는 중심 앵커
    private  EAnchors eResizeAnchor;


    public TAnchors() {
        this.anchors = new Ellipse2D[EAnchors.values().length-1];
        for (int i=0; i<EAnchors.values().length-1; i++) {
            this.anchors[i] = new Ellipse2D.Double();
        }
    }

    public Ellipse2D[] getAnchors() {
        return anchors;
    }

    public EAnchors geteSelectedAnchor() {
        return eSelectedAnchor;
    }

    public void seteSelectedAnchor(EAnchors eSelectedAnchor) {
        this.eSelectedAnchor = eSelectedAnchor;
    }

    public EAnchors geteResizeAnchor() {
        return eResizeAnchor;
    }

    public void seteResizeAnchor(EAnchors eResizeAnchor) {
        this.eResizeAnchor = eResizeAnchor;
    }

    public boolean contains(int x, int y) {
        for (int i=0; i<EAnchors.values().length-1; i++) {
            if (this.anchors[i].contains(x, y)) {
                this.eSelectedAnchor = EAnchors.values()[i];
                return true;
            }
        }
        return false;
    }
    public void draw(Graphics2D graphics2D, Rectangle BoundingRectangle) {
        for (int i = 0; i < EAnchors.values().length - 1; i++) {

            int x = BoundingRectangle.x - WIDTH / 2;
            int y = BoundingRectangle.y - HEIGHT / 2;
            int w = BoundingRectangle.width;
            int h = BoundingRectangle.height;

            EAnchors eAnchor = EAnchors.values()[i];
            switch (eAnchor) {
                case eNW:
                    break;
                case eWW:
                    y = y + h / 2;
                    break;
                case eSW:
                    y = y + h;
                    break;
                case eSS:
                    x = x + w / 2;
                    y = y + h;
                    break;
                case eSE:
                    x = x + w;
                    y = y + h;
                    break;
                case eEE:
                    x = x + w;
                    y = y + h / 2;
                    break;
                case eNE:
                    x = x + w;
                    break;
                case eNN:
                    x = x + w / 2;
                    break;
                case eRR:
                    x = x + w / 2;
                    y = y - h / 4;
                    break;
                default:
                    break;
            }

            this.anchors[eAnchor.ordinal()].setFrame(x, y, WIDTH, HEIGHT);

            graphics2D.setColor(graphics2D.getBackground());
            graphics2D.fill(this.anchors[eAnchor.ordinal()]);
            graphics2D.setColor(Color.black);
            graphics2D.draw(this.anchors[eAnchor.ordinal()]);

        }
    }

    public Point2D getResizeAnchorPoint() {
        switch (this.eSelectedAnchor) {
            case eNW:
                this.eResizeAnchor = EAnchors.eSE;
                break;
            case eWW:
                this.eResizeAnchor = EAnchors.eEE;
                break;
            case eSW:
                this.eResizeAnchor = EAnchors.eNE;
                break;
            case eSS:
                this.eResizeAnchor = EAnchors.eNN;
                break;
            case eSE:
                this.eResizeAnchor = EAnchors.eNW;
                break;
            case eEE:
                this.eResizeAnchor = EAnchors.eWW;
                break;
            case eNE:
                this.eResizeAnchor = EAnchors.eSW;
                break;
            case eNN:
                this.eResizeAnchor = EAnchors.eSS;
                break;
            default:
                break;
        }
        Point2D point = new Point2D.Double(
                anchors[eResizeAnchor.ordinal()].getCenterX(),
                anchors[eResizeAnchor.ordinal()].getCenterY()
        );
        return point;
    }

}
