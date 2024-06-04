/* World Class - Used to describe the screen of a pixel-based game
 * Subclass of a Screen, includes an ArrayList of Sprite objects
 * Authors: Joel Bianchi, Nathan Santos, Clive Sherwood
 * Last Edit: 5/29/2024
 * Added Constructor for Moveable Backgrounds
 * Adjusted variables for general Sprites
 */

import java.util.ArrayList;

public class World extends Screen{

  //------------------ WORLD FIELDS --------------------//
  //private static World currentWorld = null; //static variable to track the current world
  private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
  long lastSpriteUpdateTime = 0;

  //------------------ WORLD CONSTRUCTORS --------------------//
  //World Constructor #1
  public World(String name, PImage bgImg) {
    super(name, bgImg);
  }
  //World Constructor #2
  public World(String name) {
    this(name, null);
  }
    //World Constructor #3
  public World() {
    this("default world", null);
  }

  //World Constructor #4 for Moveable Backgrounds
  public World(String name, String movingBgFile, float scale, float x, float y) {
     super(name, movingBgFile, scale, x, y);
  }


  //------------------ WORLD SPRITE METHODS --------------------//
  public ArrayList<Sprite> getSprites(){
      return sprites;
  }

  //method to add a sprite to the world
  public void addSprite(Sprite sprite) {
    if (!sprites.contains(sprite)) {
      sprites.add(sprite);
    }
  }

  //method to add a copy of a sprite to a specific coordinate in the world
  public void addSpriteCopyTo(Sprite sprite, float x, float y) {
    if(sprite.getIsAnimated()){
      sprites.add( ((AnimatedSprite)sprite).copyTo(x,y));
    }
    sprites.add(sprite.copyTo(x,y));
  }

  //method to add a copy of a sprite to the world
  public void addSpriteCopy(Sprite sprite) {
    if(sprite.getIsAnimated()){
      sprites.add(((AnimatedSprite)sprite).copy());
    }
    sprites.add(sprite.copy());
  }

  //method to add a copy of a sprite to a specific coordinate in the world
  public void addSpriteCopyTo(Sprite sprite, float x, float y, float aSpeed) {
    if(sprite.getIsAnimated()){
      sprites.add( ((AnimatedSprite)sprite).copyTo(x, y, aSpeed));
    }
    sprites.add(sprite.copyTo(x,y));
  }

  //method to remove a sprite from the world
  public void removeSprite(Sprite sprite) {
    if (sprites.contains(sprite)) {
      sprites.remove(sprite);
    }
  }

  //method to display all the sprites on the screen
  public void showWorldSprites(){
    //System.out.println("showing sprites...");
    //Loop through all the sprites
    for(Sprite sprite : sprites){
      if(sprite.getIsAnimated()){
        AnimatedSprite aSprite = (AnimatedSprite) sprite;
        aSprite.animate();
        //System.out.println("aSprite: " + aSprite.getJsonFile() + "\t" + aSprite.iBucket + "\t" + aSprite.aSpeed);
      } else{
        sprite.show();
      }
    }
  }
  //to deprecate
  public void showSprites(){
    showWorldSprites();
  }

  //method to print out list of sprites
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
  
  //method to update all sprites in the world each cycle
  public void update() {
    long deltaTime = getTimeSince(lastSpriteUpdateTime);
    for (Sprite sprite : sprites) {
      sprite.update(deltaTime);
    }
    lastSpriteUpdateTime = getScreenTime();
  }

}
