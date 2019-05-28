/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.up.fc.dcc.taa;

import java.util.Random;
import java.util.Scanner;
import static pt.up.fc.dcc.taa.Operation.*;

/**
 *
 * @author Ayy lmao
 */
public class Main {

    private static int mode = 0;
    private static int randMin = 0;
    private static int randMax = 100;
    private static boolean output = true;
    private static Random rand = new Random(123);
    private static Tree<Integer> rootAvl = null;
    private static Tree<Integer> rootRB = null;
    private static Tree<KPPair> rootTreap = null;
    private static int[] sample = {5, 15, 30, 35, 40, 50};

    /**
     * TODO add min/max
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome!");
        Scanner scan = new Scanner(System.in);
        while (mode != -1) {
            System.out.println("\n\nAwaiting input\nType ?help for a list of commands\n"
                    + "---------------------------");
            String in = scan.nextLine();
            if (in.equals("?help")) {
                printOptions();
            } else {
                if (mode == 0) {
                    parseInit(in);
                } else {
                    parseTree(in);
                }
            }
        }
        System.out.println("Closing application...\nHave a nice day!");
    }

    private static void printOptions() {
        if (mode == 0) {
            System.out.println("List of commands:\n"
                    + "1 - AVL tree\n"
                    + "2 - Red-Black tree\n"
                    + "3 - Treap\n"
                    + "output - " + (output ? "disable" : "enable")
                    + " output after insert\n"
                    + "range X Y - Set X as min and Y as max for RNG\n"
                    + "seed X - Set random seed\n"
                    + "exit - Exit application\n"
                    + "--------------------------");
        } else {
            switch (mode) {
                case 1:
                    System.out.println("Mode: AVL tree");
                    break;
                case 2:
                    System.out.println("Mode: Red-Black tree");
                    break;
                case 3:
                    System.out.println("Mode: Treap");
                    break;
                default:
            }
            System.out.println("List of commands:\n"
                    + "insert X - Insert X to the tree\n"
                    + "search X - Look for X in the tree\n"
                    + "print - Print tree\n"
                    + "clear - Empty the tree"
                    + "sample - Load sample data\n"
                    + "random X - Insert X random numbers\n"
                    + "range X Y - Set X as min and Y as max for RNG\n"
                    + "seed X - Set X as random seed\n"
                    + "output - " + (output ? "disable" : "enable")
                    + " output after insert\n"
                    + "exit - Back to tree selection\n"
                    + "--------------------------------");
        }
    }

    private static void parseTree(String s) {
        String[] spl = s.split(" ");
        if (spl.length == 0) {
            return;
        }
        switch (spl[0]) {
            case "insert":
                doOperation(spl, INSERT);
                break;
            case "search":
                doOperation(spl, SEARCH);
                break;
            case "print":
                switch (mode) {
                    case 1:
                        if (rootAvl == null) {
                            System.out.println("[EMPTY]");
                        } else {
                            rootAvl.print();
                        }
                        break;
                    case 2:
                        if (rootRB == null) {
                            System.out.println("[EMPTY]");
                        } else {
                            rootRB.print();
                        }
                        break;
                    case 3:
                        if (rootTreap == null) {
                            System.out.println("[EMPTY]");
                        } else {
                            rootTreap.print();
                        }
                }
                break;
            case "clear":
                switch (mode) {
                    case 1:
                        rootAvl = null;
                        break;
                    case 2:
                        rootRB = null;
                        break;
                    case 3:
                        rootTreap = null;
                }
            case "sample":
                //TODO
                break;
            case "random":
                doOperation(spl, RANDOM);
                break;
            case "range":
                doOperation(spl, RANGE);
                break;
            case "seed":
                doOperation(spl, SEED);
                break;
            case "output":
                output = ! output;
                break;
            case "exit":
                mode = 0;
                break;
            default:
                System.out.println("Unrecognized command");
        }
    }

    private static void parseInit(String s) {
        String[] spl = s.split(" ");
        if (spl.length == 0) {
            return;
        }
        switch (spl[0]) {
            case "1":
                mode = 1;
                break;
            case "2":
                mode = 2;
                break;
            case "3":
                mode = 3;
                break;
            case "output":
                output = !output;
                break;
            case "seed":
                doOperation(spl, SEED);
                break;
            case "exit":
                mode = -1;
                break;
            case "?help":
                printOptions();
                break;
            default:
                System.out.println("Unrecognized command");
        }
    }

    private static void doOperation(String[] split, Operation oper) {
        Integer x;
        try {
            x = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument");
            return;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Missing argument");
            return;
        }
        switch (oper) {
            case INSERT:
                switch (mode) {
                    case 1:
                        if (rootAvl == null) {
                            rootAvl = new AvlNode(x);
                        } else {
                            rootAvl.insert(x);
                        }
                        if(output) {
                            rootAvl.print();
                        }
                        break;
                    case 2:
                        if (rootRB == null) {
                            rootRB = new RedBlackNode(x);
                        } else {
                            rootRB.insert(x);
                        }
                        if(output) {
                            rootRB.print();
                        }
                        break;
                    case 3:
                        KPPair kp = new KPPair(x, getRand());
                        if (rootTreap == null) {
                            rootTreap = new TreapNode(kp);
                        } else {
                            rootTreap.insert(kp);
                        }
                        if(output) {
                            rootTreap.print();
                        }
                }
                break;
            case SEARCH:
                switch (mode) {
                    case 1:
                        System.out.print(rootAvl == null
                                ? null : rootAvl.search(x));
                        break;
                    case 2:
                        System.out.print(rootRB == null
                                ? null : rootRB.search(x));
                        break;
                    case 3:
                        System.out.print(rootRB == null
                                ? null : rootRB.search(x));
                }
                break;
            case RANDOM:
                switch (mode) {
                    case 1:
                        if (x > 0 && rootAvl == null) {
                            rootAvl = new AvlNode(getRand());
                            x--;
                        }
                        for (int i = 1; i < x; i++) {
                            rootAvl.insert(getRand());
                        }
                        break;
                    case 2:
                        if (x > 0 && rootRB == null) {
                            rootRB = new RedBlackNode(getRand());
                            x--;
                        }
                        for (int i = 1; i < x; i++) {
                            rootRB.insert(getRand());
                        }
                        break;
                    case 3:
                        if (x > 0 && rootTreap == null) {
                            rootTreap = new TreapNode(new KPPair(getRand(), getRand()));
                            x--;
                        }
                        for (int i = 1; i < x; i++) {
                            rootTreap.insert(new KPPair(getRand(), getRand()));
                        }
                }
                break;
            case SEED:
                rand = new Random(x);
                break;
            case RANGE:
                randMin = x;
                try {
                    x = Integer.parseInt(split[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid argument");
                    return;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Missing argument");
                    return;
                }
                randMax = x;
        }
    }

    private static int getRand() {
        return (int) Math.round(randMin
                + rand.nextDouble() * (randMax - randMin));
    }

}
