package com.generation;

public class InstruCible {

		public String Operande;
	 public 	String Operateur;
		public InstruCible()
		{ 
			this.Operande=null;
			this.Operateur=null;
		}
		public InstruCible(String Operateur_ ,String Operande_ )
		{
			this.Operande=Operande_;
			this.Operateur=Operateur_;
		}
		
		public String  toString()
		{
			return  this.Operateur+" "+this.Operande;
		}

}
