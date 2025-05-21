/* World Class - Used to describe the screen of a pixel-based game
 * Subclass of a Screen, includes an ArrayList of Sprite objects
 * Authors: Joel Bianchi, Nathan Santos, Clive Sherwood, Vanessa Balbuena
 * Last Edit: 5/20/25
 * Added gravity to World
 */

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class World extends Screen{

  //------------------ WORLD FIELDS --------------------//
  private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
  long lastSpriteUpdateTime = 0;

  //------------------ WORLD CONSTRUCTORS --------------------//
  // World Constructor #1: Creates a World with the specificed background image
  public World(PApplet p, String name, PImage bgImg) {
    super(p, name, bgImg);
  }

  // World Constructor #2: Creates a World with no background image
  public World(PApplet p, String name) {
    this(p, name, null);
  }
  
  // World Constructor #3: Creates a default World
  public World(PApplet p) {
    this(p, "default world", null);
  }

  // World Constructor #4 for Moveable Backgrounds: Image can be scaled to be bigger so the background doesn't run out too quickly
  public World(PApplet p, String name, String movingBgFile, float scale, float x, float y) {
    super(p, name, movingBgFile, scale, x, y);
    System.out.println("World " + name + " constructed with " + movingBgFile);
  }

  // World Constructor #5: Creates a World with a moving background.  (not working!)
  // public World(PApplet p, String name, PImage movingBg, float scale, float x, float y) {
  //   super(p, name, movingBg, scale, x, y);
  //   System.out.println("World " + name + " constructed with " + Util.toStringPImage(movingBg));
  // }


  //------------------ WORLD SPRITE METHODS --------------------//
  
  // Returns an ArrayList of all Sprites in the World
  public ArrayList<Sprite> getSprites(){
      return sprites;
  }

  // Adds a Sprite to the World
  public void addSprite(Sprite sprite) {
    if (!sprites.contains(sprite)) {
      sprites.add(sprite);
    }
  }

  // Adds a copy of a Sprite to the World
  public void addSpriteCopy(Sprite sprite) {
    if(sprite.getIsAnimated()){
      sprites.add(((AnimatedSprite)sprite).copy());
    } else {
      sprites.add(sprite.copy());
    }
  }

  // Adds a copy of a Sprite to a specific coordinate in the World
  public void addSpriteCopyTo(Sprite sprite, float x, float y) {
    if(sprite.getIsAnimated()){
      sprites.add( ((AnimatedSprite)sprite).copyTo(x,y));
    } else {
      sprites.add(sprite.copyTo(x,y));
    }
  }

  // Adds a copy of an Animated Sprite to a specific coordinate in the World
  public void addSpriteCopyTo(Sprite sprite, float x, float y, float aSpeed) {
    if(sprite.getIsAnimated()){
      sprites.add( ((AnimatedSprite)sprite).copyTo(x, y, aSpeed));
    } else{
      sprites.add(sprite.copyTo(x,y));
    }
  }
  
  // Returns the number of Sprites in a World
  public int getNumSprites(){
    return sprites.size();
  }

  // Gets a specific Sprite based on its index
  public Sprite getSprite(int index){
    return sprites.get(index);
  }

  // Removes a Sprite from the world
  public void removeSprite(Sprite sprite) {
    if (sprites.contains(sprite)) {
      sprites.remove(sprite);
    }
  }

  // Removes a sprite from the World based on its index in the ArrayList
  public Sprite removeSprite(int index) {
      return sprites.remove(index);
  }

  // Removes all current Sprites from World (useful for restarting a level) -Vanessa Balbuena 2024
  public void clearAllSprites(){
    for(int i = 0; i < sprites.size(); i++){
      removeSprite(i);
    }
  }

  // Displays all the Sprites on the screen
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
  //to deprecate
  public void showSprites(){
    showWorldSprites();
  }

  // Displays all World + Screen visuals
  public void show(){
    // super.showBg();
    this.showWorldSprites();
    this.update();  // adds physics into the World
  }

  // Prints out list of Sprites
  public void printWorldSprites(){
      for(Sprite sprite: sprites){
          System.out.println(sprite);
      }
  }
  //to deprecate
  public void printSprites(){
    printWorldSprites();
  }


  //------------------ WORLD MUTATOR METHODS --------------------//
  
  // Updates all Sprites in the World each cycle
  public void update() {
    long deltaTime = getTimeSince(lastSpriteUpdateTime);
    for (Sprite sprite : sprites) {
      sprite.update(deltaTime);
    }
    lastSpriteUpdateTime = getScreenTime();
  }

}
