/* Platform class
 * Can be used with the following tutorials:
 * https://longbaonguyen.github.io/courses/platformer/platformer.html
 * Authors: Joel A. Bianchi, Nathan Santos 2022, Clive Sherwood 2022, Marcus Bistline 2025
 * Last Edit: 5/20/25
 * Now inherits from Sprite class
 */


import processing.core.PApplet;
import processing.core.PImage;

public class Platform extends Sprite {

	private int color = PColor.BLACK;
	private int oColor = PColor.WHITE;

	// Platform Constructor #1: Platform is PImage defined by it's center-x and top-Y positions
	public Platform(PApplet p, PImage img, float posXCenter, float posYTop, float platWidth, float platHeight) {
		
		// pass along the center-x and center-y to Sprite super
		super(p, img, 1.0f, posXCenter, posYTop + (platHeight/2));
		System.out.println(this);
		// setWidth(platWidth);
		// setHeight(platHeight);
		setColor(PColor.NULL);
	}


	// Platform Constructor #2: Platform is a rectangle of color defined by it's center-x and top-Y positions
	public Platform(PApplet p, int color, float posXCenter, float posYTop, float platWidth, float platHeight) {
		
		// pass along the center-x and center-y to Sprite super
		super(p, color, posXCenter, posYTop, platWidth, platHeight);
		System.out.println(this.toString());
		setColor(color);
	}


	public void setColor(int color){
		this.color = color;
	}
	public int getColor(){
		return color;
	}

	public void setOutlineColor(int outlineColor){
		this.oColor = outlineColor;
	}
	public int getOutlineColor(){
		return oColor;
	}

	public void show(){
		p.rectMode(p.CORNER);
		p.stroke(PColor.getRed(p, oColor), PColor.getGreen(p, oColor), PColor.getBlue(p, oColor));
		p.fill(PColor.getRed(p, color), PColor.getGreen(p, color), PColor.getBlue(p, color));
		p.rect(getLeft(), getTop(), getW(), getH());
		// System.out.println("Platform show " + toString());
	}

	public String toString(){
		return "Sprite TS: "+ super.toString() 
		+ "\n --> Platform TS: " 
		+ "L:" + super.getLeft()
		+ "\tT: " + super.getTop()
		+ "\tw: " + super.getW()
		+ "\th: " + super.getH();

	}

}
