package View;

import Model.Ball;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BallView {
    private Ball[] ball;
    private String[] ball_images = {"/ball_1.png", "/ball_2.png", "/ball_3.png", "/ball_4.png",
            "/ball_5.png", "/ball_6.png", "/ball_7.png", "/ball_8.png", "/ball_9.png", "/ball_10.png"
    };
    private double W = 888;
    private double H = 500;
    private String white_ball = "/white_ball.png";
    private Scene scene;
    private Group root;
    private Ball cueBall;
    private boolean isFaul;
    private CueView cueView;
    private boolean isVelocitySet = false;
    private boolean flag;
    private int R = 20;
    public  int scoreNum = 0;
    public  int moveNum = 5;
    public boolean changedScore = false;

    BallView(Group root, Scene scene) {
        this.scene = scene;
        this.root = root;
        ball = new Ball[11];
        double r = 20;
        double x_0 = 610;
        double y_0 = 260;
        ball[0] = new Ball(root, 300, y_0, white_ball, Color.WHITE);
        ball[1] = new Ball(root, x_0, y_0, ball_images[0], Color.WHITE);
        ball[2] = new Ball(root, x_0 + 2 * r, y_0 + r, ball_images[1], Color.WHITE);
        ball[3] = new Ball(root, x_0 + 2 * r, y_0 - r, ball_images[2], Color.WHITE);
        ball[4] = new Ball(root, x_0 + 4 * r, y_0 + 2 * r, ball_images[3], Color.WHITE);
        ball[5] = new Ball(root, x_0 + 4 * r, y_0 - 2 * r, ball_images[4], Color.WHITE);
        ball[8] = new Ball(root, x_0 + 4 * r, y_0, ball_images[7], Color.WHITE);
        ball[6] = new Ball(root, x_0 + 6 * r, y_0 + 3 * r, ball_images[5], Color.WHITE);
        ball[7] = new Ball(root, x_0 + 6 * r, y_0 + r, ball_images[6], Color.WHITE);
        ball[9] = new Ball(root, x_0 + 6 * r, y_0 - r, ball_images[8], Color.WHITE);
        ball[10] = new Ball(root, x_0 + 6 * r, y_0 - 3 * r, ball_images[9], Color.WHITE);
        this.cueBall = ball[0];
        this.cueView = new CueView(root, scene);
    }
    public void setVelocityOfCueBall() {
        cueView.setVelocity();
        if (cueView.getV() > 1 && !isVelocitySet){
            isFaul = false;
            isVelocitySet = true;
            moveNum--;
            if (moveNum > -1)
            Main.moves.setText(String.valueOf(moveNum));
            System.out.println(scoreNum);
            double angle = cueView.cueAngle;
            cueBall.setAngle(Math.toRadians(angle));
            cueBall.setV(cueView.getV());
            cueBall.setV_x((cueBall.getV() * Math.cos((cueBall.getAngle()))));
            cueBall.setV_y((cueBall.getV() * Math.sin((cueBall.getAngle()))));
            cueBall.setA_x((cueBall.getA() * Math.cos((cueBall.getAngle()))));
            cueBall.setA_y(cueBall.getA() * Math.sin((cueBall.getAngle())));
        }

    }

    public void initializeCue() {
       // cueView.initializeCue(ball[0].sphere.getLayoutX(), ball[0].sphere.getLayoutY());
    }
    public void updateGameState(Scene scene) {
        cueView.moveCue(ball[0].sphere.getLayoutX(), ball[0].sphere.getLayoutY());
        cueView.rotateCue(scene,ball[0].sphere.getLayoutX(), ball[0].sphere.getLayoutY());
    }
    public boolean isAllVelocityZero() {
        int c = 0;
        for (int i = 0; i < 11; i ++){
            if (ball[i].getV() != 0 && !ball[i].kicked )
                return false;
        }
        if (c == 1 && !ball[0].kicked) Main.win = true;
       return true;
    }
    public void controlVelocityOfCueBall() {
        if (isVelocitySet) {
            for (int i = 0; i < 11; i++) {
                root.getChildren().remove(cueView.getCue());
                ball[i].setV(Math.sqrt(Math.pow(ball[i].getV_x(), 2) + Math.pow(ball[i].getV_y(), 2)));
                if (ball[i].getV() > 0.07) {
                    ball[i].setV_x(ball[i].getV_x() - (ball[i].getA_x() * ball[i].getTime()));
                    ball[i].setV_y(ball[i].getV_y() - (ball[i].getA_y() * ball[i].getTime()));
                    ball[i].setTime(ball[i].getTime() + .00005);
                    ball[i].sphere.setLayoutX(ball[i].sphere.getLayoutX() + ball[i].getV_x());
                    ball[i].sphere.setLayoutY(ball[i].sphere.getLayoutY() + ball[i].getV_y());
                }
                if (ball[i].getV() <= 0.8) {
                    ball[i].setTime(0);
                    ball[i].setAngle(0);
                    ball[i].setV_x(0);
                    ball[i].setV_y(0);
                }

                if (isAllVelocityZero()) {
                    root.getChildren().add(cueView.getCue());
                    setVelocityOfCueBall();
                    cueView.setV(0);
                    flag = true;
                    isVelocitySet = false;
                }
            }
        }
    }
    public void UpdateAngle(Ball ball) {
        double t = Math.toDegrees(Math.atan2(ball.getV_y(), ball.getV_x()));
        if (t < 0) {
            t += 360;
        }
        ball.setAngle(Math.toRadians(t));
    }

    public void detectWallCollision() {

        for (int i = 0; i < 11; i++) {
            if (ball[i].kicked) continue;
                //up
                if (ball[i].sphere.getLayoutY() - 2 * R < 0) {
                    ball[i].sphere.setLayoutY(ball[i].sphere.getLayoutY() + R);
                    ball[i].setV_y(-(ball[i].getV_y()));
                    decreaseVelocity(ball[i]);
                    UpdateAngle(ball[i]);
                }
               //down
                if ((ball[i].sphere.getLayoutY() + 2 * R  > H)) {

                    ball[i].sphere.setLayoutY(ball[i].sphere.getLayoutY() - R);
                    ball[i].setV_y(-(ball[i].getV_y()));
                    decreaseVelocity(ball[i]);
                    UpdateAngle(ball[i]);
                }
            //right
            if (ball[i].sphere.getLayoutX() + 2 * R > W) {
                ball[i].sphere.setLayoutX(ball[i].sphere.getLayoutX() - R);
                    ball[i].setV_x(-(ball[i].getV_x()));
                    decreaseVelocity(ball[i]);
                    UpdateAngle(ball[i]);
            }

            //left
            if (ball[i].sphere.getLayoutX() - 2 * R <0) {

                    ball[i].sphere.setLayoutX(ball[i].sphere.getLayoutX() + R);
                    ball[i].setV_x(-(ball[i].getV_x()));
                    decreaseVelocity(ball[i]);
                    UpdateAngle(ball[i]);
            }

        }
    }
    public void decreaseVelocity(Ball ball){
        ball.setV_x(ball.getV_x() * 0.5);
        ball.setV_y(ball.getV_y() * 0.5);

    }
    public void handleCollisions() {
        double xDist, yDist;
        //detectAndHandlePocket();
        for (int i = 0; i < 11; i++) {
            if (ball[i].kicked) continue;
            Ball A = ball[i];
            for (int j = i + 1; j < 11; j++) {
                if (ball[j].kicked) continue;
               Ball B = ball[j];
                xDist = A.sphere.getLayoutX() - B.sphere.getLayoutX();
                yDist = A.sphere.getLayoutY() - B.sphere.getLayoutY();
                double distSquared = xDist * xDist + yDist * yDist;
                //Check the squared distances instead of the distances, same result, but avoids a square root.
                if (distSquared <= 4 * R * R) {
                    double xVelocity = B.getV_x() - A.getV_x();
                    double yVelocity = B.getV_y() - A.getV_y();
                    double dotProduct = xDist * xVelocity + yDist * yVelocity;
                    //Neat vector maths, used for checking if the objects moves towards one another.
                    if (dotProduct > 0) {
                        double collisionScale = dotProduct / distSquared;
                        double xCollision = xDist * collisionScale;
                        double yCollision = yDist * collisionScale;
                        //The Collision vector is the speed difference projected on the Dist vector,
                        //thus it is the component of the speed difference needed for the collision.
                        double combinedMass = 10;                 // A.mass + B.mass;
                        double collisionWeightA = 1;                //2 * B.mass / combinedMass;
                        double collisionWeightB = 1;                  // 2 * A.mass / combinedMass;

                        A.setV_x((collisionWeightA * xCollision) + A.getV_x());
                        A.setV_y(collisionWeightA * yCollision + A.getV_y());
                        B.setV_x(B.getV_x() - collisionWeightB * xCollision);
                        B.setV_y(B.getV_y() - collisionWeightB * yCollision);

                        A.setAngle(Math.toDegrees(Math.atan2(A.getV_y(), A.getV_x())));
                        B.setAngle(Math.toDegrees(Math.atan2(B.getV_y(), B.getV_x())));

                        if (A.getAngle() < 0) {
                            A.setAngle((A.getAngle() + 360));
                        }
                        if (B.getAngle() < 0) {
                            B.setAngle((B.getAngle() + 360));
                        }

                    }
                }
            }
        }
    }
    public void detectAndHandlePocket() {
        if (!isVelocitySet)
            return;
        for (int i = 0; i < 11 ; i++) {

            if (ball[i].kicked) continue;
            double distance1 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 437, 2) + Math.pow(ball[i].sphere.getLayoutY() - 50, 2));
            double distance2 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 42, 2) + Math.pow(ball[i].sphere.getLayoutY() - 45, 2));
            double distance3 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 45, 2) + Math.pow(ball[i].sphere.getLayoutY() - 461, 2));
            double distance4 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 440, 2) + Math.pow(ball[i].sphere.getLayoutY() - 468, 2));
            double distance5 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 840, 2) + Math.pow(ball[i].sphere.getLayoutY() - 460, 2));
            double distance6 = Math.sqrt(Math.pow(ball[i].sphere.getLayoutX() - 840, 2) + Math.pow(ball[i].sphere.getLayoutY() - 51, 2));
            if (distance1 < R  || distance2 < R || distance3 < R  || distance4 < R  || distance5 < R  || distance6 < R ) {
               root.getChildren().remove(ball[i].sphere);
               ball[i].kicked =true;
               if (i == 0)
               {
                  Main.gameOver = true;
                  return;
               }
               if (i == 8 ){
                   if (moveNum > -1)
                       scoreNum += 16;
                   else scoreNum += 8;
               }


                   else if (moveNum > -1)scoreNum += i;
                   else scoreNum += i - 5;
                   if (scoreNum < 0) Main.gameOver = true;
               changedScore = true;

                }
        }
    }
    public Ball[] getBall() {
        return ball;
    }
    public Group getRoot() {
        return root;
    }
}
