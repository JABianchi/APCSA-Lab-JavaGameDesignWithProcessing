/* Game Class Starter File
 * Authors: Joel A. Bianchi
 * Last Edit: 5/20/25
 * using new Screen show() method
 * Eliminate usage of currentWorld & currentGrid
 * Use of CycleTimer for draw loop
 * Added Platform example in level3World
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
  String level1BgFile = "images/connect4bg.png";
  PImage level1Bg;
  String player1File = "images/redhorse.png";
  PImage player1;   // Use PImage to display the image in a GridLocation
  int player1Row = 3;
  int player1Col = 0;
  String player2File = "images/yellowhorse.png";
  int player2Row = 0;
  int player2Col = 2;
  int health = 3;
  AnimatedSprite player2;
  Button b1;

  // VARIABLES: Level2World Pixel-based Screen
  World level2World;
  String level2BgFile = "images/sky.png";
  PImage level2Bg;
  String zapdosFile = "images/zapdos.png";
  Sprite zapdos; //Use Sprite for a pixel-based Location
  int zapdosStartX = 50;
  int zapdosStartY = 300;

  //VARIABLES: Level3World Pixel-based Platformer
  World level3World;
  String level3BgFile = "images/wall.jpg";
  PImage level3Bg;
  Platform plat;

  // VARIABLES: EndScreen
  World endScreen;
  String endBgFile = "images/youwin.png";
  PImage endBg;


  // VARIABLES: Tracking the current Screen being displayed
  Screen currentScreen;
  CycleTimer slowCycleTimer;

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
    splashScreen = new Screen(p, "splash", splashBg);
    level1Grid = new Grid(p, "chessBoard", level1Bg, 6, 7);
    // level1Grid.startPrintingGridMarks();
    level2World = new World(p, "sky", level2BgFile, 4.0f, 0.0f, -800.0f); //moveable World constructor --> defines center & scale (x, scale, y)???
    
    System.out.println( "World constructed: " + Util.toStringPImage(level2World.getBgImage()));
       
    // level2World = new World("sky", level2Bg);   //non-moving World construtor
    endScreen = new World(p, "end", endBg);
    currentScreen = splashScreen;

    //SETUP: All Game objects
    runningHorse = new AnimatedSprite(p, "sprites/horse_run.png", "sprites/horse_run.json", 50.0f, 75.0f, 1.0f);

    //SETUP: Level 1
    player1 = p.loadImage(player1File);
    player1.resize(level1Grid.getTileWidth(),level1Grid.getTileHeight());
    player2 = new AnimatedSprite(p, "sprites/chick_walk.png", "sprites/chick_walk.json", 0.0f, 0.0f, 0.5f);
    level1Grid.setTileSprite(new GridLocation (player2Row, player2Col), player2);

    b1 = new Button(p, "rect", 625, 525, 150, 50, "GoTo Level 2");
    // b1.setFontStyle("fonts/spidermanFont.ttf");
    b1.setFontStyle("Calibri");
    b1.setTextColor(PColor.WHITE);
    b1.setButtonColor(PColor.BLACK);
    b1.setHoverColor(PColor.get(100,50,200));
    b1.setOutlineColor(PColor.WHITE);

    System.out.println("Done loading Level 1 ...");
    
    //SETUP: Level 2
    zapdos = new Sprite(p, zapdosFile, 0.25f);
    zapdos.moveTo(zapdosStartX, zapdosStartY);
    level2World.addSpriteCopyTo(runningHorse, 100, 200);  //example Sprite added to a World at a location, with a speed
    level2World.printWorldSprites();
    System.out.println("Done loading Level 2 ...");

    // SETUP: Level 3
    level3Bg = loadImage(level3BgFile);
    level3Bg.resize(p.width, p.height);
    level3World = new World(p,"platformer", level3Bg);
    plat = new Platform(p, PColor.MAGENTA, 500.0f, 100.0f, 200.0f, 20.0f);
    plat.setOutlineColor(PColor.BLACK);
    plat.startGravity(5.0f); //sets gravity to a rate of 5.0
    level3World.addSprite(plat);    
    System.out.println("Done loading Level 3 ...");


    //SETUP: Sound
    // Load a soundfile from the sounds folder of the sketch and play it back
     //song = new SoundFile(p, "sounds/Lenny_Kravitz_Fly_Away.mp3");
     //song.play();
    
    System.out.println("Game started...");

  } //end setup()


  //Required Processing method that automatically loops
  //(Anything drawn on the screen should be called from here)
  public void draw() {

    // Update Screen Visuals
    updateTitleBar();
    updateScreen();

    // Set Timers
    int cycleTime = 1;  //milliseconds
    int slowCycleTime = 300;  //milliseconds
    if(slowCycleTimer == null){
      slowCycleTimer = new CycleTimer(p, slowCycleTime);
    }

    // Move Sprites
    if(slowCycleTimer.isDone()){
      populateSprites();
      moveSprites();
    }

    // Pause Cycle
    currentScreen.pause(cycleTime);   // slows down the game cycles

    // Check for end of game
    if(isGameOver()){
      endGame();
    }

  } //end draw()

  //------------------ USER INPUT METHODS --------------------//


  //Known Processing method that automatically will run whenever a key is pressed
  public void keyPressed(){

    //check what key was pressed
    System.out.println("\nKey pressed: " + p.keyCode); //key gives you a character for the key pressed

    //What to do when a key is pressed?tt
    
    //KEYS FOR LEVEL1
    if(currentScreen == level1Grid){

      //set [S] key to move the player1 up & avoid Out-of-Bounds errors
      if(p.keyCode == 83){
        player2Row++;
      }
      else if(p.key == 'a' && player2Col !=0){
        player2Col--;
      }
      else if(p.key == 'w'){
        if(player2Row == 0){
          player2Row = level1Grid.getNumRows()-1;
        } else {
            player2Row--;
        }
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
      }



    }

    //KEYS FOR LEVEL 2
    if(currentScreen == level2World){
      if(p.key == 'a'){
        zapdos.move(-5,0);
      } else if (p.keyCode == 32){
        zapdos.setSpeed(10,0);
      }

    }

    //CHANGING SCREENS BASED ON KEYS
    //change to level1 if 1 key pressed, level2 if 2 key is pressed
    if(p.key == '1'){
      currentScreen = level1Grid;
    } else if(p.key == '2'){
      currentScreen = level2World;
    } else if(p.key == '3'){
      currentScreen = level3World;
      plat.moveTo(500.0f, 100.0f);
      plat.setSpeed(0,0);
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
    if(currentScreen instanceof Grid){
      System.out.println("Grid location --> " + ((Grid) currentScreen).getGridLocation());
    }

    // "Mark" the grid coordinate to track the state of the Grid
    if(currentScreen instanceof Grid){
      ((Grid) currentScreen).setMark("X",((Grid) currentScreen).getGridLocation());
    }

    // what to do if clicked? (ex. assign a new location to player1)
    if(currentScreen == level1Grid){
      player1Row = level1Grid.getGridLocation().getRow();
      player1Col = level1Grid.getGridLocation().getCol();
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

    // UPDATE: first lay down the Background
    currentScreen.showBg();

    // UPDATE: splashScreen
    if(currentScreen == splashScreen){

      // Print an s in console when splashscreen is up
      System.out.print("s");

      // Change the screen to level 1 between 3 and 5 seconds
      if(splashScreen.getScreenTime() > 3000 && splashScreen.getScreenTime() < 5000){
        currentScreen = level1Grid;
      }
    }

    // UPDATE: level1Grid Screen
    if(currentScreen == level1Grid){

      // Print a '1' in console when level1
      System.out.print("1");

      // Displays the player1 image
      GridLocation player1Loc = new GridLocation(player1Row,player1Col);
      level1Grid.setTileImage(player1Loc, player1);

      // Displays the player2 image
      GridLocation player2Loc = new GridLocation(player2Row, player2Col);
      level1Grid.setTileSprite(player2Loc, player2);

      // Moves to next level based on a button click
      b1.show();
      if(b1.isClicked()){
        System.out.println("\nButton Clicked");
        currentScreen = level2World;
      }
    
    }
    
    // UPDATE: level2World Screen
    if(currentScreen == level2World){

      // Print a '2' in console when level2
      System.out.print("2");

      level2World.moveBgXY(-0.3f, 0f);  //adjust speeds of moving backgrounds, -3.0f for 100 ms delays
      zapdos.show();

    }

    // UPDATE: level3World Screen
    if(currentScreen == level3World){

      // Print a '3 in console when level3
      System.out.print("3");


    }

    // UPDATE: End Screen
    // if(currentScreen == endScreen){

    // }

    // UPDATE: Any Screen
    if(doAnimation){
      runningHorse.animateHorizontal(0.5f, 1.0f, true);
    }

    // UPDATE: Other built-in to current World/Grid/HexGrid
    currentScreen.show();

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
