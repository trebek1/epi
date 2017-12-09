import java.util.Objects;

public class Coordinate {
  public int x, y;
  public Coordinate prev;  
  
  public Coordinate(int x, int y){
    this.x = x; 
    this.y = y;
    this.prev = null; 
  }
  
  @Override
  public boolean equals(Object o){
    if(this == o){
      return true;
    }
    if(o == null || getClass() != o.getClass()){
      return false;
    }
    
    Coordinate that = (Coordinate)o; 
    if(x != that.x || y != that.y){
      return false; 
    }
    return true; 
  }
  @Override
  public int hashCode(){
    return Objects.hash(x, y);
  }
}