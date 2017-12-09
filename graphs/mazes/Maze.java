import java.util.ArrayList;

class Maze {
  public static enum Color {WHITE, BLACK}; 
  public ArrayList<ArrayList<Color>> maze = new ArrayList<>(); 

  Maze(){
    // make a new maze 
    makeMaze(); 
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

  public static void main(String[] args) {

    // create instance 
    Maze m = new Maze(); 

    // print the maze
    for(int i = 0; i < 10; i++){
      for(int k = 0; k < 10; k++){
        System.out.println(m.maze.get(i).get(k));
      }
    }    
  }
}






