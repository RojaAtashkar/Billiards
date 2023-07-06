package View;


import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class CueView {

    private Group root;
    private Scene scene;
    private double v;
    public double cueAngle = 0;
    public double preCueAngle = 0;
    public double preLineAngle = 180;
    private Rectangle cue;
    public ScrollBarView scrollBarView;
    private Circle scrollCircle;
    private double value;
    private boolean moveCue = true;

    public CueView(Group root, Scene scene) {
        this.root = root;
        this.scene = scene;
        cue = new Rectangle();
        Image cueImage = new Image("/cue.png");
        Stop[] stops = new Stop[]{new Stop(0, Color.BLACK), new Stop(1, Color.RED)};
        LinearGradient lg = new LinearGradient(.5, 0, .5, .7, true, CycleMethod.REFLECT, stops);
        cue.setFill(lg);
        cue.setWidth(3.5);
        cue.setHeight(230);
        cue.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
        initializeCue(200.0, 260.0);
        scrollBarView = new ScrollBarView(root);
        scrollCircle = scrollBarView.circle;
        root.getChildren().addAll(cue);
    }
    public void setVelocity() {
        scrollCircle.setOnMouseDragged(event -> {
            if ((event.getSceneY() - 250) < 200 && (event.getSceneY() - 250) > 0) {
                scrollCircle.setLayoutY(event.getSceneY());
                value = event.getSceneY() - 250;
                value = value * .5;
            }
        });
        scrollCircle.setOnMouseReleased(event -> {
            v = (value / 7.5);
            value = 0;
            scrollCircle.setLayoutY(250);
            //System.out.println(cueView.getV());
        });
    }
    public void rotateCue(Scene scene, Double x, Double y) {

        if (moveCue) {
            cue.setOnMouseDragged(event -> {
               // System.out.println("rotating");
                cue.setCursor(Cursor.CLOSED_HAND);
                cueAngle = Math.toDegrees(Math.atan2((event.getSceneY() - y), (event.getSceneX() - x)));
                if (cueAngle < 0) {
                    cueAngle += 360;
                }

                cue.getTransforms().addAll(new Rotate(cueAngle - preCueAngle, Rotate.Z_AXIS));

                preCueAngle = cueAngle;


            });
        }
    }
    public void moveCue(double x, double y) {
       cue.setLayoutX(x + value * Math.cos(Math.toRadians(cueAngle)));
       cue.setLayoutY(y + value * Math.sin(Math.toRadians(cueAngle)));
    }


    public void initializeCue(Double x, Double y) {
        cue.setLayoutX(x);
        cue.setLayoutY(y);

    }

    public void Update() {
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public Group getRoot() {
        return root;
    }



    public Rectangle getCue() {
        return cue;
    }

    public double getCueAngle() {
        return cueAngle;
    }

    public void setCueAngle(double cueAngle) {
        this.cueAngle = cueAngle;
    }
}
