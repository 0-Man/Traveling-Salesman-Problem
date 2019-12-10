import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
* Author: Othman Wahab
*
* Description: this is the Uniform Cost Model class where all the math is.
*
* Class: 461
* Introduction to artificial intelligence
*/
public class UniformCost_02 {
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

    public double getDistance(Point<Integer> a, Point<Integer> b) {
        return Math.sqrt((Math.pow((a.getxValue() - b.getxValue()), 2)) + (Math.pow((a.getyValue() - b.getyValue()), 2)));
    }


    /*
     *  Method: uniformEvaluator
     *  description:
     *      adds allPoints into Queue creating arraylists of possible solutions and outputs the final solution
     */
    public ArrayList<Point> uniformEvaluator(Point[] allPoints) {
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
                        pq.add(newPoint);
                        totalPushes++;
                    }else{
                            // adds new ArrayList to queue
                            newPoint.add(allPoints[i]);
                            pq.add(newPoint);
                            totalPushes++;
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

    public int comparePoints(ArrayList<Point> a, ArrayList<Point> b) {
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

