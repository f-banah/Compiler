package com.interpreteur;
import java.util.ArrayList;

public class Stack extends ArrayList<String> {
	 int s=-1;
	public String get_str() {
		 	
		return this.get( this.size() - 1 );
	                
	}
	
	public void add_str(String str) {
		
		this.add(str);
		s++;
	}
	
	public String pop_str() {
		String poped_out = this.get_str();
		this.remove( this.size() - 1 );
		s=s-1;
		return poped_out;
		
	}
}
