package edu.jsu.mcis.seabattleii;

import edu.jsu.mcis.seabattleii.AbstractModel;
import edu.jsu.mcis.seabattleii.Fleet;
import edu.jsu.mcis.seabattleii.Grid;
import edu.jsu.mcis.seabattleii.Player;
import edu.jsu.mcis.seabattleii.Ship;
import edu.jsu.mcis.seabattleii.ShipSquare;
import edu.jsu.mcis.seabattleii.Square;
import java.io.PrintStream;
import java.util.HashMap;

public final class DefaultModel
extends AbstractModel {
    private Player player1;
    private Player player2;
    String status = "";

    public void initDefaults() {
        this.player1 = new Player();
        this.player2 = new Player();
        this.setStatus("Welcome to Sea Battle II!");
        this.deployFleets();
    }

    public void deployFleets() {
        Ship next;
        int i;
        Fleet fleet1 = new Fleet();
        Fleet fleet2 = new Fleet();
        Grid grid = this.player1.getPrimary();
        for (i = 0; i < fleet1.getSize(); ++i) {
            next = fleet1.get(i);
            grid.deployShip(next);
            this.player1.incrementCount();
        }
        System.out.println("Player 1:\n" + (Object)grid + "\n");
        grid = this.player2.getPrimary();
        for (i = 0; i < fleet2.getSize(); ++i) {
            next = fleet2.get(i);
            grid.deployShip(next);
            this.player2.incrementCount();
        }
        System.out.println("Player 2:\n" + (Object)grid + "\n");
    }

    public void setStatus(String text) {
        String oldText = this.status;
        this.status = text;
        this.firePropertyChange("Status", (Object)oldText, (Object)text);
    }

    public void showFleetCounts() {
        StringBuilder s = new StringBuilder();
        s.append("Player 1: ").append(this.player1.getCount()).append(" Ship(s); ");
        s.append("Player 2: ").append(this.player2.getCount()).append(" Ship(s)");
        this.setStatus(s.toString());
    }

    public void setPlayer1SquareHit(HashMap<String, Integer> s) {
        int x = s.get("x");
        int y = s.get("y");
        Square square = this.player1.getPrimary().get(x, y);
        square.hit();
        if (square instanceof ShipSquare) {
            Ship ship = ((ShipSquare)square).getShip();
            if (ship.isSunk()) {
                this.player1.decrementCount();
                if (this.player1.getCount() == 0) {
                    this.firePropertyChange("Player1GameOver", s, (Object)ship.getName());
                }
            } else {
                this.firePropertyChange("Player1ShipHit", s, (Object)ship.getName());
            }
        } else {
            this.firePropertyChange("Player2ShotMissed", s, null);
        }
        this.showFleetCounts();
    }

    public void setPlayer2SquareHit(HashMap<String, Integer> s) {
        int x = s.get("x");
        int y = s.get("y");
        Square square = this.player2.getPrimary().get(x, y);
        square.hit();
        if (square instanceof ShipSquare) {
            Ship ship = ((ShipSquare)square).getShip();
            if (ship.isSunk()) {
                this.player2.decrementCount();
                if (this.player2.getCount() == 0) {
                    this.firePropertyChange("Player2GameOver", s, (Object)ship.getName());
                }
            } else {
                this.firePropertyChange("Player2ShipHit", s, (Object)ship.getName());
            }
        } else {
            this.firePropertyChange("Player1ShotMissed", s, null);
        }
        this.showFleetCounts();
    }
}