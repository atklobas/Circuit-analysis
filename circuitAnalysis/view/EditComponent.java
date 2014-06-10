package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import circuitAnalysis.Model;
import Commands.Command;
import Commands.CommandListener;
import Components.Component;

public class EditComponent extends JFrame implements ActionListener, KeyListener{
	Model m;
	Component c;
	private int cID;
	
	JPanel inner=new JPanel();
	HashMap<NumberField, String> map=new HashMap<NumberField, String>();
	private ArrayList<CommandListener> cmdListeners= new ArrayList<CommandListener>();
	
	public EditComponent(Model m,Component c) {
		this.c=c;
		cID=m.getComponentNumber(c);
		this.setTitle("Edit component "+c.getClass().getName()+" #"+cID);
		this.setLayout(new BorderLayout());
		JLabel title=new JLabel("Editing "+c.getClass().getName());
		title.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(title, BorderLayout.NORTH);
		this.add(inner, BorderLayout.CENTER);
		inner.setBorder(new EmptyBorder(10, 5, 10, 5) );
		JButton accept= new JButton("Accept");
		accept.addActionListener(this);
		this.add(accept, BorderLayout.SOUTH);
		inner.setLayout(new GridLayout(2,c.getEditableFields().size()+1));
		inner.add(new JLabel("Component number: "));
		inner.add(new JLabel(""+cID));
		for(String key:c.getEditableFields().keySet()){
			inner.add(new JLabel(key));
			NumberField n=new NumberField();
			n.addKeyListener(this);
			n.setText(""+c.getEditableFields().get(key));
			map.put(n, key);
			inner.add(n);
		}
		this.pack();
		this.setVisible(true);
	}

	

	public void addCommandListener(CommandListener l){
		cmdListeners.add(l);
	}
	private void fireCommand(Command c){
		for(CommandListener l: cmdListeners){
			l.performCommand(c);
		}
	}
	private void updateAndDispose(){
		HashMap<String, Double> fields= new HashMap<String, Double>();
		for(NumberField n:map.keySet()){
			fields.put(map.get(n), n.getValue());
		}
		this.fireCommand(new Commands.EditComponent(c,fields));
		this.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		updateAndDispose();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			updateAndDispose();
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
