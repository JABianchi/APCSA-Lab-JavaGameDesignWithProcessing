/* Game Class Starter File
 * Authors: Joel A. Bianchi
 * Last Edit: 5/13/25
 * using new Screen show method
 */

//import processing.sound.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Game extends PApplet{

  //------------------ GAME VARIABLES --------------------//

  // VARIABLES: Processing variable to do Processing things
  PApplet p;

  // VARIABLES: Title Bar
  String titleText = "ConnectHorse";
  String extraText = "CurrentLevel?";
  String name = "";

  // VARIABLES: Whole Game
  AnimatedSprite runningHorse;
  boolean doAnimation;

  // VARIABLES: Splash Screen
  Screen splashScreen;
  PImage splashBg;
  String splashBgFile = "images/apcsa.png";
  //SoundFile song;

  // VARIABLES: Level1Grid Screen
  Grid level1Grid;
  String level1BgFile = "images/chess.jpg";
  PImage level1Bg;
  String player1File = "images/redhorse.png";
  PImage player1;   // Use PImage to display the image in a GridLocation
  int player1Row = 3;
  int player1Col = 0;
  int player2Row = 5;
  int player2Col = 5;
  int health = 3;
  AnimatedSprite player2;
  Button b1;

  // VARIABLES: Level2World Pixel-based Screen
  World level2World;
  String level2BgFile = "images/sky.png";
  PImage level2Bg;
  String player3File = "images/zapdos.png";
  Sprite player3; //Use Sprite for a pixel-based Location
  int player3startX = 50;
  int player3startY = 300;

  // VARIABLES: EndScreen
  World endScreen;
  String endBgFile = "images/youwin.png";
  PImage endBg;


  // VARIABLES: Tracking the current Screen being displayed
  Screen currentScreen;
  World currentWorld;
  Grid currentGrid;
  private int msElapsed = 0;

  boolean start = true;


  //------------------ REQUIRED PROCESSING METHODS --------------------//

  // Processing method that runs once for screen resolution settings
  public void settings() {
    //SETUP: Match the screen size to the background image size
    size(800,600);  //these will automatically be saved as width & height

    // Allows p variable to be used by other classes to access PApplet methods
    p = this;
    
  }

  //Required Processing method that gets run once
  public void setup() {

    p.imageMode(p.CORNER);    //Set Images to read coordinates at corners
    //fullScreen();   //only use if not using a specfic bg image
    
    //SETUP: Set the title on the title bar
    surface.setTitle(titleText);

    //SETUP: Load BG images used in all screens
    splashBg = p.loadImage(splashBgFile);
    splashBg.resize(p.width, p.height);
    level1Bg = p.loadImage(level1BgFile);
    level1Bg.resize(p.width, p.height);
    level2Bg = p.loadImage(level2BgFile);
    //level2Bg.resize(p.width, p.height);
    endBg = p.loadImage(endBgFile);
    endBg.resize(p.width, p.height);

    //SETUP: Screens, Worlds, Grids
    splashScreen = new Screen(this, "splash", splashBg);
    level1Grid = new Grid(this, "chessBoard", level1Bg, 6, 8);
    //level1Grid.startPrintingGridMarks();
    level2World = new World(p, "sky", level2Bg, 4.0f, 0.0f, -800.0f); //moveable World constructor --> defines center & scale (x, scale, y)???
    System.out.println( "World constructed: " + Util.toStringPImage(level2World.getBgImage()));
       
    // level2World = new World("sky", level2Bg);   //non-moving World construtor
    endScreen = new World(this, "end", endBg);
    currentScreen = splashScreen;

    //SETUP: All Game objects
    runningHorse = new AnimatedSprite(this, "sprites/horse_run.png", "sprites/horse_run.json", 50.0f, 75.0f, 10.0f);

    //SETUP: Level 1
    player1 = p.loadImage(player1File);
    player1.resize(level1Grid.getTileWidth(),level1Grid.getTileHeight());
    player2 = new AnimatedSprite(this, "sprites/chick_walk.png", "sprites/chick_walk.json", 0.0f, 0.0f, 5.0f);
    level1Grid.setTileSprite(new GridLocation (player2Row, player2Col), player2);

    b1 = new Button(this, "rect", 625, 525, 150, 50, "GoTo Level 2");
    // b1.setFontStyle("fonts/spidermanFont.ttf");
    // b1.setFontStyle("Helvetica");
    b1.setTextColor(PColor.WHITE);
    b1.setButtonColor(PColor.BLACK);
    b1.setHoverColor(PColor.get(100,50,200));
    b1.setOutlineColor(PColor.WHITE);

    System.out.println("Done loading Level 1 ...");
    
    //SETUP: Level 2
    player3 = new Sprite(this, player3File, 0.25f);
    player3.moveTo(player3startX, player3startY);
    level2World.addSpriteCopyTo(runningHorse, 100, 200);  //example Sprite added to a World at a location, with a speed
    level2World.printWorldSprites();
    System.out.println("Done loading Level 2 ...");
    
    //SETUP: Sound
    // Load a soundfile from the sounds folder of the sketch and play it back
     //song = new SoundFile(this, "sounds/Lenny_Kravitz_Fly_Away.mp3");
     //song.play();
    
    System.out.println("Game started...");

  } //end setup()


  //Required Processing method that automatically loops
  //(Anything drawn on the screen should be called from here)
  public void draw() {

    updateTitleBar();
    updateScreen();

    //simple timing handling
    if (msElapsed % 300 == 0) {
      //sprite handling
      populateSprites();
      moveSprites();
    }
    msElapsed +=100;
    currentScreen.pause(100);

    //check for end of game
    if(isGameOver()){
      endGame();
    }

  } //end draw()

  //------------------ USER INPUT METHODS --------------------//


  //Known Processing method that automatically will run whenever a key is pressed
  public void keyPressed(){

    //check what key was pressed
    System.out.println("\nKey pressed: " + p.keyCode); //key gives you a character for the key pressed

    //What to do when a key is pressed?
    
    //KEYS FOR LEVEL1
    if(currentScreen == level1Grid){

      //set [W] key to move the player1 up & avoid Out-of-Bounds errors
      if(p.keyCode == 87){
      
        //Store old GridLocation
        GridLocation oldLoc = new GridLocation(player2Row, player2Col);
        
        //Erase image from previous location
        

        //change the field for player2Row
        player2Row--;
      }

      // if the 'n' key is pressed, ask for their name
      if(p.key == 'n'){
        name = Input.getString("What is your name?");
      }

      // if the 't' key is pressed, then toggle the animation on/off
      if(p.key == 't'){
        //Toggle the animation on & off
        doAnimation = !doAnimation;
        System.out.println("doAnimation: " + doAnimation);
        if(currentGrid != null){
          currentGrid.setMark("X",currentGrid.getGridLocation());
        }
      }



    }

    //CHANGING SCREENS BASED ON KEYS
    //change to level1 if 1 key pressed, level2 if 2 key is pressed
    if(p.key == '1'){
      currentScreen = level1Grid;
    } else if(p.key == '2'){
      currentScreen = level2World;
    }


  }

  // Known Processing method that automatically will run when a mouse click triggers it
  public void mouseClicked(){
    
    // Print coordinates of mouse click
    System.out.println("\nMouse was clicked at (" + p.mouseX + "," + p.mouseY + ")");

    // Display color of pixel clicked
    int color = p.get(p.mouseX, p.mouseY);
    PColor.printPColor(p, color);

    // Print grid coordinate clicked
    if(currentGrid != null){
      System.out.println("Grid location --> " + currentGrid.getGridLocation());
    }

    // what to do if clicked? (ex. assign a new location to player1)
    if(currentScreen == level1Grid){
      player1Row = currentGrid.getGridLocation().getRow();
      player1Col = currentGrid.getGridLocation().getCol();
    }
    


  }



  //------------------ CUSTOM  GAME METHODS --------------------//

  // Updates the title bar of the Game
  public void updateTitleBar(){

    if(!isGameOver()) {
      //set the title each loop
      // surface.setTitle(titleText + "    " + extraText + " " + name + ": " + health);

      //adjust the extra text as desired
    
    }
  }

  // Updates what is drawn on the screen each frame
  public void updateScreen(){

    // UPDATE: Background of the current Screen
    currentScreen.show();

    // UPDATE: splashScreen
    if(currentScreen == splashScreen && splashScreen.getScreenTime() > 3000 && splashScreen.getScreenTime() < 5000){
      System.out.print("s");
      currentScreen = level1Grid;
    }

    // UPDATE: level1Grid Screen
    if(currentScreen == level1Grid){
      System.out.print("1");
      currentGrid = level1Grid;

      // Displays the player1 image
      GridLocation player1Loc = new GridLocation(player1Row,player1Col);
      level1Grid.setTileImage(player1Loc, player1);

      // Displays the player2 image
      GridLocation player2Loc = new GridLocation(player2Row, player2Col);
      level1Grid.setTileSprite(player2Loc, player2);
      
      // Updates other screen elements
      level1Grid.showGridImages();
      level1Grid.showGridSprites();
      level1Grid.showWorldSprites();

      // Moves to next level based on a button click
      b1.show();
      if(b1.isClicked()){
        System.out.println("\nButton Clicked");
        currentScreen = level2World;
      }
    
    }
    
    // UPDATE: level2World Screen
    if(currentScreen == level2World){
      System.out.print("2");
      currentWorld = level2World;
      // currentGrid = null;

      level2World.moveBgXY(-3.0f, 0f);
      level2World.show();
      player3.show();
      level2World.showWorldSprites();

    }

    // UPDATE: End Screen
    // if(currentScreen == endScreen){

    // }

    // UPDATE: Any Screen
    if(doAnimation){
      runningHorse.animateHorizontal(5.0f, 10.0f, true);
    }


  }

  // Populates enemies or other sprites on the Screen
  public void populateSprites(){

    //What is the index for the last column?
    

    //Loop through all the rows in the last column

      //Generate a random number


      //10% of the time, decide to add an enemy image to a Tile
      

  }

  // Moves around the enemies/sprites on the Screen
  public void moveSprites(){

    //Loop through all of the rows & cols in the grid

        //Store the current GridLocation

        //Store the next GridLocation

        //Check if the current tile has an image that is not player1      


          //Get image/sprite from current location
            

          //CASE 1: Collision with player1


          //CASE 2: Move enemy over to new location


          //Erase image/sprite from old location

          //System.out.println(loc + " " + grid.hasTileImage(loc));

            
        //CASE 3: Enemy leaves screen at first column

  }

  // Checks if there is a collision between Sprites on the Screen
  public boolean checkCollision(GridLocation loc, GridLocation nextLoc){

    //Check what image/sprite is stored in the CURRENT location
    // PImage image = grid.getTileImage(loc);
    // AnimatedSprite sprite = grid.getTileSprite(loc);

    //if empty --> no collision

    //Check what image/sprite is stored in the NEXT location

    //if empty --> no collision

    //check if enemy runs into player

      //clear out the enemy if it hits the player (using cleartTileImage() or clearTileSprite() from Grid class)

      //Update status variable

    //check if a player collides into enemy

    return false; //<--default return
  }

  // Indicates when the main game is over
  public boolean isGameOver(){
    
    return false; //by default, the game is never over
  }

  // Describes what happens after the game is over
  public void endGame(){
      System.out.println("Game Over!");

      // Update the title bar

      // Show any end imagery
      currentScreen = endScreen;

  }


} //close class
