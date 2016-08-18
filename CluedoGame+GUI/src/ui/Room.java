package ui;

import java.util.ArrayList;
import java.util.List;

import game.GameOfCluedo;
import game.Location;
import game.Player;


/**
 * A room instance, can hold multiple players and is used to make suggestions for the game 
 * 
 * @author Jordan
 * @author Marielle
 */
public class Room extends Square {
	// mucking about with square ideas
	
	private List<Player> playersInside = new ArrayList<Player>();
	private String weapon = null;
	private String roomName;
	public ArrayList<Location> roomSquares = new ArrayList<>();
	private Stairs stairs = null; 
	private ArrayList<Door> doors = new ArrayList<>();
	
	/**
	 * Creates an new instance of a room with the specified name, and list of encompassing locations. 
	 * The room may contain a weapon (null if no weapon is in the room).
	 * 
	 * @param name of the room
	 * @param weapon contained in the room, if any
	 * @param roomSquares list of locations on the board that this room covers
	 */	
	public Room(String name, String weapon){
		roomName = name;
		this.weapon = weapon;
		setSquares();
//		this.roomSquares = roomSquares;
	}
	
	public ArrayList<Location> getRoomSquares(){
		return this.roomSquares;
	}
	
	public List<Player> getPlayers(){
		return this.playersInside;
	}
	
	public void addPlayer(Player player){
		this.playersInside.add(player);

	}
	
	public void removePlayer(Player player){
		this.playersInside.remove(player);
	}
	
	public boolean isInRoom(Location location, Player player){
		return roomSquares.contains(location) || playersInside.contains(player);
	}
	
	public boolean containsWeapon(String string){
		return this.weapon == string;
	}
	
	public void setWeapon(String weapon){
		this.weapon = weapon;
	}
	
	public String getWeapon() {
		return this.weapon;
		
	}
	
	public String getName(){
		return this.roomName;
	}
	
	public Stairs getStairs(){
		return this.stairs;
	}
	
	public void setStairs(Stairs stairs) {
		this.stairs = stairs;
	}
	
	@Override
	public String toString(){
		return "r";
	}
	
	public ArrayList<Door> getDoors(){
		return this.doors;
	}
	
	public void addDoor(Door door){
		this.doors.add(door);
	}
	

	
	public void setSquares(){
		if (this.roomName == "Kitchen"){
			for ( int i = 1; i < 6; i++){
				this.roomSquares.add(new Location(i, 0));
			}
			for ( int i = 1; i < 7; i++){
				this.roomSquares.add(new Location(i, 1));
			}
			for ( int i = 1; i < 7; i++){
				this.roomSquares.add(new Location(i, 2));
			}
			for ( int i = 1; i < 7; i++){
				this.roomSquares.add(new Location(i, 3));
			}
			for ( int i = 1; i < 7; i++){
				this.roomSquares.add(new Location(i, 4));
			}
			for ( int i = 2; i < 7; i++){
				this.roomSquares.add(new Location(i, 5));
			}
		}
			
		if (this.roomName == "Dining Room"){
			for ( int i = 9; i < 16; i++){
				this.roomSquares.add(new Location(i, 0));
			}
			for ( int i = 9; i < 16; i++){
				this.roomSquares.add(new Location(i, 1));
			}
			for ( int i = 9; i < 16; i++){
				this.roomSquares.add(new Location(i, 2));
			}
			for ( int i = 9; i < 16; i++){
				this.roomSquares.add(new Location(i, 3));
			}
			for ( int i = 9; i < 16; i++){
				this.roomSquares.add(new Location(i, 4));
			}
			for ( int i = 10; i < 16; i++){
				this.roomSquares.add(new Location(i, 5));
			}
			for ( int i = 10; i < 16; i++){
				this.roomSquares.add(new Location(i, 6));
			}
			for ( int i = 10; i < 16; i++){
				this.roomSquares.add(new Location(i, 7));
			}
		}
		if (this.roomName == "Lounge"){
			for ( int i = 20; i < 25; i++){
				this.roomSquares.add(new Location(i, 0));
			}
			for ( int i = 19; i < 25; i++){
				this.roomSquares.add(new Location(i, 1));
			}
			for ( int i = 19; i < 25; i++){
				this.roomSquares.add(new Location(i, 2));
			}
			for ( int i = 19; i < 25; i++){
				this.roomSquares.add(new Location(i, 3));
			}
			for ( int i = 19; i < 25; i++){
				this.roomSquares.add(new Location(i, 4));
			}
			for ( int i = 19; i < 25; i++){
				this.roomSquares.add(new Location(i, 5));
			}
			for ( int i = 19; i < 24; i++){
				this.roomSquares.add(new Location(i, 6));
			}
		}
		
		if (this.roomName == "Ball Room"){
			for ( int i = 2; i < 8; i++){
				this.roomSquares.add(new Location(i, 8));
			}
			for ( int i = 2; i < 8; i++){
				this.roomSquares.add(new Location(i, 9));
			}
			for ( int i = 1; i < 8; i++){
				this.roomSquares.add(new Location(i, 10));
			}
			for ( int i = 1; i < 8; i++){
				this.roomSquares.add(new Location(i, 11));
			}
			for ( int i = 1; i < 8; i++){
				this.roomSquares.add(new Location(i, 12));
			}
			for ( int i = 1; i < 8; i++){
				this.roomSquares.add(new Location(i, 13));
			}
			for ( int i = 1; i < 8; i++){
				this.roomSquares.add(new Location(i, 14));
			}
			for ( int i = 2; i < 8; i++){
				this.roomSquares.add(new Location(i, 15));
			}
			for ( int i = 2; i < 8; i++){
				this.roomSquares.add(new Location(i, 16));
			}
		}
		
		if (this.roomName == "Hall"){
			for ( int i = 18; i < 25; i++){
				this.roomSquares.add(new Location(i, 9));
			}
			for ( int i = 18; i < 25; i++){
				this.roomSquares.add(new Location(i, 10));
			}
			for ( int i = 18; i < 25; i++){
				this.roomSquares.add(new Location(i, 11));
			}
			for ( int i = 18; i < 25; i++){
				this.roomSquares.add(new Location(i, 12));
			}
			for ( int i = 18; i < 25; i++){
				this.roomSquares.add(new Location(i, 13));
			}
			for ( int i = 18; i < 25; i++){
				this.roomSquares.add(new Location(i, 14));
			}
			for ( int i = 18; i < 25; i++){
				this.roomSquares.add(new Location(i, 15));
			}
		}
		
		if (this.roomName == "Conservatory"){
			for ( int i = 1; i < 5; i++){
				this.roomSquares.add(new Location(i, 19));
			}
			for ( int i = 1; i < 6; i++){
				this.roomSquares.add(new Location(i, 20));
			}
			for ( int i = 1; i < 6; i++){
				this.roomSquares.add(new Location(i, 21));
			}
			for ( int i = 1; i < 6; i++){
				this.roomSquares.add(new Location(i, 22));
			}
			for ( int i = 1; i < 5; i++){
				this.roomSquares.add(new Location(i, 23));
			}
			for ( int i = 1; i < 5; i++){
				this.roomSquares.add(new Location(i, 24));
			}
		}
		
		
		if (this.roomName == "Billiard Room"){
			for ( int i = 8; i < 13; i++){
				this.roomSquares.add(new Location(i, 19));
			}
			for ( int i = 8; i < 13; i++){
				this.roomSquares.add(new Location(i, 20));
			}
			for ( int i = 8; i < 13; i++){
				this.roomSquares.add(new Location(i, 21));
			}
			for ( int i = 8; i < 13; i++){
				this.roomSquares.add(new Location(i, 22));
			}
			for ( int i = 8; i < 13; i++){
				this.roomSquares.add(new Location(i, 23));
			}
			for ( int i = 8; i < 13; i++){
				this.roomSquares.add(new Location(i, 24));
			}
		}					
		
		if (this.roomName == "Library"){
			for ( int i = 15; i < 18; i++){
				this.roomSquares.add(new Location(i, 18));
			}						
			for ( int i = 14; i < 19; i++){
				this.roomSquares.add(new Location(i, 19));
			}
			for ( int i = 14; i < 19; i++){
				this.roomSquares.add(new Location(i, 20));
			}
			for ( int i = 14; i < 19; i++){
				this.roomSquares.add(new Location(i, 21));
			}
			for ( int i = 14; i < 19; i++){
				this.roomSquares.add(new Location(i, 22));
			}
			for ( int i = 14; i < 19; i++){
				this.roomSquares.add(new Location(i, 23));
			}
			for ( int i = 15; i < 18; i++){
				this.roomSquares.add(new Location(i, 24));
			}
		}						
		if (this.roomName == "Study"){
			for ( int i = 21; i < 25; i++){
				this.roomSquares.add(new Location(i, 18));
			}						
			for ( int i = 21; i < 25; i++){
				this.roomSquares.add(new Location(i, 19));
			}
			for ( int i = 21; i < 25; i++){
				this.roomSquares.add(new Location(i, 20));
			}
			for ( int i = 21; i < 25; i++){
				this.roomSquares.add(new Location(i, 21));
			}
			for ( int i = 21; i < 25; i++){
				this.roomSquares.add(new Location(i, 22));
			}
			for ( int i = 21; i < 25; i++){
				this.roomSquares.add(new Location(i, 23));
			}
			for ( int i = 22; i < 25; i++){
				this.roomSquares.add(new Location(i, 24));
			}
		}	
	}

	//stairs
	

}
