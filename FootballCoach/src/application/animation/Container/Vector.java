/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.Container;

/**
 * This is a vector container class. It contains 2 points, defining the position
 * and the direction. This class also contains a lot of methods to do
 * calculations with this vector
 *
 * @author Faris
 */
public class Vector {

    private final Position pointFrom;
    private final Position pointTo;

    /**
     * Constructor with two point parameters
     *
     * @param pointFrom ExactPosition of the position
     * @param pointTo ExactPosition of the direction
     */
    public Vector(Position pointFrom, Position pointTo) {
        this.pointFrom = pointFrom;
        this.pointTo = pointTo;
    }

    /**
     * translates the point into the direction this vector points at, over the
     * specified distance.
     *
     * @param from the position to translate from
     * @param distance the distance to translate over
     * @return the resulting position
     */
    public Position translate(Position from, double distance) {
        double factor = distance / this.pointFrom.distanceTo(this.pointTo);
        double xCoordinate = (this.pointTo.getxPos() - this.pointFrom.getxPos()) * factor + from.getxPos();
        double yCoordinate = (this.pointTo.getyPos() - this.pointFrom.getyPos()) * factor + from.getyPos();
        return new Position(xCoordinate, yCoordinate);
    }

    /**
     * Get the length of the vector
     *
     * @return the length of the vector
     */
    public double getLength() {
        return Math.sqrt(Math.pow(pointFrom.getxPos() - pointTo.getxPos(), 2) + Math.pow(pointFrom.getyPos() - pointTo.getyPos(), 2));
    }

    /**
     * Check if this vector can intersect with the line between points p1 and p2
     * (the parameters)
     *
     * @param p1 point 1
     * @param p2 point 2
     * @param checkDirection if true, also check if the the direction is the
     * same as is specified with the shouldBeMovingRight parameter
     * @param shouldBeMovingRight if true, check if the ball is moving right,
     * else check if the ball is moving left
     * @return if the vector will intersect with the line between p1 and p2
     */
    public boolean intersectsWith(Position p1, Position p2, boolean checkDirection, boolean shouldBeMovingRight) {

        // if also checking for direction and the specified  direction is not 
        // the same as the direction from pointFrom to pointTo, return false
        if (checkDirection) {
            if (shouldBeMovingRight && pointFrom.getxPos() > pointTo.getxPos()) {
                // is moving left, but specified was should move right, return false
                return false;
            } else if (!shouldBeMovingRight && pointFrom.getxPos() < pointTo.getxPos()) {
                // is moving right, but specified was should move left, return false
                return false;
            }
        }

        Position intersectionPoint = getIntersectionPoint(p1, p2);

        if (intersectionPoint == null) {
            return false; // parallel lines
        }
        Position highest, lowest;
        if (p1.getyPos() > p2.getyPos()) {
            highest = p1;
            lowest = p2;
        } else {
            highest = p2;
            lowest = p1;
        }

        return highest.getyPos() > intersectionPoint.getyPos() && lowest.getyPos() < intersectionPoint.getyPos();
    }

    /**
     * Check if this vector intersects with the line between points p1 and p2
     * (the parameters)
     *
     * @param p1 point 1
     * @param p2 point 2
     * @return if the vector intersects with the line between p1 and p2
     */
    public boolean intersectsWith(Position p1, Position p2) {
        return intersectsWith(p1, p2, false, false);
    }

    /**
     * Get the intersection point of the line through p1 and p2 and the vector
     * line
     *
     * @param p1 point 1
     * @param p2 point 2
     * @return the intersection point, or null if no intersection point
     */
    public Position getIntersectionPoint(Position p1, Position p2) {

        if (pointFrom.getxPos() - pointTo.getxPos() != 0 && p1.getxPos() - p2.getxPos() != 0) {
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
            if (a - c == 0) {
                return null;
            }
            double x = (b - d) / (a - c);
            double y = a * x + b;

            return new Position(x, y);
        } else if (pointFrom.getxPos() - pointTo.getxPos() == 0 && p1.getxPos() - p2.getxPos() == 0) {
            // both lines are vertical, so both lines are parallel, so return null
            return null;
        } else if (pointFrom.getxPos() - pointTo.getxPos() == 0) {
            // only the vector line is vertical

            // line through p1 and p2:
            // y = cx + d
            // c = (y2 - y1)/(x2 - x1)
            // d = y1 - a x1
            double a = (p1.getyPos() - p2.getyPos()) / (p1.getxPos() - p2.getxPos());
            double b = p1.getyPos() - (a * p1.getxPos());

            double x = pointFrom.getxPos();
            double y = a * x + b;
            return new Position(x, y);
        } else {
            // only the line through p1 and p2 is vertical
            // line through p1 and p2:
            // y = cx + d
            // c = (y2 - y1)/(x2 - x1)
            // d = y1 - a x1
            double a = (pointFrom.getyPos() - pointTo.getyPos()) / (pointFrom.getxPos() - pointTo.getxPos());
            double b = pointFrom.getyPos() - (a * pointFrom.getxPos());

            double x = p1.getxPos();
            double y = a * x + b;
            return new Position(x, y);
        }
    }

    /**
     * Get the position of the vector
     *
     * @return the position of the vector
     */
    public Position getPointFrom() {
        return pointFrom;
    }

    /**
     * Get the direction position of the vector
     *
     * @return the direction position of the vector
     */
    public Position getPointTo() {
        return pointTo;
    }

}
