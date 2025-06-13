/**
 * GridLocation class - helper class to store coordinates more easily
 * @author Joel A Bianchi
 * @version 5/10/25
 * Modified toString
 */
public class GridLocation{
 
  int row;
  int col;
  
  public GridLocation(int row, int col){
    this.row = row;
    this.col = col;
  }
  
  public int getRow(){
    return row;
  }
  
  public int getCol(){
    return col;
  }
  
  public String toString(){
    return "[" + row + "," + col + "]";
  }
  
  public int getYCoord() {
    return row;
  }
  
  public int getXCoord() {
    return col;
  }

  public boolean equals(GridLocation otherLoc){
    if(getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol()){
      return true;
    }
    return false;

  }
 
} // end of GridLocation class
