import processing.core.PApplet;
import processing.core.PImage;

/** 
 * Sprite class - to create objects that move around with their own properties
 * Inspired by Daniel Shiffman's p5js Animated Sprite tutorial
 * Note: Picture coordinate origina at top, left corner
 * @author Joel A Bianchi
 * @author Marcus Bistline
 * @version 6/12/25
 * resize() updated for pixels (float)
 * scale() added
 */
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
  private boolean isSolid = true;
  private boolean isAnimated;
  private boolean hasGravity = false;
  private float defaultGravity = 20.0f;
  private float gravityStrength = defaultGravity;
  private float defaultJumpSpeed = 12.0f;


  //------------------ SPRITE CONSTRUCTORS --------------------//

  /**
   * Sprite Constructor #1: Only pass in the image file (Non-animated)
   * @param p             Processing applet
   * @param spriteImgFile filename for the non-animated sprite
   */
  public Sprite(PApplet p, String spriteImgFile){
    this(p, spriteImgFile, 1.0f, 0.0f, 0.0f, false);
  }

  /**
   * Sprite Constructor #2: Only pass in the image file that can be scaled (Non-animated)
   * @param p             Processing applet
   * @param spriteImgFile filename for the non-animated sprite
   * @param scale         float that multiplies the size of the image to display
   */
  public Sprite(PApplet p, String spriteImgFile, float scale){
    this(p, spriteImgFile, scale, 0.0f, 0.0f, false);
  }

  /**
   * Sprite Constructor #3: for Non-Animated Sprite (not working)
   * @param p             Processing applet
   * @param spriteImgFile filename for the non-animated sprite
   * @param scale         float that multiplies the size of the image to display
   * @param x             sets the initial LEFT edge of the Sprite
   * @param y             sets the initial TOP edge of the Sprite
   */
  public Sprite(PApplet p, String spriteImgFile, float scale, float x, float y) {
    this(p, spriteImgFile, scale, x, y, false);
  }

  /**
   * Sprite Constructor #4: for ANY Sprite from a file name
   * @param p             Processing applet
   * @param spriteImgFile filename for the non-animated sprite
   * @param scale         float that multiplies the size of the image to display
   * @param x             sets the initial LEFT edge of the Sprite
   * @param y             sets the initial TOP edge of the Sprite
   * @param isAnimated    true if animated, false otherwise
   */
  public Sprite(PApplet p, String spriteImgFile, float scale, float x, float y, boolean isAnimated) {

    System.out.println("Sprite: Loading new Sprite " + spriteImgFile + " which is animated? " + isAnimated);
    this.p = p;
    this.spriteImgFile = spriteImgFile;

    if(!isAnimated){
      if( spriteImgFile != null){
        this.spriteImg = p.loadImage(spriteImgFile);
        scale(scale);
      } else {

      }
    }

    setLeft(x);
    setTop(y);
    this.speedX = 0;
    this.speedY = 0;
    this.isAnimated = isAnimated;
    this.isSolid = true;
    // System.out.println("---->Sprite Class: "+ Game.toStringPImage(spriteImg));
  }

  /**
   * Sprite Constructor #5: Input a PImage directly, Used for moveable Sprites
   * @param p             Processing applet
   * @param spriteImg     pre-loaded PImage for Sprite
   * @param scale         float that multiplies the size of the image to display
   * @param x             sets the initial LEFT edge of the Sprite
   * @param y             sets the initial TOP edge of the Sprite
   */
  public Sprite(PApplet p, PImage spriteImg, float scale, float x, float y) {

    System.out.println("Sprite: Loading new moveable Sprite!");
    this.p = p;
    this.spriteImg = spriteImg;
    this.w = spriteImg.width * scale;
    this.h = spriteImg.height * scale;
    setLeft(x);
    setTop(y);
    resize(w,h);
    this.speedX = 0;
    this.speedY = 0;
    this.isAnimated = false;
    this.isSolid = true;
  }

  /**
   * Sprite Constructor #6: Rectangle of color Sprite, used for Platform
   * @param p             Processing applet
   * @param color         PColor int for the color of the rectangle
   * @param posXCenter    pixel x-value of center of rectangle
   * @param posYTop       pixel y-value of top of rectangle
   * @param rectWidth     number of pixels wide of rectangle
   * @param rectHeight    number of pixels high of rectangle
   */
  public Sprite(PApplet p, int color, float posXCenter, float posYTop, float rectWidth, float rectHeight){

    System.out.println("Sprite: Loading color-blob Sprite!");
    this.p = p;
    this.w = rectWidth;
    this.h = rectHeight;
    setCenterX(posXCenter);
    setTop(posYTop);
    this.speedX = 0;
    this.speedY = 0;
    this.isAnimated = false;
    this.isSolid = true;
    this.color = color;
    System.out.println("done loading Sprite: " + this);
  }


  //------------------ SPRITE MOTION METHODS --------------------//

  /**
   * Displays the Sprite image on the screen
   */
  public void show() {

    update();

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
  
  /**
   * Moves Sprite image on the screen to a specific coordinate 
   * @param x           moves left edge to this pixel value
   * @param y           moves top edge to this pixel value
   */
  public void moveTo(float x, float y){
    setLeft(x);
    setTop(y);
  }
  
  /** 
   * Moves Sprite image on the screen relative to current position
   * @param changeX     positive moves right, negative moves left
   * @param changeY     positive moves down, negative moves up
   */
  public void move(float changeX, float changeY){
    this.centerX += changeX;
    this.centerY += changeY;
    // System.out.println(getLeft() + "," + getTop());
  }
  
  /** 
   * @param speedX
   */
  public void setSpeedX( float speedX){
    this.speedX = speedX;
  }
  
  /** 
   * @param speedY
   */
  public void setSpeedY( float speedY){
    this.speedY = speedY;
  }
  
  /** 
   * Changes the speed of the Sprite
   * @param speedX
   * @param speedY
   */
  public void setSpeed( float speedX, float speedY){
    setSpeedX(speedX);
    setSpeedY(speedY);
  }
  
  /**
   * Gets the speed of the Sprite in the X-direction 
   * @return float
   */
  public float getSpeedX(){
    return speedX;
  }
  
  /** 
   * Gets the speed of the Sprite in the Y-direction
   * @return float
   */
  public float getSpeedY(){
    return speedX;
  }
  
  /** 
   * Changes the acceleration of the Sprite in the X-direction
   * @param accelX
   */
  public void setAccelerationX(float accelX){
    this.accelX = accelX;
  }
  
  /** 
   * Changes the acceleration of the Sprite in the Y-direction
   * @param accelY
   */
  public void setAccelerationY(float accelY){
    this.accelY = accelY;
  }

  /** 
   * @return float      acceleration of the Sprite in the Y-direction
   */
  public float getAccelerationY(){
    return accelY;
  }

  /** 
   * @return float    acceleration of the Sprite in the X-direction
   */
  public float getAccelerationX(){
    return accelX;
  }

  /**
   * Starts gravity acting on a Sprite at default rate or previously defined rate
   */
  public void startGravity(){
    this.hasGravity = true;
    setAccelerationY(this.gravityStrength);
  }
  
  /** 
   * Starts gravity acting on sprite at a particular acceleration rate
   * @param gravityStrength   positive acceleration in Y-direction is downwards
   */
  public void startGravity(float gravityStrength){
    this.gravityStrength = gravityStrength;
    startGravity();
  }

  /**
   * Stops gravity acting on a Sprite
   */
  public void stopGravity(){
    this.hasGravity = false;
    setAccelerationY(0.0f);
    setSpeedY(0.0f);
  }
  
  /** 
   * Creates a jump with a specific jumpSpeed at a pre-existing gravityStrength
   * @param jumpSpeed
   */
  public void jump(float jumpSpeed){
    setSpeedY(-jumpSpeed);
    startGravity();
  }
  
  /** 
   * Creates a jump with a specific jumpSpeed and also sets the Sprite's gravity strength
   * @param jumpSpeed
   * @param gravityStrength
   */
  public void jump(float jumpSpeed, float gravityStrength){
    startGravity(gravityStrength);
    jump(jumpSpeed);
  }

  /**
   * Creates a jump with default jump speed
   */
  public void jump(){
    jump(defaultJumpSpeed);
  }

  /** 
   * Rotates Sprite image on the screen
   * @param degrees
   */
  public void rotate(float degrees){
    float rads = p.radians(degrees);
    p.translate(centerX,centerY);
    rotate(rads);
  }
  
  /** 
   * Changes if a Sprite is solid
   * @param isSolid
   */
  public void setSolid(boolean isSolid){
    this.isSolid = isSolid;
  }
  
  /** 
   * Checks if a Sprite is solid
   * @return boolean
   */
  public boolean isSolid(){
    return isSolid;
  }


  //------------------ SPRITE COLLISSIONS & OVERLAPS --------------------//
  
  /** 
   * Checks if the rectangle of this Sprite overlaps in any way with the rectangle of another
   * @param otherSprite   second Sprite to compare this Sprite with
   * @return boolean      true if other Sprit overlaps with this, false otherwise
   */
  public boolean isOverlapping(Sprite otherSprite){
    if(this.getBottom() >= otherSprite.getTop()   //bottom isn'too low
      && this.getTop() <= otherSprite.getBottom() //top isn't too high
      && this.getLeft() <= otherSprite.getRight() // left isn't too far right
      && this.getRight() >= otherSprite.getLeft()       // right isn't too far left
    ){
      return true;
    } else {
      return false;
    }
  }
  
  /** 
   * Checks if sprite has landed on top of another sprite with a specified cushion on top
   * @author Marcus Bistline 2025
   * @param otherSprite   second Sprite to compare this Sprite with
   * @param cushion       number of pixels above the top before considering the top touched
   * @return boolean      true if this Sprite is touching the top of the other Sprite, false otherwise
   */
  public boolean isTouchingTop(Sprite otherSprite, float cushion){
    if(this.getBottom() > otherSprite.getTop()-cushion //is player low enough to touch platform
      && this.getBottom() < otherSprite.getBottom() // is player still above the platform
      && this.getLeft() < otherSprite.getRight() // is player inside the right edge of platform
      && this.getRight() > otherSprite.getLeft()){ // is player inside the left edge of platform
        return true;
    } else {
      return  false;
    }
  }
  
  /** 
   * Checks if sprite has landed on top of another sprite
   * @param otherSprite   second Sprite to compare this Sprite with
   * @return boolean      true if this Sprite is touching the top of the other Sprite, false otherwise
   */
  public boolean isTouchingTop(Sprite otherSprite){
    return isTouchingTop(otherSprite, 2);
  }
  
  /** 
   * Checks if sprite has bumped into the bottom of another sprite with a specified cushion on bottom
   * @author Marcus Bistline 2025
   * @param otherSprite   second Sprite to compare this Sprite with
   * @param cushion       number of pixels above the top before considering the top touched
   * @return boolean      true if this Sprite is touching the bottom of the other Sprite, false otherwise
   */
  public boolean isTouchingBottom(Sprite otherSprite, float cushion){
    if(this.getTop() > otherSprite.getTop() //is player below the platform
      && this.getTop() < otherSprite.getBottom()+cushion // is player's head hitting very close to the platform bottom
      && this.getLeft() < otherSprite.getRight() // is player inside the right edge of platform
      && this.getRight() > otherSprite.getLeft()){ // is player inside the left edge of platform
        return true;
    } else {
      return  false;
    }
  }

  /** 
   * Checks if sprite has bumped into the bottom of another sprite
   * @param otherSprite   second Sprite to compare this Sprite with
   * @return boolean      true if this Sprite is touching the bottom of the other Sprite, false otherwise
   */
  public boolean isTouchingBottom(Sprite otherSprite){
    return isTouchingBottom(otherSprite, 2);
  }

   /** 
   * Checks if sprite has bumped into the right side of another sprite with a specified cushion on right
   * @author Marcus Bistline 2025
   * @param otherSprite   second Sprite to compare this Sprite with
   * @param cushion       extra pixels to the right to consider the right touched
   * @return boolean      true if this Sprite is touching the right side of the other Sprite, false otherwise
   */
  public boolean isTouchingRight(Sprite otherSprite, float cushion){
    if(this.getLeft() > otherSprite.getRight()-cushion // is player's left to the right of the right edge of platform
      && this.getLeft() < otherSprite.getRight()+cushion // is player's left edge close to the right edge of platform
      && this.getTop() < otherSprite.getBottom() //is player above platform bottom
      && this.getBottom() > otherSprite.getTop() // is player below platform top
    ){
        return true;
    } else {
      return  false;
    }
  }

  /** 
   * Checks if sprite has landed on top of another sprite
   * @param otherSprite   second Sprite to compare this Sprite with
   * @return boolean      true if this Sprite is touching the right side of the other Sprite, false otherwise
   */
  public boolean isTouchingRight(Sprite otherSprite){
    return isTouchingRight(otherSprite, 2);
  }

  /** 
   * Checks if sprite has bumped into the left side of another sprite with a specified cushion on left
   * @author Marcus Bistline 2025
   * @param otherSprite   second Sprite to compare this Sprite with
   * @param cushion       extra pixels to the right to consider the right touched
   * @return boolean      true if this Sprite is touching the left side of the other Sprite, false otherwise
   */
  public boolean isTouchingLeft(Sprite otherSprite, float cushion){
    if(this.getRight() < otherSprite.getLeft()+cushion // is player's right to the left of the left edge of platform
      && this.getRight() > otherSprite.getLeft()-cushion // is player's right edge close to the left edge of platform
      && this.getTop() < otherSprite.getBottom() //is player above platform bottom
      && this.getBottom() > otherSprite.getTop() // is player below platform top
    ){
      return true;
    } else {
      return  false;
    }
  }

  /** 
   * Checks if sprite has bumped into the left side of another sprite
   * @param otherSprite   second Sprite to compare this Sprite with
   * @return boolean      true if this Sprite is touching the left side of the other Sprite, false otherwise
   */
  public boolean isTouchingLeft(Sprite otherSprite){
    return isTouchingLeft(otherSprite, 2);
  }
	
  /** 
   * Checks if collision occurs at the bottom of this Sprite
   * @param otherSprite     second Sprite to compare this Sprite with
   * @param cushion         how close do feet need to be to ground
   * @return boolean        return true if feet are close to the ground
   */
  public boolean doesCollideBottom(Sprite otherSprite, float cushion){
    float diff = Math.abs(getBottom() - otherSprite.getTop()); //gap between feet and grass
		if(diff < cushion){
			return true;
		}
		return false;
	}

  
  //------------------ SPRITE COORDINATES ACCESSOR & MUTATOR METHODS --------------------//
  
  /** 
   * @return float
   */
  public float getW(){
    return w;
  }
  
  /** 
   * @return float
   */
  public float getH(){
    return h;
  }
  
  /** 
   * @return float
   */
  public float getCenterX(){
    return centerX;
  }
  
  /** 
   * @return float
   */
  public float getCenterY(){
    return centerY;
  }
  
  /** 
   * @return float
   */
  public float getX(){
    return getCenterX();
  }
  
  /** 
   * @return float
   */
  public float getY(){
    return getCenterY();
  }

  
  /** 
   * @param w
   */
  public void setW(float w){
    this.w = w;
  }
  
  /** 
   * @param h
   */
  public void setH(float h){
    this.h=h;
  }
  
  /** 
   * @param centerX
   */
  public void setCenterX(float centerX){
    this.centerX = centerX;
  }
  
  /** 
   * @param centerY
   */
  public void setCenterY(float centerY){
    this.centerY=centerY;
  }

  /*------------------ SPRITE BOUNDARY METHODS  --------------------
  * -- Used from Long Bao Nguyen
  *  -- https://longbaonguyen.github.io/courses/platformer/platformer.html
  */

  /** 
   * @param left
   */
  void setLeft(float left){
    centerX = left + w/2;
  }
  
  /** 
   * @return float
   */
  float getLeft(){
    return centerX - w/2;
  }
  
  /** 
   * @param right
   */
  void setRight(float right){
    centerX = right - w/2;
  }
  
  /** 
   * @return float
   */
  float getRight(){
    return centerX + w/2;
  }
  
  /** 
   * @param top
   */
  void setTop(float top){
    centerY = top + h/2;
  }
  
  /** 
   * @return float
   */
  float getTop(){
    return centerY - h/2;
  }
  
  /** 
   * @param bottom
   */
  void setBottom(float bottom){
    centerY = bottom - h/2;
  }
  
  /** 
   * @return float
   */
  float getBottom(){
    return centerY + h/2;
  }

  
  //------------------ SPRITE IMAGE & ANIMATION METHODS --------------------//

  /**
   * Accesses the Sprite's image
   * @return PImage
   */
  public PImage getImage(){
    return this.spriteImg;
  }
  
  /** 
   * Changes the Sprite's image
   * @param img
   */
  public void setImage(PImage img){
    this.spriteImg = img;
  }
  
  /** 
   * Checks if Sprite object is animated
   * @return boolean
   */
  public boolean getIsAnimated(){
    return isAnimated;
  }
  
  /** 
   * Changes if Sprite object is animated
   * @param isAnimated
   */
  public void setIsAnimated(boolean isAnimated){
    this.isAnimated = isAnimated;
  }
  
  /** 
   * Gets the image path of the Sprite
   * @return String
   */
  public String getImagePath(){
    return this.spriteImgFile;
  }

  /** 
   * Compares 2 sprites by a name, will check the image file name if no name specified
   * @return String
   */
  public String getName(){
    if(name == null){
      return getImagePath();
    } else {
      return name;
    }
  }
  
  /** 
   * Sets the Sprites name to be used for comparisons
   * @param name
   */
  public void setName(String name){
    this.name = name;
  }

  /** 
   * Copies a Sprite to a specific location
   * @param x
   * @param y
   * @return Sprite
   */
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

  
  /** 
   * Copies a new Sprite to same location as this Sprite
   * @return Sprite
   */
  public Sprite copy(){
    return copyTo(this.centerX, this.centerY);
  }

  /** 
   * Scales the Sprite's image size to a new width & height
   * @param width           how many pixels wide the Sprite will change to
   * @param height          how many pixels high the Sprite will change to
   */
  public void resize(float width, float height){
    this.w = width;
    this.h = height;
    System.out.println("Resizing Sprite to " + width + "x" +  height);
    spriteImg.resize( (int) width, (int) height);
  }

  /** 
   * Scales Sprite to be bigger (with values > 1.0f)
   * and smaller (with values < 1.0f)
   * @param scale       number to multiple height & width of image by
   */
  public void scale(float scale){
    w = spriteImg.width * scale;
    h = spriteImg.height * scale;
  }
  
  /** 
   * Checks if this Sprite is same as otherSprite (based on name or image)
   * @param otherSprite   second Sprite to compare this Sprite with
   * @return boolean      true if both Sprites have the same name or image, false otherwise
   */
  public boolean equals(Sprite otherSprite){
    if(this.spriteImgFile != null && otherSprite != null && this.getName().equals(otherSprite.getName())){
      return true;
    }
    return false;
  }

  /**
   * Automatically moves the Sprite based on its velocity
   */
  public void update(){
    move(speedX, speedY);
  }

  /** 
   * Method called to update a Sprite's position based on its set speed and acceleration
   * @param deltaTime
   */
  public void update(float deltaTime){

    // determine number of seconds since last run of update
    float sec = deltaTime/1000;

    // change speedY if accelY is non-zero OR gravity is on
    if(hasGravity || Math.signum(accelY) == 0){
      speedY += accelY*sec;
    }

    // change speedX if accelX is non-zero
    if(Math.signum(accelX) == 0){
      speedX += accelX*sec;
    }

    // update position based on speed
    update();
  }

  /** 
   * @return String       includes Sprite's filename, centerX, centerY, left edge, top edge, speedX, speedY, w, h, isAnimated
   */
  public String toString(){
    return "Sprite: fn: "+ spriteImgFile + "\tx:" + centerX + "\ty:" + centerY + "\tL:" + getLeft() + "\tT:" + getTop() + "\tVx:" + speedX + "\tVy:" + speedY + "\tw:" + w + "\th:" + h + "\t" + isAnimated;
  }

} // end of Sprite class
