/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.CalculateMatch;


import application.animation.ContainerPackage.ExactPosition;
import java.util.ArrayList;

/**
 *
 * @author faris
 */
public class DefenderAI extends PlayerAI{
    
    private final ExactPosition thisPlayer;
    private final CurrentPositions positions;
    private final boolean isOnAllyTeam;
    private final int playerID;
    
    
    /**
     * constructor: needs the position of the attacker and the positions of other players
     * @param thisPlayer  the position of this attacker
     * @param positions     the previous positions of all other players
     * @param isOnAllyTeam  boolean conaining if this player is on the ally team
     */
    public DefenderAI(ExactPosition thisPlayer, CurrentPositions positions, boolean isOnAllyTeam, int playerID){
        super(playerID, isOnAllyTeam);
        this.thisPlayer = thisPlayer;
        this.positions = positions;
        this.isOnAllyTeam = isOnAllyTeam;
        this.playerID = playerID;
    }
    
    @Override
    public ExactPosition getNextPosition(){
        if(isOnAllyTeam){
            if(thisPlayer.equals(positions.getClosestAllyTo(BallAI.getCurrentBallPosition()))) // if closest player of own team to the ball
                return moveTowardBall(); // closest to ball
            else if(BallAI.getCurrentBallPosition().getxPos() < MIDDLE_LINE_X)
                return defend();// defend
            else 
                return attack(); // ball on other side
        } else if(thisPlayer.equals(positions.getClosestEnemyTo(BallAI.getCurrentBallPosition()))) // if closest player of own team to the ball
                return moveTowardBall(); // closest to ball
            else if(BallAI.getCurrentBallPosition().getxPos() > MIDDLE_LINE_X)
                return defend(); // defend
            else
                return attack(); // ball on other side
    }
    
    // Defender is the closest on to the ball
    private ExactPosition moveTowardBall(){
        if (positions.isClosestToBall(thisPlayer) && thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 25) {
            if ((isOnAllyTeam && thisPlayer.getxPos() < MIDDLE_LINE_X) || (!isOnAllyTeam && thisPlayer.getxPos() > MIDDLE_LINE_X)) {
                if (enoughLuckToDefendBall(thisPlayer, playerID, positions, isOnAllyTeam)) {
                    //defend ball

                    //1. check if any player in front of the midfielder is reachable and not being defended
                    ExactPosition target = null;
                    if (isOnAllyTeam) {
                        for (ExactPosition player : positions.getAllyTeam()) {
                            if (player.getxPos() > thisPlayer.getxPos() && positions.getClosestEnemyTo(player).distanceTo(player) > 100 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target) && thisPlayer.distanceTo(player) < 150) {
                                target = player;
                            }
                        }
                    } else {
                        for (ExactPosition player : positions.getEnemyTeam()) {
                            if (player.getxPos() < thisPlayer.getxPos() && positions.getClosestAllyTo(player).distanceTo(player) > 100 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target) && thisPlayer.distanceTo(player) < 150) {
                                target = player;
                            }
                        }
                    }

                    if (target != null) {
                        BallAI.shootToTeammate(target, isOnAllyTeam); // shoot ball to closest ally who's not being defended
                    } //2. else check if you can move forward yourself
                    else if ((isOnAllyTeam && positions.getClosestEnemyTo(thisPlayer).distanceTo(thisPlayer) > 100) || (!isOnAllyTeam && positions.getClosestAllyTo(thisPlayer).distanceTo(thisPlayer) < 100)) {
                        ExactPosition closestArr[] = get2ClosestPlayers(thisPlayer, positions);
                        if (closestArr != null && closestArr.length > 1 && closestArr[1] != null) {
                            ExactPosition direction = getDirectionInBetween(thisPlayer, closestArr[0], closestArr[1]);
                            BallAI.shootBallTo(direction, isOnAllyTeam);
                        } else if (isOnAllyTeam) {
                            BallAI.shootBallTo(new ExactPosition(thisPlayer.getxPos() + 10, thisPlayer.getyPos()), isOnAllyTeam);
                        } else {
                            BallAI.shootBallTo(new ExactPosition(thisPlayer.getxPos() - 10, thisPlayer.getyPos()), isOnAllyTeam);
                        }
                    } //3. else shoot to any ally not being defended
                    else {
                        if (isOnAllyTeam) {
                            for (ExactPosition player : positions.getAllyTeam()) {
                                if (positions.getClosestEnemyTo(player).distanceTo(player) > 100 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target)) {
                                    target = player;
                                }
                            }
                        } else {
                            for (ExactPosition player : positions.getEnemyTeam()) {
                                if (positions.getClosestAllyTo(player).distanceTo(player) > 100 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target)) {
                                    target = player;
                                }
                            }
                        }
                        if (target != null) {
                            BallAI.shootToTeammate(target, isOnAllyTeam); // shoot ball to closest ally who's not being defended
                        } //4. else try to walk forward anyway
                        else {
                            ExactPosition closestArr[] = get2ClosestPlayers(thisPlayer, positions);
                            if (closestArr != null && closestArr.length > 1 && closestArr[1] != null) {
                                ExactPosition direction = getDirectionInBetween(thisPlayer, closestArr[0], closestArr[1]);
                                BallAI.shootBallTo(direction, isOnAllyTeam);
                            } else if (isOnAllyTeam) {
                                BallAI.shootBallTo(new ExactPosition(thisPlayer.getxPos() + 10, thisPlayer.getyPos()), isOnAllyTeam);
                            } else {
                                BallAI.shootBallTo(new ExactPosition(thisPlayer.getxPos() - 10, thisPlayer.getyPos()), isOnAllyTeam);
                            }
                        }
                    }
                }
            } else {
                if (enoughLuckToShootBall(thisPlayer, playerID, positions, isOnAllyTeam)) {
                    //attack ball
                    
                    //1. check if any player in front of the midfielder is reachable and not being defended
                    ExactPosition target = null;
                    if (isOnAllyTeam) {
                        for (ExactPosition player : positions.getAllyTeam()) {
                            if (player.getxPos() > thisPlayer.getxPos() && positions.getClosestEnemyTo(player).distanceTo(player) > 100 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target) && thisPlayer.distanceTo(player) < 150) {
                                target = player;
                            }
                        }
                    } else {
                        for (ExactPosition player : positions.getEnemyTeam()) {
                            if (player.getxPos() < thisPlayer.getxPos() && positions.getClosestAllyTo(player).distanceTo(player) > 100 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target) && thisPlayer.distanceTo(player) < 150) {
                                target = player;
                            }
                        }
                    }

                    if (target != null) {
                        BallAI.shootToTeammate(target, isOnAllyTeam); // shoot ball to closest ally who's not being defended
                    } 
                    //2. else move forward yourself
                    else{
                        ExactPosition closestArr[] = get2ClosestPlayers(thisPlayer, positions);
                        if (closestArr != null && closestArr.length > 1 && closestArr[1] != null) {
                            ExactPosition direction = getDirectionInBetween(thisPlayer, closestArr[0], closestArr[1]);
                            BallAI.shootBallTo(direction, isOnAllyTeam);
                        } else if (isOnAllyTeam) {
                            BallAI.shootBallTo(new ExactPosition(thisPlayer.getxPos() + 10, thisPlayer.getyPos()), isOnAllyTeam);
                        } else {
                            BallAI.shootBallTo(new ExactPosition(thisPlayer.getxPos() - 10, thisPlayer.getyPos()), isOnAllyTeam);
                        }
                    }
                }
            }
        }
        // move toward ball
        return getPosBySpeed(RUNNING_SPEED, thisPlayer, BallAI.getCurrentBallPosition());
    }
    
    // Ball far from middle line: defend (try to get ball)
    private ExactPosition defend(){
        // if you are the second or third closest player to the ball, move toward it. (if you are the closest you won't be in this method)
        // else go to same x-pos as the ball.
        ArrayList<ExactPosition> closest = new ArrayList<>();
        if(isOnAllyTeam){
            for(ExactPosition p : positions.getAllyTeam())
                if(p.distanceTo(BallAI.getCurrentBallPosition()) < thisPlayer.distanceTo(BallAI.getCurrentBallPosition()))
                    closest.add(p);
        } else
            for(ExactPosition p : positions.getEnemyTeam())
                if(p.distanceTo(BallAI.getCurrentBallPosition()) < thisPlayer.distanceTo(BallAI.getCurrentBallPosition()))
                    closest.add(p);
        // if less than 3 other players are closer to the ball than you, go toward the ball.
        if(closest.size() < 3){
            return getPosBySpeed(RUNNING_SPEED, thisPlayer, BallAI.getCurrentBallPosition());
        }
        // else move toward the same x-pos as the ball, but use your favorite y-pos as y-pos.
        double xPos;
        double yPos;
        if(isOnAllyTeam){
            yPos = CurrentPositions.getAllyInfo(playerID).getFavoritePosition().getyPos();
            if(BallAI.getCurrentBallPosition().getxPos() > MIDDLE_LINE_X - 100)
                xPos = BallAI.getCurrentBallPosition().getxPos() - 50;
            else if(BallAI.getCurrentBallPosition().getxPos() > MIDDLE_LINE_X - 380)
                xPos = BallAI.getCurrentBallPosition().getxPos() - 20;
            else
                xPos = 110;
            return getPosBySpeed(RUNNING_SPEED, thisPlayer, new ExactPosition(xPos, yPos));
        } else{
            yPos = CurrentPositions.getEnemyInfo(playerID).getFavoritePosition().getyPos();
            if(BallAI.getCurrentBallPosition().getxPos() < MIDDLE_LINE_X + 100)
                xPos = BallAI.getCurrentBallPosition().getxPos() + 50;
            else if(BallAI.getCurrentBallPosition().getxPos() < MIDDLE_LINE_X + 380)
                xPos = BallAI.getCurrentBallPosition().getxPos() + 20;
            else
                xPos = 910;
        }
        return getPosBySpeed(RUNNING_SPEED, thisPlayer, new ExactPosition(xPos, yPos));
    }
    
    // Ball far from middle line: stay back, close to opponent attackers (wait)
    private ExactPosition attack(){
        if(isOnAllyTeam)
            return getPosBySpeed(RUNNING_SPEED, thisPlayer, CurrentPositions.getAllyInfo(playerID).getFavoritePosition().getTranslateX(50));
        else
            return getPosBySpeed(RUNNING_SPEED, thisPlayer, CurrentPositions.getEnemyInfo(playerID).getFavoritePosition().getTranslateX(-50));

    }
}
