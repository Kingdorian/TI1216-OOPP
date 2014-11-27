/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContainerPackage;

/**
 *
 * @author faris
 */
public class Vector {
    
    private ExactPosition pointFrom;
    private ExactPosition pointTo;
    
    /**
     * Basic constructor
     */
    public Vector(){
    }
    
    /**
     * Constructor with two point parameters
     * @param point1    ExactPosition of the position
     * @param point2    ExactPosition of the direction
     */
    public Vector(ExactPosition point1, ExactPosition point2){
        this.pointFrom = point1;
        this.pointTo = point2;
    }
    
    /**
     * Constructor with four coordinates as parameters
     * @param x1    x coordinate of point 1 (double)
     * @param y1    y coordinate of point 1 (double)
     * @param x2    x coordinate of point 2 (double)
     * @param y2    y coordinate of point 2 (double)
     */
    public Vector(double x1, double y1, double x2, double y2){
        pointFrom = new ExactPosition(x1, y1);
        pointTo = new ExactPosition(x2, y2);
    }

    public ExactPosition getPointFrom() {
        return pointFrom;
    }

    public void setPointFrom(ExactPosition pointFrom) {
        this.pointFrom = pointFrom;
    }

    public ExactPosition getPointTo() {
        return pointTo;
    }

    public void setPointTo(ExactPosition pointTo) {
        this.pointTo = pointTo;
    }
    
    
    /**
     * translates the point into the direction this vector points at, over the distance specified
     * @param from      the position to translate from
     * @param distance  the distance to translate over
     * @return          the resulting position
     */
    public ExactPosition translate(ExactPosition from, double distance){
        double factor = distance/this.pointFrom.distanceTo(this.pointTo);
        double xCoordinate = (this.pointTo.getxPos() - this.pointFrom.getxPos()) * factor + from.getxPos();
        double yCoordinate = (this.pointTo.getyPos() - this.pointFrom.getyPos()) * factor + from.getyPos();
        return new ExactPosition(xCoordinate, yCoordinate);
    }
}
