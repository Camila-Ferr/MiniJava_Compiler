package br.com.compilador.minijava.model;

import java.util.ArrayList;

public class Terminal extends Symbol {
	private static final String TYPE = "structured";

	public Terminal(String name, ArrayList<String> caminho) {
		super(name, caminho, TYPE);
	}
	
	

}
