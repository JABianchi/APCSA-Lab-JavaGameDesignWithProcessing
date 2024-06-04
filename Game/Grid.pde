/* Grid Class - Used for rectangular-tiled games
 * A 2D array of GridTiles which can be marked
 * Subclass of World that can show all Images & Sprites
 * Author: Joel Bianchi & RJ Morel
 * Last Edit: 5/29/2024
 * Working with regular Sprites
 * Ability to animate sprites in Grid tiles
 */

public class Grid extends World{
  
  //------------------ GRID FIELDS --------------------//
  private int rows;
  private int cols;
  private GridTile[][] board;
  private boolean printingGridMarks = false;
  

  //------------------ GRID CONSTRUCTORS --------------------//

  //Grid constructor #1
  public Grid(String screenName, PImage bg, int rows, int cols){
    super(screenName, bg);

    this.rows = rows;
    this.cols = cols;
    board = new GridTile[rows][cols];
    
    for(int r=0; r<rows; r++){
      for(int c=0; c<cols; c++){
        board[r][c] = new GridTile(new GridLocation(r,c));
      }
    }
  }

  //Grid Construtor #2: Only accepts the number of rows & columns (Default for 2023)
  public Grid(int rows, int cols){
    this("grid",null, rows, cols);
  }

  // Grid Constructor #3: Default constructor that creates a 3x3 Grid  
  public Grid(){
     this(3,3);
  }


  //------------------ GRID MARKING METHODS --------------------//
 
  // Method that Assigns a String mark to a location in the Grid.  
  // This mark is not necessarily visible, but can help in tracking
  // what you want recorded at each GridLocation.
  public void setMark(String mark, GridLocation loc){
    board[loc.getRow()][loc.getCol()].setNewMark(mark);
    if(printingGridMarks){
      printGrid();
    }
  } 
  
  //Method to get the mark value at a location -RJ Morel
  public String getMark(GridLocation loc){
    return board[loc.getRow()][loc.getCol()].getMark();
  }
  
  //Method to get the mark value at a location -RJ Morel
  public boolean removeMark(GridLocation loc){
    boolean isGoodClick = board[loc.getRow()][loc.getCol()].removeMark();
    return isGoodClick;
  }
  
  //Method to check if a location has a mark -RJ Morel
  public boolean hasMark(GridLocation loc){
    GridTile tile = board[loc.getRow()][loc.getCol()];
    boolean isGoodClick = tile.getMark() != tile.getNoMark();
    return isGoodClick;
  } 

  // Method that Assigns a String mark to a location in the Grid.  
  // This mark is not necessarily visible, but can help in tracking
  // what you want recorded at each GridLocation.  
  // Returns true if mark is correctly set (no previous mark) or false if not
  public boolean setNewMark(String mark, GridLocation loc){
    int row = loc.getRow();
    int col = loc.getCol();
    boolean isGoodClick = board[row][col].setNewMark(mark);
    if(printingGridMarks){
      printGrid();
    }
    return isGoodClick;
  } 

  //Method that prints out the marks in the Grid to the console
  public void printGrid(){
    for(int r = 0; r<rows; r++){
      for(int c = 0; c<cols; c++){
         System.out.print(board[r][c]);
      }
      System.out.println();
    } 
  }

  public void startPrintingGridMarks(){
    printingGridMarks = true;
  }
  public void stopPrintingGridMarks(){
    printingGridMarks = false;
  }


  //------------------ GRID ACCESSOR METHODS --------------------//

  //Method that returns the GridLocation of where the mouse is currently hovering over
  public GridLocation getGridLocation(){
      
    int row = mouseY/(pixelHeight/this.rows);
    int col = mouseX/(pixelWidth/this.cols);

    return new GridLocation(row, col);
  } 

  //Accessor method that provide the x-pixel value given a GridLocation loc
  public int getX(GridLocation loc){
    int widthOfOneTile = pixelWidth/this.cols;
    //calculate the left of the grid GridLocation
    int pixelX = (widthOfOneTile * loc.getCol()); 
    return pixelX;
  }
  public int getX(int row, int col){
    return getX(new GridLocation(row, col));
  }
  public int getCenterX(GridLocation loc){
    return getX(loc) + getTileWidth()/2;
  }
  
  //Accessor method that provide the y-pixel value given a GridLocation loc
  public int getY(GridLocation loc){
    int heightOfOneTile = pixelHeight/this.rows;
    //calculate the top of the grid GridLocation
    int pixelY = (heightOfOneTile * loc.getRow()); 
    return pixelY;
  }
  public int getY(int row, int col){
    return getY(new GridLocation(row,col));
  }
  public int getCenterY(GridLocation loc){
    return getY(loc) + getTileHeight()/2;
  }
  
  //Accessor method that returns the number of rows in the Grid
  public int getNumRows(){
    return rows;
  }
  
  //Accessor method that returns the number of cols in the Grid
  public int getNumCols(){
    return cols;
  }

  //Accessor method that returns the width of 1 Tile in the Grid
  public int getTileWidth(){
    return pixelWidth/this.cols;
  }
  //Accessor method that returns the height of 1 Tile in the Grid
  public int getTileHeight(){
    return pixelHeight/this.rows;
  }

  //Returns the GridTile object stored at a specified GridLocation
  public GridTile getTile(GridLocation loc){
    return board[loc.getRow()][loc.getCol()];
  }

  //Returns the GridTile object stored at a specified row and column
  public GridTile getTile(int r, int c){
    return board[r][c];
  }


  //------------------ GRID TILE IMAGE METHODS --------------------//

  //Method that sets the image at a particular tile in the grid & displays it
  public void setTileImage(GridLocation loc, PImage pi){
    GridTile tile = getTile(loc);
    tile.setImage(pi);
    showTileImage(loc);
  }

  //Method that returns the PImage associated with a particular Tile
  public PImage getTileImage(GridLocation loc){
    GridTile tile = getTile(loc);
    return tile.getImage();
  }


  //Method that returns if a Tile has a PImage
  public boolean hasTileImage(GridLocation loc){
    GridTile tile = getTile(loc);
    return tile.hasImage();
  }

  //Method that clears the tile image
  public void clearTileImage(GridLocation loc){
    setTileImage(loc,null);
  }

  public void showTileImage(GridLocation loc){
    GridTile tile = getTile(loc);
    if(tile.hasImage()){
      image(tile.getImage(),getX(loc),getY(loc));
    }
  }

  //Method to show all the PImages stored in each GridTile
  public void showGridImages(){

    //Loop through all the Tiles and display its images/sprites
      for(int r=0; r<getNumRows(); r++){
        for(int c=0; c<getNumCols(); c++){

          //Store temporary GridLocation
          GridLocation tempLoc = new GridLocation(r,c);
          
          //Check if the tile has an image
          if(hasTileImage(tempLoc)){
            showTileImage(tempLoc);
          }
        }
      }
  }
  //to be deprecated
  public void showImages(){
    showGridImages();
  }


  //------------------  GRID SPRITES METHODS --------------------//

  //Method that sets the Sprite at a particular tile in the grid & displays it
  public void setTileSprite(GridLocation loc, Sprite sprite){
    GridTile tile = getTile(loc);
    if(sprite == null){
      tile.setSprite(null);
      //System.out.println("Cleared tile @ " + loc);
      return;
    }
    // sprite.setLeft(getX(loc));
    // sprite.setTop(getY(loc));
    sprite.setCenterX(getCenterX(loc));
    sprite.setCenterY(getCenterY(loc));
    tile.setSprite(sprite);
    showTileSprite(loc);
    //System.out.println("Succcessfully set tile @ " + loc);
  }
  
  //Method that returns the PImage associated with a particular Tile
  public Sprite getTileSprite(GridLocation loc){
    GridTile tile = getTile(loc);
    //System.out.println("Grid.getTileSprite() " + tile.getSprite());
    return tile.getSprite();
  }
  
  //Method that returns if a Tile has a PImage
  public boolean hasTileSprite(GridLocation loc){
    GridTile tile = getTile(loc);
    return tile.hasSprite();
  }

  //Method that clears the tile image
  public void clearTileSprite(GridLocation loc){
    setTileSprite(loc,null);
  }

  //Method that checks for an AnimatedSprite and animates it
  public void animateTileSprite(GridLocation loc){
    try{
      AnimatedSprite aSprite = (AnimatedSprite)getTileSprite(loc);
      aSprite.animate();
      //System.out.println("animating");
    } catch (Exception e) {
      System.out.println("Is your Sprite an AnimatedSprite?\n"+e);
    }
  }

  public void showTileSprite(GridLocation loc){
    GridTile tile = getTile(loc);
    if(tile.hasSprite()){
      tile.getSprite().show();
    }
  }

  
  //Method to show all the PImages stored in each GridTile
  public void showGridSprites(){

    //Loop through all the Tiles and display its images/sprites
      for(int r=0; r<getNumRows(); r++){
        for(int c=0; c<getNumCols(); c++){

          //Store temporary GridLocation
          GridLocation tempLoc = new GridLocation(r,c);
          
          //Check if the tile has an image
          if(hasTileSprite(tempLoc)){

            //check if Sprite is Animated
            if(getTileSprite(tempLoc).getIsAnimated()){
              animateTileSprite(tempLoc);
            } else {
              //setTileSprite(tempLoc, getTileSprite(tempLoc));
              showTileSprite(tempLoc);
            }
          }
        }
      }
  }

  //Method to clear the screen from all Images & Sprites
  public void clearGrid(){

    //Loop through all the Tiles and display its images/sprites
      for(int r=0; r<getNumRows(); r++){
        for(int c=0; c<getNumCols(); c++){

            //Store temporary GridLocation
            GridLocation tempLoc = new GridLocation(r,c);
            
            //Check if the tile has an image
            if(hasTileSprite(tempLoc)){
              setTileSprite(tempLoc, getTileSprite(tempLoc));
              //showTileSprite(tempLoc);
    
            }
          }
        }
    }

}
