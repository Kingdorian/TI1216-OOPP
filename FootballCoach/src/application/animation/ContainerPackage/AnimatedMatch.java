/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.ContainerPackage;

import java.util.ArrayList;

/**
 *
 * @author faris
 */
public class AnimatedMatch {

    private ArrayList<PositionsTimeSlice> positionsList = new ArrayList<>();

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
