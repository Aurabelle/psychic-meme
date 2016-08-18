package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.Location;
import game.Player;

/**
 * 
 * The board comprising of instances of the square class. Hard coded emptysquare instances because we had issues with eclipse.
 * This is a 25 by 25 grid made up of a mix of empty squares, roomsquares, stair squares, door squares, and non-playable squares, which each have differnt
 * attributes used to dictate player interaction and movement.
 * 
 * @author Jordan
 * @author Marielle
 *
 */
public class Board {
	
	private char[][] cleanBoard;
	private Square[][] boardSquares;
	private Room[] rooms;
	
	private ArrayList<Location> startPositions;
	private ArrayList<Location> solutionSquares;
	
	/*****************************************************************
	 * 			New Fields for Assignment 2
	 * Mainly just for new colors to use when drawing the board to 
	 * the new GUI.
	 *****************************************************************/
	private final Color PURPLE = new Color(167,91,183);
	private final Color GREEN = new Color(181,236,114);
	private final Color PINK = new Color(225,109,156);
	private final Color YELLOW = new Color(237,250,121);
	
	private final int SQ_SIZE = 25;
	  
	public Board(){
		
		cleanBoard = new char[][]{
				// a character representation of the array, used to reset board positions
				{'x','x','x','x','x','x','x','x','x','`','x','x','x','x','x','`','x','x','x','x','x','x','x','x','x'},
				{'s','r','r','r','r','r','x','.','.','.','r','r','r','r','r','.','.','.','x','r','r','r','r','r','s'},
				{'r','r','r','r','r','r','.','.','r','r','r','r','r','r','r','r','r','.','.','r','r','r','r','r','r'},
				{'r','r','r','r','r','r','.','.','r','r','r','r','r','r','r','r','r','.','.','r','r','r','r','r','r'},
				{'r','r','r','r','r','r','.','.','r','r','r','r','r','r','r','r','r','.','.','d','r','r','r','r','r'},
				{'r','r','r','r','r','r','.','.','d','r','r','r','r','r','r','r','d','.','.','.','r','r','r','r','x'},
				{'x','r','r','r','d','r','.','.','r','r','r','r','r','r','r','r','r','.','.','.','.','.','.','.','`'},
				{'.','.','.','.','.','.','.','.','r','d','r','r','r','r','r','d','r','.','.','.','.','.','.','.','x'},
				{'x','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','r','r','r','r','r','r'},
				{'r','r','r','r','r','.','.','.','.','.','.','.','.','.','.','.','.','.','.','d','r','r','r','r','r'},
				{'r','r','r','r','r','r','r','r','.','.','x','x','x','x','x','x','.','.','.','r','r','r','r','r','r'},
				{'r','r','r','r','r','r','r','r','.','.','x','x','x','x','x','x','.','.','.','r','r','r','r','r','r'},
				{'r','r','r','r','r','r','r','d','.','.','x','x','x','x','x','x','.','.','.','r','r','r','r','d','r'},
				{'r','r','r','r','r','r','r','r','.','.','x','x','x','x','x','x','.','.','.','.','.','.','.','.','x'},
				{'r','r','r','r','r','r','r','r','.','.','x','x','x','x','x','x','.','.','.','r','r','d','r','r','x'},
				{'r','r','r','r','r','r','d','r','.','.','x','x','x','x','x','x','.','.','r','r','r','r','r','r','r'},
				{'x','.','.','.','.','.','.','.','.','.','x','x','x','x','x','x','.','.','d','r','r','r','r','r','r'},
				{'`','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','r','r','r','r','r','r','r'},
				{'x','.','.','.','.','.','.','.','.','r','r','d','d','d','r','r','.','.','.','r','r','r','r','r','x'},
				{'r','r','r','r','r','r','d','.','.','r','r','r','r','r','r','r','.','.','.','.','.','.','.','.','`'},
				{'r','r','r','r','r','r','r','.','.','r','r','r','r','r','r','d','.','.','.','.','.','.','.','.','x'},
				{'r','r','r','r','r','r','r','.','.','r','r','r','r','r','r','r','.','.','d','r','r','r','r','r','r'},
				{'r','r','r','r','r','r','r','.','.','r','r','r','r','r','r','r','.','.','r','r','r','r','r','r','r'},
				{'r','r','r','r','r','r','r','.','.','r','r','r','r','r','r','r','.','.','r','r','r','r','r','r','r'},
				{'s','r','r','r','r','r','x','`','x','r','r','r','r','r','r','r','x','.','r','r','r','r','r','r','s'},
		};	
		createBoard();
	}
	
	
	/**
	 * 
	 * Creates room instances and uses a set of loops to fill out the board 
	 * 
	 * 
	 */
	public void createBoard(){
		
		Room kitchen = new Room("Kitchen", null);
		Room ballRoom = new Room("Ball Room", null);
		Room conservatory = new Room("Conservatory", null);
		Room diningRoom = new Room("Dining Room", null);
		Room billiardRoom = new Room("Billiard Room", null);
		Room library = new Room("Library", null);
		Room lounge = new Room("Lounge", null);
		Room hall = new Room("Hall", null);
		Room study = new Room("Study", null);
		
		boardSquares = new Square[25][25];
		solutionSquares = new ArrayList<Location>();
		startPositions = new ArrayList<Location>();
		
		for ( int cols = 0; cols < boardSquares.length; cols ++){
			for ( int rows = 0; rows <  boardSquares[cols].length; rows ++){
				if (cleanBoard[cols][rows] == 'x'){
					boardSquares[cols][rows] = new NonPlayableSquare();
					if(cols > 9 && cols < 16 && rows > 9 && rows < 17){
						solutionSquares.add(new Location(cols, rows));
					}
				}
				else {
					boardSquares[cols][rows] = new EmptySquare();
				}	
				
				if(cleanBoard[cols][rows] == 'd'){
					if(cols < 6 && rows < 7){
						Door door = new Door(kitchen, new Location(cols, rows));
						boardSquares[cols][rows] = door;
						kitchen.addDoor(door);
						System.out.println("kitchen: (" + cols + ", " + rows +")" );
					}
					else if(cols < 8 && rows > 8 && rows < 16){
						Door door = new Door(diningRoom, new Location(cols, rows));
						boardSquares[cols][rows] = door;
						diningRoom.addDoor(door);
						System.out.println("dining: (" + cols + ", " + rows +")" );
					}
					else if(cols < 7 && rows > 18){
						Door door = new Door(lounge, new Location(cols, rows));
						boardSquares[cols][rows] = door;
						lounge.addDoor(door);
						System.out.println("lounge: (" + cols + ", " + rows +")" );
					}
					else if(cols > 7 && cols < 17 && rows < 8){
						Door door = new Door(ballRoom, new Location(cols, rows));
						boardSquares[cols][rows] = door;
						ballRoom.addDoor(door);
						System.out.println("ballroom: (" + cols + ", " + rows +")" );
					}
					else if(cols > 8 && cols < 16 && rows > 17){
						Door door = new Door(hall, new Location(cols, rows));
						boardSquares[cols][rows] = door;
						hall.addDoor(door);
						System.out.println("hall: (" + cols + ", " + rows +")" );
					}
					else if(cols > 18 && rows < 6){
						Door door = new Door(conservatory, new Location(cols, rows));
						boardSquares[cols][rows] = door;
						conservatory.addDoor(door);
						System.out.println("conservatory: (" + cols + ", " + rows +")" );
					}
					else if(cols > 18 && rows > 7 && rows < 13){
						Door door = new Door(billiardRoom, new Location(cols, rows));
						boardSquares[cols][rows] = door;
						billiardRoom.addDoor(door);
						System.out.println("billiard: (" + cols + ", " + rows +")" );
					}
					else if(cols > 17 && rows > 13 && rows < 19){
						Door door = new Door(library, new Location(cols, rows));
						boardSquares[cols][rows] = door;
						library.addDoor(door);
						System.out.println("library: (" + cols + ", " + rows +")" );
					}
					else if(cols > 17 && rows > 20){
						Door door = new Door(study, new Location(cols, rows));
						boardSquares[cols][rows] = door;
						study.addDoor(door);
						System.out.println("study: (" + cols + ", " + rows +")" );
					}
					else{
						System.out.println("(" + cols + ", " + rows + ")");
					}
				}
				
				if(cleanBoard[cols][rows] == '`'){
					startPositions.add(new Location(cols, rows));
				}
			}
		}

		Stairs kitchenStairs = new Stairs(new Location(5, 1), kitchen, study); 
		boardSquares[1][5] = kitchenStairs;
		kitchen.setStairs(kitchenStairs);
		
		Stairs loungeStairs = new Stairs(new Location(0, 19), lounge, conservatory);
		boardSquares[19][0] = loungeStairs;
		lounge.setStairs(loungeStairs);
		
		Stairs conservatoryStairs = new Stairs(new Location(23, 5), conservatory, lounge);
		boardSquares[5][23] = conservatoryStairs;
		conservatory.setStairs(conservatoryStairs);
		
		Stairs studyStairs = new Stairs(new Location(24, 5), study, kitchen);
		boardSquares[21][24] = studyStairs;
		study.setStairs(studyStairs);
		
		
		Room[] roomArray = {kitchen, ballRoom, conservatory, diningRoom, billiardRoom,
				library, lounge, hall, study};
		this.rooms = roomArray;
		
		//sets all squares in a room to point to the singular room instance, so they all share the info
		for (Room room : this.rooms){
			/*for(Door d : room.getDoors()){
				System.out.println(d.getLocation().toString());
			}*/
			for (Location location : room.getRoomSquares()){
				Point point = new Point(location.getXLoc(), location.getYLoc());
				boardSquares[point.x][point.y] = room;
			}
			
		}		
	}
	
	/**
	 * Takes a location and a player, and checks if the given location is within a room
	 * 
	 * @param location on board
	 * @param player
	 * @return boolean
	 */
	public boolean isInRoom(Location location, Player player){
		for (Room r : rooms){
			if (r.isInRoom(location, player)){
				return true;
			}
		}
		return false;
	}
	

	
	/**
	 * Sets a player's location on the board
	 * 
	 * @param player
	 * @param x coordinate
	 * @param y coordinate
	 */
	public void setPlayer(Player player, int x, int y){
		this.boardSquares[x][y].setPlayer(player);
	}
	
	/**
	 * Moves a player to the new x/y coordinate on the board
	 * Adds/removes player to/from room if necessary 
	 * 
	 * @param player
	 * @param x coordinate
	 * @param y coordinate
	 */
	public void movePlayer(Player player, int x, int y){
		for (Room room : rooms){
			if (room.isInRoom(player.getLocation(), player)){
				room.removePlayer(player);
				player.resetRoom();
			}
		}				
		Point point = new Point(player.getLocation().getXLoc(), player.getLocation().getYLoc());
		int currentY = point.y;
		int currentX = point.x;
		// add player to empty square / room
		this.boardSquares[currentY][currentX].removePlayer();
		this.boardSquares[y][x].setPlayer(player);
		///////
		player.setLocation(new Location(x, y));
		for (Room room : rooms){
			if (room.isInRoom(player.getLocation(), player)){
				room.addPlayer(player);
				player.setRoom(room);
			}
		}
		if (this.boardSquares[y][x] instanceof Door){
			Door door = (Door) boardSquares[y][x];
			player.setRoom(door.getRoom());
			door.getRoom().addPlayer(player);
		}
	}
	
	/**
	 * takes a player and checks the four movable directions for legal moves
	 * adds a special move for leaving a room, if applicable
	 * 
	 * @param player
	 * @return map of moves with their corresponding locations
	 */
	public Map<String, Location> getMoves(Player player){
		

		Map<String, Location> possibleMoves = new HashMap<String, Location>();
		Point upPoint = new Point(player.getLocation().getXLoc(), player.getLocation().getYLoc()-1);
//		System.out.println("up"+upPoint);
		if (upPoint.x > -1 && upPoint.x < 25 && upPoint.y > -1 && upPoint.y < 25){
			if (boardSquares[upPoint.y][upPoint.x] instanceof EmptySquare && boardSquares[upPoint.y][upPoint.x].getPlayer() == null || 
					boardSquares[upPoint.y][upPoint.x] instanceof Door){
				possibleMoves.put("Move Up", new Location(upPoint.x, upPoint.y));
			}			
		}
		Point downPoint = new Point(player.getLocation().getXLoc(), player.getLocation().getYLoc()+1);
//		System.out.println("down"+downPoint);
		if (downPoint.x > -1 && downPoint.x < 25 && downPoint.y > -1 && downPoint.y < 25){
			if (boardSquares[downPoint.y][downPoint.x] instanceof EmptySquare && boardSquares[downPoint.y][downPoint.x].getPlayer() == null || 
					boardSquares[downPoint.y][downPoint.x] instanceof Door){
				possibleMoves.put("Move Down", new Location(player.getLocation().getXLoc(), player.getLocation().getYLoc()+1));
			}	
		}
		Point leftPoint = new Point(player.getLocation().getXLoc()-1, player.getLocation().getYLoc());
//		System.out.println("left"+leftPoint);
		if (leftPoint.x > -1 && leftPoint.x < 25 && leftPoint.y > -1 && leftPoint.y < 25){
			if (boardSquares[leftPoint.y][leftPoint.x] instanceof EmptySquare && boardSquares[leftPoint.y][leftPoint.x].getPlayer() == null || 
				boardSquares[leftPoint.y][leftPoint.x] instanceof Door){
				possibleMoves.put("Move Left", new Location(player.getLocation().getXLoc()-1, player.getLocation().getYLoc()));
			}
		}
		Point rightPoint = new Point(player.getLocation().getXLoc()+1, player.getLocation().getYLoc());
//		System.out.println(rightPoint);
		if (rightPoint.x > -1 && rightPoint.x < 25 && rightPoint.y > -1 && rightPoint.y < 25){
			if (boardSquares[rightPoint.y][rightPoint.x] instanceof EmptySquare && boardSquares[rightPoint.y][rightPoint.x].getPlayer() == null || 
					boardSquares[rightPoint.y][rightPoint.x] instanceof Door){
				possibleMoves.put("Move Right", new Location(player.getLocation().getXLoc()+1, player.getLocation().getYLoc()));
			}
		}
		
		if (player.getRoom() != null){
			Point doorLocUp = new Point(player.getRoom().getDoors().get(0).getLocation().getXLoc(), player.getRoom().getDoors().get(0).getLocation().getYLoc()-1);
//			System.out.println("up"+upPoint);
			if (doorLocUp.x > -1 && doorLocUp.x < 25 && doorLocUp.y > -1 && doorLocUp.y < 25){
				if (boardSquares[doorLocUp.y][doorLocUp.x] instanceof EmptySquare){
					possibleMoves.put("Exit Room", new Location(doorLocUp.x, doorLocUp.y));
				}			
			}
			Point doorLocDown = new Point(player.getRoom().getDoors().get(0).getLocation().getXLoc(), player.getRoom().getDoors().get(0).getLocation().getYLoc()+1);
//			System.out.println("down"+downPoint);
			if (doorLocDown.x > -1 && doorLocDown.x < 25 && doorLocDown.y > -1 && doorLocDown.y < 25){
				if (boardSquares[doorLocDown.y][doorLocDown.x] instanceof EmptySquare){
					possibleMoves.put("Exit Room", new Location(doorLocDown.x, doorLocDown.y+1));
				}	
			}
			Point doorLocLeft = new Point(player.getRoom().getDoors().get(0).getLocation().getXLoc()-1, player.getRoom().getDoors().get(0).getLocation().getYLoc());
//			System.out.println("left"+leftPoint);
			if (doorLocLeft.x > -1 && doorLocLeft.x < 25 && doorLocLeft.y > -1 && doorLocLeft.y < 25){
				if (boardSquares[doorLocLeft.y][doorLocLeft.x] instanceof EmptySquare){
					possibleMoves.put("Exit Room", new Location(doorLocLeft.x-1, doorLocLeft.y));
				}
			}
			Point doorLocRight = new Point(player.getRoom().getDoors().get(0).getLocation().getXLoc()+1, player.getRoom().getDoors().get(0).getLocation().getYLoc());
//			System.out.println(rightPoint);
			if (doorLocRight.x > -1 && doorLocRight.x < 25 && doorLocRight.y > -1 && doorLocRight.y < 25){
				if (boardSquares[doorLocRight.y][doorLocRight.x] instanceof EmptySquare){
					possibleMoves.put("Exit Room", new Location(doorLocRight.x+1, doorLocRight.y));
				}
			}
		}
		if (player.getRoom() != null && player.getRoom().getStairs() != null){
			// method to add stairs to player move options
			int x = player.getRoom().getDoors().get(0).getLocation().getXLoc();
			int y = player.getRoom().getDoors().get(0).getLocation().getYLoc();
			possibleMoves.put("Take Stairs to " + player.getRoom().getStairs().getDestination().getName(), new Location(x, y));
		}
		return possibleMoves;
	}
	
	/**
	 * @return list of rooms on the board
	 */
	public Room[] getRooms(){
		return rooms;
	}
	
	/*********************************************************************************************************************
	 * 											New Methods for Assignment 2
	 **********************************************************************************************************************/
	
	public void drawBoard(Graphics g){
		//do the base colors, which will then have a grid drawn over them
		//Room Squares and Solution squares are drawn in after the grid
		//Door Squares draw an additional white outline over themselves
		for(int i = 0; i < boardSquares.length; i++){
			for(int j = 0; j < boardSquares[i].length; j++){
				Square sq = boardSquares[i][j];
				
				if(sq instanceof NonPlayableSquare){
					g.setColor(PURPLE);
					g.fillRect(j*SQ_SIZE, i*SQ_SIZE, SQ_SIZE, SQ_SIZE);
				}
				else if(sq instanceof Stairs){
					g.setColor(GREEN);
					g.fillRect(j*SQ_SIZE, i*SQ_SIZE, SQ_SIZE, SQ_SIZE);
				}
				else if(startPositions.contains(new Location(i, j))){
					g.setColor(PINK);
					g.fillRect(j*SQ_SIZE, i*SQ_SIZE, SQ_SIZE, SQ_SIZE);
				}
				else{
					//the rest are playable, or will get drawn over next
					g.setColor(YELLOW);
					g.fillRect(j*SQ_SIZE, i*SQ_SIZE, SQ_SIZE, SQ_SIZE);
				}
			}
		}
				
		//draw grid over squares
		g.setColor(Color.BLACK);
		for(int i = 0; i < 25; i++){
			for(int j = 0; j < 25; j++){
				g.drawRect(j*SQ_SIZE, i*SQ_SIZE, SQ_SIZE, SQ_SIZE);
			}		
		}
		
		//draw in the rooms and solution
		for(int i = 0; i < 25; i++){
			for(int j = 0; j < 25; j++){
				Square sq = boardSquares[i][j];
				
				if(sq instanceof Room){
					g.setColor(Color.WHITE);
					g.fillRect(j*SQ_SIZE, i*SQ_SIZE, SQ_SIZE, SQ_SIZE);
				}
				if(sq instanceof Door){
					g.setColor(PINK);
					g.fillRect(j*SQ_SIZE, i*SQ_SIZE, SQ_SIZE, SQ_SIZE);
					g.setColor(Color.WHITE);
					g.drawRect(j*SQ_SIZE, i*SQ_SIZE, SQ_SIZE, SQ_SIZE);
				}
			}		
		}
		
	}
	
}