package AI.program1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class program1 {
    static int budget;
    static ArrayList<node> nodes = new ArrayList<>();//store nodes
    static ArrayList<edge> edges = new ArrayList<>();//store edges
    static List<String> list = new ArrayList<>();
    static List<String> list1 = new ArrayList<>(); //the path for ids
    static HashMap<Character, node> Nodes = new HashMap<>();//collection of nodes with names as their key
    static int size;
    static boolean end = false;//represent status of the algorithm

    public static void setSize() {//set the size of the number of nodes
        size = nodes.size();
    }

    public static int error() {//error function defined in the question
        int count = 0;
        int cost = 0;
        for (node n : nodes) if (n.getInList()) cost += n.getValue();
        for (edge e : edges) count += e.getValue();
        return count + Math.max(0, cost - budget);
    }

    public static int cost() {//cost of the current node in the list
        int cost = 0;
        for (node n : nodes) if (n.getInList()) cost += n.getValue();
        return cost;
    }

    public static void printName() {//output the name of the node in the list
        int count = 0;
        for (node n : nodes) if (n.getInList()) count++;
        if (count == 0) System.out.print("{} ");
        else for (node n : nodes) if (n.getInList()) System.out.print(n.getName() + " ");
    }

    public static void update() {// update the cost and error of current nodes in the list
        printName();
        System.out.print("cost=" + cost() + ". error=" + error());
    }

    public static void printRandomStart() {// random start in the hill climbing algorithm
        System.out.print("Randomly chosen start state: ");
        for (int j = 0; j < size; j++) if ((int) (Math.random() * 2) == 1) nodes.get(j).setInList(true);
        update();

    }

    public static void reset() {// empty the list
        for (node n : nodes) n.setInList(false);
    }

    public static void hillClimbing(int n) {// hill climbing algorithm
        for (int i = 0; i < n; i++) {
            printRandomStart();//random start
            int error = 100;//set to a very large number
            int index = -2;
            int temp;// temporary store the error
            while (index != -1) {
                index = -1;
                System.out.println();
                System.out.println("Neighbors");
                temp = error;
                for (int k = 0; k < size; k++) {
                    nodes.get(k).changeInList();
                    update();
                    if (error() < temp) {//compare neighbour
                        index = k;
                        temp = error();
                    }
                    System.out.println();
                    nodes.get(k).changeInList();
                }
                if ((index == -1) && (cost() > budget)) System.out.println("Search failed");//if index did not change, means no better neighbour
                else if ((index == -1) && (cost() <= budget)) {
                    System.out.print("Found solution ");
                    update();
                } else {
                    nodes.get(index).changeInList();
                    System.out.print("Move to ");
                    update();
                    error = temp;//update error
                }
                System.out.println();
            }
            reset();
        }
    }

    public static void hillClimbingCompact(int n) {//same thing without anything printing
        for (int i = 0; i < n; i++) {
            printRandomStart();
            int error = 100;
            int index = -2;
            int temp;
            while (index != -1) {
                index = -1;
                temp = error;
                for (int k = 0; k < size; k++) {
                    nodes.get(k).changeInList();
                    if (error() < temp) {
                        index = k;
                        temp = error();
                    }
                    nodes.get(k).changeInList();
                }
                if ((index == -1) && (cost() > budget)) {
                    System.out.println();
                    System.out.println("Search failed");
                } else if ((index == -1) && (cost() <= budget)) {
                    System.out.println();
                    System.out.print("Found solution ");
                    update();
                } else {
                    nodes.get(index).changeInList();
                    error = temp;
                }
            }
            reset();
        }
    }

    public static void printList(int n) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String s : list1) {
            if (s.length() <= n) {
                for (int i = 0; i < s.length(); i++) {
                    Nodes.get(s.charAt(i)).setInList(true);
                    sb.append(Nodes.get(s.charAt(i)).getName()).append(" ");//collect all names in the list
                    count += Nodes.get(s.charAt(i)).getValue();
                }
                if (count < budget) {//if the cost is less than budget means it is a reachable state
                    System.out.println();
                    System.out.print(sb + "cost=" + count);
                    if (checkEdges()) {//check win
                        System.out.println();
                        System.out.println("Found solution " + sb + "cost=" + count);
                        end = true;
                        break;
                    }
                }
                sb.setLength(0);
                count = 0;
                reset();
            }
        }
    }

    public static void sort() {
        for (int i = 1; i < size + 1; i++) for (String value : list) if (value.length() == i) list1.add(value);//sort the path by length
    }

    public static boolean checkEdges() {
        for (edge e : edges) if (!e.getCovered()) return false;// check if all edges are covered
        return true;
    }

    public static void ids() {
        StringBuilder sb = new StringBuilder();
        for (node n : nodes) sb.append(n.getName());
        findAllPath(sb.toString());
        for (int i = 0; i < size; i++) {//each depth
            if (!end) {
                System.out.println("depth=" + (i + 1));
                printList(i + 1);
                System.out.println();
            }
        }
    }

    public static void printListCompact(int n) {//same thing without anything printing
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String s : list1) {
            if (s.length() <= n) {
                for (int i = 0; i < s.length(); i++) {
                    Nodes.get(s.charAt(i)).setInList(true);
                    sb.append(Nodes.get(s.charAt(i)).getName()).append(" ");
                    count += Nodes.get(s.charAt(i)).getValue();
                }
                if (count < budget) {
                    if (checkEdges()) {
                        System.out.println("Found solution " + sb + "cost=" + count);
                        end = true;
                        break;
                    }
                }
                sb.setLength(0);
                count = 0;
                reset();
            }
        }
    }

    private static void find(String s, String ans, int depth) {//recurrence to find all paths
        if (s.length() == 0) {
            if (depth == ans.length()) list.add(ans);
            return;
        }
        find(s.substring(1), ans + s.charAt(0), depth);
        find(s.substring(1), ans, depth);
    }

    public static void findAllPath(String s) {
        for (int i = 0; i < size; i++) find(s, "", i + 1);
        sort();
    }

    public static void idsCompact() {//same thing without anything printing
        StringBuilder sb = new StringBuilder();
        for (node n : nodes) sb.append(n.getName());
        findAllPath(sb.toString());
        for (int i = 0; i < size; i++) {
            if (!end) {
                printListCompact(i + 1);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("C:\\Users\\Yangge\\IdeaProjects\\random\\src\\AI\\program1\\input.txt"));
        //IMPORTANT!!!!!!!! CHANGE THE ADDRESS OF THE INPUT FILE
        String firstLine = bf.readLine();
        List<String> input = new ArrayList<>();
        String line = " ";
        while (line != null) {
            input.add(line);
            line = bf.readLine();
        }// read each line
        int i = 1;
        String[] inputNodes;
        while (input.get(i).split(" ").length == 2) {
            inputNodes = input.get(i).split(" ");
            nodes.add(new node(inputNodes[0].charAt(0), Integer.parseInt(inputNodes[1])));//initials nodes
            i++;
        }
        i++;
        for (node n : nodes) Nodes.put(n.getName(), n);
        setSize();
        while (i < input.size()) {
            inputNodes = input.get(i).split(" ");
            edges.add(new edge(Nodes.get(inputNodes[0].charAt(0)), Nodes.get(inputNodes[1].charAt(0))));//initials edges
            i++;
        }
        bf.close();
        String[] s = firstLine.split(" ");
        budget = Integer.parseInt(s[0]);
        Scanner sc = new Scanner(System.in);
        System.out.println("Input 1 or 2 for different question");//ask for input
        System.out.println("1, Iterative Deepening");
        System.out.println("2, Hill Climbing");
        String user = sc.nextLine();
        if (s[1].equals("V")) {
            if (user.equals("1")) ids();
            else hillClimbing(Integer.parseInt(s[2]));
        } else {
            if (user.equals("1")) idsCompact();
            else hillClimbingCompact(Integer.parseInt(s[2]));
        }
    }
}
