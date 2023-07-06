package View;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application implements Initializable {


    public static boolean win = false;
    public ImageView tableImageView;
    public Pane pane;
    public static boolean gameOver = false;
    public static boolean setVelocity = true;
    public Stage stage;
    public static Text score;
    public static Text moves;
    private boolean setText = false;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent pane = FXMLLoader.load(getClass().getResource("/Sample/fxml/main.fxml"));
        Group root = new Group();
        root.getChildren().add(pane);
        Scene scene = new Scene(root);
        Rectangle scoreRec = new Rectangle(65,30,70,50);
        scoreRec.setFill(Color.RED);
        root.getChildren().add(scoreRec);
        Rectangle moveRec = new Rectangle(145, 30, 70, 50);
        moveRec.setFill(Color.RED);
        root.getChildren().add(moveRec);
        BallView ballView = new BallView(root, scene);
        score = new Text(84,70, "0");
        score.setFill(Color.BLACK);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        moves = new Text(165,70, "5");
        moves.setFill(Color.BLACK);
        moves.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        root.getChildren().addAll(score, moves);
        stage.setScene(scene);
        stage.setTitle("Billiard");
        stage.show();

        Timer timer=new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                ballView.setVelocityOfCueBall();

            }

        },0,30);

        //game loop
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
       KeyFrame kf = new KeyFrame(
         Duration.seconds(0.030),                // 60 FPS
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent ae) {
                            if (gameOver){
                                Text gameOverText = new Text(100,100, "GameOver");
                                gameOverText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 100));
                                root.getChildren().add(gameOverText);
                                gameLoop.stop();
                            }
                            if (win){
                                Text winText = new Text(100,100, "Win");
                                winText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 100));
                                root.getChildren().add(winText);
                                gameLoop.stop();
                            }

                            ballView.updateGameState(scene);
                            if(ballView.changedScore) {
                                System.out.println("here");
                                ballView.changedScore = false;
                                score.setText(String.valueOf(ballView.scoreNum));
                            }
                        }
                            }
                         );

       gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(!gameOver) {
                   ballView.handleCollisions();
                    ballView.detectWallCollision();
                  ballView.controlVelocityOfCueBall();
                  ballView.detectAndHandlePocket();

                }
            }
        }.start();
    }
    private void draw() {
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image tableImage = new Image(getClass().getResourceAsStream("/table.jpg"));
        tableImageView.setImage(tableImage);
    }

}
