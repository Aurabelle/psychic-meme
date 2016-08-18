package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
	
	private Dimension totalSize = new Dimension(1000,800);
	
	//components for overall frame layout (in descending order, biggest-smallest)
	private JSplitPane completePane;
	private JSplitPane playerInfoPane;	
	
	//actual display components
	private JPanel playerJournal;
	private CluedoCanvas canvas;
	private JPanel userButtons;
	
	/**
	 * Creates a new instance of a Cluedo GUI
	 */
	public CluedoFrame(){
		super("Cluedo");
		
		canvas = new CluedoCanvas();
		canvas.setPreferredSize(null);		//null should be overridden
		
		createUserButtons();
		createInfoPane();
		completePane = new JSplitPane();
		completePane.setLeftComponent(userButtons);
		completePane.setRightComponent(playerInfoPane);
		
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
		userButtons = new JPanel();
		userButtons.setLayout(new BoxLayout(userButtons, BoxLayout.PAGE_AXIS));
		userButtons.setPreferredSize(new Dimension(200,800));
		
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
		
		Dimension buttonSize = new Dimension(50, 20);
		
		//doesn't actually work? 
		roll.setPreferredSize(buttonSize);
		move.setPreferredSize(buttonSize);
		suggest.setPreferredSize(buttonSize);
		accuse.setPreferredSize(buttonSize);
		end.setPreferredSize(buttonSize);
		
		userButtons.add(roll);
		userButtons.add(move);
		userButtons.add(suggest);
		userButtons.add(accuse);
		userButtons.add(end);
	}
	
	private void createInfoPane(){
		playerInfoPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		playerInfoPane.setPreferredSize(new Dimension(800,800));
		
		playerJournal = new JPanel();
	    playerJournal.setLayout(new BorderLayout());

	    playerJournal.add( new JLabel( "Notes:" ), BorderLayout.NORTH );
	    playerJournal.add( new JTextArea(), BorderLayout.CENTER );
	    
	    playerInfoPane.setLeftComponent(canvas);
	    playerInfoPane.setRightComponent(playerJournal);
	}

	
	public static void main( String args[] ){
	    // Create an instance of the test application
	    CluedoFrame mainFrame = new CluedoFrame();
	    mainFrame.pack();
	    mainFrame.setVisible( true );
	}
}
