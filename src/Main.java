import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter STring P1: ");
        String binaryA = sc.nextLine();
        System.out.println("Enter STring P2: ");
        String binaryB = sc.nextLine();
        Maze m = new Maze(binaryA,binaryB);
        Maze newMaze = new Maze();
        Sensor sensor = new Sensor();
        Robot robot = new Robot(new Position(1,1), Robot.Direction.UP, newMaze, m, sensor);
        FastestPathAlgo ffp = new FastestPathAlgo(binaryA, binaryB,robot);
        printFastestPath(ffp.FindFastestPath());

    }
    private static void printFastestPath(Stack<Grid> path) {
        System.out.println("The number of steps is: " + (path.size() - 1) + "\n");

        Stack<Grid> pathForPrint = (Stack<Grid>) path.clone();
        Grid temp;
        System.out.println("Path:");
        while (!pathForPrint.isEmpty()) {
            temp = pathForPrint.pop();
            if (!pathForPrint.isEmpty()) System.out.print("(" + temp.getPosition().getX() + ", " + temp.getPosition().getY() + ") --> ");
            else System.out.print("(" + temp.getPosition().getX() + ", " + temp.getPosition().getY() + ")");
        }

        System.out.println("\n");
    }
}
