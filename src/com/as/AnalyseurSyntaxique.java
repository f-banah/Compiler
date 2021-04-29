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
import com.interfaces.*;


public class AnalyseurSyntaxique {
    static String uniteCourante;
     static AnalyseurLexical analyseurLexical;
     static ArrayList <Symbole> tableSymbole;
    public static int  nbrInstructionCible;
    public static String adresse="0"; 
    static ArrayList<Variable>tabledesident=new ArrayList();
    // DECLARATION DE LA GENERATION 
    public static ArrayList<InstruCible> vic=new ArrayList();
    public  static ArrayList<Symbole> table_var=new ArrayList();
    //L'etiquete c'est le nombre
      static ArrayList<Integer> table_etiq = new ArrayList();
    
    public AnalyseurSyntaxique() throws FileNotFoundException {
      //  analyseurLexical = new AnalyseurLexical("src/com/interfaces/texte.txt");
       analyseurLexical = new AnalyseurLexical("src/com/Texte.txt");
    }
    

     boolean isOperateur(){
        //uniteCourante = analyseurLexical.uniteSuivante();
        return UniteLexicale.plus.equals(uniteCourante)
                || UniteLexicale.moins.equals(uniteCourante)
                || UniteLexicale.prod.equals(uniteCourante)
                || UniteLexicale.div.equals(uniteCourante);
    }
    

    
    
    public  boolean isOperande() throws SemantiqueExecption{
        //uniteCourante = analyseurLexical.uniteSuivante();
        if (UniteLexicale.ident.equals(uniteCourante))
        {    if(contains(analyseurLexical.UNITECOURANT)!=null)
          {    
        	genererInstr("load",(tabledesident.get(((int)contains(analyseurLexical.UNITECOURANT)))).getAdresse() );
          }
        	return variableMustBeDeclared(AnalyseurLexical.UNITECOURANT);
        
        }
        else 	if( UniteLexicale.chaine.equals(uniteCourante) || UniteLexicale.entier.equals(uniteCourante) || UniteLexicale.reel.equals(uniteCourante) )
        	{genererInstr("loadc",AnalyseurLexical.UNITECOURANT);
        	return true;
        	}
      
        	 return false ;
      
    
}
    
    public  boolean isOperande(int v) throws SemantiqueExecption{
    	
    	  if (UniteLexicale.ident.equals(uniteCourante))
              return variableMustBeDeclared(AnalyseurLexical.UNITECOURANT);
          return UniteLexicale.entier.equals(uniteCourante)
                  || UniteLexicale.reel.equals(uniteCourante)
                  || UniteLexicale.chaine.equals(uniteCourante);
    
}
    
    
  
    

    
   
        
   String ll="";
    boolean isExpressionArithemetique() throws SyntaxeException, SemantiqueExecption{
        if(  isOperande() && isOperandeTypeExpressArithOk(uniteCourante)){
            uniteCourante = analyseurLexical.uniteSuivante(); 
            if(isOperateur()){
            //	System.out.println(" \n mmmm \n"+ analyseurLexical.UNITECOURANT);
            	//System.out.println("mpmpooo"+analyseurLexical.UNITECOURANT);
            	ll=uniteCourante;
                uniteCourante = analyseurLexical.uniteSuivante();
             //   System.out.println(" \n mmmm \n"+ analyseurLexical.UNITECOURANT);
                if(isOperande() && isOperandeTypeExpressArithOk(uniteCourante)){
                	genererInstr(ll,"");
                    uniteCourante = analyseurLexical.uniteSuivante();
       
                  //  System.out.println(" \n mmmm \n"+ analyseurLexical.UNITECOURANT);
                    while (isOperateur()){
                    	ll=uniteCourante;
                        uniteCourante = analyseurLexical.uniteSuivante();  
                        if(!isOperande() || !isOperandeTypeExpressArithOk(uniteCourante))
                            throw new SyntaxeException("Expression non valide");
                        genererInstr(ll,"");
                      
                        uniteCourante = analyseurLexical.uniteSuivante();
                    }
                    return true;
                }
                throw new SyntaxeException("Expression non valide");
            }
            else{
                if(uniteCourante.equals(UniteLexicale.finLigne))
                    return true;
                else if(uniteCourante.equals(UniteLexicale.parentFerme))
                    return true;
                else if(isOperateurConditionnel())
                    return true;
                else if(uniteCourante.equals(UniteLexicale.accoladeOuv))
                    return true;
                else if (uniteCourante.equals(UniteLexicale.et))
                    return true ;
                else if (uniteCourante.equals(UniteLexicale.ou))
                    return true ;
                else if (uniteCourante.equals(UniteLexicale.diff))
                    return true ;
                throw new SyntaxeException("Expression non valide");
            }
        }
        throw new SyntaxeException("Expression non valide");
    }

    //verifie si le type de l'identifiant est le meme que  le type de l'operateur
   void verifieTypeForITOEAA(String typeIdent) throws SemantiqueExecption{
        String type = null;
        if(uniteCourante.equals(UniteLexicale.ident))
            type = trouverTypeident(AnalyseurLexical.UNITECOURANT);
        if(type == null){
            if(!Objects.equals(getCorrespondance(typeIdent), uniteCourante)) throw new SemantiqueExecption("type erronÃ© !!");
        }
        else if(!typeIdent.equals(type)) throw new SemantiqueExecption("type erronÃ© !!");
    }

    
    String mp="";
     boolean isTermeOrIsExpressionArithmetiqueInAffectation(String typeIdent) throws SyntaxeException, SemantiqueExecption{
        if(isOperande()){
            verifieTypeForITOEAA(typeIdent);
          // System.out.println("ddddddd   "+analyseurLexical.UNITECOURANT);
            uniteCourante = analyseurLexical.uniteSuivante();
         //   mp=analyseurLexical.UNITECOURANT; 
            if(isOperateur()){
            	mp=uniteCourante;
            //	System.out.println(mp);
                uniteCourante = analyseurLexical.uniteSuivante();
             if(isOperande()){
                    verifieTypeForITOEAA(typeIdent);
                    uniteCourante = analyseurLexical.uniteSuivante();
                    genererInstr(mp,"");  
                    while (isOperateur()){
                    	mp=uniteCourante;
                        uniteCourante = analyseurLexical.uniteSuivante();
                        if(isOperande()) {
                            verifieTypeForITOEAA(typeIdent);
                            genererInstr(mp,""); 
                        }else throw new SyntaxeException("Expression non valide");
                        uniteCourante = analyseurLexical.uniteSuivante();
                    }
                    
                    return true;
                }
                throw new SyntaxeException("Expression non valide");
            }
            else{
                if(uniteCourante.equals(UniteLexicale.finLigne)) {
                    return true;
                }
                throw new SyntaxeException("Expression non valide");
            }
        }
        throw new SyntaxeException("Expression non valide");
    }

 boolean isTerme() throws SemantiqueExecption{
        //uniteCourante = analyseurLexical.uniteSuivante();
        if (UniteLexicale.ident.equals(uniteCourante))
            return variableMustBeDeclared(AnalyseurLexical.UNITECOURANT);
        return  UniteLexicale.entier.equals(uniteCourante)
                || UniteLexicale.reel.equals(uniteCourante)
                || UniteLexicale.chaine.equals(uniteCourante);
    }
    boolean isTypeVariable() throws  SyntaxeException{
        //uniteCourante = analyseurLexical.uniteSuivante();
        return UniteLexicale.integer.equals(uniteCourante)
                || UniteLexicale.real.equals(uniteCourante)
                || UniteLexicale.bool.equals(uniteCourante)
                || UniteLexicale.string.equals(uniteCourante);
    }
    
    public Object contains (String lexeme){
  	  int j=0;
        for(Variable descVariable: this.tabledesident )
        {
            if(descVariable.getIdent().equals(lexeme))
            {
                return j;
            }
            j++;
        }
        return null;
    }
    
    public void  methode_variable()
    {   
  	  for(Variable descVariable: this.tabledesident)
        {
           
  	  //System.out.println(descVariable);
       }
  	 }
    
    boolean declarationVariable() throws SyntaxeException, SemantiqueExecption {
    	 String k="";
    	 Object t;
        if(isTypeVariable()){
            String typeVariable = uniteCourante;
            uniteCourante = analyseurLexical.uniteSuivante();
            if(uniteCourante.equals(UniteLexicale.ident)){
                isVariableAlreadyDeclaree(AnalyseurLexical.UNITECOURANT);
                Symbole s = new Symbole();
                s.setIdentifant(AnalyseurLexical.UNITECOURANT);
               s.setType(typeVariable);
                 s.setAdresse(this.adresse);
                 //System.out.println(AnalyseurLexical.UNITECOURANT);
                // tring type_,String ident_,String adresse_,String valeur_
                if( contains(AnalyseurLexical.UNITECOURANT)==null)
                { tabledesident.add(new Variable(typeVariable,AnalyseurLexical.UNITECOURANT,this.adresse,"",""+false));
                   //methode_variable();
                k=AnalyseurLexical.UNITECOURANT;
                 this.adresse+=1;
                }
                tableSymbole.add(s);
                uniteCourante = analyseurLexical.uniteSuivante();
              if(uniteCourante.equals(UniteLexicale.affec))
                { //  System.out.println("nnn"+uniteCourante);  
                    uniteCourante = analyseurLexical.uniteSuivante();
                  //  System.out.println("pp"+analyseurLexical.UNITECOURANT);
                     t=(int)contains(k);
                    // genererInstr("store",(tabledesident.get((int)t)).getAdresse());
                     genererInstr("loadc",analyseurLexical.UNITECOURANT);
                     genererInstr("store",(tabledesident.get(((int)contains(k)))).getAdresse());
                    tabledesident.get((int)t).modifiervaleur(analyseurLexical.UNITECOURANT);
                    
                    if(!isTerme())
                        throw new SyntaxeException("Declaration de variable non valide: " +
                            "une '=' est suivie obligatoirement par: (ident | entier | reel | chaine)");
                    isTypeOk(s.getIdentifant(), uniteCourante);
                    uniteCourante = analyseurLexical.uniteSuivante();
                }
                if(uniteCourante.equals(UniteLexicale.finLigne))
                    return true;
                while(uniteCourante.equals(UniteLexicale.virgule)){
                    uniteCourante = analyseurLexical.uniteSuivante();
                    if(!uniteCourante.equals(UniteLexicale.ident))
                        throw new SyntaxeException("Declaration de variable non" +
                                " valide: une virgule est suivie" +
                                "obligatoirement par un identifiant");
                    s = new Symbole();
                    s.setIdentifant(AnalyseurLexical.UNITECOURANT);
                    s.setType(typeVariable);
                    tableSymbole.add(s);
                    if( contains(AnalyseurLexical.UNITECOURANT)==null)
                    { tabledesident.add(new Variable(typeVariable,AnalyseurLexical.UNITECOURANT,this.adresse,"",""+false));
                       //methode_variable();
                    k=AnalyseurLexical.UNITECOURANT;
                     this.adresse+=1;
                    }
                    uniteCourante = analyseurLexical.uniteSuivante();
                    if(uniteCourante.equals(UniteLexicale.affec))
                    { 
                        uniteCourante = analyseurLexical.uniteSuivante();
                        t=(int)contains(k);
                       // System.out.println("vvv"+analyseurLexical.UNITECOURANT);
                       genererInstr("loadc",analyseurLexical.UNITECOURANT);
                        genererInstr("store",(tabledesident.get(((int)contains(k)))).getAdresse());
                      tabledesident.get((int)t).modifiervaleur(analyseurLexical.UNITECOURANT);
                        if(!isTerme())
                            throw new SyntaxeException("Declaration de variable non valide: " +
                                    "une '=' est suivie obligatoirement par: (ident | entier | reel | chaine)");
                        isTypeOk(s.getIdentifant(),uniteCourante);
                        uniteCourante = analyseurLexical.uniteSuivante();
                    }
                    else if(uniteCourante.equals(UniteLexicale.finLigne))
                    {
                        return true;
                    }
                }
                if(uniteCourante.equals(UniteLexicale.finLigne))
                {
                    return true;
                }
                throw new SyntaxeException("Declaration de variable non valide: ';' manquant");
            }
            throw new SyntaxeException("Declaration de variable non valide");
        }
        throw new SyntaxeException("Declaration de variable non valide");
   }
    
     boolean declarationConstante() throws SyntaxeException , SemantiqueExecption{
         String k="";
         Object t;
    	if (uniteCourante.equals(UniteLexicale.constante)) {
            uniteCourante = analyseurLexical.uniteSuivante();
            if (isTypeVariable()) {
                String typeVariable = uniteCourante;
                uniteCourante = analyseurLexical.uniteSuivante();
                if (uniteCourante.equals(UniteLexicale.ident)) {
                    isVariableAlreadyDeclaree(AnalyseurLexical.UNITECOURANT);
                    Symbole s = new Symbole();
                    s.setConstante(true);
                    s.setIdentifant(AnalyseurLexical.UNITECOURANT);
                    s.setType(typeVariable);
                    tableSymbole.add(s);
                    if( contains(AnalyseurLexical.UNITECOURANT)==null)
                    { tabledesident.add(new Variable(typeVariable,AnalyseurLexical.UNITECOURANT,this.adresse,"","true"));
                       //methode_variable();
                    k=AnalyseurLexical.UNITECOURANT;
                     this.adresse+=1;
                    }
                    uniteCourante = analyseurLexical.uniteSuivante();
                    if(!uniteCourante.equals(UniteLexicale.affec))
                    throw new SyntaxeException("Declaration de constante non valide: \" +\n" +"une constante doitetre suivie d'une valeur initiale");
                    
                    if (uniteCourante.equals(UniteLexicale.affec)) {
                        uniteCourante = analyseurLexical.uniteSuivante();
                        t=(int)contains(k);
                       // System.out.println(""+analyseurLexical.UNITECOURANT);
                        tabledesident.get((int)t).modifiervaleur(analyseurLexical.UNITECOURANT);
                     tabledesident.get((int)t).setbolean("true");
                     
                        if (!isTerme())
                            throw new SyntaxeException("Declaration de constante non valide: \" +\n" +
                                    "\"une '=' est suivie obligatoirement par: (ident | entier | reel | chaine)");
                        isTypeOk(s.getIdentifant(),uniteCourante);
                        uniteCourante = analyseurLexical.uniteSuivante();
                    }
                    if (uniteCourante.equals(UniteLexicale.finLigne)) {
                        return true;
                    }
                    throw new SyntaxeException("Declaration de constante non valide: ';' est attendu");
                }
                throw new SyntaxeException("Declaration de constante non valide: 'ident' est attendu");
            }
            throw new SyntaxeException("Declaration de constante non valide: 'type de constante' est attendu");
        }
        throw new SyntaxeException("Declaration de constante non valide: 'constante' est attendu");

                    /*
                    while (uniteCourante.equals(UniteLexicale.virgule)) {
                        uniteCourante = analyseurLexical.uniteSuivante();
                        if (!uniteCourante.equals(UniteLexicale.ident)) {
                            throw new SyntaxeException("Declaration de constante non valide: 'ident' est required");
                        }
                        uniteCourante = analyseurLexical.uniteSuivante();
                        if (!uniteCourante.equals(UniteLexicale.affec)) {
                            throw new SyntaxeException("Declaration de constante  non valide: '=' est required");
                        }
                        uniteCourante = analyseurLexical.uniteSuivante();
                        if (!isTerme()) {
                            throw new SyntaxeException("Declaration de constante non valide: " +
                                    "une '=' est suivie obligatoirement par: (ident | entier | reel | chaine)");
                        }
                        uniteCourante = analyseurLexical.uniteSuivante();
                        if (uniteCourante.equals(UniteLexicale.finLigne)) {
                            return true;
                        }
                    }
                    return true ;*/
    }
    /*
     else if(uniteCourante == CategorieLexical.IDENT)
        {​​​​​​​
            lexeme = AnalyseurLexical.uniteLexical;
            descVariable = contains(lexeme, tableDesSymboles);
            if(descVariable.ident == null)
                throw new GrammaireException("la variable: \'" + lexeme  +  "\' doit être declarer. Ligne: " + AnalyseurLexical.lineNumber);
            if(descVariable.nature != null)
                throw new GrammaireException("la variable: \'" + lexeme  +  "\' est une constante, affectation non possible. Ligne: " + AnalyseurLexical.lineNumber);
            uniteCourante = AnalyseurLexical.uniteSuivante();
            if(!reconnaitreTerminal(CategorieLexical.AFFECT))
                throw new GrammaireException("instruction d'affectation non valide . Ligne: " + AnalyseurLexical.lineNumber);
            uniteCourante = AnalyseurLexical.uniteSuivante();
            if(exprA())         {​​​​​​​
                System.out.println(uniteCourante);
                if(!reconnaitreTerminal(CategorieLexical.ENDLINE))
                    throw new GrammaireException("instruction d'affectation non valide . Ligne: " + AnalyseurLexical.lineNumber);
                descVariable.valeur = "0";
                genererInstr("store", descVariable.adresse );
            }​​​​​​​
            else throw new GrammaireException("instruction d'affectation non valide . Ligne: " + AnalyseurLexical.lineNumber);
        }​​​​​​​
        else if (uniteCourante == CategorieLexical.CMTLIGNE || uniteCourante == CategorieLexical.CMLMULTILIGNE){​​​​​​​
        }​​​​​​​
        else
        {​​​​​​​
            System.out.println(uniteCourante);
            throw new GrammaireException("erreur synthaxique a la ligne: " + AnalyseurLexical.lineNumber);
        }​​​​​​​}
    */
     boolean affectation () throws SyntaxeException,SemantiqueExecption {
        if(uniteCourante.equals(UniteLexicale.ident)){
            variableMustBeDeclared(AnalyseurLexical.UNITECOURANT);
            isConstanteAndWantToDoAffect(AnalyseurLexical.UNITECOURANT);
            String type = trouverTypeident(AnalyseurLexical.UNITECOURANT);
            String l = AnalyseurLexical.UNITECOURANT;
            uniteCourante = analyseurLexical.uniteSuivante();
            if (!uniteCourante.equals(UniteLexicale.affec)){
                throw new SyntaxeException("Affectation non valide");
            }
            
            uniteCourante = analyseurLexical.uniteSuivante();
          //  System.out.println("ccccccc"+uniteCourante);
            
            if(!isTermeOrIsExpressionArithmetiqueInAffectation(type))
                throw new SyntaxeException("Affectation non valide");
            
            if(uniteCourante.equals(UniteLexicale.finLigne))
            { //tabledesident.get(((int)contains(type)))).getAdresse()
            	//System.out.print("opiun"+l);
            	genererInstr("store",(tabledesident.get(((int)contains(l)))).getAdresse());
            	//genererInstr("load", );
            return true;
            
            	
            }
            throw new SyntaxeException("Affectation non valide");
        }
        throw new SyntaxeException("Affectation non valide");
    }

     boolean isOperateurConditionnel(){
        return uniteCourante.equals(UniteLexicale.inf)
                || uniteCourante.equals(UniteLexicale.sup)
                || uniteCourante.equals(UniteLexicale.infEgal)
                || uniteCourante.equals(UniteLexicale.supEgal)
                || uniteCourante.equals(UniteLexicale.egal)
                || uniteCourante.equals(UniteLexicale.diff);
    }

    boolean isBoolean(){
        return uniteCourante.equals(UniteLexicale.vrai)
                || uniteCourante.equals(UniteLexicale.faux);
    }

    /*		
     
       private boolean contition() throws GrammaireException {
        CategorieLexical opL, opL1;
        if(!exprA())
            throw new GrammaireException("condition non valide . Ligne: " + AnalyseurLexical.lineNumber);
        if(opC())
        {
            opL1 = uniteCourante;
            uniteCourante = AnalyseurLexical.uniteSuivante();
        }
        else throw new GrammaireException("condition non valide . Ligne: " + AnalyseurLexical.lineNumber);
        if(!exprA())
            throw new GrammaireException("condition non valide . Ligne: " + AnalyseurLexical.lineNumber);
        genererInstr(opCToString(opL1), "");
        if(opL()){
            opL = uniteCourante;
            uniteCourante = AnalyseurLexical.uniteSuivante();
            contition();
            genererInstr(opLToString(opL), "");
        }
        return true;
    }
    
      private boolean contition() throws GrammaireException {
        CategorieLexical opL, opL1;
        if(!exprA())
            throw new GrammaireException("condition non valide . Ligne: " + AnalyseurLexical.lineNumber);
        if(opC())
        {
            opL1 = uniteCourante;
            uniteCourante = AnalyseurLexical.uniteSuivante();
        }
        else throw new GrammaireException("condition non valide . Ligne: " + AnalyseurLexical.lineNumber);
        if(!exprA())
            throw new GrammaireException("condition non valide . Ligne: " + AnalyseurLexical.lineNumber);
        genererInstr(opCToString(opL1), "");
        if(opL()){
            opL = uniteCourante;
            uniteCourante = AnalyseurLexical.uniteSuivante();
            contition();
            genererInstr(opLToString(opL), "");
        }   return true;
    }
     */
    
    public void  affichevic()
    {
    	for(InstruCible i : vic	)
    	{
    		System.out.println(i);
    	}
    }
    
   boolean isCondition() throws SyntaxeException,SemantiqueExecption{
        
    	// verfies la grammaire d'expression arith et de terme
    	String opl;   
    	String op;
        isExpressionArithemetique();
      //  System.out.println("ppp" +analyseurLexical.UNITECOURANT);
        if(!isOperateurConditionnel())
            throw new SyntaxeException("Expression conditionnelle non valide");
        opl=uniteCourante;
      //  System.out.println("ddd"+opl);
     /****/   
        uniteCourante = analyseurLexical.uniteSuivante();
        isExpressionArithemetique();
         genererInstr(""+opl, "");
        while (uniteCourante.equals(UniteLexicale.ou)
                || uniteCourante.equals(UniteLexicale.et)
                || uniteCourante.equals(UniteLexicale.diff)
        ){  op=uniteCourante;
            uniteCourante = analyseurLexical.uniteSuivante();
          //  System.out.println("ddd");
            isExpressionArithemetique();
            opl=uniteCourante;
            if(!isOperateurConditionnel())
                throw new SyntaxeException("Expression conditionnelle non valide");
            opl=uniteCourante;
            uniteCourante = analyseurLexical.uniteSuivante();
            isExpressionArithemetique();
            genererInstr(""+opl,"");
            genererInstr(""+op,"");     
        }
        return true;
    }
   
    boolean isConcatenation() throws SyntaxeException, SemantiqueExecption{
        if(!uniteCourante.equals(UniteLexicale.chaine))
            throw new SyntaxeException("Concatenation non valide.");
      
        uniteCourante = analyseurLexical.uniteSuivante();
        if(!uniteCourante.equals(UniteLexicale.plus))
            throw new SyntaxeException("Concatenation non valide.");
        
        uniteCourante = analyseurLexical.uniteSuivante();
        if(!uniteCourante.equals(UniteLexicale.chaine))
            throw new SyntaxeException("Concatenation non valide.");
        uniteCourante = analyseurLexical.uniteSuivante();
        if(uniteCourante.equals(UniteLexicale.finLigne))
            return true;
        throw new SyntaxeException("Concatenation non valide: ';' manquant.");
    }
    
    
    
    
    
   
  boolean isAlternative() throws SyntaxeException, SemantiqueExecption{
        if(!uniteCourante.equals(UniteLexicale.si))
            throw new SyntaxeException("Erreur de syntaxe");
        uniteCourante = analyseurLexical.uniteSuivante();
        isCondition();
        if(!uniteCourante.equals(UniteLexicale.accoladeOuv))
            throw new SyntaxeException("Erreur de syntaxe: '{' attendu");
        uniteCourante = analyseurLexical.uniteSuivante();
        while(!uniteCourante.equals(UniteLexicale.accoladeFerm)){
            instruction();
            uniteCourante = analyseurLexical.uniteSuivante();
        }
        /*if(!uniteCourante.equals(UniteLexicale.accoladeFerm))
            throw new SyntaxeException("Erreur de syntaxe: '}' attendu");*/

        return true;
    }

    boolean isRepetitive()  throws SyntaxeException, SemantiqueExecption{
        if (!uniteCourante.equals(UniteLexicale.tantQue))
            throw new SyntaxeException("Erreur de syntaxe");
        uniteCourante = analyseurLexical.uniteSuivante();
     //   System.out.println("ppp  \n "+analyseurLexical.UNITECOURANT);
        int temp=nbrInstructionCible;
        isCondition();
        if(!uniteCourante.equals(UniteLexicale.accoladeOuv))
            throw new SyntaxeException("Erreur de syntaxe: '{' attendu");
        genererInstr("jzero", "");
        uniteCourante = analyseurLexical.uniteSuivante();
        while(!uniteCourante.equals(UniteLexicale.accoladeFerm)){
            instruction();
            //System.out.println(uniteCourante);
            uniteCourante = analyseurLexical.uniteSuivante();
        }
        if(!uniteCourante.equals(UniteLexicale.accoladeFerm))
            throw new SyntaxeException("Erreur de syntaxe: '}' attendu");
            genererInstr("jump", "");
            this.table_etiq.add(nbrInstructionCible);
           this.table_etiq.add(temp);
           
        return true;
    }
    
    
    
    
    

    public void vicc()
    {
    	vic.add(new InstruCible("eeee","eccccc"));
    	vic.add(new InstruCible("eeepe","ecccmpcc"));
    	vic.add(new InstruCible("eevee","ecccvcc"));
    }
 
String lexeme="";






public void convertir()
{
     for (InstruCible cible : vic)
        {
        if(cible.Operateur.equals("plus"))
        {
            cible.Operateur="add";
        }
        if(cible.Operateur.equals("plus"))
        {
            cible.Operateur="add";
        }if(cible.Operateur.equals("moins"))
        {
            cible.Operateur="sub";
        }
        if(cible.Operateur.equals("prod"))
        {
            cible.Operateur="mul";
        }
        if(cible.Operateur=="div")
        {
            cible.Operateur="div";
        }                 //infEgal
        if(cible.Operateur.equals("sup"))
        {
            cible.Operateur="sup";
        }
        if(cible.Operateur.equals("inf"))
        {
            cible.Operateur="inf";
        }
        if(cible.Operateur.equals("egal"))
        {
            cible.Operateur="equal";
        }
        ///
        if(cible.Operateur.equals("infEgal"))
        {
            cible.Operateur="infe";
        }
        if(cible.Operateur.equals("supEgal"))
        {
            cible.Operateur="supe";
        }
        if(cible.Operateur.equals("mod"))
        {
            cible.Operateur="mod";
        }
        if(cible.Operateur.equals("et"))
        {
            cible.Operateur="and";
        }
        if(cible.Operateur.equals("ou"))
        {
            cible.Operateur="or";
        }
        if(cible.Operateur.equals("ou"))
        {
            cible.Operateur="or";
        }
       
       
        }

}


 boolean ecriture() throws SyntaxeException,SemantiqueExecption{
       
	  if(!uniteCourante.equals(UniteLexicale.ecrire))
            throw new SyntaxeException("Erreur de syntaxe: print");
        uniteCourante = analyseurLexical.uniteSuivante();
        if(!uniteCourante.equals(UniteLexicale.parentOuv))
            throw new SyntaxeException("Erreur de syntaxe: '(' attendu");
        uniteCourante = analyseurLexical.uniteSuivante();
        lexeme=analyseurLexical.UNITECOURANT;
       // System.out.println("hhh"+lexeme);
      // genererInstr("writec",lexeme);
        if(uniteCourante.equals(UniteLexicale.chaine)){
        	 uniteCourante = analyseurLexical.uniteSuivante();
            if(!uniteCourante.equals(UniteLexicale.parentFerme))
                throw new SyntaxeException("Erreur de syntaxe: ')' attendu");
            genererInstr("writec",lexeme);
           // System.out.println("bbb" +lexeme);
        }else if(isOperande(5)){
        	lexeme=analyseurLexical.UNITECOURANT;
        	//System.out.println("rrr"+analyseurLexical.UNITECOURANT);
        	tabledesident.add(new Variable("resultat","ntrevar",this.adresse,"",""+false));
           isExpressionArithemetique();
           genererInstr("store",(tabledesident.get(((int)contains("ntrevar")))).getAdresse());
            if(!uniteCourante.equals(UniteLexicale.parentFerme))
                throw new SyntaxeException("Erreur de syntaxe: ')' attendu");
            contains(lexeme);
            if(contains(lexeme)!=null)
            genererInstr("write",(tabledesident.get(((int)contains(lexeme)))).getAdresse());
            else
            	genererInstr("write",(tabledesident.get(((int)contains("ntrevar")))).getAdresse());
        }
        uniteCourante = analyseurLexical.uniteSuivante();
        if(!uniteCourante.equals(UniteLexicale.finLigne))
            throw new SyntaxeException("Erreur de syntaxe: ';' attendu");
        return true;
    }

   boolean lire() throws SyntaxeException {
        if(!uniteCourante.equals(UniteLexicale.lire))
            throw new SyntaxeException("Erreur de syntaxe: read");
        uniteCourante = analyseurLexical.uniteSuivante();
        if(!uniteCourante.equals(UniteLexicale.parentOuv))
            throw new SyntaxeException("Erreur de syntaxe: '(' attendu");
        uniteCourante = analyseurLexical.uniteSuivante();
        if(!uniteCourante.equals(UniteLexicale.ident))
            throw new SyntaxeException("Erreur de syntaxe: 'ident' attendu");
     //   lexeme=analyseurLexical.UNITECOURANT;
     //   System.out.println(" nn"+lexeme);
        lexeme=analyseurLexical.UNITECOURANT;
        uniteCourante = analyseurLexical.uniteSuivante();

        if(!uniteCourante.equals(UniteLexicale.parentFerme))
            throw new SyntaxeException("Erreur de syntaxe: ')' attendu");
        uniteCourante = analyseurLexical.uniteSuivante();
        
        if(!uniteCourante.equals(UniteLexicale.finLigne))
            throw new SyntaxeException("Erreur de syntaxe: ';' attendu");
	    genererInstr("read",""+(tabledesident.get(((int)contains(lexeme)))).getAdresse());
        return true;
    }
    public void instruction() throws SyntaxeException, SemantiqueExecption{

        if(isTypeVariable())
        {
            declarationVariable();
          
        }
        else if(uniteCourante.equals(UniteLexicale.constante)) { declarationConstante();    }

        else if(uniteCourante.equals(UniteLexicale.ecrire)) { ecriture(); }

        else if(uniteCourante.equals(UniteLexicale.lire)) { lire(); }

        else if(uniteCourante.equals(UniteLexicale.si)) { isAlternative();}

        else if(uniteCourante.equals(UniteLexicale.tantQue)) { isRepetitive(); }

        else if(uniteCourante.equals(UniteLexicale.sinon)) {
                uniteCourante = analyseurLexical.uniteSuivante();
                if(!uniteCourante.equals(UniteLexicale.accoladeOuv))
                    throw new SyntaxeException("Erreur de syntaxe: '{' attendu");
                uniteCourante = analyseurLexical.uniteSuivante();
                while(!uniteCourante.equals(UniteLexicale.accoladeFerm)){
                    instruction();
                    uniteCourante = analyseurLexical.uniteSuivante();
                }
            /*if(!uniteCourante.equals(UniteLexicale.accoladeFerm))
                throw new SyntaxeException("Erreur de syntaxe: '}' attendu");*/
        }

        else if(uniteCourante.equals(UniteLexicale.ident)) { affectation(); }

        else if (uniteCourante.equals(UniteLexicale.commentaire));
        else if(uniteCourante.equals(UniteLexicale.finProgram));
        else
        {
            throw new SyntaxeException("Erreur synthaxique au niveau de : " + AnalyseurLexical.UNITECOURANT +
                    "\nAstuce bien separer les elements par des espaces ");
        }
   //     this.methode_variable();
    }
 // grammaire de programme
    public void runAnalyseSyntaxique() throws SyntaxeException , SemantiqueExecption {
        tableSymbole = new ArrayList<>();
        uniteCourante = analyseurLexical.uniteSuivante();
        if (uniteCourante.equals(UniteLexicale.debutProgram)){
            uniteCourante = analyseurLexical.uniteSuivante();
           while (!uniteCourante.equals(UniteLexicale.finProgram )) {
                instruction();
                if(uniteCourante.equals(UniteLexicale.finProgram))
                    break;
                uniteCourante = analyseurLexical.uniteSuivante();
            }
         //apres l'analyse il faut remplacer les etiquettes par leurs adresse reelle
         int temp;
           int j = 0;
           for(int i = 0; i < vic.size(); i++)
           {
               if(vic.get(i).Operateur.equalsIgnoreCase("jzero") || vic.get(i).Operateur.equalsIgnoreCase("jump") )
               {  
                   temp = table_etiq.get(j) + 1;
                //   System.out.println("  kikiki"+temp);
                   vic.get(i).Operande = "" + temp;
                   j++;
               }
           } //
           
           
               this.convertir();
            //on enregistre le code cible dans un fichier
           String str = "";
           for (InstruCible cible : vic)
                str += cible.Operateur + " " + cible.Operande + "\n";
           str += "end\n";
           Ecriture.write("src/com/VIC.txt", str);
          vic.clear();

        }

        
    
    }
    

    
     boolean genererInstr(String oper, String operd){
        ++nbrInstructionCible;
         vic.add(new InstruCible(oper, operd));   
         return true;
                                                           }   

    boolean isTypeOk (String ident , String typeDeDonne) throws SemantiqueExecption{
        boolean isOk = false;
        Symbole tampon = new Symbole();
        for (Symbole s : tableSymbole){
            if(s.getIdentifant().equals(ident)){
                tampon = s;
                if(Objects.equals(getCorrespondance(s.getType()), typeDeDonne)){
                    isOk = true;
                }
                break;
            }
        }
        if(isOk)
            return true;
        //on cherche le type de b
        if(typeDeDonne.equals(UniteLexicale.ident)){
            for (Symbole s : tableSymbole){
                if(s.getIdentifant().equals(AnalyseurLexical.UNITECOURANT)){
                    if(!Objects.equals(getCorrespondance(s.getType()), getCorrespondance(tampon.getType())) ){
                        throw new SemantiqueExecption(" type erronÃ©!!");
                    }  else return true;
                }
            }
        }
        throw new SemantiqueExecption(" type erronÃ©!!");
    }

   boolean isVariableAlreadyDeclaree(String ident) throws SemantiqueExecption {
        for (Symbole s : tableSymbole) {
            if (s.getIdentifant().equals(ident))
                throw new SemantiqueExecption("variable  deja declarÃ©e !!");
        }
        return false;
    }

     boolean isOperandeTypeExpressArithOk( String operande ) throws SemantiqueExecption{
        if (operande.equals(UniteLexicale.chaine))
            throw new SemantiqueExecption("Operande ne respecte pas le bon type");
        return true;
    }

    boolean variableMustBeDeclared(String ident) throws SemantiqueExecption{
        for (Symbole s : tableSymbole){
            if (s.getIdentifant().equals(ident)){
                return true;
            }
        }
        throw new SemantiqueExecption("variable doit etre daclarÃ©e ");
    }

  String getCorrespondance (String mr){
        if(mr.equals(UniteLexicale.integer))
            return UniteLexicale.entier;
        if(mr.equals(UniteLexicale.real))
            return UniteLexicale.reel;
        if(mr.equals(UniteLexicale.string))
            return UniteLexicale.chaine;
        return null;
    }
   String trouverTypeident(String ident){
        for (Symbole s : tableSymbole) {
            if (s.getIdentifant().equals(ident))
                return s.getType();
        } 
        return null;
    }
  boolean isConstanteAndWantToDoAffect(String ident) throws SemantiqueExecption {
        for (Symbole s : tableSymbole) {
            if (s.getIdentifant().equals(ident)){
                if(s.isConstante())
             throw new SemantiqueExecption("On ne peut pas affecter une valeur a une constante!!");
            }
        }
        return true;
    }
}


/*
    
private void instruction() throws GrammaireException{​​​​​​​


        if(uniteCourante == CategorieLexical.INT || uniteCourante == CategorieLexical.FLOAT)
        {​​​​​​​
            type = uniteCourante;
            declVar();
            int i = 0;
            for(DescVariable temp : listIdents)
            {​​​​​​​
                i++;
                if(contains(temp.ident, tableDesSymboles) == null)
                {​​​​​​​
                    descVariable = new DescVariable(type+"", temp.ident, i+tableDesSymboles.size()+"",temp.valeur, null);
                    tableDesSymboles.add(descVariable);
                }​​​​​​​
                else{​​​​​​​
                  listIdents.clear();
                    throw new GrammaireException("variable déjà declaree . Ligne: " + AnalyseurLexical.lineNumber);
                }​​​​​​​
            }​​​​​​​
            listIdents.clear();
        }​​​​​​​
        else if(uniteCourante == CategorieLexical.CONSTANTE)
        {​​​​​​​
            declConst();
            int i = 0;
            for(DescVariable temp : listIdents)
            {​​​​​​​
                i++;
                if(contains(temp.ident, tableDesSymboles) == null)
                {​​​​​​​
                    descVariable = new DescVariable(type+"", temp.ident, i+tableDesSymboles.size()+"",temp.valeur, "const");
                    tableDesSymboles.add(descVariable);
                }​​​​​​​
                else{​​​​​​​
                    listIdents.clear();
                    throw new GrammaireException("constante deja declarée . Ligne: " + AnalyseurLexical.lineNumber);
                }​​​​​​​
            }​​​​​​​
            listIdents.clear();
        }
        

        ​​​​​​​
        else if (uniteCourante == CategorieLexical.CMTLIGNE || uniteCourante == CategorieLexical.CMLMULTILIGNE) {​​​​​​​}​​​​​​​
        else if(uniteCourante == CategorieLexical.SCAN)
        {​​​​​​​
            instrRead();
        }​​​​​​​
        else if((uniteCourante == CategorieLexical.PRINT))
        {​​​​​​​
            instWrite();
        }​​​​​​​
        else if(uniteCourante == CategorieLexical.SI)
        {​​​​​​​
            instructionIf();
        }​​​​​​​
        else if(uniteCourante == CategorieLexical.TANTQUE)
        {​​​​​​​
            instructionWhile();
        }​​​​​​​
        else if(uniteCourante == CategorieLexical.IDENT)
        {​​​​​​​
            lexeme = AnalyseurLexical.uniteLexical;
            descVariable = contains(lexeme, tableDesSymboles);
            if(descVariable.ident == null)
                throw new GrammaireException("la variable: \'" + lexeme  +  "\' doit être declarer. Ligne: " + AnalyseurLexical.lineNumber);
            if(descVariable.nature != null)
                throw new GrammaireException("la variable: \'" + lexeme  +  "\' est une constante, affectation non possible. Ligne: " + AnalyseurLexical.lineNumber);
            uniteCourante = AnalyseurLexical.uniteSuivante();
            if(!reconnaitreTerminal(CategorieLexical.AFFECT))
                throw new GrammaireException("instruction d'affectation non valide . Ligne: " + AnalyseurLexical.lineNumber);
            uniteCourante = AnalyseurLexical.uniteSuivante();
            if(exprA())
            {​​​​​​​
                System.out.println(uniteCourante);
                if(!reconnaitreTerminal(CategorieLexical.ENDLINE))
                    throw new GrammaireException("instruction d'affectation non valide . Ligne: " + AnalyseurLexical.lineNumber);
                descVariable.valeur = "0";
                genererInstr("store", descVariable.adresse );
            }​​​​​​​
            else throw new GrammaireException("instruction d'affectation non valide . Ligne: " + AnalyseurLexical.lineNumber);
        }​​​​​​​
        else if (uniteCourante == CategorieLexical.CMTLIGNE || uniteCourante == CategorieLexical.CMLMULTILIGNE){​​​​​​​
        }​​​​​​​
        else
        {​​​​​​​
            System.out.println(uniteCourante);
            throw new GrammaireException("erreur synthaxique a la ligne: " + AnalyseurLexical.lineNumber);
        }​​​​​​​
    }​​​​​​​















 */







