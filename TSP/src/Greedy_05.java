import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Author: Othman Wahab
 *
 * Description: this is the Greedy Cost Model class where all the math is.
 *
 * Class: 461
 * Introduction to artificial intelligence
 */
public class Greedy_05 {

    public ArrayList<Point> greedyEvaluator(Point[] points){
        ArrayList<Point> result = new ArrayList<>();
        result.add(points[0]);

        ArrayList<Point> possibilities = new ArrayList<>();
        for (int i = 1; i < points.length; i++)
            possibilities.add(points[i]);

        while (!possibilities.isEmpty()) {
            int index = 0;
            int minIndex = 0;
            double min = 0;
            int maxSize = possibilities.size();
            for (int i = 1; i < maxSize; i++) {
                if ((maxSize - 1) >= possibilities.size());
                else {
                    double temp = getDistance(possibilities.get(index), possibilities.get(i));
                    if (min == 0) {
                        min = getDistance(possibilities.get(0), possibilities.get(1));
                        minIndex = 0;
                    } else if (min > temp) {
                        min = getDistance(possibilities.get(index), possibilities.get(i));
                        minIndex = i;
                    }
                }
            }
            result.add(possibilities.get(minIndex));
            possibilities.remove(minIndex);
        }
        result.add(points[0]);
        double totalDistance = 0;
        int rSize = result.size() - 1;
        for (int i = 0; i < rSize; i++) {
            totalDistance += getDistance(result.get(i), result.get(i + 1));
        }
        System.out.println("total distance evaluated: " + totalDistance);
        System.out.println("Total tours test: 1");
        return result;
    }

    private double getDistance(Point<Integer> a, Point<Integer> b) {
        return Math.sqrt((Math.pow((a.getxValue() - b.getxValue()), 2)) + (Math.pow((a.getyValue() - b.getyValue()), 2)));
    }
}
