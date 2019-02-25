public class Sensor {
    public void sense(Maze realMap, Maze emptyMap, Position position, Robot.Direction direction){
        senseLeft(realMap, emptyMap, position, direction);
        senseRight(realMap, emptyMap, position, direction);
        senseForward(realMap, emptyMap, position, direction);
    }

    private void senseForward(Maze realMap, Maze emptyMap, Position position, Robot.Direction direction) {
        int x = position.getX();
        int y = position.getY();
        switch (direction){
            case UP:
                if(x-2>=0){
                    emptyMap.setGridType(x-2, y-1, realMap.getGridType(x-2, y-1));
                    emptyMap.setGridType(x-2, y, realMap.getGridType(x-2, y));
                    emptyMap.setGridType(x-2, y+1, realMap.getGridType(x-2, y+1));
                }
                break;
            case LEFT:
                if(y-2>=0){
                    emptyMap.setGridType(x-1, y-2, realMap.getGridType(x-1, y-2));
                    emptyMap.setGridType(x, y-2, realMap.getGridType(x, y-2));
                    emptyMap.setGridType(x+1, y-2, realMap.getGridType(x+1, y-2));
                }
                break;
            case DOWN:
                if(x+2<=emptyMap.ROW-1){
                    emptyMap.setGridType(x+2, y-1, realMap.getGridType(x+2, y-1));
                    emptyMap.setGridType(x+2, y, realMap.getGridType(x+2, y));
                    emptyMap.setGridType(x+2, y+1, realMap.getGridType(x+2, y+1));
                }
                break;
            case RIGHT:
                if(y+2<=emptyMap.COLUMN-1){
                    emptyMap.setGridType(x-1, y+2, realMap.getGridType(x-1, y+2));
                    emptyMap.setGridType(x, y+2, realMap.getGridType(x, y+2));
                    emptyMap.setGridType(x+1, y+2, realMap.getGridType(x+1, y+2));
                }
        }
    }

    private void senseRight(Maze realMap, Maze emptyMap, Position position, Robot.Direction direction) {
        int x = position.getX();
        int y = position.getY();
        switch (direction){
            case UP:
                for(int i = 2; i<=3; ++i){
                    if(y+i >= emptyMap.COLUMN) break;
                    emptyMap.setGridType(x, y+i, realMap.getGridType(x, y+i));
                    if(realMap.getGridType(x,y+i) == Grid.GridType.OBSTACLE) break;
                }
                break;
            case LEFT:
                for(int i = 2; i<=3; ++i){
                    if(x-i < 0) break;
                    emptyMap.setGridType(x-i, y, realMap.getGridType(x-i, y));
                    if(realMap.getGridType(x-i,y) == Grid.GridType.OBSTACLE) break;
                }
                break;
            case DOWN:
                for(int i = 2; i<=3; ++i){
                    if(y-i < 0) break;
                    emptyMap.setGridType(x, y-i, realMap.getGridType(x, y-i));
                    if(realMap.getGridType(x,y-i) == Grid.GridType.OBSTACLE) break;
                }
                break;
            case RIGHT:
                for(int i = 2; i<=3; ++i){
                    if(x+i>=emptyMap.ROW) break;
                    emptyMap.setGridType(x+i, y, realMap.getGridType(x+i, y));
                    if(realMap.getGridType(x+i,y) == Grid.GridType.OBSTACLE) break;
                }
        }
    }

    private void senseLeft(Maze realMap, Maze emptyMap, Position position, Robot.Direction direction) {
        int x = position.getX();
        int y = position.getY();
        switch(direction){
            case UP:
                for(int i = 2; i<=5; ++i){
                    if(y-i < 0 ) break;
                    emptyMap.setGridType(x, y-i, realMap.getGridType(x, y-i));
                    if(realMap.getGridType(x,y-i) == Grid.GridType.OBSTACLE) break;
                }
                break;
            case LEFT:
                for(int i = 2; i<=5; ++i){
                    if(x+i>=emptyMap.ROW) break;
                    emptyMap.setGridType(x+i, y, realMap.getGridType(x+i, y));
                    if(realMap.getGridType(x+i,y) == Grid.GridType.OBSTACLE) break;
                }
                break;
            case DOWN:
                for(int i = 2; i<=5; ++i){
                    if(y+i >= emptyMap.COLUMN) break;
                    emptyMap.setGridType(x, y+i, realMap.getGridType(x, y+i));
                    if(realMap.getGridType(x,y+i) == Grid.GridType.OBSTACLE) break;
                }
                break;
            case RIGHT:
                for(int i = 2; i<=5; ++i){
                    if(x-i < 0) break;
                    emptyMap.setGridType(x-i, y, realMap.getGridType(x-i, y));
                    if(realMap.getGridType(x-i,y) == Grid.GridType.OBSTACLE) break;
                }
                break;
        }
    }

    public boolean isObstacleFront(int row, int col,  Maze map, Robot.Direction direction){
        switch(direction){
            case UP:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row-2, col+i) || map.getGridType(row-2, col+i) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
                break;
            case LEFT:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row+i, col-2) || map.getGridType(row+i, col-2) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
                break;
            case DOWN:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row+2, col+i)|| map.getGridType(row+2, col+i) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
                break;
            case RIGHT:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row+i, col+2)|| map.getGridType(row+i, col+2) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
        }
        return false;
    }

    public boolean isObstacleLeft(int row, int col,  Maze map, Robot.Direction direction){
        switch(direction){
            case UP:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row+i, col-2)|| map.getGridType(row+i, col-2) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
                break;
            case LEFT:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row+2, col+i) || map.getGridType(row+2, col+i) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
                break;
            case DOWN:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row+i, col+2)|| map.getGridType(row+i, col+2) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
                break;
            case RIGHT:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row-2, col+i)|| map.getGridType(row-2, col+i) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
        }
        return false;
    }

    public boolean isObstacleRight(int row, int col, Maze map, Robot.Direction direction){
        switch(direction){
            case UP:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row+i, col+2) || map.getGridType(row+i, col+2) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
                break;
            case LEFT:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row-2, col+i) || map.getGridType(row-2, col+i) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
                break;
            case DOWN:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row+i, col-2) || map.getGridType(row+i, col-2) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
                break;
            case RIGHT:
                for(int i = -1; i<=1; ++i){
                    if( outOfBound(row+2, col+i) || map.getGridType(row+2, col+i) == Grid.GridType.OBSTACLE){
                        return true;
                    }
                }
        }
        return false;

    }

    public boolean outOfBound(int row, int col){
        if(row<0 || row >= 20 || col <0 || col >= 15){
            return true;
        }
        return false;
    }
}