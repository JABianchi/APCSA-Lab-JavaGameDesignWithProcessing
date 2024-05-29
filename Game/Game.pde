/* Game Class Starter File
 * Authors: Joel A. Bianchi
 * Last Edit: 5/22/2024
 */

//import processing.sound.*;

//------------------ GAME VARIABLES --------------------//

//Title Bar
String titleText = "HorseChess";
String extraText = "Who's Turn?";


//Splash Screen Variables
Screen splashScreen;
PImage splashBg;
String splashBgFile = "images/apcsa.png";
//SoundFile song;


//Level1 Grid-Screen Variables
Grid level1Grid;
PImage level1Bg;
String level1BgFile = "images/chess.jpg";

PImage player1;   //Use PImage to display the image in a GridLocation
String player1File = "images/x_wood.png";
int player1Row = 3;
int player1Col = 0;
int health = 3;
PImage enemy;
String enemyFile = "images/bomb.png";
Button b1 = new Button("rect", 400, 500, 100, 50, "GoToLevel2");
AnimatedSprite enemySprite;


//Level2 Pixel-based-Screen Variables
World level2World;
PImage level2Bg;
String level2BgFile = "images/sky.jpg";

Sprite player2;   //Use PImage to display the image in a GridLocation
String player2File = "images/zapdos.png";
int player2startX = 50;
int player2startY = 300;


//EndScreen variables
World endScreen;
PImage endBg;
String endBgFile = "images/youwin.png";


//Whole Game Variables
AnimatedSprite exampleSprite;
boolean doAnimation;


//Variables to track the current Screen being displayed
Screen currentScreen;
World currentWorld;
Grid currentGrid;
private int msElapsed = 0;


//------------------ REQUIRED PROCESSING METHODS --------------------//

//Required Processing method that gets run once
void setup() {

  //Match the screen size to the background image size
  size(800,600);
  
  //Set the title on the title bar
  surface.setTitle(titleText);

  //Load BG images used in all screens
  splashBg = loadImage(splashBgFile);
  splashBg.resize(800,600);
  level1Bg = loadImage(level1BgFile);
  level1Bg.resize(800,600);
  level2Bg = loadImage(level2BgFile);
  level2Bg.resize(800,600);
  endBg = loadImage(endBgFile);
  endBg.resize(800,600);  //------------------ OTHER GRID METHODS --------------------//


  //setup the screens/worlds/grids in the Game
  splashScreen = new Screen("splash", splashBg);
  level1Grid = new Grid("chessBoard", level1Bg, 6, 8);
  level2World = new World("sky", level2BgFile, 8.0, 0, 0); //moveable World constructor --> defines center & scale (x, scale, y)???
  //level2World = new World("sky", level2Bg);   //simple World construtor
  endScreen = new World("end", endBg);
  currentScreen = splashScreen;

  //Level 1 Image Setup - GRID  
  player1 = loadImage(player1File);
  player1.resize(level1Grid.getTileWidthPixels(),level1Grid.getTileHeightPixels());
  enemy = loadImage(enemyFile);
  enemy.resize(level1Grid.getTileWidthPixels(),level1Grid.getTileHeightPixels());
  

  //Adding pixel-based Animated Sprites to the world
  // level1Grid.addSpriteCopyTo(exampleSprite);
  level1Grid.printSprites();
  System.out.println("Done adding sprites to level 1..");
  
  //LEVEL 2 SPRITE SETUP - WORLD
  player2 = new Sprite(player2File, 0.25);
  //player2.moveTo(player2startX, player2startY);
  // enemy = loadImage("images/articuno.png");
  // enemy.resize(100,100);

  
  //Other Setup
  exampleAnimationSetup();

  // Load a soundfile from the /data folder of the sketch and play it back
  // song = new SoundFile(this, "sounds/Lenny_Kravitz_Fly_Away.mp3");
  // song.play();
  
  imageMode(CORNER);    //Set Images to read coordinates at corners
  //fullScreen();   //only use if not using a specfic bg image
  println("Game started...");

} //end setup()

//Required Processing method that automatically loops
//(Anything drawn on the screen should be called from here)
void draw() {

  updateTitleBar();

  //simple timing handling
  if (msElapsed % 300 == 0) {
    //sprite handling
    populateSprites();
    moveSprites();
  }

  updateScreen();

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
  System.out.println("Key pressed: " + key); //keyCode gives you an integer for the key

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



  } else if (currentScreen == level2World){

  //set [W] key to move the player1 down
    if(key == 'w'){
      player2.move(0,-10);
    } else if (key == 's'){
      player2.move(0,10);
    } else if (key == 'a'){
      player2.move(-10,0);
    } else if (key == 'd'){
      player2.move(10,0);
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
  System.out.println("Mouse was clicked at (" + mouseX + "," + mouseY + ")");
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

  //Update the Background of the current Screen
  if(currentScreen.getBg() != null){
    background(currentScreen.getBg());
  }

  //splashScreen update
  if(splashScreen.getScreenTime() > 3000 && splashScreen.getScreenTime() < 5000){
    currentScreen = level1Grid;
  }

  //level1Grid Screen Updates
  if(currentScreen == level1Grid){
    currentGrid = level1Grid;

    //Display the Player1 image
    GridLocation player1Loc = new GridLocation(player1Row,0);
    level1Grid.setTileImage(player1Loc, player1);
      
    //update other screen elements
    level1Grid.showSprites();
    level1Grid.showImages();
    level1Grid.showGridSprites();

    //move to next level based on a button click
    b1.show();
    if(b1.isClicked()){
      currentScreen = level2World;
    }
    
  }

  //level2World Updates
  else if(currentScreen == level2World){
    currentWorld = level2World;
    
    level2World.moveBgXY(-3.0, 0);
    level2World.show();

    player2.show();


  }

  //Updtes for any Screen
  checkExampleAnimation();


}

//Method to populate enemies or other sprites on the screen
public void populateSprites(){

  //What is the index for the last column?
  int lastCol = level1Grid.getNumCols() -1; 

  //Loop through all the rows in the last column
  for(int r=0; r<level1Grid.getNumRows(); r++){

    GridLocation loc = new GridLocation(r, lastCol);

    //Generate a random number
    double rando = Math.random();

    //10% of the time, decide to add an enemy image to a Tile
    if(rando < 0.1){
      level1Grid.setTileImage(loc, enemy);
      System.out.println("Adding bomb to " + loc);
    }

  }

}

//Method to move around the enemies/sprites on the screen
public void moveSprites(){

//Loop through all of the rows & cols in the grid
for(int r=0; r<level1Grid.getNumRows(); r++){
  for(int c=0; c<level1Grid.getNumCols(); c++){

    GridLocation loc = new GridLocation(r,c);

    //check for enemy bomb at the loc
    if(level1Grid.getTileImage(loc) == enemy ){

      //erase bomb from current loc
      level1Grid.clearTileImage(loc);
      
      //only move if it's a legal col
      if( c >= 1){
        //add bomb to loc to left
        GridLocation leftLoc = new GridLocation(r, c-1);
        level1Grid.setTileImage(leftLoc, enemy);
        //System.out.println("moving bomb");
      }
    }
  }
}
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

//example method that creates 1 horse run along the screen
public void exampleAnimationSetup(){  
  int i = 2;
  exampleSprite = new AnimatedSprite("sprites/horse_run.png", "sprites/horse_run.json", 50.0, i*75.0);
  //exampleSprite.resize(200,200);
}

//example method that animates the horse Sprites
public void checkExampleAnimation(){
  if(doAnimation){
    exampleSprite.animateHorizontal(5.0, 10.0, true);
    //System.out.println("animating!");
  }
}
