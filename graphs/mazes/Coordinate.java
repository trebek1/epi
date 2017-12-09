public static class Coordinate {
  public int x,y; 
  
  public Coordinate(int x, int y){
    this.x = x; 
    this.y = y; 
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
    return Object.hash(x, y);
  }
}