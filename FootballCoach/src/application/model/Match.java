package application.model;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author faris, Dorian
 */
public class Match {

    private Team homeTeam;
    private Team visitorTeam;
    private int pointsHomeTeam = -1;
    private int pointsVisitorTeam = -1;

    public boolean equals(Object obj) {
    	System.out.println(obj.toString());
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

    public String toString() {
        if (homeTeam == null || visitorTeam == null) {
            return "";
        }
        return "Match [homeTeam=" + homeTeam.getName()
                + ", visitorTeam=" + visitorTeam.getName()
                + ", pointsHomeTeam=" + pointsHomeTeam + ", pointsVisitorTeam="
                + pointsVisitorTeam + "]";
    }

    public Match(Team homeTeam, Team visitorTeam, int pointsHomeTeam, int pointsVisitorTeam) {
        this(homeTeam, visitorTeam);
        this.pointsHomeTeam = pointsHomeTeam;
        this.pointsVisitorTeam = pointsVisitorTeam;

//System.out.println("points constructor");
    }

    public Match(Team homeTeam, Team visitorTeam) {
        this.homeTeam = homeTeam;
        this.visitorTeam = visitorTeam;
//System.out.println(pointsHomeTeam + pointsVisitorTeam);
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(Team visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public int getPointsHomeTeam() {
        return pointsHomeTeam;
    }

    public void setPointsHomeTeam(int pointsHomeTeam) {
        this.pointsHomeTeam = pointsHomeTeam;
    }

    public int getPointsVisitorTeam() {
        return pointsVisitorTeam;
    }

    public void setPointsVisitorTeam(int pointsVisitorTeam) {
        this.pointsVisitorTeam = pointsVisitorTeam;
    }

}
