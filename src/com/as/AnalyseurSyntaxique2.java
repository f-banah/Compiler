package com.as;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Objects;
import com.*;

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

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.*;
import com.al.*;
import com.as.*;
import com.asem.Symbole;
import com.generation.*;
public class AnalyseurSyntaxique2 {
    private static String uniteCourante;
    private static AnalyseurLexical analyseurLexical;
    private static ArrayList <Symbole> tableSymbole;
    private static String type;
    String a="";
    String lex;
    String b;
    
    private static ArrayList <Symbole> table_s=new ArrayList<>();
    private static ArrayList <InstruCible> vic=new ArrayList<>();
    int nbinst;
    private static ArrayList <Integer> table_etiq=new ArrayList<>();
    Symbole v;
    
    public AnalyseurSyntaxique2() throws FileNotFoundException {
        analyseurLexical = new AnalyseurLexical("src/com/Texte.txt");
    }
    private boolean isOperateur(){
        //uniteCourante = analyseurLexical.uniteSuivante();
        return UniteLexicale.plus.equals(uniteCourante)
                || UniteLexicale.moins.equals(uniteCourante)
                || UniteLexicale.prod.equals(uniteCourante)
                || UniteLexicale.div.equals(uniteCourante);
    }
    private boolean isOperande() throws SemantiqueExecption{
        lex=AnalyseurLexical.UNITECOURANT ;
    	
        if (UniteLexicale.ident.equals(uniteCourante))
        {   v=contains (uniteCourante, table_s);
        if(v!=null) {
        	generercode("load",v.adresse);
            return true;
        }
        	return variableMustBeDeclared(AnalyseurLexical.UNITECOURANT);
        }
        if (UniteLexicale.reel.equals(uniteCourante)) 
        {
        	generercode("LOADC",lex);
        	return true;
        	
        }
        if (UniteLexicale.entier.equals(uniteCourante)) 
        {
        	generercode("LOADC",lex);
        	return true;
        }
        return UniteLexicale.chaine.equals(uniteCourante);
    }
    private boolean isExpressionArithemetique() throws SyntaxeException, SemantiqueExecption{
        if(isOperande() && isOperandeTypeExpressArithOk(uniteCourante)){
            uniteCourante = analyseurLexical.uniteSuivante();
            if(isOperateur()){
                uniteCourante = analyseurLexical.uniteSuivante();
                if(isOperande() && isOperandeTypeExpressArithOk(uniteCourante)){
                    uniteCourante = analyseurLexical.uniteSuivante();
                    while (isOperateur()){
                    	if(UniteLexicale.plus.equals(uniteCourante)) generercode("ADD", "");
                    	if(UniteLexicale.moins.equals(uniteCourante)) generercode("SUB", "");
                    	if(UniteLexicale.prod.equals(uniteCourante)) generercode("MUL", "");
                    	if(UniteLexicale.div.equals(uniteCourante)) generercode("DIV", "");
                        uniteCourante = analyseurLexical.uniteSuivante();
                        if(!isOperande() || !isOperandeTypeExpressArithOk(uniteCourante))
                            throw new SyntaxeException("Expression non valide");
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
    private void verifieTypeForITOEAA(String typeIdent) throws SemantiqueExecption{
        String type = null;
        if(uniteCourante.equals(UniteLexicale.ident))
            type = trouverTypeident(AnalyseurLexical.UNITECOURANT);
        if(type == null){
            if(!Objects.equals(getCorrespondance(typeIdent), uniteCourante)) throw new SemantiqueExecption("type erroné !!");
        }
        else if(!typeIdent.equals(type)) throw new SemantiqueExecption("type erroné !!");
    }

    private boolean isTermeOrIsExpressionArithmetiqueInAffectation(String typeIdent) throws SyntaxeException, SemantiqueExecption{
        if(isOperande()){
            verifieTypeForITOEAA(typeIdent);
            uniteCourante = analyseurLexical.uniteSuivante();
            if(isOperateur()){
                uniteCourante = analyseurLexical.uniteSuivante();
                if(isOperande()){
                    verifieTypeForITOEAA(typeIdent);
                    uniteCourante = analyseurLexical.uniteSuivante();
                    while (isOperateur()){
                        uniteCourante = analyseurLexical.uniteSuivante();
                        if(isOperande()) {
                            verifieTypeForITOEAA(typeIdent);
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

    private boolean isTerme() throws SemantiqueExecption{
        //uniteCourante = analyseurLexical.uniteSuivante();
        if (UniteLexicale.ident.equals(uniteCourante))
            return variableMustBeDeclared(AnalyseurLexical.UNITECOURANT);
        return  UniteLexicale.entier.equals(uniteCourante)
                || UniteLexicale.reel.equals(uniteCourante)
                || UniteLexicale.chaine.equals(uniteCourante);
    }
    private boolean isTypeVariable() throws  SyntaxeException{
        //uniteCourante = analyseurLexical.uniteSuivante();
        return UniteLexicale.integer.equals(uniteCourante)
                || UniteLexicale.real.equals(uniteCourante)
                || UniteLexicale.bool.equals(uniteCourante)
                || UniteLexicale.string.equals(uniteCourante);
    }
    private boolean declarationVariable() throws SyntaxeException, SemantiqueExecption {
        if(isTypeVariable()){
            String typeVariable = uniteCourante;
            uniteCourante = analyseurLexical.uniteSuivante();
            if(uniteCourante.equals(UniteLexicale.ident)){
                isVariableAlreadyDeclaree(AnalyseurLexical.UNITECOURANT);
                Symbole s = new Symbole();
                Variable vi=new Variable();
                
                s.setIdentifant(AnalyseurLexical.UNITECOURANT);
                s.setType(typeVariable);
                tableSymbole.add(s);
                uniteCourante = analyseurLexical.uniteSuivante();
                if(uniteCourante.equals(UniteLexicale.affec))
                {
                	tableSymbole.get(tableSymbole.size()-1).valeur="0";
                    uniteCourante = analyseurLexical.uniteSuivante();
                    //listIdents.get(listIdents.size()-1).valeur = "0";
                    
                    if(!isTerme())
                        throw new SyntaxeException("Declaration de variable non valide: " +
                            "une '=' est suivie obligatoirement par: (ident | entier | reel | chaine)");
                   //else genererInstr("store", listIdents.size() + tableDesSymboles.size() + "");
                    else generercode("STORE", tableSymbole.size()+table_s.size()+"");	
                    isTypeOk(s.getIdentifant(), uniteCourante);
                    
                    uniteCourante = analyseurLexical.uniteSuivante();
                }
                if(uniteCourante.equals(UniteLexicale.finLigne))
                    return true;
                while (uniteCourante.equals(UniteLexicale.virgule)){
                    uniteCourante = analyseurLexical.uniteSuivante();
                    if(!uniteCourante.equals(UniteLexicale.ident))
                        throw new SyntaxeException("Declaration de variable non" +
                                " valide: une virgule est suivie" +
                                "obligatoirement par un identifiant");
                    s = new Symbole();
                    s.setIdentifant(AnalyseurLexical.UNITECOURANT);
                    s.setType(typeVariable);
                    tableSymbole.add(s);
                    uniteCourante = analyseurLexical.uniteSuivante();
                    if(uniteCourante.equals(UniteLexicale.affec))
                    {
                    	tableSymbole.get(tableSymbole.size()-1).valeur="0";
                        uniteCourante = analyseurLexical.uniteSuivante();
                        if(!isTerme())
                            throw new SyntaxeException("Declaration de variable non valide: " +
                                    "une '=' est suivie obligatoirement par: (ident | entier | reel | chaine)");
                        else generercode("STORE", tableSymbole.size()+table_s.size()+"");
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

    private boolean declarationConstante() throws SyntaxeException , SemantiqueExecption{
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
                    uniteCourante = analyseurLexical.uniteSuivante();
                    if (uniteCourante.equals(UniteLexicale.affec)) {
                    	tableSymbole.get(tableSymbole.size()-1).valeur="0";
                        uniteCourante = analyseurLexical.uniteSuivante();
                        if (!isTerme())
                            throw new SyntaxeException("Declaration de constante non valide: \" +\n" +
                                    "\"une '=' est suivie obligatoirement par: (ident | entier | reel | chaine)");
                        else generercode("STORE", tableSymbole.size()+table_s.size()+"");
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

    private boolean affectation () throws SyntaxeException,SemantiqueExecption {
        if(uniteCourante.equals(UniteLexicale.ident)){
            variableMustBeDeclared(AnalyseurLexical.UNITECOURANT);
            isConstanteAndWantToDoAffect(AnalyseurLexical.UNITECOURANT);
            String type = trouverTypeident(AnalyseurLexical.UNITECOURANT);
            uniteCourante = analyseurLexical.uniteSuivante();
            if (!uniteCourante.equals(UniteLexicale.affec)){
                throw new SyntaxeException("Affectation non valide");
            }
            
            uniteCourante = analyseurLexical.uniteSuivante();
            if(!isTermeOrIsExpressionArithmetiqueInAffectation(type))
                throw new SyntaxeException("Affectation non valide");
            else
            if(uniteCourante.equals(UniteLexicale.finLigne))
            {  
            	b = "0";
                generercode("store", a );
            	return true;}
             
            throw new SyntaxeException("Affectation non valide");
        }
        throw new SyntaxeException("Affectation non valide");
    }

    private boolean isOperateurConditionnel(){
        return uniteCourante.equals(UniteLexicale.inf)
                || uniteCourante.equals(UniteLexicale.sup)
                || uniteCourante.equals(UniteLexicale.infEgal)
                || uniteCourante.equals(UniteLexicale.supEgal)
                || uniteCourante.equals(UniteLexicale.egal)
                || uniteCourante.equals(UniteLexicale.diff);
    }

    private boolean isBoolean(){
        return uniteCourante.equals(UniteLexicale.vrai)
                || uniteCourante.equals(UniteLexicale.faux);
    }

    private boolean isCondition() throws SyntaxeException,SemantiqueExecption{
        // verfies la grammaire d'expression arith et de terme
        isExpressionArithemetique();
        if(!isOperateurConditionnel())
            throw new SyntaxeException("Expression conditionnelle non valide");
        
        if(UniteLexicale.sup.equals(uniteCourante)) generercode("SUPE", "");
    	if(UniteLexicale.infEgal.equals(uniteCourante)) generercode("INFE", "");
    	if(UniteLexicale.supEgal.equals(uniteCourante)) generercode("SUPE", "");
    	if(UniteLexicale.egal.equals(uniteCourante)) generercode("EQUAL", "");
    	if(UniteLexicale.diff.equals(uniteCourante)) generercode("NEQUAL", "");
    	
        uniteCourante = analyseurLexical.uniteSuivante();
        isExpressionArithemetique();
        while (uniteCourante.equals(UniteLexicale.ou)
                || uniteCourante.equals(UniteLexicale.et)
                || uniteCourante.equals(UniteLexicale.diff)
        ){  
        	if(UniteLexicale.et.equals(uniteCourante)) generercode("AND", "");
        	if(UniteLexicale.ou.equals(uniteCourante)) generercode("OR", "");
        	//if(UniteLexicale.not.equals(uniteCourante)) generercode("NEQUAL", "");
            uniteCourante = analyseurLexical.uniteSuivante();
            isExpressionArithemetique();
            if(!isOperateurConditionnel())
                throw new SyntaxeException("Expression conditionnelle non valide");
            if(UniteLexicale.sup.equals(uniteCourante)) generercode("SUPE", "");
        	if(UniteLexicale.infEgal.equals(uniteCourante)) generercode("INFE", "");
        	if(UniteLexicale.supEgal.equals(uniteCourante)) generercode("SUPE", "");
        	if(UniteLexicale.egal.equals(uniteCourante)) generercode("EQUAL", "");
        	if(UniteLexicale.diff.equals(uniteCourante)) generercode("NEQUAL", "");
            
            uniteCourante = analyseurLexical.uniteSuivante();
            isExpressionArithemetique();
        }
        return true;
    }

    private boolean isConcatenation() throws SyntaxeException, SemantiqueExecption{
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

    private boolean isAlternative() throws SyntaxeException, SemantiqueExecption{
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
        generercode("JZERO", "");
        /*if(!uniteCourante.equals(UniteLexicale.accoladeFerm))
            throw new SyntaxeException("Erreur de syntaxe: '}' attendu");*/

        return true;
    }

    private boolean isRepetitive()  throws SyntaxeException, SemantiqueExecption{
        if (!uniteCourante.equals(UniteLexicale.tantQue))
            throw new SyntaxeException("Erreur de syntaxe");
        int temp = nbinst;
        uniteCourante = analyseurLexical.uniteSuivante();
        isCondition();
        if(!uniteCourante.equals(UniteLexicale.accoladeOuv))
            throw new SyntaxeException("Erreur de syntaxe: '{' attendu");
        uniteCourante = analyseurLexical.uniteSuivante();
        generercode("jzero", "");
        while(!uniteCourante.equals(UniteLexicale.accoladeFerm)){
            instruction();
            //System.out.println(uniteCourante);
            uniteCourante = analyseurLexical.uniteSuivante();
        }
        if(uniteCourante.equals(UniteLexicale.accoladeFerm))
        {
        	generercode("JUMP", "");
        	table_etiq.add(nbinst);
        	table_etiq.add(temp);
            return true;
        } 
        else throw new SyntaxeException("Erreur de syntaxe: '}' attendu");
        
    }

    private boolean ecriture() throws SyntaxeException,SemantiqueExecption{
    	String l="";
        if(!uniteCourante.equals(UniteLexicale.ecrire))
            throw new SyntaxeException("Erreur de syntaxe: print");
        uniteCourante = analyseurLexical.uniteSuivante();
        if(!uniteCourante.equals(UniteLexicale.parentOuv))
            throw new SyntaxeException("Erreur de syntaxe: '(' attendu");
        uniteCourante = analyseurLexical.uniteSuivante();
        if(uniteCourante.equals(UniteLexicale.chaine)){
            uniteCourante = analyseurLexical.uniteSuivante();
            if(!uniteCourante.equals(UniteLexicale.parentFerme))
                throw new SyntaxeException("Erreur de syntaxe: ')' attendu");
        }else if(isOperande()){
        	/*if(uniteCourante.equals(UniteLexicale.ident)) {
        		
        		l=uniteCourante;
        		//on verifie que la variable a déjà été déclarer
                v = contains(l, table_s);
                if(v != null)
                {
                    generercode("write",v.adresse);
                    return true;
                }
        	}*/
            isExpressionArithemetique();
            if(!uniteCourante.equals(UniteLexicale.parentFerme))
                throw new SyntaxeException("Erreur de syntaxe: ')' attendu");
        }
        uniteCourante = analyseurLexical.uniteSuivante();
        if(!uniteCourante.equals(UniteLexicale.finLigne))
           throw new SyntaxeException("Erreur de syntaxe: ';' attendu");
        return true;
    }

    private boolean lire() throws SyntaxeException {
    	String c="";
        if(!uniteCourante.equals(UniteLexicale.lire))
            throw new SyntaxeException("Erreur de syntaxe: read");
        uniteCourante = analyseurLexical.uniteSuivante();
        if(!uniteCourante.equals(UniteLexicale.parentOuv))
            throw new SyntaxeException("Erreur de syntaxe: '(' attendu");
        uniteCourante = analyseurLexical.uniteSuivante();
        if(uniteCourante.equals(UniteLexicale.ident))
        	c=analyseurLexical.UNITECOURANT;
        else throw new SyntaxeException("Erreur de syntaxe: 'ident' attendu");
        uniteCourante = analyseurLexical.uniteSuivante();
        if(!uniteCourante.equals(UniteLexicale.parentFerme))
            throw new SyntaxeException("Erreur de syntaxe: ')' attendu");
        uniteCourante = analyseurLexical.uniteSuivante();
        if(uniteCourante.equals(UniteLexicale.finLigne))
        {v = contains(c, table_s);
        if(v != null)
        {
            generercode("write",v.adresse);
            return true;
        }
        throw new SyntaxeException("Erreur de syntaxe:la variable doitetre declare");
        }
        else throw new SyntaxeException("Erreur de syntaxe: ';' attendu");
       
    }

    private void instruction() throws SyntaxeException, SemantiqueExecption{

        if(isTypeVariable())
        {
        	if(uniteCourante == UniteLexicale.entier || uniteCourante == UniteLexicale.reel)
        {
            type = uniteCourante;
            declarationVariable();
            int i = 0;
            for(Symbole temp : tableSymbole)
            {
                i++;
                if(contains(temp.identifant, table_s) == null)
                {
                    v = new Symbole(temp.identifant, type+"",i+table_s.size()+"",temp.valeur, false);
                    table_s.add(v);
                }
                else{

                    tableSymbole.clear();
                    throw new SyntaxeException ("variable déjà declaree ");
                }
            }
            tableSymbole.clear();
        
        }
        declarationVariable();
        }
        else if(uniteCourante.equals(UniteLexicale.constante)) { type = uniteCourante;
        declarationVariable();
        int i = 0;
        for(Symbole temp : tableSymbole)
        {
            i++;
            if(contains(temp.identifant, table_s) == null)
            {
                v = new Symbole(temp.identifant, type+"",i+table_s.size()+"",temp.valeur, true);
                table_s.add(v);
            }
            else{

                tableSymbole.clear();
                throw new SyntaxeException ("variable déjà declaree ");
            }
        }
        tableSymbole.clear();
    }
    
    
        else if(uniteCourante.equals(UniteLexicale.ecrire)) { ecriture(); }

        else if(uniteCourante.equals(UniteLexicale.lire)) { lire(); }

        else if(uniteCourante.equals(UniteLexicale.si)) { isAlternative(); }

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
    }
 // grammaire de programme
    public void runAnalyseSyntaxique() throws SyntaxeException , SemantiqueExecption {
        tableSymbole = new ArrayList<>();
        
        nbinst = 0;
        table_s.clear();
        
        //vd.clear();
        vic.clear();
        table_etiq.clear();
         
        uniteCourante = analyseurLexical.uniteSuivante();
        if (uniteCourante.equals(UniteLexicale.debutProgram)) {
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
                if(vic.get(i).op.equalsIgnoreCase("jzero") || vic.get(i).op.equalsIgnoreCase("jump") )
                {
                    temp = table_etiq.get(j) + 1;
                    vic.get(i).opd = "" + temp;
                    j++;
                }
            } //
            
             //on enregistre le code cible dans un fichier
            String str = "";
            for (InstructionCible cible : vic)
                 str += cible.op.toUpperCase() + " " + cible.opd.toUpperCase() + "\n";
            str += "end\n";
            Ecriture.write("src/com/VIC.txt", str);
            
             
             
        }
    }

    private boolean isTypeOk (String ident , String typeDeDonne) throws SemantiqueExecption{
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
                        throw new SemantiqueExecption(" type erroné!!");
                    }else return true;
                }
            }
        }
        throw new SemantiqueExecption(" type erroné!!");
    }

    private boolean isVariableAlreadyDeclaree(String ident) throws SemantiqueExecption {
        for (Symbole s : tableSymbole) {
            if (s.getIdentifant().equals(ident))
                throw new SemantiqueExecption("variable  deja declarée !!");
        }
        return false;
    }

    private boolean isOperandeTypeExpressArithOk( String operande ) throws SemantiqueExecption{
        if (operande.equals(UniteLexicale.chaine))
            throw new SemantiqueExecption("Operande ne respecte pas le bon type");
        return true;
    }

    private boolean variableMustBeDeclared(String ident) throws SemantiqueExecption{
        for (Symbole s : table_s){
            if (s.getIdentifant().equals(ident)){
            	a=s.adresse;
            	b=s.valeur;
                return true;
            }
        }
        throw new SemantiqueExecption("variable doit etre daclarée ");
    }

    private String getCorrespondance (String mr){
        if(mr.equals(UniteLexicale.integer))
            return UniteLexicale.entier;
        if(mr.equals(UniteLexicale.real))
            return UniteLexicale.reel;
        if(mr.equals(UniteLexicale.string))
            return UniteLexicale.chaine;
        return null;
    }

    private String trouverTypeident(String ident){
        for (Symbole s : tableSymbole) {
            if (s.getIdentifant().equals(ident))
                return s.getType();
        }
        return null;
    }

    private boolean isConstanteAndWantToDoAffect(String ident) throws SemantiqueExecption{
        for (Symbole s : tableSymbole) {
            if (s.getIdentifant().equals(ident)){
                if(s.isConstante())
                    throw new SemantiqueExecption("On ne peut pas affecter une valeur a une constante!!");
            }
        }
        return true;
    }
    
    //generatio
    private boolean generercode(String op, String opd){
        ++nbinst;
        return vic.add(new InstructionCible(op, opd));
    }

    
    
    
    public Symbole contains (String lexeme, ArrayList<Symbole> table_s){
        for(Symbole descVariable: table_s)
        {
            if(descVariable.identifant.equals(lexeme))
            {
                return descVariable;
            }
        }
        return null;
    }
}