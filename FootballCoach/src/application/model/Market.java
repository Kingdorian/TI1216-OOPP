/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model;

import java.util.ArrayList;

/**
 *
 * @author faris
 */
public class Market {

    private ArrayList<Players> playersForSale = new ArrayList<>();
    private ArrayList<Integer> price = new ArrayList<>();

    public ArrayList<Players> getPlayersForSale() {
        return playersForSale;
    }

    public void addPlayer(Players player, int price) {
        if (!playersForSale.contains(player)) {
            playersForSale.add(player);
            this.price.add(price);
        }
    }

    public void removePlayer(Players player) {
        for (int i = 0; i < playersForSale.size(); i++) {
            if (playersForSale.get(i).equals(player)) {
            	System.out.println(playersForSale.toString());
                playersForSale.remove(i);
                price.remove(i);
            }
        }
    }

    public int getPlayersPrice(Players player) {
        for (int i = 0; i < playersForSale.size(); i++) {
            if (playersForSale.get(i).equals(player)) {
                return price.get(i);
            }
        }
        return 0;
    }
}
