import java.net.CookieHandler;
import java.util.*;
/**
 * Author: Othman Wahab
 *
 * Description: this is the Class Heuristic Model class where all the math is.
 *
 * Class: 461
 * Introduction to artificial intelligence
 */
public class Genetic_01 {

    private int maxIterations = 1000000;
    private float mutationRate =(float) 0.1;

    private Point[] point1;


    /** Name: geneticEvaluator
     * Descriptions: order is to create a solution (randomly) then compare it to predictedDistance, if more then do
     * crossover and compare to predictedDistance, add to population. Have bestSolution be parent1
     * @param points
     * @param predictedDistance
     * @return
     */
    public ArrayList<Point> geneticEvaluator(Point[] points, int predictedDistance){
        ArrayList<Point> bestSolution = new ArrayList<Point>();

        point1 = points;

        // create random solution
        ArrayList<ArrayList<Point>> population = new ArrayList<ArrayList<Point>>();
        ArrayList<Point> temp1 = randomSolution(points);
        bestSolution = randomSolution(points);

        // this loop check if both parents are identical (only needed for small populations sizes
        while(getFullDistance(temp1) == getFullDistance(bestSolution)){
            temp1 = randomSolution(points);
        }

        int genCount=0;

        for (int i = 0; i < maxIterations; i++){
            ArrayList<Point> secondParent;
            if (population.size() == 0) {
                secondParent = temp1;
                System.out.println("bug fix 1");
            }
            else
                secondParent = findSecondCheapest(population);
            population = new ArrayList<ArrayList<Point>>();

            population.add(bestSolution);
            population.add(secondParent);

            // adds crossover and mutated children to population to find the cheapest

            // right here is where bestSolution and secondParent combine into one, why I don't know and it
            // breaks the code because all the contents of population then equal the same thing because,
            // crossing over the identical solution gets you the same solution.
            ArrayList<ArrayList<Point>> children = crossOver(bestSolution, secondParent);
            population.add(children.get(0));
            population.add(children.get(1));

            // finds best solution and saves it to bestSolution
            bestSolution = findCheapest(population);

            // Print every 100,000 generations
            if (i % 100000 == 0) {
                System.out.println("Generation: " + i);
                System.out.print("Best tour: ");
                for(int j = 0; j <bestSolution.size(); j++){
                    System.out.print(" " + findCity(points, bestSolution.get(j)));
                }
                System.out.println("\nTour distance: " + getFullDistance(bestSolution) + "\n");
            }
            genCount++;
            // exit parameter if bestSolution distance is less then predicted
            if(getFullDistance(bestSolution) < predictedDistance){

                break;
            }
        }

        System.out.println("\nGeneration: " + genCount);

        System.out.println("Tour distance: " + getFullDistance(bestSolution));
        return bestSolution;
    }

    /** Name: findCheapest
     * Description: findsCheapest value by looking through population's solution's full distance, if they are cheaper
     * than the best solution they are saved.
     * @param pople
     * @return
     */
    private ArrayList<Point> findCheapest(ArrayList<ArrayList<Point>> pople){
        int bestIndex = 0;
        ArrayList<Point> bestPoint = pople.get(0);

        for (int i = 0; i < pople.size(); i++){
            
            double bestdistance = getFullDistance(bestPoint);
            double indexDistance = getFullDistance(pople.get(i));
            if (indexDistance < bestdistance)
                bestPoint = pople.get(i);

        }


        return bestPoint;
    }

    /** Name: findSecondCheapest
     *Description: finds second cheapest city by removing the first one from the pop and find cheapest again
     * @param pople
     * @return
     */
    private ArrayList<Point> findSecondCheapest(ArrayList<ArrayList<Point>> pople){
        ArrayList<ArrayList<Point>> pop = pople;
        ArrayList<Point> cheapest = findCheapest(pop);
        int bestIndex = pop.indexOf(cheapest);
        pop.remove(bestIndex);

        bestIndex = 0;
        for (int i = 0; i < pop.size(); i++){
            if (getFullDistance(pop.get(i)) < getFullDistance(pop.get(bestIndex)))
                bestIndex = i;
        }

        return pop.get(bestIndex);
    }


    /** Name: crossOver
     * Description: Partially map crossover with two parent solutions, output are two mutated children solutions.
     * @param bestSolution
     * @param secondSolution
     * @return
     */
    private ArrayList<ArrayList<Point>> crossOver(ArrayList<Point> bestSolution, ArrayList<Point> secondSolution){
        // find number after starting point and before end point
        int breakpoint = (int) (Math.random() * ((bestSolution.size() - 1) - 1)) + 1;

        ArrayList<ArrayList<Point>> children = new ArrayList<>();

        ArrayList<Point> parent1 = new ArrayList<>(bestSolution);
        ArrayList<Point> parent2 = new ArrayList<>(secondSolution);

        //creating child by partially map crossover (PMX)

        for (int i = 1; i <= breakpoint; i++){
            Point<Integer> newVal = new Point<>();
            newVal = parent2.get(i);
            Collections.swap(parent1, parent1.indexOf(newVal), i);

        }
        // mutates child then adds child to children list
        children.add(mutate(parent1));
        parent1 = new ArrayList<>(bestSolution);

        // creating child 2 by PMX
        for(int i = breakpoint; i < parent1.size(); i++){
            Point<Integer> newVal = new Point<>();
            newVal = parent1.get(i);
            Collections.swap(parent2, parent2.indexOf(newVal), i);

        }
        children.add(mutate(parent2));

        return children;
    }

    /** Name: mutate
     *  Description: If the mutate is with the range of mutationRate it will mutates a child solution by randomly
     *  changing swapping two random points.
     * @param child
     * @return
     */
    private ArrayList<Point> mutate(ArrayList<Point> child){
        float mutate = (float) Math.random();
        ArrayList<Point> temp = child;
        if (mutate < mutationRate){
            int index1 = (int) (Math.random() * ((child.size() - 1) - 1)) + 1;
            int index2 = (int) (Math.random() * ((child.size() - 1) - 1)) + 1;
            ArrayList<Point> mutatedChild = child;
            Collections.swap(mutatedChild, index1, index2);
            return mutatedChild;
        }
        return temp;
    }


    /** Name: findCity
     * Description: finds the city index in the Point array by comparing the data in the point
     * @param cities
     * @param p
     * @return
     */
    private int findCity(Point[] cities, Point<Integer> p){
        for (int i = 0; i < cities.length; i++){
            if (cities[i] == p)
                return i;
        }
        return -1;
    }

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

    /**
     * Name: randomSolution
     * Description: creates a random solution for points in cities.
     * Starts at city 0 and ends at city 0
     *
     * @param points
     * @return result
     */
    private ArrayList<Point> randomSolution (Point[] points){
        ArrayList<Point> result = new ArrayList<>();
        result.add(points[0]);

        // loop to add random points to arraylist if they don't
        while (result.size() < points.length){
            // random number between 1 and points.length
            int index = (int) (Math.random() * (points.length - 1)) + 1;
            Point<Integer> temp = points[index];
            if (result.contains(temp));
            else
                result.add(temp);
        }
        // adds home city to solution end
        result.add(points[0]);

        return result;
    }
}
