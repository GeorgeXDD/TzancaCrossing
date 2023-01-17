package com.example.tzancashootingv1;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public interface Commons {
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGTH = 600;
    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGTH = 800;
    public static final int GROUND = 440;
    public static final int BOMB_HEIGHT = 5;
    public static final int ALIEN_HEIGHT = 25;
    public static final int ALIEN_WIDTH = 25;
    public static final int BORDER_RIGHT = 30;
    public static final int BORDER_LEFT = 30;
    public static final int GO_DOWN = 25;
    public static final int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    public static final int CHANCE = 5;
    public static final int DELAY = 17;
    public static final int PLAYER_WIDTH = 25;
    public static final int PLAYER_HEIGHT = 25;

    void start(Stage stage) throws Exception;
}
