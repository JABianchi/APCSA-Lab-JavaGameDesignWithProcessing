/* Button Class - Used to add a button into a Game
 * Author: Joel Bianchi
 * Last Edit: 5/20/25
 * Font edits
 */

import processing.core.PApplet;
import processing.core.PFont;
import java.awt.Font;
import java.io.InputStream;


public class Button {

    PApplet p;

    //------------------ BUTTON FIELDS --------------------//
    private String shape;
    private float shapeX, shapeY;     //coordinates of CENTER of button shape
    private float shapeW, shapeH;     //size of shape in pixels
    private float shapeRounding;
    private String text;
    private int textColor;
    private String fontStyle;           //file path to system or saved font file
    private PFont font;                 //font object
    private float fontFactor;
    private float fontSize;
    private int baseColor;
    private int hoverColor;
    private int clickColor;
    private int currentColor;
    private int outlineColor;
    private boolean isVisible;
    private boolean doesHoverHighlight;
    private boolean doesClickHighlight;


    //------------------ BUTTON CONSTRUCTORS --------------------//

    //Button Constructor
    public Button(PApplet p, String shape, float x, float y, float w, float h, String text) {

        this.p = p;
        //super(null,1.0, x, y,false);  //if Button is Sprite
        this.shape = shape.toUpperCase();
        this.shapeW = w;
        this.shapeH = h;
        this.shapeX = x + (shapeW/2);
        this.shapeY = y + (shapeH/2);
        this.shapeRounding = 24;
        this.text = text;
        this.textColor = PColor.BLACK;
        this.fontFactor = 0.9f;
        this.fontSize = shapeH/2 * fontFactor;
        this.baseColor = PColor.YELLOW;
        this.doesHoverHighlight = true;
        this.hoverColor = PColor.BLUE;
        this.doesClickHighlight = true;
        this.clickColor = PColor.RED;
        this.currentColor = baseColor;
        this.outlineColor = PColor.BLACK;
        this.isVisible = true;
        this.fontStyle = "Georgia"; //"Arial", "Courier New", "Georgia"
        this.font = p.createFont(fontStyle, fontSize);
    }


    //------------------ BUTTON METHODS --------------------//

    //Button method to be called each cycle -- ie. inside draw() or updateScreen() 
    public void show() {
    
        //Sets outline stroke around button (3 pixels, BLACK)
        p.strokeWeight(2);
        p.stroke(outlineColor);

        //Sets color of button based on Mouse hover
        if (doesClickHighlight && isClicked()) {
            currentColor = clickColor;
        } else if (doesHoverHighlight && isMouseOverButton()){
            currentColor = hoverColor;
        } else {
            currentColor = baseColor;
        }

        //Set color inside Button
        p.fill(currentColor);

        //Only show the button if visible
        if(isVisible){

            //Draws particular Button Shape
            if(shape.equals("CIRCLE")){
                p.ellipseMode(p.CENTER);
                p.ellipse(shapeX, shapeY, shapeW, shapeH);
            //     System.out.println("circle shape");
            } else if(shape.equals("RECT")){
                p.rectMode(p.CENTER);
                p.rect(shapeX, shapeY, shapeW, shapeH, shapeRounding, shapeRounding, shapeRounding, shapeRounding);
                // System.out.println("RECT shape");
            } else {
                System.out.println("Wrong shape String.  Type \"RECT\" or \"CIRCLE\"");
                return;
            }

            //Set Text inside Button
            p.textAlign(p.CENTER, p.CENTER);
            p.fill(textColor);
            float fontSize = shapeH/2 * fontFactor;
            p.textSize(fontSize);
            p.textFont(font);
            p.text(text, shapeX, shapeY);

        }
    }


    //------------------ BUTTON HOVERING METHODS --------------------//

    public boolean isClicked(){
        if (isMouseOverButton() && p.mousePressed) {
            System.out.println("Button Clicked");
            return true;
        } else{
            return false;
        }
    }
    
    public boolean isMouseOverButton(){ //move to Sprite class eventually
        if(shape.equals("RECT")){
            return isOverRect();
        } else if(shape.equals("CIRCLE")){
            return isOverCircle();
        } else {
            return false;
        }
    }
    
    private boolean isOverRect(){
        if(p.mouseX >= shapeX-shapeW/2 && p.mouseX <= shapeX+shapeW/2
            && p.mouseY >= shapeY-shapeH/2 && p.mouseY <= shapeY+shapeH/2){
            return true;
        } else {
            return false;
        }
    }

    private boolean isOverCircle(){
        float diameter = shapeH;
        float disX = shapeX - p.mouseX;
        float disY = shapeY - p.mouseY;
        if (Math.sqrt(Math.pow(disX,2) + Math.pow(disY,2)) < diameter/2 ) {
            return true;
        } else {
            return false;
        }
    }

//------------------ BUTTON MUTATOR METHODS --------------------//

    public void setText(String text){
        this.text = text;
    }
    public void setTextColor(int color){
        this.textColor = color;
    }
    public void setFontStyle(String fontStyleFile){
        this.fontStyle = fontStyleFile;
        this.font = p.createFont(fontStyleFile, fontSize);
        System.out.println("font is " + font );
    }

    public void setFontFactor(float ff){
        this.fontFactor = ff;
    }
    public void setButtonColor(int color){
        this.baseColor = color;
    }
    public void setOutlineColor(int color){
        this.outlineColor = color;
    }
    public void setHoverHighlight(boolean highlight){
        this.doesHoverHighlight = highlight;
    }

    //Method to pass in a Processing color or null
    //ie. Color.getColor(0,0,255) OR Color.BLUE
    public void setHoverColor(Integer color){
        if(color != null){
            setHoverHighlight(true);
            this.hoverColor = color;
        } else {
            setHoverHighlight(false);
        }
    }

    public void setClickHighlight(boolean b){
        this.doesClickHighlight = b;
    }

    //Method to pass in a Processing color or null
    //ie. color(0,0,255) for blue
    public void setClickColor(Integer color){
        if(color != null){
            setClickHighlight(true);
            this.clickColor = color;
        } else {
            setClickHighlight(false);
        }
    }
    public void setVisible(boolean visible){
        this.isVisible = visible;
    }


    public void setShapeRounding(float shapeRounding){
        this.shapeRounding =  shapeRounding;
    }

    public String toString(){
        return "Button shape " + this.shape + " with text \"" + this.text + "\" @loc " + this.shapeX +","+this.shapeY + " w:"+this.shapeW+" h:"+this.shapeH;
    }



} //end Button class
