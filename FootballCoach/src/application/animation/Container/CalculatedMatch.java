/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.Container;

import java.util.ArrayList;

/**
 * This class is used to store all frames of positions of a match
 *
 * @author Faris
 */
public class CalculatedMatch {

    private ArrayList<PositionFrame> positionsList = new ArrayList<>();

    /**
     * Get a frame based on the index
     *
     * @param frameNumber int: index of the frame
     * @return the requested frame
     */
    public PositionFrame getPosition(int frameNumber) {
        return positionsList.get(frameNumber);
    }

    /**
     * Add a frame
     *
     * @param frame a frame to add
     */
    public void addPositionFrame(PositionFrame frame) {
        this.positionsList.add(frame);
    }

    /**
     * Get the amount of frames in this match
     *
     * @return int: amout of frames
     */
    public int amoutOfFrames() {
        return positionsList.size();
    }

}
