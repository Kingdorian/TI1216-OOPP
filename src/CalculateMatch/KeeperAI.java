/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculateMatch;

import ContainerPackage.ExactPosition;

/**
 *
 * @author faris
 */
public class KeeperAI extends PlayerAI {

    private final ExactPosition thisPlayer;
    private final CurrentPositions positions;
    private final boolean isOnAllyTeam;
    private final int playerID;

    /**
     * constructor: needs the position of the attacker and the positions of
     * other players
     *
     * @param thisPlayer the position of this attacker
     * @param positions the previous positions of all other players
     * @param isOnAllyTeam boolean conaining if this player is on the ally team
     */
    public KeeperAI(ExactPosition thisPlayer, CurrentPositions positions, boolean isOnAllyTeam) {
        super(positions.getPlayerID(thisPlayer), isOnAllyTeam);
        this.thisPlayer = thisPlayer;
        this.positions = positions;
        this.isOnAllyTeam = isOnAllyTeam;
        playerID = positions.getPlayerID(thisPlayer);
    }
    
    /**
     * gives the position the attacker want to move to
     *
     * @return the ExactPosition the attacker want to move to
     */
    @Override
    public ExactPosition getNextPosition() {

        return thisPlayer;

    }
    
    // TODO: Normally: stay in center of goal <><><><><><><><><><><><><><><><><><><><><><><><>
    
    // TODO: Ball close to goal: go toward y pos of ball <><><><><><><><><><><><><><><><><><><><><><><><>
    
    // TODO: Ball shot at goal: stop ball based on luck and skills <><><><><><><><><><><><><><><><><><><><><><><><>
    
}
