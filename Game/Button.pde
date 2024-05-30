/* Button Class - Used to add a button into a Game
 * Author: Joel Bianchi
 * Last Edit: 5/29/2024
 * Fixed centering of button hover
 */


public class Button {

    //------------------ BUTTON FIELDS --------------------//
    private String caption;
    private String shape;
    private float shapeX, shapeY;     //coordinates of CENTER of button shape
    private float shapeW, shapeH;     //size of shape in pixels
    private color baseColor;
    private color highlightColor;
    private color clickColor;
    private color currentColor;
    private boolean visible;


    //------------------ BUTTON CONSTRUCTORS --------------------//

    //Button Constructor #1
    public Button(String shape, float x, float y, float w, float h, String txt) {
        //super(null,1.0, x, y,false);
        this.shape = shape;
        this.shapeW = w;
        this.shapeH = h;
        this.shapeX = x + (shapeW/2);
        this.shapeY = y + (shapeH/2);
        
        this.caption = txt;
        this.baseColor = color(255,255, 0);   //yellow
        this.highlightColor = color(0,0,255); //blue
        this.clickColor = color(255,0,0); //red
        this.currentColor = baseColor;
        this.visible = true;
    }


    //------------------ BUTTON METHODS --------------------//

    //Button method to be called each cycle -- ie. inside draw() or updateScreen() 
    void show() {
        
        //Sets outline stroke around button (3 pixels, BLACK)
        strokeWeight(3);
        stroke(0);

        //Sets color of button based on Mouse hover
        if (isClicked()) {
            currentColor = clickColor;
        } else if (isMouseOverButton()){
            currentColor = highlightColor;
        } else {
            currentColor = baseColor;
        }

        //Set color inside Button
        fill(currentColor);

        //Draws particular Button Shape
        if(shape.equals("circle")){
            ellipseMode(CENTER);
            ellipse(shapeX, shapeY, shapeW, shapeH);
        //     System.out.println("circle shape");
        } else if(shape.equals("rect")){
            rectMode(CENTER);
            rect(shapeX, shapeY, shapeW, shapeH);
            // System.out.println("rect shape");
        } else {
            System.out.println("Wrong shape String.  Type \"rect\" or \"circle\"");
            return;
        }

        //Set Text inside Button
        fill(0); //set font color to black
        float fontSize = shapeH/2 * 0.33;
        textSize(fontSize);
        float tw = textWidth(caption);
        float tx = shapeX - (tw/2);
        float ty = shapeY + (fontSize / 2);
        text(caption, tx, ty);
    }


    //------------------ BUTTON HOVERING METHODS --------------------//

    public boolean isClicked(){
        if (isMouseOverButton() && mousePressed) {
            System.out.println("Button Clicked");
            return true;
        } else{
            return false;
        }
    }
    
    public boolean isMouseOverButton(){ //move to Sprite class eventually
        if(shape.equals("rect")){
            return isOverRect();
        } else if(shape.equals("circle")){
            return isOverCircle();
        } else {
            return false;
        }
    }
    
    private boolean isOverRect(){
        if(mouseX >= shapeX-shapeW/2 && mouseX <= shapeX+shapeW/2
            && mouseY >= shapeY-shapeH/2 && mouseY <= shapeY+shapeH/2){
            return true;
        } else {
            return false;
        }
    }

    private boolean isOverCircle(){
        float diameter = shapeH;
        float disX = shapeX - mouseX;
        float disY = shapeY - mouseY;
        if (sqrt(sq(disX) + sq(disY)) < diameter/2 ) {
            return true;
        } else {
            return false;
        }
    }

//------------------ BUTTON MUTATOR METHODS --------------------//

    public void setButtonColor(color c){
        this.baseColor = c;
    }

    public void setHighlightColor(color c){
        this.highlightColor = c;
    }
    
    public void setClickColor(color c){
        this.clickColor = c;
    }


} //end Button class