package application.model;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Faris, Dorian
 */
public class Match {

    private Team homeTeam;
    private Team visitorTeam;
    private int pointsHomeTeam = -1;
    private int pointsVisitorTeam = -1;

    /**
     * Equals method
     * @param obj object to compare to
     * @return if this and obj are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Match)) {
            return false;
        }
        Match other = (Match) obj;
        if (homeTeam == null) {
            if (other.getHomeTeam() != null) {
                return false;
            }
        } else if (!homeTeam.equals(other.getHomeTeam())) {
            return false;
        }
        if (pointsHomeTeam != other.pointsHomeTeam) {
            return false;
        }
        if (pointsVisitorTeam != other.pointsVisitorTeam) {
            return false;
        }
        if (visitorTeam == null) {
            if (other.visitorTeam != null) {
                return false;
            }
        } else if (!visitorTeam.equals(other.visitorTeam)) {
            return false;
        }
        return true;
    }

    /**
     * Convert this class to a string
     * @return string containing information bout this class
     */
    @Override
    public String toString() {
        if (homeTeam == null || visitorTeam == null) {
            return "";
        }
        return "Match [homeTeam=" + homeTeam.getName()
                + ", visitorTeam=" + visitorTeam.getName()
                + ", pointsHomeTeam=" + pointsHomeTeam + ", pointsVisitorTeam="
                + pointsVisitorTeam + "]";
    }

    /**
     * Constructor
     * @param homeTeam the home team
     * @param visitorTeam the visitor team
     * @param pointsHomeTeam the points scored by the home team
     * @param pointsVisitorTeam the points scored by the visitor team
     */
    public Match(Team homeTeam, Team visitorTeam, int pointsHomeTeam, int pointsVisitorTeam) {
        this(homeTeam, visitorTeam);
        this.pointsHomeTeam = pointsHomeTeam;
        this.pointsVisitorTeam = pointsVisitorTeam;
    }

    /**
     * Construcot
     * @param homeTeam the home team
     * @param visitorTeam the visitor team
     */
    public Match(Team homeTeam, Team visitorTeam) {
        this.homeTeam = homeTeam;
        this.visitorTeam = visitorTeam;
    }

    /**
     * Get the home team
     * @return the home team 
     */
    public Team getHomeTeam() {
        return homeTeam;
    }

    /**
     * Set the home team
     * @param homeTeam the home team
     */
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    /**
     * Get the visitor team
     * @return the visitor team
     */
    public Team getVisitorTeam() {
        return visitorTeam;
    }

    /**
     * Set the visitor team
     * @param visitorTeam the visitor team
     */
    public void setVisitorTeam(Team visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    /**
     * Get the amount of points scored by the home team
     * @return the amount of points scored by the home team
     */
    public int getPointsHomeTeam() {
        return pointsHomeTeam;
    }

    /**
     * Set the amount of points scored by the home team
     * @param pointsHomeTeam the amount of points scored by the home team
     */
    public void setPointsHomeTeam(int pointsHomeTeam) {
        this.pointsHomeTeam = pointsHomeTeam;
    }

    /**
     * Get the amount of points scored by the visitor team
     * @return the amount of points scored by the visitor team
     */
    public int getPointsVisitorTeam() {
        return pointsVisitorTeam;
    }

    /**
     * Set the amount of points scored by the visitor team
     * @param pointsVisitorTeam the amount of points scored by the visitor team
     */
    public void setPointsVisitorTeam(int pointsVisitorTeam) {
        this.pointsVisitorTeam = pointsVisitorTeam;
    }

}
