package com.interpreteur2;
import java.util.ArrayList;


public class VIC extends ArrayList<Instruction> {
	
	public void add_inst(Instruction inst) {
		this.add(inst);
	}
	
	public Instruction get_inst(int index) {
		return this.get(index);
	}
}
