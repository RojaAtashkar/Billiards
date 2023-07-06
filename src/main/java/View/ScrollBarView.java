package View;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

// help : Created by Sadiqur Rahman on 5/22/2016.
public class ScrollBarView {
    Rectangle bar = new Rectangle(10, 235, 32.5, 230);
    Group root;
    Circle circle;

    public ScrollBarView(Group root) {
        this.root = root;
        //set up bar
        Stop[] stops = new Stop[]{new Stop(0, javafx.scene.paint.Color.BLACK), new Stop(1, javafx.scene.paint.Color
                .RED)};
        javafx.scene.paint.LinearGradient lg = new javafx.scene.paint.LinearGradient(.5, 0, .5, .7, true, CycleMethod.REFLECT, stops);
        bar.setFill(lg);
        bar.setCursor(Cursor.CLOSED_HAND);
        //set up velocity line
        Rectangle line = new Rectangle(18, 250, 16, 200);
        Stop[] stopsLine = new Stop[]{new Stop(0, javafx.scene.paint.Color.BLACK), new Stop(1, javafx.scene.paint.Color
                .CRIMSON)};
        javafx.scene.paint.LinearGradient lg1 = new javafx.scene.paint.LinearGradient(0, .5, 1, .5, true, CycleMethod
                .REFLECT, stopsLine);
        line.setFill(lg1);
        //set up point circle
        circle = new Circle(8);
        circle.setLayoutX(26.3);
        circle.setLayoutY(250);
        Stop[] stop = new Stop[]{new Stop(0, javafx.scene.paint.Color.WHITE), new Stop(1, javafx.scene.paint.Color
                .BLACK)};
        javafx.scene.paint.LinearGradient g = new javafx.scene.paint.LinearGradient(0, 0, 0, 2, true, CycleMethod
                .REFLECT, stop);
        circle.setFill(g);
        root.getChildren().addAll(bar, line, circle);
    }

    public Circle getCircle() {
        return circle;
    }
}
