/**
 * HexLocation Class
 * HexLocations use x & y coordinate instead of row & column designations
 * @author Joel A Bianchi
 * @version 5/8/25
 * Updated to Java version
 */
public class HexLocation extends GridLocation{
 
  private int xCoord;
  private int yCoord;
  
  public HexLocation(int x, int y) {
    super(x,y);
    xCoord = x;
    yCoord = y;
  }
  
  public int getYCoord() {
    return yCoord;
  }
  
  public int getXCoord() {
    return xCoord;
  }
  
  public boolean equals(HexLocation otherLoc) {
    return yCoord == otherLoc.getYCoord() && xCoord == otherLoc.getXCoord();
  }
  
  public String toString() {
    return "(" + xCoord + ", " + yCoord + ")";
  }

} // end of HexLocation class
