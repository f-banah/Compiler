package com.interpreteur2;
import java.awt.Frame;
import javax.swing.*;
import java.util.Scanner;
import  com.interfaces.*;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import com.interfaces.Interface;
import com.interfaces.*;

import java.io.*;
import java.awt.event.*;
import java.awt.*;

public class Interpreter_core {

	public String text;
	public Memory m = new Memory();
	public Stack s = new Stack();
	public VIC v = new VIC();
	String affiche = "";
	Frame2 interpreteur =new Frame2(affiche);
	
	
	
	
	public Interpreter_core(String text) throws Exception {
		super();
		this.text = text;
		this.initialize();
		
		this.execute();
		
	}

	public void initialize() {
		
		String[] lines = this.text.split("\n") ;
		
		for (	String line : lines	) {
			
			
			if (	line != "" 	) {
				
				
				String[] words = line.split(" ");
				
				String op = words[0];
				
				String opd ="";
				
				if (words.length == 2) { opd = words[1]; }
				
				
				
				if (	op.equalsIgnoreCase("load") 	){ 	this.v.add_inst( new Instruction( Operation.LOAD   , opd ) 	); }
				
				else if (	op.equalsIgnoreCase("loadc")	){ 	this.v.add_inst( new Instruction( Operation.LOADC  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("store")	){ 	this.v.add_inst( new Instruction( Operation.STORE  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jzero")	){ 	this.v.add_inst( new Instruction( Operation.JZERO  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jnzero")	){ 	this.v.add_inst( new Instruction( Operation.JNZERO  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jumpg")	){ 	this.v.add_inst( new Instruction( Operation.JUMPG  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jumpng")	){ 	this.v.add_inst( new Instruction( Operation.JUMPNG  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jumpge")	){ 	this.v.add_inst( new Instruction( Operation.JUMPGE  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jumpnge")	){ 	this.v.add_inst( new Instruction( Operation.JUMPNGE  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jumpl")	){ 	this.v.add_inst( new Instruction( Operation.JUMPL  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jumpnl")	){ 	this.v.add_inst( new Instruction( Operation.JUMPNL  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jumple")	){ 	this.v.add_inst( new Instruction( Operation.JUMPLE  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jumpnle")	){ 	this.v.add_inst( new Instruction( Operation.JUMPNLE  , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("jump") 	){ 	this.v.add_inst( new Instruction( Operation.JUMP   , opd )  ); }
				
				else if ( 	op.equalsIgnoreCase("write")	){ 	this.v.add_inst( new Instruction( Operation.WRITE  , opd )  ); }
				
				else if ( 	op.equalsIgnoreCase("writec") ){ 	this.v.add_inst( new Instruction( Operation.WRITEC , opd ) 	); }
				
				else if ( 	op.equalsIgnoreCase("read") 	){	this.v.add_inst( new Instruction( Operation.READ   , opd ) 	); }
					
				else if ( 	op.equalsIgnoreCase("add")	){ 	this.v.add_inst( new Instruction( Operation.ADD  ) 	); }
				
				else if ( 	op.equalsIgnoreCase("sub") 	){ 	this.v.add_inst( new Instruction( Operation.SUB  ) 	); }
				
				else if ( 	op.equalsIgnoreCase("mul") 	){ 	this.v.add_inst( new Instruction( Operation.MUL  ) 	); }
				
				else if ( 	op.equalsIgnoreCase("div") 	){ 	this.v.add_inst( new Instruction( Operation.DIV 	)   ); }
				
				else if ( 	op.equalsIgnoreCase("and")	){ 	this.v.add_inst( new Instruction( Operation.AND  ) 	); }
				
				else if ( 	op.equalsIgnoreCase("or") 	){ 	this.v.add_inst( new Instruction( Operation.OR  ) 	); }
				
				else if ( 	op.equalsIgnoreCase("xor") 	){ 	this.v.add_inst( new Instruction( Operation.XOR  ) 	); }
				
				else if ( 	op.equalsIgnoreCase("not") 	){ 	this.v.add_inst( new Instruction( Operation.NOT 	)   ); }
				
				else if ( 	op.equalsIgnoreCase("equal") 	){ 	this.v.add_inst( new Instruction( Operation.EQUAL) 	); }
				
				else if ( 	op.equalsIgnoreCase("nequal") 	){ 	this.v.add_inst( new Instruction( Operation.NEQUAL) ); }
				
				else if ( 	op.equalsIgnoreCase("inf") 	){ 	this.v.add_inst( new Instruction( Operation.INF  ) 	); }
				
				else if ( 	op.equalsIgnoreCase("infe") 	){ 	this.v.add_inst( new Instruction( Operation.INFE ) 	); }
				
				else if ( 	op.equalsIgnoreCase("sup") 	){ 	this.v.add_inst( new Instruction( Operation.SUP  ) 	); }
				
				else if ( 	op.equalsIgnoreCase("supe") 	){ 	this.v.add_inst( new Instruction( Operation.SUPE ) 	); }
				
				else if ( 	op.equalsIgnoreCase("end") 	){ 	v.add_inst( new Instruction( Operation.END  ) 	); }
				
				else {	v.add_inst( new Instruction( Operation.ERROR  ) 	); }
				
				
				
			}
			
			

			
		}
		
		
		
	}
	
	public void execute() throws Exception {
		
		Instruction current_inst = null;
		
		int current_inst_index = 1;
		
		String val = null;
		Integer n1 = null;
		Integer n2 = null;
		String afficheur ="";
		
		
		if ( this.v.get(this.v.size()-1).op.equals(Operation.END) ) {
			
			while ( !this.v.get_inst(current_inst_index - 1).op.equals(Operation.END) 	) {
				
				current_inst = this.v.get(current_inst_index -1);
				current_inst_index ++ ;
				
				
				switch  ( current_inst.op ) {
				
				case LOAD 	:
								val = m.get_m(	Integer.valueOf( current_inst.opd	) 	);
								s.add_str(val);
								break;
				
				case LOADC 	:
								val = current_inst.opd;
								s.add_str(val);
								break;
					
				case STORE 	:
								m.add_m( Integer.valueOf( current_inst.opd ), s.pop_str() );
								break;
					
				case JZERO 	:
								Integer jzero = Integer.valueOf(s.pop_str());
								if (jzero == 0 ) {	current_inst_index = Integer.valueOf( current_inst.opd ) ;}
								break;
								
				case JNZERO 	:
					
								Integer jnzero = Integer.valueOf(s.pop_str());
								if (jnzero != 0 ) {	current_inst_index = Integer.valueOf( current_inst.opd ) ;}
								break;
								
				case JUMP 	:
								current_inst_index = Integer.valueOf( current_inst.opd );
								break;
								
				case JUMPG 	:
								Integer jumpg = Integer.valueOf(s.pop_str());
								if (jumpg > 0 ) {	current_inst_index = Integer.valueOf( current_inst.opd ) ;}
								break;
								
				case JUMPNG 	:
								Integer jumpng = Integer.valueOf(s.pop_str());
								if (!(jumpng > 0 )) {	current_inst_index = Integer.valueOf( current_inst.opd ) ;}
								break;
								
				case JUMPGE 	:
								Integer jumpge = Integer.valueOf(s.pop_str());
								if (jumpge >= 0 ) {	current_inst_index = Integer.valueOf( current_inst.opd ) ;}
								break;
								
				case JUMPNGE 	:
								Integer jumpnge = Integer.valueOf(s.pop_str());
								if (!(jumpnge >= 0) ) {	current_inst_index = Integer.valueOf( current_inst.opd ) ;}
								break;
								
				case JUMPL 	:
								Integer jumpl = Integer.valueOf(s.pop_str());
								if (jumpl < 0 ) {	current_inst_index = Integer.valueOf( current_inst.opd ) ;}
								break;
								
				case JUMPNL 	:
								Integer jumpnl = Integer.valueOf(s.pop_str());
								if (!(jumpnl < 0 )) {	current_inst_index = Integer.valueOf( current_inst.opd ) ;}
								break;
								
				case JUMPLE 	:
								Integer jumple = Integer.valueOf(s.pop_str());
								if (jumple <= 0 ) {	current_inst_index = Integer.valueOf( current_inst.opd ) ;}
								break;
								
				case JUMPNLE 	:
								Integer jumpnle = Integer.valueOf(s.pop_str());
								if (!(jumpnle <= 0 )) {	current_inst_index = Integer.valueOf( current_inst.opd ) ;}
								break;
								
				case WRITE 	:
								Integer adr = Integer.valueOf( current_inst.opd );
			                   
								
								System.out.println(	m.get_m(adr)	);
								affiche = "\n"+ m.get_m(adr);
								interpreteur.ajoutetexte(affiche);
			                    break;

					
				case WRITEC :
	                			System.out.println(	current_inst.opd );
	                			afficheur= ""+current_inst.opd + "\n" ;
	                			affiche = "\n"+ current_inst.opd;
								interpreteur.ajoutetexte(affiche);
	                			break;
					
				case READ 	:
								Scanner sc = new Scanner(System.in);
								Integer number;
								
								//interpreteur.ajoutelabel (affiche);
							    System.out.println("Please enter a Integer");
							    while (!sc.hasNextInt()) {
							        System.out.println("Please enter a valid Integer : ");
							        sc.next(); 
							    }
							    number = sc.nextInt();
							    System.out.println("----------------");
								m.add_m(Integer.valueOf( current_inst.opd ), number.toString());
								break;
					
				case ADD 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								Integer add = n1+n2;
								s.add_str(add.toString());
								break;
					
				case SUB 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								Integer sub = n2-n1;
								s.add_str(sub.toString());
								break;
		
				case MUL	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								Integer mul = n1*n2;
								s.add_str(mul.toString());
								break;
					
				case DIV 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								Integer div = n2/n1;
								s.add_str(div.toString());
								break;
								
				case AND 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								Integer and = n1&n2;
								s.add_str(and.toString());
								break;
					
				case OR 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								Integer or = n1|n2;
								s.add_str(or.toString());
								break;
			
				case XOR	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								Integer xor = n1^n2;
								s.add_str(xor.toString());
								break;
					
				case NOT 	:
								n1 = Integer.valueOf(s.pop_str());
								Integer not = ~n1;
								s.add_str(not.toString());
								break;

		
					
				case EQUAL 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								if ( n1 == n2 ) { s.add_str("1"); }
								else { s.add_str("0"); }
								break;
								
				case NEQUAL 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								if ( n1 != n2 ) { s.add_str("1"); }
								else { s.add_str("0"); }
								break;
								
				case INF 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								if ( n1 > n2 ) { s.add_str("1"); }
								else { s.add_str("0"); }
								break;
				case INFE 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								if ( n1 >= n2 ) { s.add_str("1"); }
								else { s.add_str("0"); }
								break;
				case SUP 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								if ( n1 < n2 ) { s.add_str("1"); }
								else { s.add_str("0"); }
								break;
				case SUPE 	:
								n1 = Integer.valueOf(s.pop_str());
								n2 = Integer.valueOf(s.pop_str());
								if ( n1 <= n2 ) { s.add_str("1"); }
								else { s.add_str("0"); }
								break;
				
				case ERROR 	:
					
								System.out.println("INVALID OPERATION NUMBER "+(current_inst_index-1));
								System.exit(0);
								break;
				
							
					
				}
				
			}
			
		}
		
		else {
			System.out.println("END OPERTION IS NEEDED IN THE END");
			System.exit(0);
		}
		

		
		
	}
	
	
	
	
}
