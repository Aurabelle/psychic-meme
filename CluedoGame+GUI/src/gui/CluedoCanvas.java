package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import ui.Board;

public class CluedoCanvas extends JPanel{
	//essential as it extends JPanel
	private static final long serialVersionUID = 2L;
	
	private Board board = new Board();
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		board.drawBoard(g);
	}


}
