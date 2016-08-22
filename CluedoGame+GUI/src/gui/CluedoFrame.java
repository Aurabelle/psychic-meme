package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import game.Player;

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
 * This tutorial was also very useful when creating the JMenuBar (http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html),
 * and this site for the JRadioButtons with JDialogs (http://myy.haaga-helia.fi/~atk84d/tutorial/uiswing/mini/fifthexample.html)
 * 
 * Credit also to DJP for the MoonLander tutorial exercise, and the author of the 
 * COMP261 GUIs used with the Maps and 3D Rendering programs.
 * 
 * @author Marielle
 *
 */
public class CluedoFrame extends Subject implements WindowListener, MouseListener{
	//required UID for JFrame extension
	private static final long serialVersionUID = 1L;
	
	/*************************************************************************
	 *					Abstract Methods for GUI actions 
	 *************************************************************************/
	//protected abstract void displayPlayerDetails(Graphics g);
	//protected abstract void displayPlayerHand(Graphics g);
	//protected abstract void displayBoard(Graphics g);
	//protected abstract void redraw(Graphics g)?
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
	private JMenuBar menuBar;
	private JPanel userButtons;
	private CluedoCanvas canvas;
	private JPanel journalPanel;
	private JPanel playerStats;
	private JPanel cardDisplay;
	private JTextArea journalText;
	
	private JFrame popUpFrame;
	
	//helpers for player initialization
	private ArrayList<String> possibleTokens;
	JRadioButton[] options;
	
	/**
	 * Creates a new instance of a Cluedo GUI
	 */
	public CluedoFrame(){
		super("Cluedo");
		initialise();
	}
	
	private void initialise(){
		populateTokenArray();
		
		createUserButtons();
		createBoardCardsPane();
		createInfoPane();
		createMenuBar();
		
		completePane = new JSplitPane();
		completePane.setLeftComponent(userButtons);
		completePane.setRightComponent(mainPlayerInterface);
		
		setLayout(new BorderLayout());
		setPreferredSize(totalSize);
		add(menuBar, BorderLayout.NORTH);
		add(completePane, BorderLayout.CENTER);
		
		popUpFrame = new JFrame();
		
		//closing is now handled by windowClosing()
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		
		setResizable(false);		//for now, until I actually get something working
		pack();
		setVisible(true);
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
		journalPanel = new JPanel();
	    journalPanel.setLayout(new BorderLayout());

	    journalPanel.add(new JLabel( "Notes:" ), BorderLayout.NORTH);
	    journalText = new JTextArea();
	    journalPanel.add(journalText, BorderLayout.CENTER);
	    
	    playerInfoPane.setTopComponent(playerStats);
	    playerInfoPane.setBottomComponent(journalPanel);
	    
	    mainPlayerInterface.setLeftComponent(boardAndCards);
	    mainPlayerInterface.setRightComponent(playerInfoPane);
	}
	
	private void createBoardCardsPane(){
		canvas = new CluedoCanvas();
		canvas.setPreferredSize(canvasSize);
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				//onMouseClick(e);
				redraw();
			}
		});
		
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

	private void createMenuBar(){
		menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newGameOpt = new JMenuItem("New Game");
		newGameOpt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//onButtonPress("new");
			}
		});
		fileMenu.add(newGameOpt);
		fileMenu.add(new JMenuItem("Exit"));
		
		menuBar.add(fileMenu);	
	}

	/**
	 * This creates a new dialog window which returns the number of players
	 * required by the current game
	 */
	public int getNumPlayers(){
		Object[] possibleNums = {3, 4, 5, 6};
		int numPlayers = (Integer)JOptionPane.showInputDialog(
                popUpFrame,
                "Please select the total number of players for this game:",
                "Set Number of Players",
                JOptionPane.PLAIN_MESSAGE,
                null, possibleNums, 3);

		if(numPlayers > 2){
			//onButtonPress("numPlayers");
			//set player menu state to selectable? 
			System.out.println(numPlayers);
		}
		return numPlayers;
	}
	
	public String getPlayerName(){
		String name = (String)JOptionPane.showInputDialog(
                popUpFrame,
                "Please enter your name",
                "Set Player Name",
                JOptionPane.PLAIN_MESSAGE,
                null, null, "");
		if ((name != null) && (name.length() > 0)) {
			return name;
		}
		else{
			return "Player";
		}
	}
	
	public String getPlayerToken(){
		popUpFrame = new JFrame();

		int r = JOptionPane.showConfirmDialog(popUpFrame, createTokenChoiceDialog(), "Player Character Choice", JOptionPane.OK_OPTION);
		
		if(r == JOptionPane.YES_OPTION){
			for (JRadioButton button : options) {
	            if (button.isSelected()) {
	            	return button.getActionCommand();
	            }
	        }
		}
		return "none"; 
	}
	
	private void removeToken(String token){
		int index = possibleTokens.indexOf(token);
		possibleTokens.remove(index);
	}
	
	private JPanel createTokenChoiceDialog(){
		options = new JRadioButton[possibleTokens.size()];
		ButtonGroup buttonGroup = new ButtonGroup();
		
		 for(int i = 0; i < possibleTokens.size(); i++){
			 //if this is the first button added, set it as selected (to avoid not having something selected when 
			 //the dialog is exited)
			 JRadioButton jrb; 
			 if(i == 0){
				 jrb = new JRadioButton(possibleTokens.get(i), true);
				 jrb.setActionCommand(possibleTokens.get(i));
				 
			 }
			 else{
				 jrb = new JRadioButton(possibleTokens.get(i));
				 jrb.setActionCommand(possibleTokens.get(i));
			 }
			 options[i] = jrb;
			 buttonGroup.add(jrb);
		 }
		
		 JPanel radioOptions = new JPanel();
        JLabel label = new JLabel("Please choose your character token: ");

        radioOptions.setLayout(new BoxLayout(radioOptions, BoxLayout.Y_AXIS));
        radioOptions.add(label);

        for (int i = 0; i < options.length; i++) {
            radioOptions.add(options[i]);
        }
        return radioOptions;
	}
	
	private void populateTokenArray(){
		String[] tokens = {"Miss Scarlet", "Colonel Mustard", "Mrs White", "Mr Green", "Mrs Peacock", "Professor Plum"};
		possibleTokens = new ArrayList<String>();
		for(String s: tokens){
			possibleTokens.add(s);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		//onMouseClick(me)
	}
	
	public void redraw(){
		this.repaint();
	}
	
	public JTextArea getJournal(){
		return journalText;
	}
	
	/**
	 * This method is called when the user clicks on the "X" button in the
	 * right-hand corner.
	 *
	 * @param e
	 * @author David J Pearce : thanks :D
	 */
 	public void windowClosing(WindowEvent e) {
		// Ask the user to confirm they wanted to do this
		int r = JOptionPane.showConfirmDialog(this, new JLabel(
		"Exit Cluedo?"), "Confirm Exit",
		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (r == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
 	public static void main(String args[]){
 		CluedoFrame cf = new CluedoFrame();
 	}
	/*************************************************************************
	 * The following methods are required for the Window/MouseListener 
	 * interfaces but there is no reason to implement them at this stage. 
	 * @param e / me
	 ************************************************************************/
    public void windowClosed(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}

	@Override
	public void mouseEntered(MouseEvent me) {}
	@Override
	public void mouseExited(MouseEvent me) {}
	@Override
	public void mousePressed(MouseEvent me) {}
	@Override
	public void mouseReleased(MouseEvent me) {}
}
