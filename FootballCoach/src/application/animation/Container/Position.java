/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.Container;

/**
 * This class stores a position as two doubles (and it makes sure no position
 * outside of the field can be declared)
 *
 * @author Faris
 */
public class Position {

    private double xPos; // stores x position
    private double yPos; // stores y position
    private boolean onField = true;

    /**
     * Constructor with x and y positions
     *
     * @param xPos
     * @param yPos
     */
    public Position(double xPos, double yPos) {
        this.yPos = yPos;
        this.xPos = xPos;

        // make sure nothing will ever get drawn outside the screen
        if (yPos < 0) {
            this.yPos = 0;
        }
        if (xPos < 0) {
            this.xPos = 0;
        }
        if (yPos > 760) {
            this.yPos = 760;
        }
        if (xPos > 1010) {
            this.xPos = 1010;
        }
    }

    /**
     * Basic constructor without parameters
     */
    public Position() {
    }

    /**
     * Get the x-coordinate
     *
     * @return x-coordinate
     */
    public double getxPos() {
        return xPos;
    }

    /**
     * Set the x-coordinate
     *
     * @param xPos x-coordinate
     */
    public void setxPos(double xPos) {
        this.xPos = xPos;

        if (xPos < 0) {
            this.xPos = 0;
        }
        if (xPos > 1010) {
            this.xPos = 1010;
        }
    }

    /**
     * Get the y-coordinate
     *
     * @return y-coordinate
     */
    public double getyPos() {
        return yPos;
    }

    /**
     * Set the y-coordinate
     *
     * @param yPos y-coordinate
     */
    public void setyPos(double yPos) {
        this.yPos = yPos;

        if (yPos < 0) {
            this.xPos = 0;
        }
        if (yPos > 760) {
            this.xPos = 760;
        }
    }

    /**
     * Gives the distance between this position and a different position
     *
     * @param other player to get disntance to
     * @return double: the distance
     */
    public double distanceTo(Position other) {
        if (other == null) {
            return 5000;
        }
        return Math.sqrt(Math.pow(this.getxPos() - other.getxPos(), 2) + Math.pow(this.getyPos() - other.getyPos(), 2));
    }

    /**
     * Compares this position to an object
     *
     * @param o Object to compare to
     * @return if this is equal to Object o
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) {
            return false;
        }

        Position other = (Position) o;
        return other.getxPos() == this.getxPos() && other.getyPos() == this.getyPos();
    }

    /**
     * Returns if this player is on the field
     *
     * @return a boolean: is the player currently on the field?
     */
    public boolean isOnField() {
        return onField;
    }

    /**
     * set: is this player on the field?
     *
     * @param onField a boolean: is the player currently on the field?
     */
    public void setOnField(boolean onField) {
        this.onField = onField;
    }

    /**
     * Get a new instance of this position, but with a translated x-coordinate
     *
     * @param x delta-x
     * @return new position with translated x-coordinate
     */
    public Position getTranslateX(double x) {
        Position result = new Position();
        result.setxPos(xPos + x);
        result.setyPos(yPos);
        return result;
    }

    /**
     * Get a new instance of this position, but with a translated y-coordinate
     *
     * @param y delta-y
     * @return new position with translated y-coordinate
     */
    public Position getTranslateY(double y) {
        Position result = new Position();
        result.setxPos(xPos);
        result.setyPos(yPos + y);
        return result;
    }

    /**
     * Get a string representation of this instance
     *
     * @return a string representation of this instance
     */
    @Override
    public String toString() {
        return "position: x: " + xPos + ", y: " + yPos;
    }
}
