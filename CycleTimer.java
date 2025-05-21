/* CycleTimer Class - Used for create repeating timers
 * Author: Joel Bianchi
 * Last Edit: 5/20/25
 */

import processing.core.PApplet;

public class CycleTimer {

    PApplet p;

    //------------------ CYCLETIMER FIELDS --------------------//
    private long startTime;
    private long lastReset = 0;
    private long frequencyMs;

    // CycleTimer Constructor
    public CycleTimer(PApplet p, long frequencyMs) {
        this.p = p;
        startTime = getGameTime();
        this.frequencyMs = frequencyMs;
    }

    // Returns the total number of milliseconds the game has been running
    public long getGameTime(){
        return p.millis();
    }

    public long getTimerTime(){
        return getGameTime() - startTime;
    }

    // Returns the milliseconds from the last reset
    public long getCycleTime(){
        return getGameTime() - lastReset;
    }

    public boolean isDone(){
        // this.frequencyMs = frequencyMs;
        if(getCycleTime() > frequencyMs){
            resetCycleTime();
            return true;
        }
        return false;
    }

    // Returns the seconds from ms
    public float getSeconds(long msTime){
        return msTime / 1000.0f;
    }

    // Resets the timer
    public void resetCycleTime(){
        lastReset = getGameTime();
    }
    
    // Pauses ALL screen methods!
    public void pause(final int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    public String toString(){
        return "CycleTimer: "
            + "\tCurrentTime: " + getCycleTime()
            + "\tfreqMs" + frequencyMs;
    }


}

