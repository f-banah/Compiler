package com.interpreteur;
import java.io.FileNotFoundException;
import java.util.Scanner;
import  com.*;
import com.as.AnalyseurSyntaxique;
import com.exception.SemantiqueExecption;
import com.exception.SyntaxeException;
import com.al.AnalyseurLexical;
import com.as.AnalyseurSyntaxique;
import com.exception.SyntaxeException;
import com.generation.InstruCible;

import java.io.FileNotFoundException;
public class Main  {
	
	
	public static void main(String[] args) throws FileNotFoundException, SyntaxeException, SemantiqueExecption {
		
	/*AnalyseurSyntaxique p = new AnalyseurSyntaxique();
	System.out.println(p);
	p.instruction();
    //AnalyseurSyntaxique.runAnalyseSyntaxique();
     * */
     
		
		
	 new Interpreter("src/com/VIC.txt");
	
	
	}

}
