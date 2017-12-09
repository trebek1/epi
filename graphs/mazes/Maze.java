import java.util.ArrayList;
import java.util.Stack; 

class Maze {
  public static enum Color { WHITE, BLACK };
  public ArrayList<Coordinate> path = new ArrayList<>();
  public ArrayList<ArrayList<Color>> maze = new ArrayList<>();
  public Stack<Coordinate> stack = new Stack<>(); // for iterative DFS 
  public final int size;

  Maze(){
    // make a new maze 
    makeMaze();

    // maze is square in this case so get size here 
    size = maze.get(0).size(); 
  }

  public void makeMaze(){
    for(int i = 0; i < 10; i++){
      ArrayList<Color> a = new ArrayList<>();
      for(int j = 0; j < 10; j++){
        a.add(j, Color.WHITE);
      }
      maze.add(i, a);
    }

    // add where you want walls 
    // set index, element 
    // row 1
    // These are walls

    maze.get(0).set(0, Color.BLACK); 
    maze.get(0).set(6, Color.BLACK); 
    maze.get(0).set(7, Color.BLACK); 

    maze.get(1).set(2, Color.BLACK); 

    maze.get(2).set(0, Color.BLACK); 
    maze.get(2).set(2, Color.BLACK); 
    maze.get(2).set(5, Color.BLACK); 
    maze.get(2).set(6, Color.BLACK); 
    maze.get(2).set(8, Color.BLACK); 
    maze.get(2).set(9, Color.BLACK);

    maze.get(3).set(3, Color.BLACK); 
    maze.get(3).set(4, Color.BLACK); 
    maze.get(3).set(5, Color.BLACK); 
    maze.get(3).set(8, Color.BLACK);

    maze.get(4).set(1, Color.BLACK); 
    maze.get(4).set(2, Color.BLACK);

    maze.get(5).set(1, Color.BLACK); 
    maze.get(5).set(2, Color.BLACK); 
    maze.get(5).set(5, Color.BLACK); 
    maze.get(5).set(7, Color.BLACK);    
    maze.get(5).set(8, Color.BLACK);    

    maze.get(6).set(4, Color.BLACK);    

    maze.get(7).set(0, Color.BLACK);    
    maze.get(7).set(2, Color.BLACK);    
    maze.get(7).set(4, Color.BLACK);    
    maze.get(7).set(6, Color.BLACK);

    maze.get(8).set(0, Color.BLACK); 
    maze.get(8).set(2, Color.BLACK); 
    maze.get(8).set(3, Color.BLACK); 
    maze.get(8).set(7, Color.BLACK);    
    maze.get(8).set(8, Color.BLACK);
    maze.get(8).set(9, Color.BLACK);

    maze.get(9).set(7, Color.BLACK);
    maze.get(9).set(8, Color.BLACK);

  }

  boolean valid(int x, int y){
    if(x >= 0 && y >= 0 && x < size && y < size && maze.get(x).get(y) != Color.BLACK){
      return true;
    }
    return false; 
  }

  void printPath(){
    System.out.println("path size " + path.size()); 
    for(int i = 0; i < path.size(); i++){
      Coordinate c = path.get(i); 
      System.out.println("x= " + c.x + " y=" + c.y);
    }
  }

  void handleCase(int x, int y){
    Coordinate c; 
    c = new Coordinate(x, y);
    maze.get(x).set(y, Color.BLACK);
    path.add(c);
    stack.push(c);
  }

  ArrayList<Coordinate> searchThisDungeonDFSIter(int finalX, int finalY){
    
    Coordinate c = stack.peek();
    while(!stack.empty()){
      int i = c.x; 
      int j = c.y;
      int left = j - 1; 
      int right = j + 1;
      int up = i - 1; 
      int down = i + 1;

      if(i == finalX && j == finalY){
        System.out.println("found the path iteratively");
        printPath();
        return path;
      } 

      if(valid(down, j)){
        handleCase(down, j);
        c = new Coordinate(down, j);
        continue;
      }

      if(valid(up, j)){
        handleCase(up, j);
        c = new Coordinate(up, j);
        continue;
      }

      if(valid(i, right)){
        handleCase(i, right);
        c = new Coordinate(i,right);
        continue;
      }

      if(valid(i, left)){
        handleCase(i, left);
        c = new Coordinate(i, left);
        continue;
      }

      stack.pop();
      path.remove(path.size() - 1);
      maze.get(i).set(j, Color.BLACK);
      c = stack.peek();
    }

    System.out.println("did not find exit");
    return null;

  }

  ArrayList<Coordinate> searchThisDungeonDFS(int i, int j){
    maze.get(i).set(j, Color.BLACK);
    if(i == 0 && (j == (size - 1))){
      System.out.println("found the end!");
      System.out.println("size " + path.size());
      printPath();
      return path;
    }

    int left = j - 1; 
    int right = j + 1;
    int up = i - 1; 
    int down = i + 1; 

    int[] deltas = {1, -1}; 

    for(int m = 0; m < deltas.length; m++){
      for(int k = 0; k < deltas.length; k++){
        int delta = deltas[k];
        if(m == 0){
          if(valid(i + delta, j)){
            path.add(new Coordinate(i + delta, j));
            return searchThisDungeonDFS(i + delta, j);
          }
        } else {
          if(valid(i, j + delta)){
            path.add(new Coordinate(i, j + delta));
            return searchThisDungeonDFS(i, j + delta);
          }
        }
      }
    }
    
    // if(down < size && maze.get(down).get(j) != Color.BLACK){
    //   path.add(new Coordinate(down, j));
    //   return searchThisDungeonDFS(down, j);
    // }

    // if(up >= 0 && maze.get(up).get(j) != Color.BLACK){
    //   path.add(new Coordinate(up, j));
    //   return searchThisDungeonDFS(up, j);
    // }

    // if(right < size && maze.get(i).get(right) != Color.BLACK){
    //   path.add(new Coordinate(i, right));
    //   return searchThisDungeonDFS(i, right);
    // }

    // if(left >= 0 && maze.get(i).get(left) != Color.BLACK){
    //   path.add(new Coordinate(i, left));
    //   return searchThisDungeonDFS(i, left);
    // }

    // remove last node if no where to go
    path.remove(path.size() - 1);
    maze.get(i).set(j, Color.BLACK);

    // return if nowhere else to explore
    if(path.size() == 0){
      return null;
    }
    Coordinate prev = path.get(path.size() - 1);
    return searchThisDungeonDFS(prev.x, prev.y); 
  }

  void reset(){
    // reset the path 
    path = new ArrayList<>();

    // reset the maze 
    maze = new ArrayList<>();

    // make the initial maze again 
    makeMaze();  
  }

  public static void main(String[] args) {

    // create instance 
    Maze m = new Maze();
    Coordinate start = new Coordinate(9, 0);
    m.maze.get(9).set(0, Maze.Color.BLACK);
    m.path.add(start);
    m.searchThisDungeonDFS(9, 0);
    m.reset(); 

    m.maze.get(9).set(0, Maze.Color.BLACK);
    m.path.add(start);
    m.stack.push(start);
    m.searchThisDungeonDFSIter(0, 9);  
  }
}
