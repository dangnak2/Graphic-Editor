package global;

import shapes.*;

public class Constants {
    public enum ETransformationStyle{
        e2PTransformation,
        eNPTransformation,
    }
    public enum ETools {
        eSelection("Selection", new TSelection(), "./images/selection.png", "images/selectionSELT.png", "선택", ETransformationStyle.e2PTransformation),
        eRectangle("Rectangle", new TRectangle(), "./images/rectangle.png", "images/rectangleSELT.png", "직사각형", ETransformationStyle.e2PTransformation),
        eOval("Oval", new TOval(), "./images/oval.png", "./images/ovalSELT.png", "타원형", ETransformationStyle.e2PTransformation),
        eLine("Line", new TLine(), "./images/line.png", "./images/lineSELT.png", "직선", ETransformationStyle.e2PTransformation),
        ePolygon("Polygon", new TPolygon(), "./images/polygon.png", "./images/polygonSELT.png", "다각형", ETransformationStyle.eNPTransformation),
        eCurve("Curve", new TCurve(), "./images/curve.png", "./images/curveSELT.png", "곡선", ETransformationStyle.e2PTransformation);

        private String label;
        private String iconFileName;
        private String iconSELFilename;
        private String toolTip;
        private ETransformationStyle eTransformationStyle;

        private TShape tool;
        private ETools (String label, TShape tool, String iconFileName, String iconSELFilename, String tooTip, ETransformationStyle eTransformationStyle){
            this.iconFileName = iconFileName;
            this.iconSELFilename = iconSELFilename;
            this.label = label;
            this.tool = tool;
            this.toolTip = tooTip;
            this.eTransformationStyle = eTransformationStyle;
        }

        public String getIconFileName(){return this.iconFileName;}
        public String getIconSELFileName(){return this.iconSELFilename;}
        public String getLabel(){
            return this.label;
        }
        public String getToolTip() { return this.toolTip; }
        public TShape newShape(){
            return this.tool.clone();
        }

        public ETransformationStyle getTransformationStyle() {
            return this.eTransformationStyle;
        }
    }

    public enum EFileMenu{
        eNew("New", "새 그림판 생성"),
        eOpen("Open", "파일 불러오기"),
        eSave("Save", "파일을 저장하기"),
        eSaveAs("SaveAs", "파일을 다른 이름으로 저장"),
        eClose("Close", "그림판 닫기"),
        eQuit("Quit", "프로그램 종료");

        private String label;
        private String toolTip;

        private EFileMenu(String label, String toolTip){
            this.label = label;
            this.toolTip = toolTip;
        }
        public String getLabel(){
            return this.label;
        }
        public String getToolTip(){
            return this.toolTip;
        }
    }

    public enum EEditMenu{
        eUndo("Undo", "실행취소"),
        eRedo("Redo", "되돌리기"),
        eCut("Cut", "자르기"),
        eCopy("Copy", "복사하기"),
        ePaste("Paste", "붙여넣기"),
        eGroup("Group", "선택하기");

        private String label;
        private String toolTip;

        private EEditMenu(String label, String toolTip){
            this.label = label;
            this.toolTip = toolTip;
        }

        public String getLabel(){
            return this.label;
        }
        public String getTooltip(){
            return this.toolTip;
        }
    }
}
