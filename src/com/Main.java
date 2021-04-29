package com;

import com.al.AnalyseurLexical;
import com.as.AnalyseurSyntaxique;
import com.exception.SyntaxeException;
import com.generation.InstruCible;
import com.interpreteur2.Interpreter;

//import com.interpreteur.*;

import com.interpreteur2.*;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
        //p =new textfield.getText()
        	AnalyseurLexical al = new AnalyseurLexical("src/Texte.txt");
            String cond = "";
            do{
                cond = al.uniteSuivante();
           //     System.out.println("<" + AnalyseurLexical.UNITECOURANT + " , " + cond + ">");
            }while (!cond.equals(AnalyseurLexical.ENDFILE));
            

            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique();
           // analyseurSyntaxique.methode_variable();
        //    System.out.println("\nuuuu)");
          //  new Interpreter("src/inst");
       
            analyseurSyntaxique.runAnalyseSyntaxique();
         //   System.out.println(analyseurSyntaxique.s);
       //   analyseurSyntaxique.vicc();
         //   System.out.println(analyseurSyntaxique.vic.get(0));
         //   System.out.println(analyseurSyntaxique.vic.get(1));
           // System.out.println(analyseurSyntaxique.vic.get(2));
          //  System.out.println(analyseurSyntaxique.vic.get(3));
         new Interpreter("src/com/VIC.txt");
           // analyseurSyntaxique.affichevic();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
