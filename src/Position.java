public class Position{
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position(){
        this.x = 0;
        this.y = 0;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void printPos(){
        System.out.println("("+x+","+y+")");
    }

    public boolean equals(Position pos){
        if(this != pos) return false;
        if(pos == null) return false;
        if(this.getClass() != pos.getClass()) return false;
        return (x==pos.getX() && y==pos.getY());
    }

    public String toString(){
        return x+","+y;
    }
}