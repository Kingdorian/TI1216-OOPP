/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.Container;

import application.model.Goalkeeper;
import application.model.Player;
import application.model.Players;
import application.model.Team;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Faris
 */
public class TeamPositions {

    private Team team;
    private ArrayList<Players> playerList;
    private final ArrayList<Player> fieldPlayerList;
    private final ArrayList<Goalkeeper> keeperList;

    private ArrayList<PlayerIndex> selectedPlayers = new ArrayList<>();

    /**
     * Class to store a combination of playerinfo and index
     */
    private class PlayerIndex {

        public Players player;
        public int index;
        public Circle circle;

        /**
         * Constructor
         *
         * @param player the player
         * @param index the index
         */
        PlayerIndex(Players player, Circle circle, int index) {
            this.player = player;
            this.circle = circle;
            this.index = index;
        }

        /**
         * equals method
         *
         * @param o object to compare to
         * @return if this and o are equal
         */
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof PlayerIndex)) {
                return false;
            }
            PlayerIndex other = (PlayerIndex) o;
            return this.player == other.player || this.index == other.index; // either index or player has to be the same
        }
    }

    /**
     * Constructor
     *
     * @param team team of which this are the teams positions
     */
    public TeamPositions(Team team) {
        this.team = team;
        playerList = team.getPlayers();

        // sort playerList by kind and ability (best players of each kind higher up in the list)
        Collections.sort(playerList, (Players p1, Players p2) -> {
            int kind = kindToNumber(p1) - kindToNumber(p2);
            if (kind != 0) {
                return kind;
            } else {
                return Double.compare(p2.getAbility(), p1.getAbility());
            }
        });

        //add all field playerList of the team to a list
        this.fieldPlayerList = new ArrayList<>();
        for (Players player : playerList) {
            if (player instanceof Player) {
                fieldPlayerList.add((Player) player);
            }
        }

        //add all goalkeepers of the team to a list
        this.keeperList = new ArrayList<>();
        for (Players player : playerList) {
            if (player instanceof Goalkeeper) {
                keeperList.add((Goalkeeper) player);
            }
        }
    }

    private int kindToNumber(Players p) {
        switch (p.getKind()) {
            case "Forward":
                return 4;
            case "Allrounder":
                return 3;
            case "Midfielder":
                return 2;
            case "Defender":
                return 1;
            default:
                return 0;
        }
    }

    public void addPlayer(Players player, Circle circle, int id) {
        remove(player, circle, id);
        selectedPlayers.add(new PlayerIndex(player, circle, id));
    }

    /**
     * remove each player with the same index OR player info from the player
     * lists
     *
     * @param player
     */
    private void remove(Players player, Circle circle, int index) {

        for (int i = 0; i < selectedPlayers.size(); i++) {
            PlayerIndex pi = selectedPlayers.get(i);
            if (pi.player.equals(player) || pi.circle.equals(circle) || pi.index == index) {
                selectedPlayers.remove(i--); // lower i, so no instance will be skipped
            }
        }
    }

    /**
     * get a player with a certain index
     *
     * @param index the index of the player
     * @return the player (or null, if there is no player with the specified
     * index)
     */
    public Players getPlayer(int index) {

        for (PlayerIndex pi : selectedPlayers) {
            if (pi.index == index) {
                return pi.player;
            }
        }

        return null;
    }

    public ArrayList<Players> getPlayers() {
        ArrayList<Players> result = new ArrayList<>();

        for (PlayerIndex pi : selectedPlayers) {
            result.add(pi.player);
        }

        return result;
    }

    public PlayerInfo getKeeper() {

        Goalkeeper keeper = null;
        Circle circle = null;

        for (PlayerIndex selectedPlayer : selectedPlayers) {
            if (selectedPlayer.player instanceof Goalkeeper) {
                keeper = (Goalkeeper) selectedPlayer.player;
                circle = selectedPlayer.circle;
            }
        }
        if (keeper == null || circle == null) {
            return null;
        }

        return new PlayerInfo(keeper.getStopPower(), keeper.getPenaltyStopPower(),
                new Position(circle.getCenterX(), circle.getCenterY()));
    }

    public ArrayList<PlayerInfo> getDefenders() {

        ArrayList<PlayerInfo> result = new ArrayList<>();

        Player defender;
        Circle circle;

        for (PlayerIndex selectedPlayer : selectedPlayers) {
            if ((selectedPlayer.circle.getFill().equals(Color.BLUE) && selectedPlayer.circle.getCenterX() < 358 && selectedPlayer.player instanceof Player) || // left team got blue color
                    (selectedPlayer.circle.getFill().equals(Color.RED) && selectedPlayer.circle.getCenterX() > 663 && selectedPlayer.player instanceof Player)) { // right team got red color
                defender = (Player) selectedPlayer.player;
                circle = selectedPlayer.circle;

                result.add(new PlayerInfo(defender.getAttack(), defender.getStamina(), defender.getDefence(),
                        new Position(circle.getCenterX(), circle.getCenterY())));
            }
        }
        for (PlayerInfo result1 : result) {
            System.out.println("defenders  = " + result1.getFavoritePosition());
        }
        return result;
    }

    public ArrayList<PlayerInfo> getMidfielders() {

        ArrayList<PlayerInfo> result = new ArrayList<>();

        Player midfielder;
        Circle circle;

        for (PlayerIndex selectedPlayer : selectedPlayers) {
            if ((selectedPlayer.circle.getFill().equals(Color.BLUE) && selectedPlayer.circle.getCenterX() >= 358 && selectedPlayer.circle.getCenterX() < 663 && selectedPlayer.player instanceof Player) || // left team got blue color
                    (selectedPlayer.circle.getFill().equals(Color.RED) && selectedPlayer.circle.getCenterX() > 358 && selectedPlayer.circle.getCenterX() <= 663 && selectedPlayer.player instanceof Player)) { // right team got red color
                midfielder = (Player) selectedPlayer.player;
                circle = selectedPlayer.circle;

                result.add(new PlayerInfo(midfielder.getAttack(), midfielder.getStamina(), midfielder.getDefence(),
                        new Position(circle.getCenterX(), circle.getCenterY())));
            }
        }
        for (PlayerInfo result1 : result) {
            System.out.println("midfielders  = " + result1.getFavoritePosition());
        }
        return result;
    }

    public ArrayList<PlayerInfo> getAttackers() {

        ArrayList<PlayerInfo> result = new ArrayList<>();

        Player attacker;
        Circle circle;

        for (PlayerIndex selectedPlayer : selectedPlayers) {
            if ((selectedPlayer.circle.getFill().equals(Color.BLUE) && selectedPlayer.circle.getCenterX() >= 663 && selectedPlayer.player instanceof Player) || // left team got blue color
                    (selectedPlayer.circle.getFill().equals(Color.RED) && selectedPlayer.circle.getCenterX() <= 358 && selectedPlayer.player instanceof Player)) { // right team got red color
                attacker = (Player) selectedPlayer.player;
                circle = selectedPlayer.circle;

                result.add(new PlayerInfo(attacker.getAttack(), attacker.getStamina(), attacker.getDefence(),
                        new Position(circle.getCenterX(), circle.getCenterY())));
            }
        }
        for (PlayerInfo result1 : result) {
            System.out.println("attackers  = " + result1.getFavoritePosition());
        }
        return result;
    }

    public boolean checkValid() {
        return selectedPlayers.size() == 11;
    }

    public void setDefaultLeftPlayers() {

        //set best keeper
        this.addPlayer(keeperList.get(0), DefaultPos.L0.circle, 0);

        //set field playerList
//        for (int i = 1; i < 11; i++) {
//            this.addPlayer(fieldPlayerList.get(i-1), DefaultPos.Loop.getL(i).circle, i);
//        }
        // put the best defenders in the field
        for (int i = 1; i < 5; i++) {
            this.addPlayer(fieldPlayerList.get(i - 1), DefaultPos.Loop.getL(i).circle, i);
        }

        // put the best midfielders in the field
        int offsett = 4;
        while (fieldPlayerList.get(offsett).getKind().equals("Defender") && offsett < fieldPlayerList.size() - 4) {
            offsett++;
        }

        for (int i = 5; i < 8; i++, offsett++) {
            this.addPlayer(fieldPlayerList.get(offsett), DefaultPos.Loop.getL(i).circle, i);
        }

        // put the best attackers in the field
        while ((fieldPlayerList.get(offsett).getKind().equals("Midfielder") || fieldPlayerList.get(offsett).getKind().equals("Allrounder")) && offsett < fieldPlayerList.size() - 4) {
            offsett++;
        }

        for (int i = 8; i < 11; i++, offsett++) {
            this.addPlayer(fieldPlayerList.get(offsett), DefaultPos.Loop.getL(i).circle, i);
        }
    }

    public void setDefaultRightPlayers() {

        //set best keeper
        this.addPlayer(keeperList.get(0), DefaultPos.R0.circle, 0);

        //set field playerList
//        for (int i = 1; i < 11; i++) {
//            this.addPlayer(fieldPlayerList.get(i-1), DefaultPos.Loop.getR(i).circle, i);
//        }
        for (int i = 1; i < 5; i++) {
            this.addPlayer(fieldPlayerList.get(i - 1), DefaultPos.Loop.getR(i).circle, i);
        }

        // put the best midfielders in the field
        int offsett = 4;
        while (fieldPlayerList.get(offsett).getKind().equals("Defender") && offsett < fieldPlayerList.size() - 4) {
            offsett++;
        }

        for (int i = 5; i < 8; i++, offsett++) {
            this.addPlayer(fieldPlayerList.get(offsett), DefaultPos.Loop.getR(i).circle, i);
        }

        // put the best attackers in the field
        while ((fieldPlayerList.get(offsett).getKind().equals("Midfielder") || fieldPlayerList.get(offsett).getKind().equals("Allrounder")) && offsett < fieldPlayerList.size() - 4) {
            offsett++;
        }

        for (int i = 8; i < 11; i++, offsett++) {
            this.addPlayer(fieldPlayerList.get(offsett), DefaultPos.Loop.getR(i).circle, i);
        }
    }

    public ArrayList<Player> getFieldPlayerList() {
        return fieldPlayerList;
    }

    public ArrayList<Goalkeeper> getKeeperList() {
        return keeperList;
    }

}
