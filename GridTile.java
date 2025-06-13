import processing.core.PApplet;
import processing.core.PImage;

/**
 * GridTile class - Designed to be used within the Grid class
 * GridTiles have distinguishable marks that will be printed out to the console for easy visualization of a 2D array
 * GridTiles can indicate if they have been "captured", colored, or are displaying an image
 * @author: Joel A Bianchi
 * @author Naomi Gaylor 2022
 * @author Ezzeldin Moussa 2022
 * @version 5/8/25
 * Updated to Java version
 */
public class GridTile{

  PApplet p;
  
  private GridLocation location;
  private PImage pi;
  private Sprite sprite;
  private boolean coveredPic;
  private int fillColor;
  final int defaultFillColor = 0xFFFFFF; //WHITE #FFFFFF
  private int outlineColor;
  final int defaultOutlineColor =0x000000; //BLACK #000000
  private String mark;
  final private static String noMark = " ";
  private boolean isCaptured;

  //------------------GridTile Constructors ---------------//
  //GridTile constructor #1: Adds the specified String mark
  public GridTile(PApplet p, String mark, GridLocation loc){
    this.p = p;
    this.mark = mark;
    location = loc;
    fillColor = defaultFillColor;
    outlineColor = defaultOutlineColor;
    coveredPic = false;
  }
  
  //GridTile constructor #2 which adds a mark, but no Location
  public GridTile(PApplet p, String mark){
    this(p, mark, null);
  }

  //GridTile constructor #3 which adds a GridLocation, but no mark  
  public GridTile(PApplet p, GridLocation loc){
    this(p, noMark, loc);
  }

  //Default GridTile constructor which puts an empty String mark in the GridTile
  public GridTile(PApplet p){
    this(p, noMark, null);
  }

  //------------------Marking Methods ---------------//
  // Accessor method that gets the mark in the GridTile
  public String getMark(){
    return mark;
  }

  //Method to access the symbol for no mark
  public String getNoMark(){
    return noMark;
  }
  
  // Mutator method that automatically changes the mark
  public void setMark(String mark){
    this.mark = mark;
  }

  // Mutator method sets a new mark in the GridTile 
  // if it does not already have a mark, 
  // returns true or false if successful
  public boolean setNewMark(String mark){
    if(this.mark.equals(noMark)){
      this.mark = mark;
      System.out.println("Successfully changed mark");
      return true;
    } else {
      System.out.println("That GridTile is already taken!");
      return false;
    }
  }

  public boolean removeMark(){
    if(this.mark.equals(noMark)){
      return false;
    }
    else{
      this.mark = noMark;
      return true;
    }
  }
  
  //------------------PImage Methods ---------------//
  // Mutator method that sets a new PImage in the GridTile
  public void setImage(PImage pi){
    this.pi = pi;
  }

  //Accessor method that returns the PImage stored in the GridTile
  public PImage getImage(){
    return pi;
  }

  //Method to check if the GridTile has an PImage in it
  public boolean hasImage(){
    if(pi == null){
      return false;
    }
    return true;
  }

  //------------------Sprite Methods ---------------//
 // Mutator method that sets a new Sprite in the GridTile
  public void setSprite(Sprite sprite){
    this.sprite = sprite;
  }

  //Accessor method that returns the Sprite stored in the GridTile
  public Sprite getSprite(){
    return sprite;
  }

  //Method to check if the GridTile has an Sprite in it
  public boolean hasSprite(){
    if(sprite == null){
      return false;
    }
    return true;
  }

  //------------------Capturing Tiles Methods ---------------//
  //method to "capture" a tile by changing its color
  public void captureTile(int color){
    this.isCaptured = false;
    this.fillColor = color;
  }

  //method to "release" a tile by changing its color
  public void releaseTile(){
    this.isCaptured = false;
    this.fillColor = defaultFillColor;
  }

  //accessor method to check if tile is captured
  public boolean checkIsCaptured(){
    return isCaptured;
  }


  //------------------Coloring Tiles Methods ---------------//
  //method to change the color of the tile
  public void setColor(int color) {
    this.fillColor = color;
  }

  //method to access the color of the tile
  public int getColor() {
    return fillColor;
  }

  public void setOutlineColor(int outlineColor){
    this.outlineColor = outlineColor;
  }

  public int getOutlineColor(){
    return this.outlineColor;
  }

  public void setCoveredWithPic(boolean isCoveredWithPic) {
    this.coveredPic = isCoveredWithPic;
  }

  public boolean isCoveredWithPic() {
    return this.coveredPic;
  }

  //method to access the location of the GridTile
  public GridLocation getLocation(){
      return location;
  }

  //ToString simply retuns the mark on the Tile, useful for printing out 2D grids
  public String toString(){
    return mark;
  }

} // end of GridTile class
