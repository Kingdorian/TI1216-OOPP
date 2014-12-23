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
    
    private final ExactPosition pointFrom;
    private final ExactPosition pointTo;
    
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
    
    public double getLength(){
        return Math.sqrt(Math.pow(pointFrom.getxPos() - pointTo.getxPos(), 2) + Math.pow(pointFrom.getyPos() - pointTo.getyPos(), 2)) ;
    }
    
    
    /**
     * check if this vector can intersect with the line between points p1 and p2 (the parameters)
     * @param p1    point 1
     * @param p2    point 2
     * @param checkDirection    if true, also check if the the direction is the same as is specified with the shouldBeMovingRight parameter
     * @param shouldBeMovingRight   if true, check if the ball is moving right, else check if the ball is moving left
     * @return      if the vector will intersect with the line between p1 and p2
     */
    public boolean intersectsWith(ExactPosition p1, ExactPosition p2, boolean checkDirection, boolean shouldBeMovingRight){

        // if also checking for direction and the specified  direction is not 
        // the same as the direction from pointFrom to pointTo, return false
        if(checkDirection){
            if(shouldBeMovingRight && pointFrom.getxPos() > pointTo.getxPos()){
                // is moving left, but specified was should move right, return false
                return false;
            } else if(!shouldBeMovingRight && pointFrom.getxPos() < pointTo.getxPos()){
                // is moving right, but specified was should move left, return false
                return false;
            }
        }
        
        ExactPosition intersectionPoint = getIntersectionPoint(p1, p2);
        
        if(intersectionPoint == null)
            return false; // parallel lines
        
        ExactPosition highest , lowest;
        if(p1.getyPos() > p2.getyPos()){
            highest = p1;
            lowest = p2;
        } else{
            highest = p2;
            lowest = p1;
        }
        
        return highest.getyPos() > intersectionPoint.getyPos() && lowest.getyPos() < intersectionPoint.getyPos();
    }

    /**
     * check if this vector intersects with the line between points p1 and p2 (the parameters)
     * @param p1    point 1
     * @param p2    point 2
     * @return      if the vector intersects with the line between p1 and p2
     */
    public boolean intersectsWith(ExactPosition p1, ExactPosition p2){
        return intersectsWith(p1, p2, false, false);
    }
    
    
    /**
     * get the intersection point of the line through p1 and p2 and the vector line
     * @param p1    point 1
     * @param p2    point 2
     * @return      the intersection point, or null if no intersection point
     */
    private ExactPosition getIntersectionPoint(ExactPosition p1, ExactPosition p2){
        
        if(pointFrom.getxPos() - pointTo.getxPos() != 0 && p1.getxPos() - p2.getxPos() != 0){
            // both lines are not vertical, so apply basic high school math to get the intersection point:
            
            // line 1 (vector line):
            // y = ax + b
            // a = (y2 - y1)/(x2 - x1)
            // b = y1 - a x1
            double a = (pointFrom.getyPos() - pointTo.getyPos()) / (pointFrom.getxPos() - pointTo.getxPos());
            double b = pointFrom.getyPos() - (a * pointFrom.getxPos());

            // line 2 (line through p1 and p2):
            // y = cx + d
            // c = (y2 - y1)/(x2 - x1)
            // d = y1 - a x1
            double c = (p1.getyPos() - p2.getyPos()) / (p1.getxPos() - p2.getxPos());
            double d = p1.getyPos() - (c * p1.getxPos());

            // intersection point: x = (d-b)/(a-c)
            // if a-c = 0, lines are parallel, so return null
            if(a-c == 0)
                return null;
            double x = (b-d) / (a-c);
            double y = a*x + b;

            return new ExactPosition(x, y);
        } else if(pointFrom.getxPos() - pointTo.getxPos() == 0 && p1.getxPos() - p2.getxPos() == 0){
            // both lines are vertical, so both lines are parallel, so return null
            return null;
        } else if(pointFrom.getxPos() - pointTo.getxPos() == 0){
            // only the vector line is vertical
            
            // line through p1 and p2:
            // y = cx + d
            // c = (y2 - y1)/(x2 - x1)
            // d = y1 - a x1
            double a = (p1.getyPos() - p2.getyPos()) / (p1.getxPos() - p2.getxPos());
            double b = p1.getyPos() - (a * p1.getxPos());
            
            double x = pointFrom.getxPos();
            double y = a*x + b;
            return new ExactPosition(x, y); 
        } else{
            // only the line through p1 and p2 is vertical
            // line through p1 and p2:
            // y = cx + d
            // c = (y2 - y1)/(x2 - x1)
            // d = y1 - a x1
            double a = (pointFrom.getyPos() - pointTo.getyPos()) / (pointFrom.getxPos() - pointTo.getxPos());
            double b = pointFrom.getyPos() - (a * pointFrom.getxPos());
            
            double x = p1.getxPos();
            double y = a*x + b;
            return new ExactPosition(x, y);
        }
    }

    public ExactPosition getPointFrom() {
        return pointFrom;
    }

    public ExactPosition getPointTo() {
        return pointTo;
    }
    
}
