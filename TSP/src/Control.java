import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import java.util.List;
/**
 * Author: Othman Wahab
 *
 * Description: this is the Control where the user side's inputs are converted to be understood by
 * the model side.
 *
 * Class: 461
 * Introduction to artificial intelligence
 */
public class Control {

    public long timeElapsed = 0;

    /** Name: chooseEval
     * Description: Control side of program converts user inputs into understandable values for the model.
     * A Point array is created to send to each model and solutions are returned and displayed with proper index, and
     * made into a graph.
     * @param file
     * @param size
     * @param eval
     * @return
     * @throws FileNotFoundException
     */
    public ArrayList<Point> chooseEval(String file, int size, int eval) throws FileNotFoundException {
        // Time evaluator
        long start = System.currentTimeMillis();
        // creates a List from csv data of type Point
        Scanner s = new Scanner(new File(file));
        Scanner dataScanner = null;
        Scanner keyIn = new Scanner(System.in);
        int index = 0;
        List<Point> pointList = new ArrayList<>();

        while (s.hasNextLine()) {
            dataScanner = new Scanner(s.nextLine());
            dataScanner.useDelimiter(",");
            Point p = new Point();

            while (dataScanner.hasNext()){
                String data = dataScanner.next();
                if (index == 0)
                    p.setX(Integer.parseInt(data));
                else
                    p.setY(Integer.parseInt(data));

                index++;
            }
            index = 0;
            pointList.add(p);
        }
        s.close();
        // Converts List to an array of the correct size
        Point[] points = new Point[size+1];
        for (int i = 0; i <= size; i++)
            points[i] = pointList.get(i);

        switch(eval){
            case 1:
                int predictedDistance;
                System.out.println("What is the predicted Distance?");
                predictedDistance = keyIn.nextInt();
                Genetic_01 gen = new Genetic_01();
                ArrayList<Point> genResult = gen.geneticEvaluator(points, predictedDistance);


                if (genResult.isEmpty())
                    System.out.println("result is empty");
                else {
                    // finds city from points' points and prints index
                    System.out.print("Final Tour: ");
                    for (int i = 0; i < genResult.size(); i++) {
                        System.out.print(" " + findCity(points, genResult.get(i)));
                    }

                    // Graphs result
                    GraphPanel graph = new GraphPanel();
                    graph.displayGraph(genResult);
                }
                break;
            case 2:
                UniformCost_02 uni = new UniformCost_02();
                ArrayList<Point> uniResult = uni.uniformEvaluator(points);
                if (uniResult.isEmpty())
                    System.out.println("result is empty");
                else {

                    System.out.println("Tour: ");
                    for (int i = 0; i < uniResult.size(); i++) {
                        System.out.print(" " + findCity(points, uniResult.get(i)) + ", ");
                    }
                    GraphPanel graph = new GraphPanel();
                    graph.displayGraph(uniResult);
                }
                break;
            case 3:
                ClassHeuristic_03 heu = new ClassHeuristic_03();
                ArrayList<Point> heuResult = heu.heuristicEvaluator(points);
                if (heuResult.isEmpty())
                    System.out.println("result is empty");
                else {
                    System.out.println("Tour: ");
                    for (int i = 0; i < heuResult.size(); i++) {
                        System.out.print(" " + findCity(points, heuResult.get(i)) + ", ");
                    }
                    GraphPanel graph = new GraphPanel();
                    graph.displayGraph(heuResult);
                }
                break;
            case 4:
                SimulatedAnnealing_04 annealing = new SimulatedAnnealing_04();
                ArrayList<Point> annealingResult = annealing.simulatedAnnealing(10, 10000, 0.9995, points);

                if (annealingResult.isEmpty())
                    System.out.println("result is empty");
                else{
                    System.out.println("Tour: ");
                    for(int i = 0; i <annealingResult.size(); i++){
                        System.out.print(" " + findCity(points, annealingResult.get(i)) + ", ");
                    }
                    GraphPanel graph = new GraphPanel();
                    graph.displayGraph(annealingResult);
                }
                break;
            case 5:
                Greedy_05 gre = new Greedy_05();
                ArrayList<Point> greResult = gre.greedyEvaluator(points);

                if (greResult.isEmpty())
                    System.out.println("result is empty");
                else {
                    System.out.println("Tour: ");
                    for (int i = 0; i < greResult.size(); i++) {
                        System.out.print(" " + findCity(points, greResult.get(i)) + ", ");
                    }
                    GraphPanel graph = new GraphPanel();
                    graph.displayGraph(greResult);
                    break;
                }
        }

        long finish = System.currentTimeMillis();

        timeElapsed = finish - start;


        return null;
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

    /** Name: DisplayGraph
     * Description: inputs parameters to display graph based on a list Solution
     * @param points
     */
    public void DisplayGraph(ArrayList<Point> points){

        GraphPanel graph = new GraphPanel();

        for (int i = 1; i <= points.size(); i++){
            int x1 = (Integer) points.get(i-1).getxValue();
            int x2 = (Integer) points.get(i-1).getxValue();
            int y1 = (Integer) points.get(i-1).getxValue();
            int y2 = (Integer) points.get(i-1).getxValue();

            Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
            graph.addLine(x1, x2, y1, y2, randomColor);
        }

        //graph.displayGraph();


    }



}