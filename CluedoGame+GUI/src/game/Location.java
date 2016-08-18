package game;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
/**
 * This class represents the alphanumerical location on a 
 * Cluedo Board, which is used primarily for finding and 
 * moving players and weapons around the board.
 * 
 * @author Marielle Cheyne
 * @author Jordan Ching
 *
 */    

public class Location {
	private int xLoc;
	private int yLoc;
	
	
	public Location(int x, int y){
		xLoc = x;
		yLoc = y;
	}
	
	public int getYLoc(){
		return this.yLoc;
	}

	public int getXLoc(){
		return this.xLoc;
	}
	
	
	void setYLoc(int y){
		this.yLoc = y;
	}

	void setXLoc(int x){
		this.xLoc = x;
	}
	
	public String toString(){
		return "(" + xLoc + ", " + yLoc +")";
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Location){
			Location loc = (Location) obj;
			return this.xLoc == loc.getXLoc() && this.yLoc == loc.getYLoc();
		}
		return false;
	}
	
}
