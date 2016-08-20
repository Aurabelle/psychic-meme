package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

/**
 * CluedoFrame is what it says on the box: a frame for holding all the elements 
 * needed for a GUI for a game of Cluedo. 
 * It extends the Subject class, which in turn extends JFrame so that a CluedoFrame 
 * can still be a JFrame. The Subject class is based on the Subject/Observer design 
 * pattern from Lecture 9 for SWEN222. The Subject superclass allows this Frame to 
 * notify Observers attached to the Subject of user events when required.
 * 
 * 
 * Credit to Maxim Shoustin (http://stackoverflow.com/questions/13057836/using-two-jpanels-in-one-jframe)
 * for the example using JSplitPanes, which really helped me figure out some
 * important Swing concepts.
 * Credit also to DJP for the MoonLander tutorial exercise, and the author of the 
 * COMP261 GUIs used with the Maps and 3D Rendering programs.
 * 
 * @author Marielle
 *
 */
public class CluedoFrame extends Subject{
	//required UID for JFrame extension
	private static final long serialVersionUID = 1L;
	
	/*************************************************************************
	 *					Abstract Methods for GUI actions 
	 *************************************************************************/
	//protected abstract void displayPlayerDetails(Graphics g);
	//protected abstract void displayPlayerHand(Graphics g);
	//protected abstract void displayBoard(Graphics g);
	//protected abstract void onButtonPress(String action);
	//protected abstract void onMouseClick(MouseEvent me);
	
	/**************************************************************************
	 * 					GUI components & creation
	 *************************************************************************/
	private Dimension totalSize = new Dimension(1050,860);
	private Dimension canvasSize = new Dimension(650, 700);
	private Dimension mainInterfaceSize = new Dimension(850,860);
	private Dimension userButtonSize = new Dimension(100, 860);
	private Dimension playerDetailSize = new Dimension(100, 100);
	private Dimension cardDisplaySize = new Dimension(650, 260);
	
	//components for overall frame layout (in descending order, biggest-smallest)
	private JSplitPane completePane;
	private JSplitPane mainPlayerInterface;	//playerInfoPane and board/cards
	private JSplitPane playerInfoPane;	//journal and player details
	private JSplitPane boardAndCards;	// board and player's hand
	
	//actual display components
	private JPanel userButtons;
	private CluedoCanvas canvas;
	private JPanel playerJournal;
	private JPanel playerStats;
	private JPanel cardDisplay;
	
	/**
	 * Creates a new instance of a Cluedo GUI
	 */
	public CluedoFrame(){
		super("Cluedo");

		createUserButtons();
		createBoardCardsPane();
		createInfoPane();
		
		completePane = new JSplitPane();
		completePane.setLeftComponent(userButtons);
		completePane.setRightComponent(mainPlayerInterface);
		
		setLayout(new BorderLayout());
		setPreferredSize(totalSize);
		add(completePane, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setResizable(false);		//for now, until I actually get something working
		//pack();
		//setVisible(true);
	}
	
	/**
	 * Creates the section of the GUI which will contain the options for actions
	 * a player may take during their turn.
	 */
	private void createUserButtons(){
		JPanel allButtons = new JPanel();
		allButtons.setLayout(new GridLayout(0, 1, 0, 10));
		
		userButtons = new JPanel();
		userButtons.setPreferredSize(userButtonSize);
		
		//add the buttons that allow player interaction
		JButton roll = new JButton("Roll Dice");
		roll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				CluedoFrame.this.notify("roll");
			}
		});
		
		JButton move = new JButton("Move");
		move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				CluedoFrame.this.notify("move");
				//needs to be able to grey out other buttons until 
				//a move is complete - how to notify Subject?
			}
		});
		
		JButton suggest = new JButton("Suggest");
		suggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				CluedoFrame.this.notify("suggest");
			}
		});
		
		JButton accuse = new JButton("Accuse");
		accuse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				CluedoFrame.this.notify("accuse");
			}
		});
		
		JButton end = new JButton("End Turn");
		end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				CluedoFrame.this.notify("end");
			}
		});
		
		allButtons.add(roll);
		allButtons.add(move);
		allButtons.add(suggest);
		allButtons.add(accuse);
		allButtons.add(end);

		userButtons.add(allButtons);
	}
	
	private void createInfoPane(){
		mainPlayerInterface = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainPlayerInterface.setPreferredSize(mainInterfaceSize);
		
		playerInfoPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		playerStats = new JPanel(){
			@Override
			public void paint(Graphics g){
				//displayPlayerDetails(g);
				g.setColor(Color.MAGENTA);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		playerStats.setPreferredSize(playerDetailSize);
		playerJournal = new JPanel();
	    playerJournal.setLayout(new BorderLayout());

	    playerJournal.add(new JLabel( "Notes:" ), BorderLayout.NORTH);
	    playerJournal.add(new JTextArea(), BorderLayout.CENTER);
	    
	    playerInfoPane.setTopComponent(playerStats);
	    playerInfoPane.setBottomComponent(playerJournal);
	    
	    mainPlayerInterface.setLeftComponent(boardAndCards);
	    mainPlayerInterface.setRightComponent(playerInfoPane);
	}
	
	private void createBoardCardsPane(){
		canvas = new CluedoCanvas();
		canvas.setPreferredSize(canvasSize);	
		
		cardDisplay = new JPanel(){
			@Override
			public void paint(Graphics g){
				//displayHand
				g.setColor(Color.RED);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		cardDisplay.setMaximumSize(cardDisplaySize);
		
		boardAndCards = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		boardAndCards.setTopComponent(canvas);
		boardAndCards.setBottomComponent(cardDisplay);
	}

	
	public static void main( String args[] ){
	    // Create an instance of the test application
	    CluedoFrame mainFrame = new CluedoFrame();
	    mainFrame.pack();
	    mainFrame.setVisible( true );
	}
}
