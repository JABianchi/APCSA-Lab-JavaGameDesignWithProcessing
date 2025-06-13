import processing.core.PApplet;

/**
 * CycleTimer Class - Used for create repeating timers
 * @author Joel A Bianchi
 * @version 6/12/25
 * Renamed getCycleTime() to getTime()
 * Added getCountdown() method
 * Added javadocs
 */
public class CycleTimer {

    PApplet p;


    //------------------ CYCLETIMER FIELDS --------------------//
    private long startTime;
    private long lastReset = 0;
    private long frequencyMs;


    //------------------ CYCLETIMER CONSTRUCTOR --------------------//

    /**
     * CycleTimer Constructor
     * @param p             Processing applet
     * @param frequencyMs   milliseconds for each cycle
     */ 
    public CycleTimer(PApplet p, long frequencyMs) {
        this.p = p;
        startTime = getGameTime();
        this.frequencyMs = frequencyMs;
    }


    //------------------ ACCESSORS & MUTATORS --------------------//
    
    /** 
     * Gets the total number of milliseconds the game has been running
     * @return long     milliseconds the game has been running
     */
    public long getGameTime(){
        return p.millis();
    }

    /** 
     * Gets the time (in ms) since the CycleTimer object was constructed
     * @return long     milliseconds object has been active
     */
    public long getTimerTime(){
        return getGameTime() - startTime;
    }

    /** 
     * Gets the milliseconds since the cycle's last reset
     * @return long     milliseconds into the current cycle
     */
    public long getTime(){
        return getGameTime() - lastReset;
    }

    /** 
     * Used for a timer counting down from 
     * @return long     milliseconds until cycle ends
     */
    public long getCountdown(){
        return frequencyMs - getTime();
    }

    /** 
     * Checks if cycle has completed
     * @return boolean  <code>true</code> when cycle is complete, <code>false</code> otherwise
     */
    public boolean isDone(){
        if(getTime() > frequencyMs){
            resetCycleTime();
            return true;
        }
        return false;
    }

    /** 
     * Helper method to translate milliseconds to seconds
     * @param msTime    time in milliseconds
     * @return float    time in seconds
     */
    public float getSeconds(long msTime){
        return msTime / 1000.0f;
    }

    /**
     * Resets the timer 
     */
    public void resetCycleTime(){
        lastReset = getGameTime();
    }
    
    /** 
     * Pauses ALL screen methods!
     * @param milliseconds
     */
    public void pause(final int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    /** 
     * @return String       includes currentTime and cycle's frequency
     */
    public String toString(){
        return "CycleTimer: "
            + "\tCurrentTime: " + getTime()
            + "\tfreqMs" + frequencyMs;
    }

} // end of CycleTimer class
