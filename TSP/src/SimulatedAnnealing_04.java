import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SimulatedAnnealing_04 {

    private ArrayList<Point> previousPath, currentPath;

    /** Name: getDistance
     * Description: gets distance between two cities
     * @param a
     * @param b
     * @return
     */
    private double getDistance(Point<Integer> a, Point<Integer> b) {
        return Math.sqrt((Math.pow((a.getxValue() - b.getxValue()), 2)) + (Math.pow((a.getyValue() - b.getyValue()), 2)));
    }

    /** Name: getFullDistance
     * Description: gets full distance within a solution
     * @param path
     * @return
     */
    private double getFullDistance(ArrayList<Point> path){
        // loop to find total distance of path
        double totalDistance = 0;
        int rSize = path.size() - 1;
        for (int i = 0; i < rSize; i++) {
            totalDistance += getDistance(path.get(i), path.get(i + 1));
        }
        return totalDistance;
    }

    /** Name: swapCities
     * Description: swaps two random cities in a solution
     * @param path
     */
    private void swapCities(ArrayList<Point> path){
        // finds random number within range of index
        // ((int) (Math.random() * (maximum - minimum))) + minimum;
        int a = ((int) (Math.random()*((path.size()-1) - 1))) + 1;
        int b = ((int) (Math.random()*((path.size()-1) - 1))) + 1;


        // saves previous path
        previousPath = currentPath;
        // swap points in currentpath
        Point<Integer> x = path.get(a);
        Point<Integer> y = path.get(b);
        currentPath.set(a, y);
        currentPath.set(b, x);
    }

    /** Name: revertSwap
     *  Description: reverts swap
     */
    private void revertSwap() {
        currentPath = previousPath;
    }

    /** Name: simulatedAnnealing
     *  Description: performs simulatedAnnealing on points, to create an optimal solution.
     * @param startingTemp
     * @param numberOfIterations
     * @param coolingRate
     * @param points
     * @return
     */
    public ArrayList<Point> simulatedAnnealing(double startingTemp, int numberOfIterations, double coolingRate, Point[] points){
        double t = startingTemp;
        ArrayList<Point> path = initialPath(points);
        double bestDistance = getFullDistance(path);

        currentPath = path;

        // start simulation loop
        for(int i = 0; i < numberOfIterations; i++){
            if (t > 0.1){
                swapCities(path);
                double currentDistance = getFullDistance(path);
                if (currentDistance < bestDistance){
                    bestDistance = currentDistance;
                } else if (Math.exp((bestDistance - currentDistance) / t) < Math.random()){
                    revertSwap();
                }
                t *= coolingRate;
            }else {
                continue;
            }
        }
        System.out.println("total distance evaluated: " + bestDistance);
        System.out.println("Total tours test: 1");
        return currentPath;
    }

    /** Name: initialPath
     *  Description: creates an initialPath using greedy algorithm.
     * @param points
     * @return
     */
    private ArrayList<Point> initialPath(Point[] points){
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

        return result;
    }
}
