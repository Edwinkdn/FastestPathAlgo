public class Grid {
    private Position pos;
    private GridType gridType;
    private boolean travelled;
    private boolean reachable;

    public enum GridType{
        FREESPACE,
        OBSTACLE,
        UNKNOWN
    }

    public Grid(Position pos){
        this.pos = pos;
        this.gridType = GridType.UNKNOWN;
        this.travelled = false;
        this.reachable = false;
    }

    public Position getPosition(){
        return pos;
    }

    public GridType getGridType(){
        return gridType;
    }

    public boolean isTravelled(){
        return travelled;
    }

    public boolean isObstacle() { return gridType == GridType.OBSTACLE;}

    public void setUnreachable() { reachable = false;}

    public void setReachable() { reachable = true; }

    public boolean isFreeSpace() { return gridType == GridType.FREESPACE;}

    public void setTravelled(){
        travelled = true;
    }

    public void setGridType(GridType gridType){
        if(this.gridType != GridType.UNKNOWN) return;
        this.gridType = gridType;
        if(gridType == GridType.OBSTACLE) setUnreachable();
    }

    public String toString(){
        return pos.toString()+gridType;
    }
}