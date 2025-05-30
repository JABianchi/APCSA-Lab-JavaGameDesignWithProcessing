/** 
 * World Class - Used to describe the screen of a pixel-based game
 * Subclass of a Screen, includes an ArrayList of Sprite objects
 * @author Joel A Bianchi
 * @author Nathan Santos
 * @author Clive Sherwood
 * @author Vanessa Balbuena
 * @version 5/29/25
 * Added getColliders() method
 * Added javadoc formatting
 */

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;


public class World extends Screen{

  //------------------ WORLD FIELDS --------------------//
  private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
  long lastSpriteUpdateTime = 0;

  //------------------ WORLD CONSTRUCTORS --------------------//
  
  /**
   * World Constructor #1: Creates a World with the specificed background image
   * @param p             Processing applet
   * @param screenName    String to track Screens
   * @param bgImg         stationary background image
   */
  public World(PApplet p, String screenName, PImage bgImg) {
    super(p, screenName, bgImg);
  }

  /**
   * World Constructor #2: Creates a World with no background image
   * @param p             Processing applet
   * @param screenName    String to track Screens
   */
  public World(PApplet p, String screenName) {
    this(p, screenName, null);
  }
  
  /**
   * World Constructor #3: Creates a default World
   * @param p             Processing applet
   */
  public World(PApplet p) {
    this(p, "default world", null);
  }

  /**
   * World Constructor #4 for Moveable Backgrounds
   * Image can be scaled to be bigger so the background doesn't run out too quickly
   * @param p             Processing applet
   * @param screenName    String to track Screens
   * @param movingBgFile  filename for the background image to move
   * @param scale         float that multiplies the size of the image to display
   * @param x             sets the initial left edge of the background
   * @param y             sets the intial top edge of the background
   */
  public World(PApplet p, String screenName, String movingBgFile, float scale, float x, float y) {
    super(p, screenName, movingBgFile, scale, x, y);
    System.out.println("World " + screenName + " constructed with " + movingBgFile);
  }

  
  


  /**
   * World Constructor #5: for Moveable backgrounds (NOT WORKING!)
   * Takes in a file, not PImage
   * @param p             Processing applet
   * @param screenName    String to track Screens
   * @param movingBgFile  filename for the background image to move
   * @param scale         float that multiplies the size of the image to display
   * @param x             sets the initial left edge of the background
   * @param y             sets the intial top edge of the background
   */
  // public World(PApplet p, String screenName, PImage movingBg, float scale, float x, float y) {
  //   super(p, screenName, movingBg, scale, x, y);
  //   System.out.println("World " + screenName + " constructed with " + Util.toStringPImage(movingBg));
  // }


  //------------------ WORLD SPRITE METHODS --------------------//
  
  /**
   * @return ArrayList<Sprite>   all Sprites added to the World
   */
  public ArrayList<Sprite> getSprites(){
      return sprites;
  }  
  
  /** 
   * Adds a Sprite to the World
   * @param sprite
   */
  public void addSprite(Sprite sprite) {
    if (!sprites.contains(sprite)) {
      sprites.add(sprite);
    }
  }
  
  /** 
   * Adds a copy of a Sprite to the World
   * @param sprite
   */
  public void addSpriteCopy(Sprite sprite) {
    if(sprite.getIsAnimated()){
      sprites.add(((AnimatedSprite)sprite).copy());
    } else {
      sprites.add(sprite.copy());
    }
  }
  
  /** 
   * Adds a copy of a Sprite to a specific coordinate in the World
   * @param sprite
   * @param x
   * @param y
   */
  public void addSpriteCopyTo(Sprite sprite, float x, float y) {
    if(sprite.getIsAnimated()){
      sprites.add( ((AnimatedSprite)sprite).copyTo(x,y));
    } else {
      sprites.add(sprite.copyTo(x,y));
    }
  }
  
  /**
   * Adds a copy of an Animated Sprite to a specific coordinate in the World 
   * @param sprite
   * @param x
   * @param y
   * @param aSpeed
   */
  public void addSpriteCopyTo(Sprite sprite, float x, float y, float aSpeed) {
    if(sprite.getIsAnimated()){
      sprites.add( ((AnimatedSprite)sprite).copyTo(x, y, aSpeed));
    } else{
      sprites.add(sprite.copyTo(x,y));
    }
  }
  
  /** 
   * @return int      number of Sprites in a World
   */
  public int getNumSprites(){
    return sprites.size();
  }
  
  /** 
   * Gets a specific Sprite based on its index
   * @param index     order that a Sprite was added to the World
   * @return Sprite
   */
  public Sprite getSprite(int index){
    return sprites.get(index);
  }
  
  /** 
   * Removes a Sprite from the world
   * @param sprite
   */
  public void removeSprite(Sprite sprite) {
    if (sprites.contains(sprite)) {
      sprites.remove(sprite);
    }
  }
  
  /** 
   * Removes a sprite from the World based on its index in the ArrayList
   * @param index     order that a Sprite was added to the World
   * @return Sprite
   */
  public Sprite removeSprite(int index) {
      return sprites.remove(index);
  }

  /**
   * Removes all current Sprites from World (useful for restarting a level)
   * @author Vanessa Balbuena 2024
   */
  public void clearAllSprites(){
    for(int i = 0; i < sprites.size(); i++){
      removeSprite(i);
    }
  }
  
  /**
   * Displays all the Sprites on the screen
   */
  public void showWorldSprites(){
    //System.out.println("showing sprites...");
    //Loop through all the sprites
    for(Sprite sprite : sprites){
      if(sprite.getIsAnimated()){
        AnimatedSprite aSprite = (AnimatedSprite) sprite;
        aSprite.animate();
        //System.out.println("aSprite: " + aSprite.getJsonFile() + "\t" + aSprite.iBucket + "\t" + aSprite.aSpeed);
        //System.out.println(aSprite.getCenterX() + "," + aSprite.getCenterY());
      } else{
        sprite.show();
      }
    }
  }

  @Deprecated
  public void showSprites(){
    showWorldSprites();
  }

  /**
   * Displays all World + Screen visuals
   */
  public void show(){
    // super.showBg();
    this.showWorldSprites();
    this.update();  // adds physics into the World
  }

  /**
   * Prints out list of Sprites
   */
  public void printWorldSprites(){
      for(Sprite sprite: sprites){
          System.out.println(sprite);
      }
  }

  @Deprecated
  public void printSprites(){
    printWorldSprites();
  }

  
  /** 
   * Checks all Sprites for collisions with one specific Sprite
   * @param checkSprite         Sprite to compare all others to
   * @return ArrayList<Sprite>  all the Sprites that are currently colliding with a specific Sprite
   */
  protected ArrayList<Sprite> getColliders(Sprite checkSprite) {
		
    ArrayList<Sprite> collidingSprites = new ArrayList<Sprite>(); 

		for (Sprite otherSprite : this.getSprites()) {
      if (otherSprite != checkSprite
        && otherSprite.isSolid()
        && checkSprite.isOverlapping(otherSprite)
      ){
        collidingSprites.add(otherSprite);
      }
		}
    return collidingSprites;
	}


  //------------------ WORLD MUTATOR METHODS --------------------//
  
  /**
   * Updates all Sprites in the World each cycle
   */
  public void update() {
    long deltaTime = getTimeSince(lastSpriteUpdateTime);
    for (Sprite sprite : sprites) {
      sprite.update(deltaTime);
    }
    lastSpriteUpdateTime = getScreenTime();
  }

} // end of World class
