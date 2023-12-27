package br.com.compilador.minijava.model;

import java.util.ArrayList;
import java.util.List;

public class State {
    private String name;
    private List<Rule> rules;
    
    public State(String name, List<Rule> expressions) {
        this.name = name;
        this.rules = expressions;
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Rule> getExpressions() {
		return rules;
	}
	public void setExpressions(List<Rule> expressions) {
		this.rules = expressions;
	}
	
	 public static State getByName(String name, ArrayList<State> states) {
	        for (State state : states) {
	            if (state.getName().equals(name)) {
	                return state;
	            }
	        }
	        return null; 
	    }


}


