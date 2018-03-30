package jp.maceration.component.mascott.data;

import java.awt.image.BufferedImage;

/**
 * Mascott Move Data Class.
 * 
 * @author Shinnosuke.J
 *
 */
public class MascottMoveData {
	
	public String			moveX;
	public String			moveY;
	public BufferedImage	image;
	
	/**
	 * Constructor Method.
	 */
	public MascottMoveData() {
		super();
		
		this.moveX = "";
		this.moveY = "";
		this.image = null;
	}

}
