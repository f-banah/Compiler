package com.as;

//package de l'annalyseur syntaxique

import com.al.AnalyseurLexical;
import com.al.UniteLexicale;
import com.asem.Symbole;
import com.exception.SemantiqueExecption;
import com.exception.SyntaxeException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.*;
import com.generation.*;

public class genererCode {
	
	public static List a =new ArrayList();
	public static  String k="";
	public static  List<String> c = new ArrayList();
	
	public static int nbrInstructionCible;
	  // DECLARATION DE LA GENERATION 
    private static ArrayList<InstruCible> vic=new ArrayList();
    public  static ArrayList<Variable> table_var=new ArrayList();
    //L'etiquete c'est le nombre
     private static ArrayList<Integer> TabEtiquete = new ArrayList();
	
	public void mamethode() throws FileNotFoundException
	{  
     AnalyseurLexical al = new AnalyseurLexical("src/Texte.txt");
        String cond = "";
        do{
            cond = al.uniteSuivante();
            //System.out.println("<" + AnalyseurLexical.UNITECOURANT + " , " + cond + ">");
           a.add(AnalyseurLexical.UNITECOURANT);
        }while (!cond.equals(AnalyseurLexical.ENDFILE));
 // System.out.println(a);
        int i=1;
       do { 
    	   if(a.get(i).equals(";"))
           { c.add(k);
         	 k="";}
        k= k+" "+a.get(i);
        i++;
	}while(i<a.size()-1);
  //     System.out.println(c.size());
       
       for(i=0;i<c.size();i++)
       {
    	   System.out.println("("+i+")"+c.get(i));
       }
   	{
   		
   	}
	}
	
	public static String var =""+0;
	
	private void instruction(String unitecourant) {
		
		if(unitecourant=="int" || unitecourant=="Float")
		{
			if(this.contains(unitecourant)==null)
			{
				
			}
		}
	}
	
	
	
  public void expression()
  { int y;
    for(y=1;y<a.size();y++)
    {	if(a.get(y).equals("int" ) )
    	{  if(this.contains((String)a.get(y+1))==null)
    	    {table_var.add(new Variable("entier",(String)a.get(y+1),var ,"0")); 
    	     var="0"+var+1;
    	    }
    	 if(a.get(y+2).equals(","))
    	 {
    		 if(this.contains((String)a.get(y+3))==null)
     	    {table_var.add(new Variable("entier",(String)a.get(y+3),var ,"0")); 
     	     var="0"+var+1;
     	    }
    	 }
    	}
    if(a.get(y).equals("string" ) )
	{  if(this.contains((String)a.get(y+1))==null)
	    {table_var.add(new Variable("chaine",(String)a.get(y+1),var ,"0")); 
	     var="0"+var+1;
	    }
	 if(a.get(y+2).equals(","))
	 {
		 if(this.contains((String)a.get(y+3))==null)
 	    {table_var.add(new Variable("chaine",(String)a.get(y+3),var ,"0")); 
 	     var="0"+var+1;
 	    }
		 
	 }
	 
	}
    	if(a.get(y).equals("="))
    	{  if(this.contains((String)a.get(y-1))!=null)
        	 {
    	   	table_var.get((int)this.contains((String)a.get(y-1))).modifiervaleur((String)a.get(y+1));
    	     }
    		
    	}
    	
    	if(a.get(y).equals("+"))
    	{  if(this.contains((String)a.get(y-1))!=null)
        	 {
    	   	table_var.get((int)this.contains((String)a.get(y-1))).modifiervaleur((String)a.get(y+1));
    	     }
    	}
    }  
  }
  public Object contains (String lexeme){
	  int i=0;
      for(Variable descVariable: table_var )
      {
          if(descVariable.getIdent().equals(lexeme))
          {
              return i;
          }
          i++;
      }
      return null;
  }
  public void  methode_variable()
  {   
	  for(Variable descVariable: table_var )
      {
         
	  System.out.println(descVariable);
     }
	  
  }
  public void  methode_variablevic()
  {   
	  for(InstruCible descVariable: vic)
      {
         
	  System.out.println(descVariable);
     }
  }
public static void main(String[] args) throws FileNotFoundException
{ genererCode p=new genererCode();
	p.mamethode();
	p.expression();
	p.methode_variable();
	p.generervexpr();
    //System.out.println(p.methode_variable());
	
}

  private boolean genererInstr(String oper, String operd){
      nbrInstructionCible++;
      return vic.add(new InstruCible(oper, operd));   
                                                         }
  private void generervexpr()
  {  int i;
     int j;
  for(i=1;i<a.size()-1;i++)
  {
	  if(a.get(i).equals("reel") || a.get(i).equals("int") || a.get(i).equals("string"))
		  {      
		  if(contains((String)a.get(i+1))!=null)
		    {genererInstr("load",""+table_var.get((int) contains((String)a.get(i+1))).adresse); 
		       //  return true;
		    }
		  }
	if(a.get(i).equals("="))
	{
		  if(contains((String)a.get(i-1))!=null)
		    {genererInstr("store",""+table_var.get((int) contains((String)a.get(i+1))).adresse); 
		       //  return true;
		    }
		
	}
	 /* if(a.get(i)=="int")
	  {  genererInstr()
		  
	  }
	  */
  }  
  this.methode_variablevic();
  }
   

  /*
  
  private boolean sexpr() throws GrammaireException{
      lexeme = AnalyseurLexical.uniteLexical;
      if(uniteCourante == CategorieLexical.REEL)
      {
          genererInstr("loadc", lexeme);
          return true;
      }
      if(uniteCourante == CategorieLexical.ENTIER)
      {
          genererInstr("loadc", lexeme);
          return true;
      }
      if(uniteCourante == CategorieLexical.IDENT)
      {
          //on verifie que la variable a déjà été déclarer
          descVariable = contains(lexeme, tableDesSymboles);
          if(descVariable != null)
          {
              if(descVariable.valeur == null)
                  throw new GrammaireException("la variable: \'" + lexeme  +  "\' doit être initialiser. Ligne: " + AnalyseurLexical.lineNumber);
              genererInstr("load",descVariable.adresse);
              return true;
          }
          throw new GrammaireException("la variable: \'" + lexeme  +  "\' doit être déclarer. Ligne: " + AnalyseurLexical.lineNumber);
      }
      return false;
  }
  
  */
  
  
  
  
  
  
  
  
  
  
}