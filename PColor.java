/* PColor class - provides static variables and methods for Processing colors
 * Author: Joel Bianchi
 * Last Edit: 5/20/25
 * Added equals method & NULL
 */

import processing.core.PApplet;

public class PColor {

    //------------------ COLOR CONSTANTS--------------------//
    public static int BLACK = get(0,0,0); // #000000;
    public static int WHITE = get(255,255,255); // #FFFFFF;
    public static int GRAY = get(127,127,127); // #7F7F7F;
    public static int RED = get(255,0,0); // #FF0000;
    public static int GREEN = get(0,255,0); // #00FF00;
    public static int BLUE = get(0,0,255); // #0000FF;
    public static int CYAN = get(0,255,255); // #00FFFF;
    public static int MAGENTA = get(255,0,255); // #FF00FF;
    public static int YELLOW = get(255,255,0); // #FFFF00;
    public static int NULL = -1;


    // Returns a processing color int when provided red, green and blue values (each between 0-255)
    public static int get(int red, int green, int blue){
        return (int)( -(long)Math.pow(16,6) + red*Math.pow(16,4) + green*Math.pow(16,2) + blue );
    }

    // Returns a processing color int when provided a hex color code that starts with a hashtag (ie. #00FF77 )
    public static int get(String hexString){
        int hashIndex = hexString.indexOf("#");

        int red = Integer.parseInt(hexString.substring(hashIndex+1, hashIndex+3), 16);
        int green = Integer.parseInt(hexString.substring(hashIndex+3, hashIndex+5), 16);
        int blue = Integer.parseInt(hexString.substring(hashIndex+5, hashIndex+7), 16);
        return get(red, green, blue);        
    }

    // Returns individual RGB values (or use p.red() )
    public static int getRed(PApplet p, int color){
        return (int)p.red(color);
    }
    public static int getGreen(PApplet p, int color){
        return (int)p.green(color);
    }
    public static int getBlue(PApplet p, int color){
        return (int)p.blue(color);
    }



    // Prints out a Processing color int with RGB and Hex notations
    public static void printPColor(PApplet p, int color){
        System.out.println("Pixel color --> " + getRGBString(p,color) + "\t" + getHexString(color));
    }

    // Provides a String for a Processing color int with separate red, green, and blue values
    public static String getRGBString(PApplet p, int color){
        return "(R:"+(int)p.red(color)+",G:"+(int)p.green(color)+",B:"+(int)p.blue(color)+")";
    }

    // Provides a String for a Processing color int with hexadecimal values as used in Processing and commonly on the web
    public static String getHexString(int color){
        int regularColor = (int)((long)Math.pow(16,6) + color);
        return "#"+String.format("0x%06X", regularColor).substring(2);
    }

    // Compares the RGB values of 2 colors
    public boolean equals(PApplet p, int firstColor, int secondColor) {
        return getRed(p, firstColor) == getRed(p, secondColor) 
            && getGreen(p, firstColor) == getGreen(p, secondColor)
            && getBlue(p, firstColor) == getBlue(p, secondColor);
    }


}
