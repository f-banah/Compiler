package com.interfaces;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import com.interfaces.*;


import java.io.*;
import java.awt.event.*;
import java.awt.*;



//package de l'annalyseur syntaxique

import com.al.AnalyseurLexical;
import com.al.UniteLexicale;
import com.as.AnalyseurSyntaxique;
import com.as.Ecriture;
import com.asem.Symbole;
import com.exception.SemantiqueExecption;
import com.exception.SyntaxeException;

import java.io.FileNotFoundException;
import java.net.http.WebSocket.Listener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.*;
import com.generation.*;
import com.interpreteur2.Interpreter;





public class Interface extends JFrame  implements Listener{
	JPanel p=new JPanel();
	String text="";
	JLabel label;
	JTextArea textfield;
     JTextArea console = new JTextArea();
	public static  compiler d;
	 JMenuBar menuBar=new JMenuBar();
	JMenu menu1=new JMenu("Fichier");
	 JMenu menu2=new JMenu("Editer");
	 JMenu menu3=new JMenu("Compile");
	
	JMenu menu4=new JMenu("Run");
	 JMenu menu5=new JMenu("Mode");
    JMenuItem menuItem1=new JMenuItem("Nouveau");
	 JMenuItem menuItem2=new JMenuItem("Ouvrir");
	 JMenuItem menuItem3=new JMenuItem("Enregistrer");
	 JMenuItem menuItem4=new JMenuItem("Copier");
	 JMenuItem menuItem5=new JMenuItem("Couper");
	 JMenuItem menuItem6=new JMenuItem("Coller");
	 JMenuItem menuItem7=new JMenuItem("Sélectionner Tout");
	
	 JMenuItem menuItem8=new JMenuItem("compiler");
   JMenuItem menuItem9=new JMenuItem("run");
	

	
    
	
	
	
public Interface() {
	//ajout menu 
	setLayout(new FlowLayout());
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		menuBar.add(menu4);
		menuBar.add(menu5);

		menu1.add(menuItem1);
		setJMenuBar(menuBar);
		menu1.add(menuItem2);
		setJMenuBar(menuBar);
		menu1.add(menuItem3);
		setJMenuBar(menuBar);
		menu2.add(menuItem4);
		setJMenuBar(menuBar);
		menu2.add(menuItem5);
		setJMenuBar(menuBar);
		menu2.add(menuItem6);
		setJMenuBar(menuBar);
		menu2.add(menuItem7);
		setJMenuBar(menuBar);
		menu3.add(menuItem8);
		setJMenuBar(menuBar);
		menu4.add(menuItem9);
		setJMenuBar(menuBar);
		label = new JLabel("");
		add(label);
		
		textfield = new JTextArea(100,100);

		addMenuItem(menu5, "Dark", Color.BLACK);
	 	addMenuItem(menu5, "White", Color.WHITE);
	 	addMenuItem(menu5, "Blue", Color.BLUE);
	 	menu5.setMnemonic( 'M' );
		nouveau nv = new nouveau();
		menuItem1.setIcon( new ImageIcon( "src/com/interfaces/add.png" ) );
		menu1.setMnemonic( 'F' );
		menuItem2.setMnemonic( 'N' );
		menuItem1.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );
		menuItem1.addActionListener(nv);
		
		
		//\src\com\interfaces
		ovr v = new ovr();
		menuItem2.setIcon( new ImageIcon( "src/com/interfaces/icons.png" ) );
        menuItem2.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK) );
		menuItem2.addActionListener(v);
		
		enregistrer s = new enregistrer();
		menuItem3.setIcon( new ImageIcon( "src/com/interfaces/save.png" ) );
        menuItem3.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK) );
		menuItem3.addActionListener(s);
		
		copier cp = new copier();
		menuItem4.setIcon( new ImageIcon( "src/com/interfaces/copy.png" ) );
		menu2.setMnemonic('E');
        menuItem4.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK) );
		menuItem4.addActionListener(cp);
		
		couper coup = new couper();
		menuItem5.setIcon( new ImageIcon( "src/com/interfaces/cut.png" ) );
        menuItem5.setMnemonic( 'X' );
        menuItem5.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK) );
		menuItem5.addActionListener(coup);
		
		
		coller cl = new coller();
		menuItem6.setIcon( new ImageIcon( "src/com/interfaces/paste.png" ) );
        menuItem6.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK) );
		menuItem6.addActionListener(cl);
		
		select se = new select();
		menuItem7.setIcon( new ImageIcon( "src/com/interfaces/checked.png" ) );
        menuItem7.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK) );
		menuItem7.addActionListener(se);
		
	//	compiler comp=new compiler();
		menu3.setMnemonic( 'C' );
	    menuItem8.addActionListener(new compiler());
	    menu4.setMnemonic( 'R' );
	    
	    menuItem9.addActionListener(new run());
		
		label.setForeground(Color.DARK_GRAY);
		textfield.setBackground(Color.WHITE);
		
	setTitle("Compilateur");
	setSize(1200,1400);
	
	
	setVisible(true);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
 void addMenuItem(JMenu menu, String name, final Color c) {
	JMenuItem item = new JMenuItem(name);
	item.addActionListener(new ActionListener () {
		public void actionPerformed(ActionEvent event) {
		    // Changement de la couleur du fond
		    getContentPane().setBackground(c);
		    // Actualisation de la fenêtre
		    repaint();
		}
	    });
	// Ajout de l'entrée dans le menu
	menu.add(item);
    }	
	

	
	

	
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
  
public class nouveau implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		label.setText("Code Source");
		p.setLayout(new BorderLayout());
	     p.add(textfield,BorderLayout.CENTER);
	//	p.add(textfield);
	     console.setForeground(Color.BLACK);
		p.add(console,BorderLayout.PAGE_END);
		
		JScrollPane scrollableText = new JScrollPane(p);   
        scrollableText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scrollableText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
  
        setPreferredSize(new Dimension(50, 50));
        add(scrollableText,BorderLayout.CENTER);  
        
	}
}
 void ouvrir( File ov){
	textfield.setText("");
	BufferedReader IN = null;
	String ligne = null;
	try{
		IN = new BufferedReader(new FileReader(ov));
	    while((ligne=IN.readLine())!=null){
	    	textfield.append(ligne+"\n");
	    }
	    IN.close();
	}
	catch(Exception ex){
		textfield.setText(""+ex);
	}
}

public class ovr implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		JFileChooser o= new JFileChooser();
		o.setAcceptAllFileFilterUsed(true);
		o.setCurrentDirectory(new File("C:\\Bureau"));
	    int a =o.showOpenDialog(Interface.this);
		if (a ==JFileChooser.APPROVE_OPTION){
			ouvrir(o.getSelectedFile());
		}
	}

	

}
public class enregistrer implements ActionListener{
	
	 PrintWriter wrt;
	public void actionPerformed(ActionEvent e) {
		JFileChooser save= new JFileChooser();
		save.setAcceptAllFileFilterUsed(true);
		save.setCurrentDirectory(new File("C:\\Documents and Settings\\Administrateur\\Bureau"));
		int a=save.showSaveDialog(Interface.this);
		if ( a==save.APPROVE_OPTION){
			try{
				wrt=new PrintWriter(new FileWriter(save.getSelectedFile().getCanonicalPath()));
				wrt.println(textfield.getText());
				wrt.close();
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(null," Impossible d'enregistrer dans ce fichier  ! "," Message de d'Erreur ",JOptionPane.ERROR);
			}	
		}
		else if(a==1){
			//System.exit(0);
		}
	}
}
public class select implements ActionListener{
	public void actionPerformed(ActionEvent e) {
	textfield.selectAll();
	}
}
public class copier implements ActionListener{
	public void actionPerformed(ActionEvent e) {
	textfield.copy();
	}	
}

public class coller implements ActionListener{
	public void actionPerformed(ActionEvent e) {
	textfield.paste();
	}
}
public class couper implements ActionListener{
	public void actionPerformed(ActionEvent e) {
	textfield.cut();
	}
}

class Frame extends JFrame {
	
	public Frame(String text) {
		TextArea t= new TextArea();
		t.setForeground(Color.RED);
		//t.setBackground(Color.RED);
		t.append(text);
		this.setTitle("Compilation");
		this.setSize(800, 400);
		this.add(t);
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}









public static void rrr () throws SyntaxeException, SemantiqueExecption, FileNotFoundException
{
	AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique();
    analyseurSyntaxique.runAnalyseSyntaxique();
}


public class run implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			String A = textfield.getText();	
			Ecriture.write("src/com/Texte.txt", A);
	
			   try {
			           /*AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique();
			            analyseurSyntaxique.runAnalyseSyntaxique();
			            */
				  // new compiler();
				   Interface.rrr();
			          new Interpreter("src/com/VIC.txt");
			        } catch (Exception v) {
			        	System.out.println("ggggggggg");
			        	new Frame(v.getMessage());
			         
			        }

}
}


public class compiler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			String A = textfield.getText();	
			
			//src\com\interfaces\texte.txt
			//Ecriture.write("src/com/interfaces/texte.txt", A);
			Ecriture.write("src/com/Texte.txt", A);
	
			   try {
			      
			            /*AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique();
			          
			             analyseurSyntaxique.runAnalyseSyntaxique();	
			             */
				   Interface.rrr();
			        } catch (Exception r) {
			      
			        	System.out.println(r.getMessage());
			        	new Frame(r.getMessage());
			        
			        }

}
}

public static void main(String[] args) {
	
	
	Interface ig =new Interface();
    Image icon = Toolkit.getDefaultToolkit().getImage("src/com/interfaces/compiler.png");  
    ig.setIconImage(icon);  
	ig.setVisible(true);

}}

