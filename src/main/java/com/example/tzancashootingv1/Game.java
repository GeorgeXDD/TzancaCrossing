package com.example.tzancashootingv1;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.internal.access.JavaIOFileDescriptorAccess;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements Commons{
    private AnimationTimer timer;

    private Pane root;

    private int time = 0;
    private List<Node> cars = new ArrayList<>();
    private Node player1;
    Image map = new Image("https://i.imgur.com/xho6QmL.png");
    //    Image map = new Image("https://i.imgur.com/wsZ2A5o.jpeg");
    ImagePattern player = new ImagePattern(map, 38, 38, 38, 38, false);

    Image map2 = new Image("https://i.imgur.com/CGaQhHJ.png");
    ImagePattern alien = new ImagePattern(map2, 38, 38, 38, 38, false);

    Image imagebg = new Image("https://i.imgur.com/BbB9Cft.png");
    BackgroundImage bgimage = new BackgroundImage(imagebg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    Background bg = new Background(bgimage);

    MediaPlayer mediaPlayer;


    public void music() {
        String path = "src/main/resources/com/example/tzancashootingv1/tzancaAudio.mp3";
        Media media = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

    }


    private Parent createContent() {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql = "INSERT INTO `user_account`(`account_id`, `firstname`, `lastname`, `username`, `password`, `score`)" + " VALUES (?, ?, ?, ?, ?,?)";

        int currentScore;
        try {
            PreparedStatement preparedStmt = connectDB.prepareStatement(sql);

            Label usernameTextField = null;
            String verifyScore = "SELECT score FROM user_account WHERE username = '" + usernameTextField.getText() + "'";
            ResultSet queryResult = preparedStmt.executeQuery(verifyScore);
            currentScore = 0;
            while (queryResult.next()) {
                currentScore = queryResult.getInt(1);
            }
            connectDB.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        root = new Pane();

        root.setBackground(bg);

        root.setPrefSize(1240, 700);

        player1 = initPlayer();

        root.getChildren().add(player1);
        music();

        Label timerLabel = new Label("Time: 0");
        timerLabel.setTranslateX(10);
        timerLabel.setTranslateY(10);
        root.getChildren().add(timerLabel);

        Label highScoreLabel = new Label("highScoreLabel: ");
        highScoreLabel.setText("Fastest Time: "+currentScore);
        highScoreLabel.setTranslateX(100);
        highScoreLabel.setTranslateY(10);
        root.getChildren().add(highScoreLabel);
        time=0;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
                time++;
                timerLabel.setText("Time: " + time);
            }
        };
        timer.start();


        return root;
    }

    private Node initPlayer() {
        Rectangle rect = new Rectangle(38, 38, Color.GREEN);
        rect.setFill(player);
        rect.setTranslateY(700 - 39);
        rect.setTranslateX(1240 - 600);
        return rect;
    }

    private Node spawnCar() {
        Rectangle rect = new Rectangle(40, 40, Color.RED);
        rect.setFill(alien);
        rect.setTranslateY((int) (Math.random() * 10) * 60);
        rect.setTranslateY(rect.getTranslateY() + 50);
        root.getChildren().add(rect);
        return rect;
    }

    private void onUpdate() {
        for (Node car : cars)
            car.setTranslateX(car.getTranslateX() + Math.random() * 10);

        if (Math.random() < 0.075) {
            cars.add(spawnCar());
        }

        checkState();
    }

    private void checkState() {
        for (Node car : cars) {
            if (car.getBoundsInParent().intersects(player1.getBoundsInParent())) {
                player1.setTranslateX(1200 - 600);
                player1.setTranslateY(700 - 39);
                return;
            }
        }
        if (player1.getTranslateY() > 700) {
            player1.setTranslateX(1200 - 600);
            player1.setTranslateY(700 - 39);
        }


        if (player1.getTranslateY() <= 30) {
            timer.stop();

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String sql = "INSERT INTO `user_account`(`account_id`, `firstname`, `lastname`, `username`, `password`, `score`)" + " VALUES (?, ?, ?, ?, ?,?)";

            int score;
            try {
                PreparedStatement preparedStmt = connectDB.prepareStatement(sql);

                Label usernameTextField = null;
                String verifyScore = "SELECT score FROM user_account WHERE username = '" + usernameTextField.getText() + "'";
                ResultSet queryResult = preparedStmt.executeQuery(verifyScore);
                int currentScore = 0;
                while (queryResult.next()) {
                    currentScore = queryResult.getInt(1);
                }
                if (time < currentScore) {
                    String updateScore = "UPDATE user_account SET score = " + time + " WHERE username = '" + usernameTextField.getText() + "'";
                    preparedStmt.executeUpdate(updateScore);
                }
                score = currentScore;
                connectDB.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            HBox hBox2 = new HBox();
            hBox2.setTranslateX(510);
            hBox2.setTranslateY(300);

            Text text2 = new Text("YOUR TIME: " + String.valueOf(time+1));
            text2.setFont(Font.font(38));
            text2.setOpacity(0);
            text2.setSelectionFill(Color.RED);

            hBox2.getChildren().add(text2);

            FadeTransition ft2 = new FadeTransition(Duration.seconds(0.92), text2);
            ft2.setToValue(1);
            ft2.setDelay(Duration.seconds(15 * 0.15));
            ft2.play();


            String win = "YOU WON, 'VIATZA MIA'!";

            Button btn = new Button();
            btn.setStyle("-fx-background-color: #f2eb33;; -fx-text-fill: grey;");
            btn.setText("Quit");
            btn.setOnAction((ActionEvent event) -> {
//                stage.close();
            });
            btn.setTranslateX(290);
            btn.setTranslateY(120);

            HBox hBox = new HBox();
            hBox.setTranslateX(350);
            hBox.setTranslateY(240);

            hBox.getChildren().add(btn);

            root.getChildren().add(hBox);
            root.getChildren().add(hBox2);

            for (int i = 0; i < win.toCharArray().length; i++) {
                char letter = win.charAt(i);

                Text text = new Text(String.valueOf(letter));
                text.setFont(Font.font(48));
                text.setOpacity(0);
                text.setSelectionFill(Color.RED);

                hBox.getChildren().add(text);

                FadeTransition ft = new FadeTransition(Duration.seconds(0.66), text);
                ft.setToValue(1);
                ft.setDelay(Duration.seconds(i * 0.15));
                ft.play();
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tzanca Crossing!");
        Image icon = new Image("https://i.imgur.com/xho6QmL.png");
        stage.getIcons().add(icon);

        stage.setScene(new Scene(createContent()));

        stage.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    player1.setTranslateY(player1.getTranslateY() - 40);
                    break;
                case S:
                    player1.setTranslateY(player1.getTranslateY() + 40);
                    break;
                case A:
                    player1.setTranslateX(player1.getTranslateX() - 40);
                    break;
                case D:
                    player1.setTranslateX(player1.getTranslateX() + 40);
                    break;
                default:
                    break;
            }
        });

        stage.show();
        stage.setOnHidden(event -> mediaPlayer.stop());
    }
}
