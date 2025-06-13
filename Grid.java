import processing.core.PApplet;
import processing.core.PImage;

/** 
 * Grid Class - Used for rectangular-tiled games
 * A 2D array of GridTiles which can be marked
 * Subclass of World that can show all Images & Sprites
 * @author Joel A Bianchi
 * @author RJ Morel
 * @version 6/12/25
 * All Grids take in files, create & resize background PImages
 * Added copyTileSprite() & moveTileSprite() to help with populating & moving Sprites through the Grid
 */
public class Grid extends World{
  
  //------------------ GRID FIELDS --------------------//
  private int rows;
  private int cols;
  private GridTile[][] board;
  private boolean printingGridMarks = false;
  

  //------------------ GRID CONSTRUCTORS --------------------//

  /**
   * Grid Constructor #1: Default constructor that creates a 3x3 Grid  
   * @param p             Processing applet
   */
  public Grid(PApplet p){
     this(p, 3,3);
  }

  /**
   * Grid Construtor #2: Only accepts the number of rows & columns (Default for 2023)
   * @param p             Processing applet
   * @param rows          number of rows in the grid
   * @param cols          number of columns in the grid
   */
  public Grid(PApplet p, int rows, int cols){
    this(p, "grid",null, rows, cols);
  }

  /**
   * Grid constructor #3: Sets background image + rows & cols
   * @param p             Processing applet
   * @param screenName    String to track Screens
   * @param bgFile        filename for stationary background image
   * @param rows          number of rows in the grid
   * @param cols          number of columns in the grid
   */
  public Grid(PApplet p, String screenName, String bgFile, int rows, int cols){
    this(p, screenName, bgFile, null, rows, cols);
  }

  /**
   * Grid constructor #4: Takeas in 2D String array parameter to set tile marks
   * @param p             Processing applet
   * @param screenName    String to track Screens
   * @param bgFile        filename for stationary background image
   * @param tileMarks     a 2D array of String marks to setup entire Grid
   * @param rows          number of rows in the grid
   * @param cols          number of columns in the grid
   */
  public Grid(PApplet p, String screenName, String bgFile, String[][] tileMarks, int rows, int cols){
    super(p, screenName, bgFile);

    this.rows = rows;
    this.cols = cols;
    board = new GridTile[rows][cols];
    
    for(int r=0; r<rows; r++){
      for(int c=0; c<cols; c++){
        board[r][c] = new GridTile(p, new GridLocation(r,c));
      }
    }

    if(tileMarks != null){
      setAllMarks(tileMarks);
    }

  }

  

  //------------------ GRID MARKING METHODS --------------------//
 
  /** 
   * Assigns a String mark to a location in the Grid.
   * This mark is not necessarily visible, but can help in tracking
   * what you want recorded at each GridLocation.
   * @param mark
   * @param loc      GridLocation for a specific GridTile
   */
  public void setMark(String mark, GridLocation loc){
    board[loc.getRow()][loc.getCol()].setMark(mark);
    if(printingGridMarks){
      printGrid();
    }
  }
  
  /** 
   * Gets the mark value at a location
   * @param loc      GridLocation for a specific GridTile
   * @return String
   * @author RJ Morel 2023
   */
  public String getMark(GridLocation loc){
    return board[loc.getRow()][loc.getCol()].getMark();
  }
  
  /** 
   * Gets the mark value at a location
   * @param loc      GridLocation for a specific GridTile
   * @return boolean
   * @author RJ Morel 2023
   */
  public boolean removeMark(GridLocation loc){
    boolean isGoodClick = board[loc.getRow()][loc.getCol()].removeMark();
    return isGoodClick;
  }
  
  /** 
   * Checks if a location has a mark
   * @param loc      GridLocation for a specific GridTile
   * @return boolean
   * @author RJ Morel 2023
   */ 
  public boolean hasMark(GridLocation loc){
    GridTile tile = board[loc.getRow()][loc.getCol()];
    boolean isGoodClick = tile.getMark() != tile.getNoMark();
    return isGoodClick;
  }
  
  /** 
   * Assigns a String mark to a location in the Grid.
   * This mark is not necessarily visible, but can help in tracking
   * what you want recorded at each GridLocation.
   * @param mark
   * @param loc
   * @return boolean      <code>true</code> if mark is correctly set (no previous mark), <code>false</code> otherwise
   */
  public boolean setNewMark(String mark, GridLocation loc){
    int row = loc.getRow();
    int col = loc.getCol();
    boolean isGoodClick = board[row][col].setNewMark(mark);
    if(printingGridMarks){
      printGrid();
    }
    return isGoodClick;
  }

  /**
   * Prints out the marks in the Grid to the console
   */
  public void printGrid(){
    for(int r = 0; r<rows; r++){
      for(int c = 0; c<cols; c++){
         System.out.print(board[r][c]);
      }
      System.out.println();
    } 
  }

  /**
   * If Grid mark system is used, shows all marks in the Grid in the console
   */
  public void startPrintingGridMarks(){
    printingGridMarks = true;
  }

  /**
   * Turns off Grid mark system from printing all marks in the Grid in the console
   */
  public void stopPrintingGridMarks(){
    printingGridMarks = false;
  }

  
  /**
   * Sets the marks for an entire grid from a 2D String array
   * tileMarks MUST match the same number of rows & columns as the grid 
   * @param tileMarks
   */
  public void setAllMarks(String[][] tileMarks){

    if(tileMarks != null){

      int markRows = rows;
      int markCols = cols;
      
      if(tileMarks.length > rows){
        System.out.println("TileMarks has TOO MANY rows!");
      } else if (tileMarks.length < rows){
        System.out.println("TileMarks does not have enough rows!");
        markRows = tileMarks.length;
      }

      if(tileMarks[0].length > cols){
        System.out.println("TileMarks has TOO MANY columns!");
      } else if (tileMarks[0].length < cols){
        System.out.println("TileMarks does not have enough columns!");
        markCols = tileMarks[0].length;
      }

      // System.out.println(markR)

      for(int r=0; r<markRows; r++){
        for(int c=0; c<markCols; c++){
          board[r][c].setMark(tileMarks[r][c]);
        }
      }
      
    } else {
      System.out.println("Cannot setup tileMarks because object is NULL!");
    }
  }


  //------------------ GRID ACCESSOR METHODS --------------------//

  /** 
   * @return GridLocation     where the mouse is currently hovering over
   */  
  public GridLocation getGridLocation(){
      
    int row = p.mouseY/(p.pixelHeight/this.rows);
    int col = p.mouseX/(p.pixelWidth/this.cols);

    return new GridLocation(row, col);
  }
  
  /** 
   * Provides the x-pixel value given a GridLocation loc
   * @param loc       GridLocation to check
   * @return int      x-pixel value
   */
  public int getX(GridLocation loc){
    int widthOfOneTile = p.pixelWidth/this.cols;
    //calculate the left of the grid GridLocation
    int pixelX = (widthOfOneTile * loc.getCol()); 
    return pixelX;
  }
  
  /** 
   * @param row     row of a specific GridTile
   * @param col     column of a specific GridTile
   * @return int    x-value of left edge of a GridTile
   */
  public int getX(int row, int col){
    return getX(new GridLocation(row, col));
  }
  
  /** 
   * @param loc     GridLocation for a specific GridTile
   * @return int    x-value of center pixel of the GridLocation
   */
  public int getCenterX(GridLocation loc){
    return getX(loc) + getTileWidth()/2;
  }
  
  /** 
   * @param loc     GridLocation for a specific GridTile
   * @return int    y-value of top pixel of a GridLocation
   */
  public int getY(GridLocation loc){
    int heightOfOneTile = p.pixelHeight/this.rows;
    //calculate the top of the grid GridLocation
    int pixelY = (heightOfOneTile * loc.getRow()); 
    return pixelY;
  }
  
  /** 
   * @param row     row of a specific GridTile
   * @param col     column of a specific GridTile
   * @return int    y-value of left edge of a GridTile
   */
  public int getY(int row, int col){
    return getY(new GridLocation(row,col));
  }
  
  /** 
   * @param loc     GridLocation for a specific GridTile
   * @return int    y-value of center pixel of the GridLocation
   */
  public int getCenterY(GridLocation loc){
    return getY(loc) + getTileHeight()/2;
  }
  
  /** 
   * Accessor method
   * @return int      number of rows in the Grid
   */
  public int getNumRows(){
    return rows;
  }
  
  
  /** 
   * Accessor method
   * @return int      number of cols in the Grid
   */ 
  public int getNumCols(){
    return cols;
  }

  
  /** 
   * Accessor method
   * @return int      width of 1 Tile in the Grid
   */
  public int getTileWidth(){
    return p.pixelWidth/this.cols;
  }
  
  /** 
   * Accessor method 
   * @return int        height of 1 Tile in the Grid
   */
  public int getTileHeight(){
    return p.pixelHeight/this.rows;
  }

  
  /**
   * Returns the GridTile object stored at a specified GridLocation
   * @param loc      GridLocation for a specific GridTile
   * @return GridTile
   */
  public GridTile getTile(GridLocation loc){
    return board[loc.getRow()][loc.getCol()];
  }
  
  /** 
   * @param row         row of a specific GridTile
   * @param col         column of a specific GridTile
   * @return GridTile   GridTile object stored at a specified row and column
   */
  public GridTile getTile(int row, int col){
    return board[row][col];
  }


  
  //------------------ GRID TILE IMAGE METHODS --------------------//

  /** 
   * Sets the image at a particular tile in the grid & displays it
   * @param loc      GridLocation for a specific GridTile
   * @param img
   */
  public void setTileImage(GridLocation loc, PImage img){
    GridTile tile = getTile(loc);
    tile.setImage(img);
    //showTileImage(loc);
  }
  
  /** 
   * Returns the PImage associated with a particular GridTile
   * @param loc      GridLocation for a specific GridTile
   * @return PImage
   */
  public PImage getTileImage(GridLocation loc){
    GridTile tile = getTile(loc);
    return tile.getImage();
  }
  
  /** 
   * @param loc         GridLocation for a specifc GridTile
   * @return boolean    <code>true</code> if the GridTile has a PImage, <code>false</code> otherwise
   */
  public boolean hasTileImage(GridLocation loc){
    GridTile tile = getTile(loc);
    return tile.hasImage();
  }
  
  /** 
   * Clears the tile image
   * @param loc      GridLocation for a specific GridTile
   */
  public void clearTileImage(GridLocation loc){
    setTileImage(loc,null);
  }

  
  /** 
   * Displays the PImage at a specific location
   * @param loc      GridLocation for a specific GridTile
   */
  public void showTileImage(GridLocation loc){
    GridTile tile = getTile(loc);
    if(tile.hasImage()){
      p.image(tile.getImage(),getX(loc),getY(loc));
    }
  }

  /**
   * Displays the PImages stored in ALL the GridTiles
   */
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

  @Deprecated
  public void showImages(){
    showGridImages();
  }

  /**
   * Displays all World + Screen + Grid visuals
   */
  public void show(){
    super.show();
    this.showGridImages();
    this.showGridSprites();
  }

  
   //------------------  GRID SPRITES METHODS --------------------//

  /** 
   * Sets the Sprite at a particular tile in the grid & displays it
   * @param loc         GridLocation to change
   * @param sprite      new Sprite to add to tile
   */
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
  
  /** 
   * Gets the Sprite from a specific GridTile
   * @param loc         GridLocation for a specific GridTile
   * @return Sprite     Sprite associated with a particular Tile
   */
  public Sprite getTileSprite(GridLocation loc){
    GridTile tile = getTile(loc);
    //System.out.println("Grid.getTileSprite() " + tile.getSprite());
    return tile.getSprite();
  }

  /** 
   * Sets a copy of a Sprite at a particular tile in the grid & displays it
   * @param loc         GridLocation to add copied Sprite to
   * @param sprite      Sprite to make a copy of and add to tile
   */
  public void copyTileSprite(GridLocation loc, Sprite sprite){
    Sprite copySprite = sprite.copy();
    setTileSprite(loc, copySprite);
  }

   /** 
   * Moves the Sprite at a particular tile in the grid to a new location
   * @param oldLoc      original GridLocation of the Sprite
   * @param newLoc      new GridLocation of the Sprite
   */
  public void moveTileSprite(GridLocation oldLoc, GridLocation newLoc){
    Sprite currentSprite = getTileSprite(oldLoc);
    clearTileSprite(oldLoc);
    setTileSprite(newLoc, currentSprite);
  }
  
  /** 
   * Checks if a Tile has a PImage
   * @param loc         GridLocation for a specific GridTile
   * @return boolean      <code>true</code> if the GridTile has a stored image, <code>false</code> otherwise
   */  
  public boolean hasTileSprite(GridLocation loc){
    GridTile tile = getTile(loc);
    return tile.hasSprite();
  }

  /**
   * Clears the Sprite from a particular tile
   * If trying to move the Sprite, you caan also use moveTileSprite() instead
   * @param loc         GridLocation for a specific GridTile
   */
  public void clearTileSprite(GridLocation loc){
    setTileSprite(loc,null);
  }
  
  /** 
   * Checks for an AnimatedSprite and animates it
   * @param loc         GridLocation for a specific GridTile
   */
  public void animateTileSprite(GridLocation loc){
    try{
      AnimatedSprite aSprite = (AnimatedSprite)getTileSprite(loc);
      aSprite.animate();
      //System.out.println("animating");
    } catch (Exception e) {
      System.out.println("Is your Sprite an AnimatedSprite?\n"+e);
    }
  }
  
  /** 
   * Displays the Sprite on a single GridTile
   * @param loc         GridLocation for a specific GridTile
   */
  public void showTileSprite(GridLocation loc){
    GridTile tile = getTile(loc);
    if(tile.hasSprite()){
      tile.getSprite().show();
    }
  }

  /**
   * Displays ALL the Sprites stored in the Grid
   */
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

  /**
   * Clears the screen from all Images & Sprites
   */
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

} // end of Grid class
