import processing.core.PApplet;
import processing.core.PImage;

/**
 * Platform class
 * Can be used with the following tutorials:
 * https://longbaonguyen.github.io/courses/platformer/platformer.html
 * @author Joel A Bianchi
 * @author Nathan Santos 2022
 * @author Clive Sherwood 2022
 * @author Marcus Bistline 2025
 * @version 5/29/25
 * Now inherits from Sprite class
 * javadoc formatting
 */
public class Platform extends Sprite {

	private int color = PColor.BLACK;
	private int oColor = PColor.WHITE;

	/**
	 * Platform Constructor #1: Platform is PImage defined by it's center-x and top-Y positions
	 * @param p             Processing applet
	 * @param img			PImage to be displayed on the Platform
	 * @param posXCenter	x-pixel-value of the center of the Platform
	 * @param posYTop		y-pixel-value of the top of the Platform
	 * @param platWidth		width in pixels of the Platform
	 * @param platHeight	height in pixels of the Platform
	 */
	public Platform(PApplet p, PImage img, float posXCenter, float posYTop, float platWidth, float platHeight) {
		
		// pass along the center-x and center-y to Sprite super
		super(p, img, 1.0f, posXCenter, posYTop + (platHeight/2));
		System.out.println(this);
		// setWidth(platWidth);
		// setHeight(platHeight);
		setColor(PColor.NULL);
	}

	/**
	 * Platform Constructor #2: Platform is a rectangle of color defined by it's center-x and top-Y positions
	 * @param p             Processing applet
	 * @param color         PColor int for the color of the Platform
	 * @param posXCenter	x-pixel-value of the center of the Platform
	 * @param posYTop		y-pixel-value of the top of the Platform
	 * @param platWidth		width in pixels of the Platform
	 * @param platHeight	height in pixels of the Platform
	 */
	public Platform(PApplet p, int color, float posXCenter, float posYTop, float platWidth, float platHeight) {
		// pass along the center-x and center-y to Sprite super
		super(p, color, posXCenter, posYTop, platWidth, platHeight);
		System.out.println(this.toString());
		setColor(color);
	}


	/** 
	 * Mutator method
	 * @param color         PColor int for the color of the inside of the rectangle
	 */
	public void setColor(int color){
		this.color = color;
	}
	
	/**
	 * Accessor method
	 * @return int         color of the inside of the rectangle as a PColor
	 */
	public int getColor(){
		return color;
	}
	
	/** 
	 * Mutator method
	 * @param outlineColor   PColor int for the color of the outline of the rectangle
	 */
	public void setOutlineColor(int outlineColor){
		this.oColor = outlineColor;
	}
	
	/** 
	 * Accessor method
	 * @return int		color of the outline of the rectangle as a PColor
	 */
	public int getOutlineColor(){
		return oColor;
	}

	/**
	 * Displays the Platform on the Screen
	 */
	public void show(){
		p.rectMode(p.CORNER);
		p.stroke(PColor.getRed(p, oColor), PColor.getGreen(p, oColor), PColor.getBlue(p, oColor));
		p.fill(PColor.getRed(p, color), PColor.getGreen(p, color), PColor.getBlue(p, color));
		p.rect(getLeft(), getTop(), getW(), getH());
		// System.out.println("Platform show " + toString());
	}

	
	/** 
	 * @return String		includes Sprite's <code>toString()</code> + the left, top, width, and height
	 */
	public String toString(){
		return "Sprite TS: "+ super.toString() 
		+ "\n --> Platform TS: " 
		+ "L:" + super.getLeft()
		+ "\tT: " + super.getTop()
		+ "\tw: " + super.getW()
		+ "\th: " + super.getH();

	}

} // end of Platform class
