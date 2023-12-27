package br.com.compilador.minijava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Symbol {
	
	private String name;
	private ArrayList <String> caminho;
	private String type;
	protected Map<String, List<String>> options;
	protected String action;

	
	public Symbol(String name, ArrayList<String> caminho, String type) {
		this.name = name;
		this.caminho = caminho;
		this.type = type;
		
	}
	
	public ArrayList <String> getCaminho() {
		return caminho;
	}
	public void setCaminhos(ArrayList <String> caminho) {
		this.caminho = caminho;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static Symbol getSymbol(String nome, ArrayList<Symbol> simbolos) {
		for (Symbol i : simbolos) {
			if (i.getName().equals(nome)) {
				return i;
			}
		}
		System.out.println(nome);
		return null;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String, List<String>> getOptions() {
		return options;
	}
	public void setOptions(Map<String, List<String>> options) {
		this.options = options;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}


}
