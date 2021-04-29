package com.interpreteur2;

import java.awt.Color;
import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.interfaces.Interface;
public class Frame2 extends JFrame {
	TextArea t= new TextArea();
	JTextField notre_champ = new JTextField("bonjout");
	
	public Frame2(String text) {
		
		t.setForeground(Color.WHITE);
		t.setBackground(Color.BLACK);
		t.append(text);
		this.setTitle("Compilateur");
		this.setSize(800, 400);
		this.add(t);
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	
	
public void Frame5(String text, JTextField AAA) {
		
		t.setForeground(Color.WHITE);
		t.setBackground(Color.BLACK);
		t.append(text);
		this.setTitle("interpreteur");
		this.setSize(800, 400);
		this.add(t);
		t.setBounds(2, 3, 100, 100);
		this.add(AAA);
		AAA.setBackground(Color.red);
		AAA.setForeground(Color.red);
		AAA.setBounds(100, 100, 45,25);
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void  ajoutelabel (String a)
	{ 
		/*notre_champ.setForeground(Color.RED);
	   notre_champ.setBackground(Color.RED);
		this.add( notre_champ);
		*/
		JTextField notre_champ = new JTextField("bonjout");
		this.Frame5(a,notre_champ);
		
	}
	
	
	
	
	public  void ajoutetexte(String text) {
		t.append(text);
	}
}