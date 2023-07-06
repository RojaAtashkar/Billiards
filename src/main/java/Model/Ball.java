package Model;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Ball {
    public Sphere sphere = new Sphere(10, 40);
    private double v_x;
    private double v_y;
    private double velocity;
    private double angle;
    private double time;
    private double a_x;
    private double a_y;
    private double a = 0;
    private double v;
    public boolean kicked = false;
    private int ball_num;

    public Ball(Group root, double x, double y, String imageName, Color color) {
        sphere.setLayoutX(x);
        sphere.setLayoutY(y);
        sphere.setRadius(20);
        Image ballImage = new Image(getClass().getResourceAsStream(imageName));
        PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(Color.WHITE);
        if (color == Color.BLACK)
            material.setDiffuseColor(color);
        material.setDiffuseMap(ballImage);
        material.setSpecularPower(30);
        sphere.setMaterial(material);
        root.getChildren().addAll(sphere);
        this.v_x = 0;
        this.v_y = 0;
        this.a_x = 0;
        this.a_y = 0;
        this.angle = 0;
    }

    public double getA_x() {
        return a_x;
    }

    public void setA_x(double a_x) {
        this.a_x = a_x;
    }

    public double getA_y() {
        return a_y;
    }

    public void setA_y(double a_y) {
        this.a_y = a_y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getBall_num() {
        return ball_num;
    }

    public void setBall_num(int ball_num) {
        this.ball_num = ball_num;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getV_x() {
        return v_x;
    }

    public void setV_x(double v_x) {
        this.v_x = v_x;
    }

    public double getV_y() {
        return v_y;
    }

    public void setV_y(double v_y) {
        this.v_y = v_y;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }
}
