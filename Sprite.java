/* Sprite class - to create objects that move around with their own properties
 * Inspired by Daniel Shiffman's p5js Animated Sprite tutorial
 * Note: Picture coordinate origina at top, left corner
 * Author: Joel Bianchi
 * Last Edit: 5/20/25
 * Added new constructor for Platforms
 * Added acceleration for Physics + jumping
 */


import processing.core.PApplet;
import processing.core.PImage;

public class Sprite{

  public PApplet p;
  
  //------------------ SPRITE FIELDS --------------------//
  private PImage spriteImg;
  private String spriteImgFile;
  private int color = PColor.NULL;
  private String name;
  private float w;
  private float h;
  private float centerX;
  private float centerY;
  private float speedX;
  private float speedY;
  private float accelX;
  private float accelY;
  private boolean isAnimated;
  private boolean hasGravity = false;


  //------------------ SPRITE CONSTRUCTORS --------------------//

  // Sprite Constructor #1: Only pass in the image file (Non-animated)
  public Sprite(PApplet p, String spriteImgFile){
    this(p, spriteImgFile, 1.0f, 0.0f, 0.0f, false);
  }

  // Sprite Constructor #2: Only pass in the image file that can be scaled (Non-animated)
  public Sprite(PApplet p, String spriteImgFile, float scale){
    this(p, spriteImgFile, scale, 0.0f, 0.0f, false);
  }

  // Sprite Constructor #3: for Non-Animated Sprite (not working)
  public Sprite(PApplet p, String spriteImgFile, float scale, float x, float y) {
    this(p, spriteImgFile, scale, x, y, false);
  }

  // Sprite Constructor #4: for ANY Sprite from a file name
  public Sprite(PApplet p, String spriteImgFile, float scale, float x, float y, boolean isAnimated) {

    System.out.println("Sprite: Loading new Sprite " + spriteImgFile + " which is animated? " + isAnimated);
    this.p = p;
    this.spriteImgFile = spriteImgFile;

    if(!isAnimated){
      if( spriteImgFile != null){
        this.spriteImg = p.loadImage(spriteImgFile);
        w = spriteImg.width * scale;
        h = spriteImg.height * scale;
        // System.out.println("Sprite 64: " + spriteImg);
      } else {

      }
    }

    setLeft(x);
    setTop(y);
    this.speedX = 0;
    this.speedY = 0;
    this.isAnimated = isAnimated;

    // System.out.println("---->Sprite Class 69: "+ Game.toStringPImage(spriteImg));

  }

  // Sprite Constructor #5: Input a PImage directly, Used for moveable Sprites
  public Sprite(PApplet p, PImage spriteImg, float scale, float x, float y) {

    System.out.println("Sprite: Loading new moveable Sprite!");
    this.p = p;
    this.spriteImg = spriteImg;
    this.w = spriteImg.width * scale;
    this.h = spriteImg.height * scale;
    setLeft(x);
    setTop(y);
    this.speedX = 0;
    this.speedY = 0;
    this.isAnimated = false;

  }

  // Sprite Constructor #6: Blob of color Sprite, used for Platform
  public Sprite(PApplet p, int color, float posXCenter, float posYTop, float platWidth, float platHeight){

    System.out.println("Sprite: Loading color-blob Sprite!");
    this.p = p;
    this.w = platWidth;
    this.h = platHeight;
    setCenterX(posXCenter);
    setTop(posYTop);
    this.speedX = 0;
    this.speedY = 0;
    this.isAnimated = false;
    this.color = color;
    System.out.println("done loading Sprite: " + this);
  }


  //------------------ SPRITE MOTION METHODS --------------------//

  // method to display the Sprite image on the screen
  public void show() {

    // Sprite comes from Image file
    if(spriteImgFile != null){
      // System.out.println("\nspriteshow\t" + spriteImg);
      p.image(spriteImg, getLeft(), getTop(), w, h);
    }

    // Sprite is just a blob of color
    else{
      // System.out.println("spriteshow\tcolor blob");
    }
  }

  // method to move Sprite image on the screen to a specific coordinate
  public void moveTo(float x, float y){
    setLeft(x);
    setTop(y);
  }

  // method to move Sprite image on the screen relative to current position
  public void move(float changeX, float changeY){
    this.centerX += changeX;
    this.centerY += changeY;
    // System.out.println(getLeft() + "," + getTop());
  }

  // Changes the speed of the Sprite
  public void setSpeed( float speedX, float speedY){
    this.speedX = speedX;
    this.speedY = speedY;
  }

  // Gets the speed of the Sprite in the X-direction
  public float getSpeedX(){
    return speedX;
  }

  // Gets the speed of the Sprite in the Y-direction
  public float getSpeedY(){
    return speedX;
  }

  // Change the acceleration of the Sprite in the Y-direction
  public void setAccelerationY(float accelY){
    this.accelY = accelY;
  }

  // Change the acceleration of the Sprite in the X-direction
  public void setAccelerationX(float accelX){
    this.accelX = accelX;
  }

  // Starts gravity acting on sprite at a particular acceleration rate
  public void startGravity(float accelY){
    this.hasGravity = true;
    setAccelerationY(accelY);
  }

  // Starts gravity acting on a Sprite at default rate
  public void startGravity(){
    startGravity(5.0f); //positive acceleration in Y-direction is downwards
  }

  // Stops gravity acting on a Sprite
  public void stopGravity(){
    this.hasGravity = false;
  }

  // method to rotate Sprite image on the screen
  public void rotate(float degrees){
    float rads = p.radians(degrees);
    p.translate(centerX,centerY);
    rotate(rads);
  }



  //------------------ SPRITE COORDINATES ACCESSOR & MUTATOR METHODS --------------------//

  public float getW(){
    return w;
  }
  public float getH(){
    return h;
  }
  public float getCenterX(){
    return centerX;
  }
  public float getCenterY(){
    return centerY;
  }
  public float getX(){
    return getCenterX();
  }
  public float getY(){
    return getCenterY();
  }

  public void setW(float w){
    this.w = w;
  }
  public void setH(float h){
    this.h=h;
  }
  public void setCenterX(float centerX){
    this.centerX = centerX;
  }
  public void setCenterY(float centerY){
    this.centerY=centerY;
  }
  
  
  /*------------------ SPRITE BOUNDARY METHODS  --------------------
   * -- Used from Long Bao Nguyen
   *  -- https://longbaonguyen.github.io/courses/platformer/platformer.html
   */
  void setLeft(float left){
    centerX = left + w/2;
  }
  float getLeft(){
    return centerX - w/2;
  }
  void setRight(float right){
    centerX = right - w/2;
  }
  float getRight(){
    return centerX + w/2;
  }
  void setTop(float top){
    centerY = top + h/2;
  }
  float getTop(){
    return centerY - h/2;
  }
  void setBottom(float bottom){
    centerY = bottom - h/2;
  }
  float getBottom(){
    return centerY + h/2;
  }

  //------------------ SPRITE IMAGE & ANIMATION METHODS --------------------//

  //Accessor method to the Sprite object
  public PImage getImage(){
    return this.spriteImg;
  }
  //Mutator method to the Sprite object
  public void setImage(PImage img){
    this.spriteImg = img;
  }

  //Accessor method to check if Sprite object is animated
  public boolean getIsAnimated(){
    return isAnimated;
  }
  //Mutator method to change if Sprite object is animated
  public void setIsAnimated(boolean a){
    isAnimated = a;
  }

  //Accessor method to the image path of the Sprite
  public String getImagePath(){
    return this.spriteImgFile;
  }

  //Method to be used to compare 2 sprites by a name, will check the image file name if no name specified
  public String getName(){
    if(name == null){
      return getImagePath();
    } else {
      return name;
    }
  }

  //Sets the Sprites name to be used for comparisons
  public void setName(String name){
    this.name = name;
  }


  //Method to copy a Sprite to a specific location
  public Sprite copyTo(float x, float y){

    // PImage si = this.spriteImg;
    String sif = this.spriteImgFile;
    float cx = this.centerX;
    float cy = this.centerY;
    float sx = this.speedX;
    float sy = this.speedY;
    float w = this.w;
    float h = this.h;
    boolean ia = this.isAnimated;
    
    Sprite sp = new Sprite(p, sif, 1.0f, cx, cy, ia);
    sp.setSpeed(sx,sy);
    sp.setW(w);
    sp.setH(h);

    return sp;

  }

  public void resize(int width, int height){
    spriteImg.resize(width, height);
  }


  // // method that automatically moves the Sprite based on its velocity
  // public void update(){
  //   // speedX = 
  //   // if()
  //   move(speedX, speedY);
  // }

  public void update(float deltaTime){
    // speedX += deltaTime/1000;
    if(hasGravity){
      float sec = deltaTime/1000;
      speedY += accelY*sec;
    }
    move(speedX, speedY);
  }

  //Method to copy a Sprite to same location
  public Sprite copy(){
    return copyTo(this.centerX, this.centerY);
  }

  //Method to check if 2 Sprites are the same (based on name or image)
  public boolean equals(Sprite otherSprite){
    if(this.spriteImgFile != null && otherSprite != null && this.getName().equals(otherSprite.getName())){
      return true;
    }
    return false;
  }

  public String toString(){
    return "Sprite: fn: "+ spriteImgFile + "\tx:" + centerX + "\ty:" + centerY + "\tL:" + getLeft() + "\tT:" + getTop() + "\tVx:" + speedX + "\tVy:" + speedY + "\tw:" + w + "\th:" + h + "\t" + isAnimated;
  }

}
