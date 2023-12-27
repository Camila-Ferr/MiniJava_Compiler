package br.com.compilador.minijava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NaoTerminal extends Symbol{

	public NaoTerminal(String name, ArrayList<String> caminho, String type) {
		super(name, caminho, type);
		// TODO Auto-generated constructor stub
	}
	public NaoTerminal (String name, ArrayList<String> caminho, String type,Map<String, List<String>> options) {
		super(name, caminho, type);
		this.options = (options);
		
	}
	
	public NaoTerminal (String name, ArrayList<String> caminho, String type,String action) {
		super(name, caminho, type);
		this.action = action;
		
	}
}
