/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContainerPackage;

import java.util.ArrayList;

/**
 *
 * @author faris
 */
public class Match {
    
    private TeamInfo opponentTeamInfo, allyTeamInfo;
    private ArrayList<PositionsTimeSlice> positionsList = new ArrayList<>();

    public TeamInfo getOpponentTeamInfo() {
        return opponentTeamInfo;
    }

    public void setOpponentTeamInfo(TeamInfo opponentTeamInfo) {
        this.opponentTeamInfo = opponentTeamInfo;
    }

    public TeamInfo getAllyTeamInfo() {
        return allyTeamInfo;
    }

    public void setAllyTeamInfo(TeamInfo allyTeamInfo) {
        this.allyTeamInfo = allyTeamInfo;
    }

    public PositionsTimeSlice getPositions(int timeElapsedInSeconds) {
        return positionsList.get(timeElapsedInSeconds);
    }

    public void addPositionSlice(PositionsTimeSlice positions) {
        this.positionsList.add(positions);
    }
    
    public int amoutOfSlices(){
        return positionsList.size();
    }
}
