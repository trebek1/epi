import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList; 

class Grid {
  public static enum Color { WHITE, BLACK };
  public ArrayList<ArrayList<Color>> grid = new ArrayList<>();
  public Queue<Coordinate> q;
  public final int size;

  Grid(){
    // make a new maze
    makeGrid();

    // maze is square in this case so get size here
    size = grid.get(0).size();
  }

  public void makeGrid(){
    for(int i = 0; i < 10; i++){
      ArrayList<Color> a = new ArrayList<>();
      for(int j = 0; j < 10; j++){
        a.add(j, Color.WHITE);
      }
      grid.add(i, a);
    }

    // add where you want walls
    // set index, element
    // row 1
    // These are walls

    grid.get(0).set(0, Color.BLACK);
    grid.get(0).set(2, Color.BLACK);
    grid.get(0).set(6, Color.BLACK);
    grid.get(0).set(7, Color.BLACK);
    grid.get(0).set(8, Color.BLACK);
    grid.get(0).set(9, Color.BLACK);

    grid.get(1).set(2, Color.BLACK);
    grid.get(1).set(5, Color.BLACK);
    grid.get(1).set(8, Color.BLACK);
    grid.get(1).set(9, Color.BLACK);

    grid.get(2).set(0, Color.BLACK);
    grid.get(2).set(1, Color.BLACK);
    grid.get(2).set(2, Color.BLACK);
    grid.get(2).set(5, Color.BLACK);
    grid.get(2).set(6, Color.BLACK);
    grid.get(2).set(8, Color.BLACK);
    grid.get(2).set(9, Color.BLACK);

    grid.get(3).set(1, Color.BLACK);
    grid.get(3).set(3, Color.BLACK);
    grid.get(3).set(4, Color.BLACK);
    grid.get(3).set(5, Color.BLACK);
    grid.get(3).set(6, Color.BLACK);
    grid.get(3).set(8, Color.BLACK);

    grid.get(4).set(0, Color.BLACK); 
    grid.get(4).set(2, Color.BLACK);
    grid.get(4).set(7, Color.BLACK);

    grid.get(5).set(0, Color.BLACK); 
    grid.get(5).set(2, Color.BLACK); 
    grid.get(5).set(5, Color.BLACK); 
    grid.get(5).set(7, Color.BLACK);    
    grid.get(5).set(8, Color.BLACK);
    grid.get(5).set(9, Color.BLACK);

    grid.get(6).set(4, Color.BLACK);
    grid.get(6).set(6, Color.BLACK);
    grid.get(6).set(9, Color.BLACK);

    grid.get(7).set(0, Color.BLACK);
    grid.get(7).set(2, Color.BLACK);
    grid.get(7).set(4, Color.BLACK);
    grid.get(7).set(6, Color.BLACK);

    grid.get(8).set(0, Color.BLACK); 
    grid.get(8).set(2, Color.BLACK); 
    grid.get(8).set(3, Color.BLACK); 
    grid.get(8).set(7, Color.BLACK);    
    grid.get(8).set(8, Color.BLACK);
    grid.get(8).set(9, Color.BLACK);

    grid.get(9).set(7, Color.BLACK);
    grid.get(9).set(8, Color.BLACK);
  }

  boolean isValid(int x, int y, Color paint){
    if(x >= 0 && y >= 0 && x < size && y < size && grid.get(x).get(y) != paint){
      return true;
    }
    return false; 
  }

  void handleSquare(int x, int y, Color paint){
    Coordinate c = new Coordinate(x, y);
    grid.get(x).set(y, paint);
    q.add(c);
  }

  void printMatrix(){
    for(int i = 0; i < size; i++){
      System.out.println(grid.get(i));
    }
  }

  void fillMatrix(int startX, int startY){

    q = new LinkedList<>();

    // craete starting coordinate 
    Coordinate c = new Coordinate(startX, startY);

    Color other = grid.get(startX).get(startY);
    Color paint = (other == Color.BLACK) ? Color.WHITE : Color.BLACK;

    grid.get(startX).set(startY, paint);
    q.add(c);

    while(!q.isEmpty()){
      c = q.remove();

      int i = c.x;
      int j = c.y;

      int up = i - 1;
      int down = i + 1;
      int left = j - 1;
      int right = j + 1;

      // up 
      if(isValid(up, j, paint)){
        handleSquare(up, j, paint);
      }

      // down 
      if(isValid(down, j, paint)){
        handleSquare(down, j, paint);
      }

      // left
      if(isValid(i, left, paint)){
        handleSquare(i, left, paint);
      }

      // right
      if(isValid(i, right, paint)){
        handleSquare(i, right, paint);
      }
    }

    printMatrix();
  }

  void reset(){
    // reset the maze 
    grid = new ArrayList<>();

    // make the initial maze again 
    makeGrid();
  }

  public static void main(String[] args) {

    // create instance
    Grid g = new Grid();
    g.fillMatrix(5, 4);
    //g.printMatrix();
  }
}
