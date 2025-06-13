import processing.core.PApplet;
import processing.core.PImage;

/**
 * Screen class - a high level class that handles background screens & millisecond timing
 * Has a World Subclass
 * @author Joel A Bianchi
 * @author Carey Jiang
 * @version 6/12/25
 * All Screens take in files, create & resize background PImages
 */
public class Screen{

    PApplet p;

    //------------------ SCREEN FIELDS --------------------//
    private String screenName;
    private String bgFile;
    private PImage bgImg;
    private boolean isMoveable;
    private Sprite mbg;
    private long startTime;
    private long lastTime = 0;

    //------------------ SCREEN CONSTRUCTORS --------------------//

    /**
     * Screen Constructor #1: Stationary background image
     * @param p             Processing applet
     * @param screenName    String to track Screens
     * @param bgFile        file location for a stationary background image
     */
    public Screen(PApplet p, String screenName, String bgFile) {
        this.p = p;
        this.isMoveable = false;
        this.setName(screenName);
        this.bgFile = bgFile;

        if(bgFile != null) {
            bgImg = p.loadImage(bgFile);
            bgImg.resize(p.width, p.height);
            this.setBgImg(bgImg);
            System.out.println("bg of " + screenName + " Screen: " + Util.toStringPImage(bgImg));
        }
        startTime = getTotalTime(); //?

    }

    /**
     * Screen Constructor #2: For background images that move - Takes String (Coded as a Sprite, not a Processing background PImage)
     * @param p             Processing applet
     * @param screenName    String to track Screens
     * @param movingBgFile  filename for the background image to move
     * @param scale         float that multiplies the size of the image to display
     * @param x             sets the initial left edge of the background
     * @param y             sets the intial top edge of the background
     */
    public Screen(PApplet p, String screenName, String movingBgFile, float scale, float x, float y) {
        this.p = p;
        this.isMoveable = true;
        this.setName(screenName);
        mbg = new Sprite(p, movingBgFile, scale, x, y);
        startTime = getTotalTime();
    }

    // @Deprecated
    // /**
    //  * Screen Constructor #3: For background images that move - Takes PImage (Coded as a Sprite, not a Processing background PImage)
    //  * @param p             Processing applet
    //  * @param screenName    String to track Screens
    //  * @param movingBg      PImage for a moving background
    //  * @param scale         float that multiplies the size of the image to display
    //  * @param x             sets the initial left edge of the background
    //  * @param y             sets the intial top edge of the background
    //  */
    // public Screen(PApplet p, String screenName, PImage movingBg, float scale, float x, float y) {
    //     this.p = p;
    //     this.isMoveable = true;
    //     this.setName(screenName);
    //     mbg = new Sprite(p, movingBg, scale, x, y);
    //     System.out.println("Screen constructed with " + mbg.getImagePath() + "\t" + mbg);
    //     startTime = getTotalTime();
    // }


    //------------------ ACCESSORS & MUTATORS --------------------//
    
    /** 
     * Sets the name of the Screen to be used for debugging
     * @param screenName    String to track Screens
     */
    public void setName(String screenName){
        this.screenName = screenName;
    }

    
    /**
     * Gets the name of the Screen to be used for debugging 
     * @return String      Can be used to track the Screen
     */
    public String getName(){
        return screenName;
    }

    /** 
     * Sets the background image for NON-MOVEABLE backgrounds
     * @param bgImg            stationary background image
     */
    public void setBgImg(PImage bgImg){
        if(!isMoveable){
            this.bgImg = bgImg;
            //p.background(bg);
        }
    }

    /** 
     * Gets the background as a PImage
     * @return PImage       moving OR non-moving background image
     */
    public PImage getBgImage(){
        
        if(isMoveable){
            return mbg.getImage();
        }
        return bgImg;
    }
    
    /** 
     * Checks if the Screen's background is moveable
     * @return boolean      <code>true</code> if background is moveable, <code>false</code> if stationary
     */
    public boolean getIsMoveable(){
        return isMoveable;
    }


    //------------------ SCREEN MOVING METHODS --------------------//

    /** 
     * Moves the background image in the X & Y directions
     * @param speedX        positive moves right, negative moves left
     * @param speedY        positive moves down, negative moves up
     */
    public void moveBgXY(float speedX, float speedY){
        if(isMoveable){
            mbg.move(speedX, speedY);
        } else {
            System.out.println("Can't move this background");
        }
    }
    
    /** 
     * Moves the left edge of the background to a specified pixel (ie. -50.0f)
     * @param leftX         pixel of left edge of moving background
     */
    public void setLeftX(float leftX) {
        mbg.setLeft(leftX);
    }
    
    /** 
     * Returns the pixel value for the left edge of the background image
     * @return float        pixel of left edge of moving background
     */
    public float getLeftX() {
        return mbg.getLeft();
    }
    
    /** 
     * Moves the top edge of the background to a specified pixel (ie. -50.0f)
     * @param topY          pixel of top edge of moving background
     */
    public void setTopY(float topY) {
        mbg.setTop(topY);
    }
    
    /** 
     * Returns the pixel value for the top edge of the background image
     * @return float        pixel of top edge of moving background
     */
    public float getTopY() {
        return mbg.getTop();
    }
    /**
     * Updates any movement of the background to be shown
     */
    public void showBg(){
        if(isMoveable){
            mbg.show();
        } else if(bgImg != null){
            p.background(bgImg);
        }
    }

    /**
     * Updates all sprites and images on the Screen
     * Does nothing unless Overridden by World, Grid, and HexGrid
     */
    public void show(){

    }

    
    /** 
     * @author CAREY JIANG 2024
     * @return float        distance to right edge for a moveable background
     */
    public float distToRightEdge(){
        return (mbg.getW() - p.width) + (mbg.getLeft());
    }


    //------------------ SCREEN TIME METHODS --------------------//

    /** 
     * @return long         total number of milliseconds the game has been running
     */
    public long getTotalTime(){
        return p.millis();
    }
    
    /** 
     * @return long         milliseconds the Screen has been running
     */
    public long getScreenTime(){
        return p.millis() - startTime;
    }
    
    /** 
     * @param lastCheck     milliseconds since last check
     * @return long         milliseconds from a previous time
     */
    public long getTimeSince(long lastCheck){
        return getScreenTime() - lastCheck;
    }
    
    /** 
     * @return float        seconds the Screen has been running
     */
    public float getScreenTimeSeconds(){
        return getScreenTime() / 1000.0f;
    }
    public void resetTime(){
        startTime = getTotalTime();
    }
    
    /** 
     * Resets the timer for the screen.  Can be used when Screen first becomes visible
     * @param milliseconds      how long to pause the app
     */
    // Pauses ALL screen methods!
    public void pause(final int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    
    /** 
     * @return String       includes screenName, leftX, and topY
     */
    public String toString(){
        return "Screen: " + screenName + " at LeftX:" + getLeftX() + " ,TopY:" + getTopY() ;
    }

} // end of Screen class
