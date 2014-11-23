/**
 * 
 */
package edu.csupomona.cs.cs141.classproject;

import java.io.Serializable;

/**
 * @author Isa
 *
 */
public class EmptyMember implements GridMember, Serializable {
	
	private boolean seen;

	public String toString(){
		String result;
		if(isSeen()){
			result = "[ ]";
		}
		else result = "[*]";
		return result;
	}
	
	public boolean isSeen(){
		return seen;
	}
	
	public void resetSee(){
		seen = false;
	}
	
	public void see(){
		seen = true;
	}
	
	public boolean isSomething(){
		return false;
	}
	
	
	
}
