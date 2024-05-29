/* Screen class - a high level class that handles background screens & millisecond timing
 * Has a World Subclass
 * Author: Joel Bianchi & Carey Jiang
 * Last Edit: 5/28/24
 * Ability to reset the timer for a particular screen
 * Added moveable background
 */

public class Screen {

    //------------------ SCREEN FIELDS --------------------//
    private String screenName;
    private PImage bg;
    private boolean isMoveable;
    private Sprite mbg;
    
    private long startTime;
    private long lastTime = 0;

    //------------------ SCREEN CONSTRUCTORS --------------------//

    //Screen Constructor #1: For background images that move (Coded as a Sprite, not a Processing background PImage)
    public Screen(String screenName, String movingBgFile, float scale, float x, float y) {
        this.isMoveable = true;
        this.setName(screenName);
        mbg = new Sprite(movingBgFile, scale, x, y);
        
        // this.x = x;
        // this.y = y;
        startTime = getTotalTime();
    }

    //Screen Constructor #2: Stationary background image
    public Screen(String screenName, PImage bg) {
        this.isMoveable = false;
        this.setName(screenName);
        if(bg != null) {
            this.setBg(bg);
        }
    }

    //------------------ ACCESSORS & MUTATORS --------------------//
    public void setName(String screenName){
        this.screenName = screenName;
    }
    public String getName(){
        return screenName;
    }

    public void setBg(PImage bg){
        if(!isMoveable){
            this.bg = bg;
            //background(bg);
        }
    }
    public PImage getBg(){
        return bg;
    }


    //------------------ SCREEN MOVING METHODS --------------------//

    //move the background image in the X direction
    public void moveBgXY(float speedX, float speedY){
        if(isMoveable){
            mbg.move(speedX, speedY);
        } else {
            System.out.println("Can't move this background");
        }
    }

    public void setLeftX(float leftX) {
        mbg.setLeft(leftX);
    }
    public float getLeftX() {
        return mbg.getLeft();
    }

    public void setTopY(float topY) {
        mbg.setTop(topY);
    }
    public float getTopY() {
        return mbg.getTop();
    }

    //updates any movement of the background
    public void show(){
        if(isMoveable){
            mbg.show();
            //System.out.println("Showing mbg");
        }
    }



    //------------------ SCREEN TIME METHODS --------------------//

    public long getTotalTime(){
        return millis();  //milliseconds world
    }
    public long getScreenTime(){
        return millis() - startTime;  //milliseconds world
    }
    public long getTimeSince(long lastCheck){
        return getScreenTime() - lastCheck;
    }
    public float getScreenTimeSeconds(){
        return getScreenTime() / 1000.0f;
    }
    
    //pauses ALL screen methods!
    public void pause(final int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    //resets the timer for the screen
    public void resetTime(){
        startTime = getTotalTime();
    }



    public String toString(){
        return "Screen: " + screenName + " at LeftX:" + getLeftX() + " ,TopY:" + getTopY() ;
    }


}
