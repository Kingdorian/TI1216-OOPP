package application.model;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author faris
 */
public class Match {
	private Team homeTeam;
    private Team visitorTeam;
    private int pointsHomeTeam;
    private int pointsVisitorTeam;
	
		} else if (!homeTeam.equals(other.getHomeTeam())){
			System.out.println(homeTeam.toString() + "\n" + other.getHomeTeam().toString() );
			System.out.println("Hometeams not equal");
		}
		if (pointsHomeTeam != other.pointsHomeTeam){
			System.out.println("Points Hometeam not equal");
		if (pointsVisitorTeam != other.pointsVisitorTeam)
			System.out.println("Points visitor team not equal");
		}	
		return "Match [homeTeam=" + homeTeam.getName() + ", visitorTeam=" + visitorTeam.getName()



    public Match(Team homeTeam, Team visitorTeam, int pointsHomeTeam, int pointsVisitorTeam) {
        this.homeTeam = homeTeam;
        this.visitorTeam = visitorTeam;
        this.pointsHomeTeam = pointsHomeTeam;
        this.pointsVisitorTeam = pointsVisitorTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getVisitorTeam () {
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


    public boolean equals(Object obj) {
        if (!(obj instanceof Match)) {
            return false;
        }
        Match other = (Match) obj;
        if (homeTeam == null) {
            if (other.homeTeam != null) {
                return false;
            }
        } else if (!homeTeam.equals(other.homeTeam)) {
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
        return "Match [homeTeam=" + homeTeam + ", visitorTeam=" + visitorTeam
                + ", pointsHomeTeam=" + pointsHomeTeam + ", pointsVisitorTeam="
                + pointsVisitorTeam + "]";
    }
}
