public class Robot{
    private Position center;
    private Direction direction;
    private Maze emptyMap;
    private Maze realMap;
    private Sensor sensor;

    public enum Direction{
        UP, LEFT, DOWN, RIGHT
    }

    public Robot(Position center, Direction direction, Maze emptyMap, Maze realMap, Sensor sensor){
        this.center = center;
        this.direction = direction;
        this.emptyMap = emptyMap;
        this.realMap = realMap;
        this.sensor = sensor;
    }

    public void turnLeft(){
        this.direction = Direction.values()[(direction.ordinal()+1)%4];
        System.out.println(direction);
    }

    public void turnRight(){
        this.direction = Direction.values()[(direction.ordinal()+3)%4];
        System.out.println(direction);
    }

    public void printMap(){
        emptyMap.printMap(center.getX(), center.getY());
    }


    public void move(){
        switch(direction){
            case UP:
                if(!sensor.isObstacleFront(center.getX(), center.getY(), emptyMap, direction))
                    center.setX(center.getX()-1);
                break;
            case LEFT:
                if(!sensor.isObstacleFront(center.getX(), center.getY(), emptyMap, direction))
                    center.setY(center.getY()-1);
                break;
            case DOWN:
                if(!sensor.isObstacleFront(center.getX(), center.getY(), emptyMap, direction))
                    center.setX(center.getX()+1);
                break;
            case RIGHT:
                if(!sensor.isObstacleFront(center.getX(), center.getY(), emptyMap, direction))
                    center.setY(center.getY()+1);
        }
        System.out.println("center is "+center.toString() + " " + direction);
    }

    private void paintMap(){
        int x = center.getX();
        int y = center.getY();
        for(int i = x - 1; i<=x+1;++i){
            for(int j = y-1; j<=y+1; ++j){
                emptyMap.setGridType(i, j, Grid.GridType.FREESPACE);
            }
        }
    }

    public void sense(){
        paintMap();
        sensor.sense(realMap, emptyMap, center, direction);
    }

    public boolean isObstacleFront(){
        return sensor.isObstacleFront(center.getX(), center.getY(), emptyMap, direction);
    }

    public boolean isObstacleLeft(){
        return sensor.isObstacleLeft(center.getX(), center.getY(), emptyMap, direction);
    }

    public boolean isObstacleRight(){
        return sensor.isObstacleRight(center.getX(), center.getY(), emptyMap, direction);
    }

    public Direction getDirection() {
        return this.direction;
    }
}