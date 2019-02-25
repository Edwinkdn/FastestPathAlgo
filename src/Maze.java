public class Maze{
    public static final int ROW = 20;
    public static final int COLUMN = 15;

    private Grid[][] map;

    public Maze(){
        this.map = new Grid[ROW][COLUMN];
        for(int i = 0; i<ROW; ++i){
            for(int j = 0; j<COLUMN; ++j){
                map[i][j] = new Grid(new Position(i,j));
            }
        }
    }

    public Maze(String binaryA, String binaryB){
        this.map = new Grid[ROW][COLUMN];
        initMap(binaryA, binaryB);
    }

    private void initMap(String binaryA, String binaryB){
        char[] binaryCharA = binaryA.toCharArray();
        char[] binaryCharB = binaryB.toCharArray();
        int m = 0;
        int n = 0;
        for(int i = ROW-1; i>=0; --i){
            for(int j = 0; j<COLUMN; ++j){
                map[i][j] = new Grid(new Position(i,j));
                if(binaryCharA[m++]=='1'){
                    map[i][j].setTravelled();
                    if(binaryCharB[n++]=='0') map[i][j].setGridType(Grid.GridType.FREESPACE);
                    else map[i][j].setGridType(Grid.GridType.OBSTACLE);
                }
            }
        }
    }

    public void setGridType(int row, int col, Grid.GridType type){
        if(row<0 || row >= ROW || col<0 || col >= COLUMN){
            System.out.printf("(%d, %d) is out of bound\n", row, col);
            return;
        }
        map[row][col].setGridType(type);
    }

    public Grid getGrid(int row, int col){
        if(row<0 || row >= ROW || col<0 || col >= COLUMN){
            System.out.printf("(%d, %d) is out of bound\n", row, col);
            System.exit(0);
        }
        return map[row][col];
    }

    public Grid.GridType getGridType(int row, int col){
        return getGrid(row, col).getGridType();
    }

    public void printMap(int row, int col){
        printEdge();
        for(int i = 0; i<ROW; ++i){
            System.out.print("|");
            for(int j = 0; j<COLUMN; ++j){
                if(i>=row-1 && i<=row+1 && j>=col-1 && j<=col+1){
                    System.out.print("r");
                }
                else{
                    Grid.GridType t = map[i][j].getGridType();
                    if(t== Grid.GridType.UNKNOWN)
                        System.out.print("?");
                    else if(t == Grid.GridType.OBSTACLE)
                        System.out.print("O");
                    else
                        System.out.print(" ");
                }
                if(j == COLUMN-1) System.out.print("|");
                else System.out.print(" ");

            }
            System.out.println();
        }
        printEdge();
    }

    private void printEdge(){
        for(int i = 0; i< COLUMN; ++i){
            System.out.print("= ");
        }
        System.out.print("=");
        System.out.println();
    }

    public String toHexPart1(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<ROW; ++i){
            for(int j = 0; j<COLUMN; ++j){
                if(map[i][j].isTravelled()){
                    sb.append("1");
                }
                else sb.append("0");
            }
        }
        String binaryPart1 = "11" + sb.toString() + "11";
        System.out.println(binaryPart1);
        return MapDescriptor.binaryToHex(binaryPart1);
    }

    public String toHexPart2(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<ROW; ++i){
            for(int j = 0; j<COLUMN; ++j){
                if(map[i][j].isTravelled()){
                    if(map[i][j].isObstacle()){
                        sb.append("1");
                    }
                    else sb.append("0");
                }
            }
        }
        int length = sb.length();
        int needed = 8 - (length % 8);
        for(int m = 0; m<needed;m++){
            sb.append("0");
        }
        String binaryPart2 = sb.toString();
        System.out.println(binaryPart2);
        return MapDescriptor.binaryToHex(binaryPart2);
    }


}