package com.generation;

public class Variable {

	
	public String type;
	public String ident;
    public 	String adresse;
	public String valeur;
	public String constante ;

    public Variable()
	{  this.type=null;
	   this.ident=null;
	   this.adresse=null;
	   this.valeur=null;
	  this.constante=null;
	}
  public   Variable( String type_,String ident_,String adresse_,String valeur_,String b)
    {
    	this.type=type_;
    	this.ident=ident_;
    	this.adresse=adresse_;
    	this.valeur=valeur_;
    	 this.constante=b;
    	
    }
    public String getAdresse()
    {
    	return this.adresse;
    }
    public String SetConstante()
    {
    	return this.adresse;
    }
  public String getIdent()
  {
	  return this.ident;
  }
  public void setbolean(String a)
  {
	   this.constante=a;
  }
  public String getValeur()
  {
	  return this.valeur;
  }
  
  public String getType()
  {
	  return this.type;
  }
  // Dans le cas ou l'on veut modifier la valeur d'un element dans notre Table
  // comme 
	/*public void  modifiervaleur(String adresse,String ident,String valeur,String operation)
	{   if(this.adresse==adresse && this.ident==ident)
    	{   if(operation=="Aff")
		    	this.valeur=valeur;
		    if(operation=="Concat")
		    	this.valeur=this.valeur+valeur;
		    if(operation=="Add")
		    {  int k , j;
		       k=ParseInt(operation);
		       j=ParseInt(this.valeur);
		       k=k+j;
		       this.valeur=""+k+"";
		    	  
		    }
	    }
		
	}
	
	
	/*this.type=null;
	   this.ident=null;
	   this.adresse=null;
	   this.valeur=null;*/
	
  public void  modifiervaleur(String valeur)
  {  
	 this.valeur=valeur;
  }
	public String toString()
	{
		return "valeur= "+this.valeur+"  type=  "+this.type+"  adresse="+this.adresse+" ident="+this.ident+"   Constante  "+this.constante;
	}
	
	private int ParseInt(String operation) {
		// TODO Auto-generated method stub
		return 0;
	}
}
