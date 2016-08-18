package gui;

import java.util.List;

import javax.swing.JFrame;

public abstract class Subject extends JFrame{
	//auto-generated UID JFrame requires
	private static final long serialVersionUID = 1L;
	private List<Observer> obs;
	
	public Subject(String name){
		super(name);
	}
	
	public void notify(String msg){
		for(Observer o : obs){
			o.notify(msg);
		}
	}
}
