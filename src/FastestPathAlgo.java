import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class FastestPathAlgo {
    private ArrayList<Grid> openList;       //array of grids with gCost calculated
    private ArrayList<Grid> closedList;     //array of grids already included in path
    private Grid current;                   //current grid
    private HashMap<Grid,Grid> parents;
    private Grid[] neighbors;              //array of 4 neighbours of current grid
    private Robot.Direction curDir;         //current direction of robot
    private double gCosts[][];               //array of real cost from start pos to [x][y]
    private Robot bot;
    private Maze exploredMaze;               //explored Maze
    private final Position Goal = new Position(18,13);
    public FastestPathAlgo(String P1, String P2, Robot bot) {
        this.exploredMaze = new Maze(P1, P2);
        init(this.exploredMaze,bot);
    }
    public void init(Maze exploredMap, Robot bot){
        this.bot = bot;
        this.exploredMaze = exploredMap;
        this.openList = new ArrayList<>();
        this.closedList = new ArrayList<>();
        this.parents = new HashMap<>();
        this.neighbors = new Grid[4];
        this.current = exploredMap.getGrid(1,1); // starts in the start zone, robot center.
        this.curDir = bot.getDirection();
        System.out.println(bot.getDirection().toString());
        this.gCosts = new double[20][15];

        // Initialise gCosts array
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 15; j++) {
                if (exploredMap.getGridType(i,j)== Grid.GridType.FREESPACE) {
                    gCosts[i][j] = 0;
                } else {
                    gCosts[i][j] = 9999;
                }
            }
        }
        openList.add(current);

        // Initialise starting point
        gCosts[1][1] = 0;
    }
    public Stack<Grid> FindFastestPath(){
        Stack<Grid> actualPath = new Stack<Grid>();
        System.out.println("Finding Fastest path to Goal.....");
        do{
            current=minCostGrid(18,13);

            if (parents.containsKey(current)) {
                curDir = getTargetDir(parents.get(current).getPosition().getX(), parents.get(current).getPosition().getY(), curDir, current);
           }
            openList.remove(current);
            closedList.add(current);

            // Check if goal is reached
            if(closedList.contains(exploredMaze.getGrid(18,13))){
                while (true) {
                    actualPath.push(current);
                    current = parents.get(current);
                    if (current == exploredMaze.getGrid(1,1)) {
                        break;
                    }
                }
                System.out.println("Goal Reached");
                return actualPath;
            }
            // Find Neighbors
            //UP
            if (!((current.getPosition().getX() + 1)>=20)&& !((current.getPosition().getX()+1)<0)){
                neighbors[0] = exploredMaze.getGrid(current.getPosition().getX() + 1, current.getPosition().getY());
                if (neighbors[0].getGridType() != Grid.GridType.FREESPACE) {
                    neighbors[0] = null;
                }
            }
            //DOWN
            if (!((current.getPosition().getX()-1)>=20)&& !((current.getPosition().getX()-1)<0)){
                neighbors[1] = exploredMaze.getGrid(current.getPosition().getX() - 1, current.getPosition().getY());
                if (neighbors[1].getGridType()!= Grid.GridType.FREESPACE) {
                    neighbors[1] = null;
                }
            }
            //LEFT
            if (!((current.getPosition().getY()-1)>=15)&&!((current.getPosition().getY() - 1)<0)){
                neighbors[2] = exploredMaze.getGrid(current.getPosition().getX(), current.getPosition().getY() - 1);
                if (neighbors[2].getGridType()!= Grid.GridType.FREESPACE) {
                    neighbors[2] = null;
                }
            }
            //RIGHT
            if (!((current.getPosition().getY() + 1)>=15) && !((current.getPosition().getY()+1)<0)) {
                neighbors[3] = exploredMaze.getGrid(current.getPosition().getX(), current.getPosition().getY() + 1);
                if (neighbors[3].getGridType() != Grid.GridType.FREESPACE) {
                    neighbors[3] = null;
                }
            }

            // Generate Children
            for (int i = 0; i < 4; i++) {
                if (neighbors[i] != null) {
                    if (openList.contains(neighbors[i])) {
                        continue;
                    }

                    if (!(openList.contains(neighbors[i]))) {
                        if(!(parents.containsKey(neighbors[i]))){
                            parents.put(neighbors[i], current);
                            //System.out.println(neighbors[i].getPosition().toString() + " First " +current.getPosition().toString());
                        }
                        gCosts[neighbors[i].getPosition().getX()][neighbors[i].getPosition().getY()] = gCosts[current.getPosition().getX()][current.getPosition().getY()] + costG(current, neighbors[i], curDir);
                        openList.add(neighbors[i]);
                    } else {
                        double currentGScore = gCosts[neighbors[i].getPosition().getX()][neighbors[i].getPosition().getY()];
                        double newGScore = gCosts[current.getPosition().getX()][current.getPosition().getY()] + costG(current, neighbors[i], curDir);
                        if (newGScore < currentGScore) {
                            gCosts[neighbors[i].getPosition().getX()][neighbors[i].getPosition().getY()] = newGScore;
                            parents.put(neighbors[i], current);
                            //System.out.println(neighbors[i].getPosition().toString() + " Second " +current.getPosition().toString());
                        }
                    }
                }
            }
        }while(!openList.isEmpty());

        System.out.println("Path not found!");
        return null;
    }

    private Grid minCostGrid(int goalX, int goalY) {
        double minCost = 9999;
        Grid bestGrid = null;
        for(int i=openList.size()-1; i>=0;i--){
            double gCost = gCosts[(openList.get(i).getPosition().getX())][(openList.get(i).getPosition().getY())];
            double cost = gCost + Hcost(openList.get(i), goalX, goalY);
            if (cost < minCost) {
                minCost = cost;
                bestGrid = openList.get(i);
            }
        }
        return bestGrid;
    }

    private double Hcost(Grid grid, int goalX, int goalY) {
        // calculate heuristic based on distance from goal.
        double movCost = (Math.abs(goalX - grid.getPosition().getX()) + Math.abs(goalY - grid.getPosition().getY())) * 10;
        if (movCost == 0)
            return 0;

        // turn cost must be added to heuristic based on number of turns
        double turnCost = 0;
        if (goalX - grid.getPosition().getX() != 0 || goalY - grid.getPosition().getY() != 0) {
            turnCost = 20;
        }
        return (movCost +turnCost);
    }

    private Robot.Direction getTargetDir(int botX, int botY, Robot.Direction botDir, Grid target) {
        if (botX - target.getPosition().getX() > 0) {
            return Robot.Direction.LEFT;
        } else if (target.getPosition().getX() - botX > 0) {
            return Robot.Direction.RIGHT;
        } else {
            if (botY - target.getPosition().getY() > 0) {
                return Robot.Direction.DOWN;
            } else if (target.getPosition().getY() - botY > 0) {
                return Robot.Direction.UP;
            } else {
                return botDir;
            }
        }
    }

    /**
     * Calculate the actual cost of moving from Cell a to Cell b (assuming both are neighbors).
     */
    private double costG(Grid current, Grid target, Robot.Direction curDir) {
        double movCost = 10; // one movement to neighbor

        double turnCost;
        Robot.Direction targetDir;
        if (current.getPosition().getX() - target.getPosition().getX() > 0)
            targetDir = Robot.Direction.LEFT;
        else if (target.getPosition().getX() - current.getPosition().getX() > 0)
            targetDir =Robot.Direction.RIGHT;
        else {
            if (current.getPosition().getY() - target.getPosition().getY() > 0)
                targetDir =Robot.Direction.DOWN;
            else if (target.getPosition().getY() - current.getPosition().getY() > 0)
                targetDir = Robot.Direction.UP;
            else
                targetDir = curDir;

        }
        int numOfTurn = Math.abs(curDir.ordinal() - targetDir.ordinal());
        if (numOfTurn > 2)
            numOfTurn = numOfTurn % 2;
        turnCost = numOfTurn * 20;

        return movCost + turnCost;
    }


}
