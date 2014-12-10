package application.controller;


import application.model.Goalkeeper;
import application.model.Match;
import application.model.Player;
import application.model.Players;
import application.model.Team;
import java.util.ArrayList;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author faris
 */
public class GenerateMatch {
    
    /**
     * generates match results (only scores) based of player strength
     * @param homeTeam      Team: home
     * @param visitorTeam   Team: visitor
     * @return              Match: result of the match
     */
    public static Match generateMatch(Team homeTeam, Team visitorTeam){
        return generateMatch(homeTeam, visitorTeam, new Random().nextLong(), new Random().nextLong());

    }
    
    
    /**
     * this method is for testing
     * @param homeTeam
     * @param visitorTeam
     * @param seed1
     * @param seed2
     * @return 
     */
    public static Match generateMatch(Team homeTeam, Team visitorTeam, long seed1, long seed2){
        int homePower=0, visitorPower=0;
        ArrayList<Players> playerList = homeTeam.getPlayers();
        
        for(int i=0;i<playerList.size();i++){
            Players pl = playerList.get(i);
        //for(Players pl : playerList){
            if(!(pl instanceof Player))
                homePower += 2 * (((Goalkeeper) pl).getStopPower() + ((Goalkeeper) pl).getPenaltyStopPower()/2);
            else{
                homePower += ((Player) pl).getAttack() + ((Player) pl).getDefence() + ((Player) pl).getStamina();
            }
        }
        playerList = visitorTeam.getPlayers();
        for(Players pl : playerList){
            if(!(pl instanceof Player))
                visitorPower += 2* (((Goalkeeper) pl).getStopPower() + ((Goalkeeper) pl).getPenaltyStopPower()/2);
            else{
                visitorPower += ((Player) pl).getAttack() + ((Player) pl).getDefence() + ((Player) pl).getStamina();
            }
        }
        if((homeTeam.hasArtificialGrass() && !visitorTeam.hasArtificialGrass()) || (!homeTeam.hasArtificialGrass() && visitorTeam.hasArtificialGrass()))
            homePower *= 1.05;
        homePower *= 4;
        visitorPower *= 4;
        double ratio = (double) homePower/visitorPower;
        
        int homeGoals = (int) ((new Random(seed1).nextDouble()*ratio*3));
        int visitorGoals = (int) ((new Random(seed2).nextDouble()/ratio*3));

        return new Match(homeTeam, visitorTeam, homeGoals, visitorGoals);
    }
}
