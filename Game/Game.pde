/* Game Class Starter File
 * Authors: Joel A. Bianchi
 * Last Edit: 5/29/2024
 * Modified for AnimatedSprites
 */

//import processing.sound.*;

//------------------ GAME VARIABLES --------------------//

//VARIABLES: Title Bar
String titleText = "PeanutChessSkyHorse";
String extraText = "CurrentLevel?";

//VARIABLES: Whole Game
AnimatedSprite runningHorse;
boolean doAnimation;

//VARIABLES: Splash Screen
Screen splashScreen;
PImage splashBg;
String splashBgFile = "images/apcsa.png";
//SoundFile song;

//VARIABLES: Level1Grid Screen
Grid level1Grid;
PImage level1Bg;
String level1BgFile = "images/chess.jpg";
PImage player1;   //Use PImage to display the image in a GridLocation
String player1File = "images/x_wood.png";
int player1Row = 3;
int player1Col = 0;
int health = 3;
AnimatedSprite walkingChick;
Button b1 = new Button("rect", 650, 525, 100, 50, "GoToLevel2");

//VARIABLES: Level2World Pixel-based Screen
World level2World;
PImage level2Bg;
String level2BgFile = "images/sky.jpg";
Sprite player2; //Use Sprite for a pixel-based Location
String player2File = "images/zapdos.png";
int player2startX = 50;
int player2startY = 300;

//VARIABLES: EndScreen
World endScreen;
PImage endBg;
String endBgFile = "images/youwin.png";


//VARIABLES: Tracking the current Screen being displayed
Screen currentScreen;
World currentWorld;
Grid currentGrid;
private int msElapsed = 0;


//------------------ REQUIRED PROCESSING METHODS --------------------//

//Required Processing method that gets run once
void setup() {

  //SETUP: Match the screen size to the background image size
  size(800,600);  //these will automatically be saved as width & height
  imageMode(CORNER);    //Set Images to read coordinates at corners
  //fullScreen();   //only use if not using a specfic bg image
  
  //SETUP: Set the title on the title bar
  surface.setTitle(titleText);

  //SETUP: Load BG images used in all screens
  splashBg = loadImage(splashBgFile);
  splashBg.resize(width, height);
  level1Bg = loadImage(level1BgFile);
  level1Bg.resize(width, height);
  level2Bg = loadImage(level2BgFile);
  level2Bg.resize(width, height);
  endBg = loadImage(endBgFile);
  endBg.resize(width, height);  

  //SETUP: Screens, Worlds, Grids
  splashScreen = new Screen("splash", splashBg);
  level1Grid = new Grid("chessBoard", level1Bg, 6, 8);
  //level1Grid.startPrintingGridMarks();
  level2World = new World("sky", level2BgFile, 8.0, 0, 0); //moveable World constructor --> defines center & scale (x, scale, y)???
  //level2World = new World("sky", level2Bg);   //non-moving World construtor
  endScreen = new World("end", endBg);
  currentScreen = splashScreen;

  //SETUP: All Game objects
  runningHorse = new AnimatedSprite("sprites/horse_run.png", "sprites/horse_run.json", 50.0, 75.0, 10.0);

  //SETUP: Level 1
  player1 = loadImage(player1File);
  player1.resize(level1Grid.getTileWidth(),level1Grid.getTileHeight());
  walkingChick = new AnimatedSprite("sprites/chick_walk.png", "sprites/chick_walk.json", 0.0, 0.0, 5.0);
  level1Grid.setTileSprite(new GridLocation (5,5), walkingChick);
  System.out.println("Done loading Level 1 ...");
  
  //SETUP: Level 2
  player2 = new Sprite(player2File, 0.25);
  //player2.moveTo(player2startX, player2startY);
  level2World.addSpriteCopyTo(runningHorse, 100, 200);  //example Sprite added to a World at a location, with a speed
  level2World.printWorldSprites();
  System.out.println("Done loading Level 2 ...");
  
  //SETUP: Sound
  // Load a soundfile from the /data folder of the sketch and play it back
  // song = new SoundFile(this, "sounds/Lenny_Kravitz_Fly_Away.mp3");
  // song.play();
  
  println("Game started...");

} //end setup()


//Required Processing method that automatically loops
//(Anything drawn on the screen should be called from here)
void draw() {

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
void keyPressed(){

  //check what key was pressed
  System.out.println("\nKey pressed: " + keyCode); //key gives you a character for the key pressed

  //What to do when a key is pressed?
  
  //KEYS FOR LEVEL1
  if(currentScreen == level1Grid){

    //set [W] key to move the player1 up & avoid Out-of-Bounds errors
    if(keyCode == 87){
    
      //Store old GridLocation
      GridLocation oldLoc = new GridLocation(player1Row, player1Col);
      
      //Erase image from previous location
      

      //change the field for player1Row
      player1Row--;
    }



  }

  //CHANGING SCREENS BASED ON KEYS
  //change to level1 if 1 key pressed, level2 if 2 key is pressed
  if(key == '1'){
    currentScreen = level1Grid;
  } else if(key == '2'){
    currentScreen = level2World;
  }


}

//Known Processing method that automatically will run when a mouse click triggers it
void mouseClicked(){
  
  //check if click was successful
  System.out.println("\nMouse was clicked at (" + mouseX + "," + mouseY + ")");
  if(currentGrid != null){
    System.out.println("Grid location: " + currentGrid.getGridLocation());
  }

  //what to do if clicked? (Make player1 jump back?)
  


  //Toggle the animation on & off
  doAnimation = !doAnimation;
  System.out.println("doAnimation: " + doAnimation);
  if(currentGrid != null){
    currentGrid.setMark("X",currentGrid.getGridLocation());
  }

}



//------------------ CUSTOM  GAME METHODS --------------------//

//method to update the Title Bar of the Game
public void updateTitleBar(){

  if(!isGameOver()) {
    //set the title each loop
    surface.setTitle(titleText + "    " + extraText + " " + health);

    //adjust the extra text as desired
  
  }
}

//method to update what is drawn on the screen each frame
public void updateScreen(){

  //UPDATE: Background of the current Screen
  if(currentScreen.getBg() != null){
    background(currentScreen.getBg());
  }

  //UPDATE: splashScreen
  if(currentScreen == splashScreen && splashScreen.getScreenTime() > 3000 && splashScreen.getScreenTime() < 5000){
    System.out.print("s");
    currentScreen = level1Grid;
  }

  //UPDATE: level1Grid Screen
  if(currentScreen == level1Grid){
    System.out.print("1");
    currentGrid = level1Grid;

    //Display the Player1 image
    GridLocation player1Loc = new GridLocation(player1Row,0);
    level1Grid.setTileImage(player1Loc, player1);
    
    //update other screen elements
    level1Grid.showGridImages();
    level1Grid.showGridSprites();
    level1Grid.showWorldSprites();

    //move to next level based on a button click
    b1.show();
    if(b1.isClicked()){
      System.out.println("\nButton Clicked");
      currentScreen = level2World;
    }
  
  }
  
  //UPDATE: level2World Scren
  if(currentScreen == level2World){
    System.out.print("2");
    currentWorld = level2World;
    currentGrid = null;
    
    level2World.moveBgXY(-3.0, 0);
    level2World.show();

    player2.show();

    level2World.showWorldSprites();

  }

  //UPDATE: End Screen
  // if(currentScreen == endScreen){

  // }

  //UPDATE: Any Screen
  if(doAnimation){
    runningHorse.animateHorizontal(5.0, 10.0, true);
  }


}

//Method to populate enemies or other sprites on the screen
public void populateSprites(){

  //What is the index for the last column?
  

  //Loop through all the rows in the last column

    //Generate a random number


    //10% of the time, decide to add an enemy image to a Tile
    

}

//Method to move around the enemies/sprites on the screen
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

//Method to check if there is a collision between Sprites on the Screen
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

//method to indicate when the main game is over
public boolean isGameOver(){
  
  return false; //by default, the game is never over
}

//method to describe what happens after the game is over
public void endGame(){
    System.out.println("Game Over!");

    //Update the title bar

    //Show any end imagery
    currentScreen = endScreen;

}
