/**
 * Author: Othman Wahab
 *
 * Description:
 * Traveling Salesman Problem:
 * This is the main viewer class, this algorithm is based on the model control view methodology. So this class will
 * generally consist of viewer side instructions of what algorithm they would like to perform and how many cities they
 * would like to go through.
 *
 * Class: 461
 * Introduction to artificial intelligence
 *
 */
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main (String[] args) throws FileNotFoundException {

        Scanner keyIn = new Scanner(System.in);

        System.out.println("Welcome to TSP Calculator: ");
        System.out.println("This program works by the following main menu, after choosing which algorithm you " +
                "would like the algorithm to use you will then be prompted to choose how my cities you would like the" +
                " algorithm to go through. ");

        boolean loop = true;
        int choice = 0;
        int size = 3;
        Control cn = new Control();


        while(loop) {
            System.out.println("Main Menu:");
            System.out.println("1: Genetic Algorithm");
            System.out.println("2: Uniform Cost");
            System.out.println("3: Heuristic");
            System.out.println("4: Simulated Annealing");
            System.out.println("5: Greed Algorithm");
            System.out.println("6: Choose number of cities (default: 4)");
            System.out.println("7: Quit");

            choice = keyIn.nextInt();

            switch(choice){
                case 1:
                    /*
                    * This Evaluator is the Genetic Algorithm
                    * The way the first tour is made is through Greedy Search, the crossover is
                    * Partially Mapped Crossover (PMX). The way this works is:
                    * create a random breakpoint integer between the start and end points, we input two parents of our
                    * two best solutions. We then swap parent1 points from (after) start to breakpoint with points that
                    * were assigned in parent2, we do the same thing with parent2 but swap from (after) breakpoint to
                    * (before) endpoint.
                    *
                     */
                    cn.chooseEval("TSPDataComma.csv", size, 1);
                    System.out.println("\nTime: " + cn.timeElapsed);
                    break;
                case 2:
                    cn.chooseEval("TSPDataComma.csv", size, 2);
                    System.out.println("\nTime: " + cn.timeElapsed);
                    break;
                case 3:
                    cn.chooseEval("TSPDataComma.csv", size, 3);
                    System.out.println("\nTime: " + cn.timeElapsed);
                    break;
                case 4:
                    cn.chooseEval("TSPDataComma.csv", size, 4);
                    System.out.println("\nTime: " + cn.timeElapsed);
                    break;
                case 5:
                    /*
                    *   This is Simulated Annealing
                    * The Cooling rate is currently hardcoded to 0.9995
                    * you can see this in case 4 in Control.java
                    *
                     */
                    cn.chooseEval("TSPDataComma.csv", size, 5);
                    System.out.println("\nTime: " + cn.timeElapsed);
                    break;
                case 6:
                    System.out.println("Type in to change number of cities:");
                    size = keyIn.nextInt();
                    size = size - 1;
                    break;
                case 7:
                    loop = false;
                    System.exit(0);
                    break;
            }
        }
    }
}
