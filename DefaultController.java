package edu.jsu.mcis.seabattleii;

import edu.jsu.mcis.seabattleii.AbstractController;
import java.util.HashMap;

public class DefaultController
extends AbstractController {
    public static final String STATUS = "Status";
    public static final String PLAYER1_SHIP_DEPLOY = "Player1ShipDeploy";
    public static final String PLAYER2_SHIP_DEPLOY = "Player2ShipDeploy";
    public static final String PLAYER1_SQUARE_HIT = "Player1SquareHit";
    public static final String PLAYER1_SHIP_HIT = "Player1ShipHit";
    public static final String PLAYER1_SHIP_SUNK = "Player1ShipSunk";
    public static final String PLAYER1_SHOT_MISSED = "Player1ShotMissed";
    public static final String PLAYER1_GAME_OVER = "Player1GameOver";
    public static final String PLAYER2_SQUARE_HIT = "Player2SquareHit";
    public static final String PLAYER2_SHIP_HIT = "Player2ShipHit";
    public static final String PLAYER2_SHIP_SUNK = "Player2ShipSunk";
    public static final String PLAYER2_SHOT_MISSED = "Player2ShotMissed";
    public static final String PLAYER2_GAME_OVER = "Player2GameOver";

    public void setStatus(String text) {
        this.setModelProperty(STATUS, (Object)text);
    }

    public void player1ShotFired(HashMap<String, Integer> square) {
        this.setModelProperty(PLAYER2_SQUARE_HIT, square);
    }

    public void player2ShotFired(HashMap<String, Integer> square) {
        this.setModelProperty(PLAYER1_SQUARE_HIT, square);
    }
}