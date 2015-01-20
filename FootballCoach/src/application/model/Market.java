/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model;

import java.util.ArrayList;
import java.util.Random;

import application.Main;

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
    
    public void computerBuyPlayer(Competition competition){
    	computerBuyPlayer(competition, new Random().nextLong());
    }
    
    public void computerSellPlayer(Competition competition){
    	computerSellPlayer(competition, new Random().nextLong());
    }
    
    public void computerBuyPlayer(Competition competition, long seed) {
        ArrayList<Team> teams = new ArrayList<>();
        for (Team t : competition.getTeams()) {
            teams.add(t);
        }
        Random random = new Random(seed);
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i) != competition.getTeamByName(Main.getChosenTeamName())) {
                if (Math.pow(random.nextDouble(), teams.get(i).getPlayers().size() - 14) > random.nextDouble()) {
                    buyWhichPlayer(competition, teams.get(i), random);
                }
            }
        }
    }
    
    public void computerSellPlayer(Competition competition, long seed) {
    	ArrayList<Team> teams = new ArrayList<>();
    	for (Team t : competition.getTeams()) {
    		teams.add(t);
    	}
    	Random random = new Random(seed);
    	for (int i = 0; i < teams.size(); i++) {
    		if (teams.get(i) != competition.getTeamByName(Main.getChosenTeamName())) {
    			if (Math.pow(0.99, teams.get(i).getPlayersNotForSale(competition).size() - 14) < random.nextDouble()) {
    				sellWhichPlayer(competition, teams.get(i), random);
    			}
    		}
    	}
    }
    
    private void buyWhichPlayer(Competition competition, Team team, Random random) {
        if (Math.pow(0.5, team.getAmountGoalkeepers() * 2.0 - 2) > random.nextDouble()) //1 gk = 100%, 2gk = 25%, 3gk = 6%, 4gk = 1.6%
        {
            buyGoalkeeper(competition, team, random);
        }

        if (Math.pow(0.5, team.getAmountDefenders() / 2.0 - 2) > random.nextDouble()) {
            buyPlayers(competition, team, "Defender", random);
        }

        if (Math.pow(0.5, team.getAmountMidfielders() / 1.5 - 4) > random.nextDouble()) {
            buyPlayers(competition, team, "Midfielder", random);
        }

        if (Math.pow(0.5, team.getAmountForwards() - 4) > random.nextDouble()) {
            buyPlayers(competition, team, "Forward", random);
        }
    }
    
    private void buyPlayers(Competition competition, Team team, String kind, Random random){
    	boolean transfered = false;
    	for(int i = 0; i < playersForSale.size() && transfered == false; i++){
    		if(team.getBudget() >= getPlayersPrice(playersForSale.get(i)) && (playersForSale.get(i).getKind().equals(kind)) || (playersForSale.get(i).getKind().equals("Allrounder")) && (team.getPlayers().contains(playersForSale.get(i))) == false){
    			if(Math.pow(0.5, (1.2 * (4 - playersForSale.get(i).getAbility())) * (0.17 * (19 - competition.getRank(team)))) > random.nextDouble()){
    				competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
					transfered = true;
					removePlayer(playersForSale.get(i));
    			}
    		}
    	}
    }
    
    private void buyGoalkeeper(Competition competition, Team team, Random random){
    	boolean transfered = false;
    	for(int i = 0; i < playersForSale.size() && transfered == false; i++){
    		if(team.getBudget() >= getPlayersPrice(playersForSale.get(i)) && (playersForSale.get(i).getKind().equals("Goalkeeper")) && (team.getPlayers().contains(playersForSale.get(i))) == false){
    			if(Math.pow(0.5, (1.22 * (4 - playersForSale.get(i).getAbility())) * (0.18 * (19 - competition.getRank(team)))) > random.nextDouble()){
    				competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
					transfered = true;
					removePlayer(playersForSale.get(i));
    			}
    		}
    	}
    }
    
    private void sellWhichPlayer(Competition competition, Team team, Random random){
    	if(Math.pow(0.5, team.getAmountDefenders() / 2.7 - 2) < random.nextDouble()){
    		sellPlayers(competition, team, "Defender", random);
    	}
    	if(Math.pow(0.5, team.getAmountGoalkeepers() * 2 - 5.5) < random.nextDouble()){
    		sellGoalkeeper(competition, team, random);
    	}
    	if(Math.pow(0.5, team.getAmountMidfielders() * 1.7 - 8) < random.nextDouble()){
    		sellPlayers(competition, team, "Midfielder", random);
    	}
    	if (Math.pow(0.5, team.getAmountForwards() - 4) < random.nextDouble()){
    		sellPlayers(competition, team, "Forward", random);
    	}
    }
    
    private void sellPlayers(Competition competition, Team team, String kind, Random random){
    	boolean transfered = false;
    	for(int i = 0; i < team.getPlayers().size() && transfered == false; i++){
    		if(team.getPlayers().get(i).getKind().equals(kind) || team.getPlayers().get(i).getKind().equals("Allrounder") && playersForSale.contains(team.getPlayers().get(i)) == false){
    			if(Math.pow(0.5, (team.getPlayers().get(i).getAbility() * 1.2) - 1.5) > random.nextDouble()){
    				addPlayer(team.getPlayers().get(i), team.getPlayers().get(i).getPrice());
    				transfered = true;
    			}
    		}
    	}
    }
    
    private void sellGoalkeeper(Competition competition, Team team, Random random){
    	boolean transfered = false;
    	for(int i = 0; i < team.getPlayers().size() && transfered == false; i++){
    		if(playersForSale.contains(team.getPlayers().get(i)) == false){
    			if(team.getPlayers().get(i).getKind().equals("Goalkeeper") && Math.pow(0.5, (team.getPlayers().get(i).getAbility() * 1.2) - 1.5) > random.nextDouble()){
    				addPlayer(team.getPlayers().get(i), team.getPlayers().get(i).getPrice());
    				transfered = true;
    			}
    		}
    		else{
    			transfered = true;
    		}
    	}
    }
}
