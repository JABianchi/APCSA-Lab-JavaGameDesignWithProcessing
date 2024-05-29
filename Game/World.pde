/* World Class - Used to describe the screen of a pixel-based game
 * Subclass of a Screen, includes an ArrayList of AnimatedSprite objects
 * Authors: Joel Bianchi, Nathan Santos, Clive Sherwood
 * Last Edit: 5/28/2024
 * Added Constructor for Moveable Backgrounds
 */

import java.util.ArrayList;

public class World extends Screen{

  //------------------ WORLD FIELDS --------------------//
  //private static World currentWorld = null; //static variable to track the current world
  private ArrayList<AnimatedSprite> sprites = new ArrayList<AnimatedSprite>();
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
  public ArrayList<AnimatedSprite> getSprites(){
      return sprites;
  }

  //method to add a sprite to the world
  public void addSprite(AnimatedSprite sprite) {
    if (!sprites.contains(sprite)) {
      sprites.add(sprite);
    }
  }

  //method to add a copy of a sprite to a specific coordinate in the world
  public void addSpriteCopyTo(AnimatedSprite sprite, float x, float y) {
    sprites.add(sprite.copyTo(x,y));
  }

  //method to add a copy of a sprite to the world
  public void addSpriteCopy(AnimatedSprite sprite) {
    sprites.add(sprite.copy());
  }

  //method to remove a sprite from the world
  public void removeSprite(AnimatedSprite sprite) {
    if (sprites.contains(sprite)) {
      sprites.remove(sprite);
    }
  }

  //method to display all the sprites on the screen
  public void showSprites(){
    //System.out.println("showing sprites...");
    //Loop through all the sprites
    for(AnimatedSprite sprite : sprites){
        sprite.show();        
    }
  }

  //method to get ArrayList of all sprites in World
  // public ArrayList<Sprite> getSprites(){
  //   return sprites;
  // }

  //method to print out list of sprites
  public void printSprites(){
      for(Sprite sprite: sprites){
          System.out.println(sprite);
      }
  }


  //------------------ WORLD MUTATOR METHODS --------------------//
  
  //method to update all sprites in the world each cycle
  public void update() {
    long deltaTime = getTimeSince(lastSpriteUpdateTime);
    for (AnimatedSprite sprite : sprites) {
      sprite.update(deltaTime);
    }
    lastSpriteUpdateTime = getScreenTime();
  }

}
