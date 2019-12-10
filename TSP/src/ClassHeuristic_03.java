import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Author: Othman Wahab
 *
 * Description: this is the Class Heuristic Model class where all the math is.
 *
 * Class: 461
 * Introduction to artificial intelligence
 */
public class ClassHeuristic_03 {
    // private PriorityQueue with overridden compare method
    private PriorityQueue<ArrayList<Point>> pq = new PriorityQueue<ArrayList<Point>>(20, new Comparator<ArrayList<Point>>() {
        @Override
        public int compare(ArrayList<Point> a, ArrayList<Point> b) {
            double distanceA = 0;
            double distanceB = 0;
            int aSize = a.size() - 1;
            int bSize = b.size() - 1;

            for (int i = 0; i < aSize; i++) {
                distanceA += getDistance(a.get(i), a.get(i + 1));
            }
            for (int i = 0; i < bSize; i++) {
                distanceB += getDistance(b.get(i), b.get(i + 1));
            }

            if (distanceA > distanceB) {
                return 1;
            } else if (distanceA < distanceB) {
                return -1;
            }
            return 0;
        }
    });

    /** Name: getDistance
     * Description: gets distance between two cities
     * @param a
     * @param b
     * @return
     */
    private double getDistance(Point<Integer> a, Point<Integer> b) {
        return Math.sqrt((Math.pow((a.getxValue() - b.getxValue()), 2)) + (Math.pow((a.getyValue() - b.getyValue()), 2)));
    }


    /** Name: intersectionDeterminate
     *  Description: determines whether the points in a solution intersect using an intersection algorithm
     * @param points
     * @return
     */
    private boolean intersectionDeterminate(ArrayList<Point> points){
        Point<Integer> a = points.get(points.size()-2);
        Point<Integer> b = points.get(points.size()-1);

            int maxSize = points.size() - 2;
            for(int i = 0; i < maxSize; i++){

                Point<Integer> c = points.get(i);
                Point<Integer> d = points.get(i + 1);

                // determinant is calculated for two sets of coordinates and set into these variables
                double determinant1 = ((a.getxValue() - c.getxValue()) * (b.getyValue() - c.getyValue())) - ((b.getxValue() - c.getxValue()) * (a.getyValue() - c.getyValue())) ;
                double determinant2 = ((a.getxValue() - d.getxValue()) * (b.getyValue() - d.getyValue())) - ((b.getxValue() - d.getxValue()) * (a.getyValue() - d.getyValue())) ;
                double determinant3 = ((c.getxValue() - a.getxValue()) * (d.getyValue() - a.getyValue())) - ((d.getxValue() - a.getxValue()) * (c.getyValue() - a.getyValue())) ;
                double determinant4 = ((c.getxValue() - b.getxValue()) * (d.getyValue() - b.getyValue())) - ((d.getxValue() - b.getxValue()) * (c.getyValue() - b.getyValue())) ;

                boolean signA;
                boolean signB;
                boolean signC;
                boolean signD;

                //if the determinant is positive then the sign is positive or true
                if (determinant1 >= 0)
                    signA = true;
                else
                    signA = false;

                if (determinant2 >= 0)
                    signB = true;
                else
                    signB = false;

                if (determinant3 >= 0)
                    signC = true;
                else
                    signC = false;

                if (determinant4 >= 0)
                    signD = true;
                else
                    signD = false;

                //if the signs match between a and d and b and c then the lines intersect
                if (signA != signD && signB != signC){
                    return true;
                }
            }
                return false;
    }

    /**
     *  Method: heuristicEvaluator
     *  description:
     *      this is to actually add the Point types into the Queue.
     */
    public ArrayList<Point> heuristicEvaluator(Point[] allPoints) {
        // add initial point 0 connections to queue
        for (int i = 1; i < allPoints.length; i++) {
            ArrayList<Point> temp = new ArrayList();
            temp.add(allPoints[0]);
            temp.add(allPoints[i]);
            pq.add(temp);
        }

        // this then will pop pq and do the next process
        int totalPops = 0;
        int totalPushes = 0;
        boolean loop = true;
        int maxSize = allPoints.length;
        boolean intersectbool;
        while (loop) {
            // check if pq is null at head
            if (pq.peek() == null) {
                loop = false;
                System.out.println("pq is empty");
                return null;
            } else {   // pop head pq point
                ArrayList<Point> temp = new ArrayList<>(pq.poll());
                totalPops++;
                // find all possibilities
                // make sure they don't repeat, also no possibilities of repeats in queue (i think)
                for (int i = 0; i < maxSize; i ++){
                    ArrayList<Point> newPoint = new ArrayList<>(temp);
                    if (temp.contains(allPoints[i]) && temp.size() != maxSize){
                        //System.out.println(newPoint.size() + "skipped allPoints element" + allPoints[i].getxValue());
                    }else if (temp.size() == maxSize) {
                        newPoint.add(allPoints[0]);
                        intersectbool = intersectionDeterminate(temp);
                        if (!intersectbool) {
                            pq.add(newPoint);
                            totalPushes++;
                        }
                    }else{
                        // adds new ArrayList to queue
                        newPoint.add(allPoints[i]);
                        intersectbool = intersectionDeterminate(temp);
                        if (!intersectbool) {
                            pq.add(newPoint);
                            totalPushes++;
                        }
                    }
                }
                // check to stop loop
                if (temp.size() >= (maxSize+1) && pq.peek().size() >= (maxSize +1) && comparePoints(temp, pq.peek()) == 1){
                    loop = false;
                    System.out.println("total pops from queue: " + totalPops);
                    System.out.println("total pushes to queue: " + totalPushes);
                    return temp;
                }
            }
        }
        return null;
    }

    /** Name: comparePoints
     *  Description: compares distances of two solutions and determines which is bigger
     * @param a
     * @param b
     * @return
     */
    private int comparePoints(ArrayList<Point> a, ArrayList<Point> b) {
        double distanceA = 0;
        double distanceB = 0;
        int aSize = a.size() - 1;
        int bSize = b.size() - 1;

        for (int i = 0; i < aSize; i++) {
            distanceA += getDistance(a.get(i), a.get(i + 1));
        }
        for (int i = 0; i < bSize; i++) {
            distanceB += getDistance(b.get(i), b.get(i + 1));
        }

        if (distanceA < distanceB) {
            System.out.println("total distance evaluated: " + distanceA);
            return 1;
        } else if (distanceA > distanceB) {
            return -1;
        }
        return 0;
    }
}
