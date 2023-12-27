package br.com.compilador.minijava.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

import br.com.compilador.minijava.exception.ErroSintaxeException;
import br.com.compilador.minijava.model.ArvoreSintatica;
import br.com.compilador.minijava.model.NaoTerminal;
import br.com.compilador.minijava.model.Symbol;
import br.com.compilador.minijava.model.TabelaVariaveis;
import br.com.compilador.minijava.model.Token;

public class Parser  {
	private Token atualTokien;
	private Symbol atualSymbol;
	private ArrayList<Symbol> symbols;
	private Iterator<Token> contadorTokiens;
	private  Map<String,String> variaveis;
	private ArrayList<String> method ;
	private int indice;
	private ArrayList<Token> tokiens;
	private String action;
	
	private ArvoreSintatica arvore = new ArvoreSintatica("GOAL");
	private Stack<String> pilhaNosPais;
	private String actualMethod;
	
	private static String TYPE_VAR = null;
	
	private static final ArrayList<String> RESERVADOS = new ArrayList<>(Arrays.asList("public","static","void","main","String","int","boolean","true","false",";",")","(","[","]","{","}","+","-","*","/","if","else"));
	private static final ArrayList<String> TYPES = new ArrayList<>(Arrays.asList("int","class","boolean","String"));
	private static final ArrayList<String> ARRAY = new ArrayList<>(Arrays.asList("new","."));
	private static final ArrayList<String> OUT_ARVORE = new ArrayList<>(Arrays.asList("VAZIO","INT_OR_ARRAY_TYPE","CONSUME_INT"));
	
	
	
	public Parser(ArrayList<Token> tokiens, ArrayList<Symbol> symbols) throws Exception {
		this.contadorTokiens = tokiens.iterator();
        this.atualTokien = contadorTokiens.next();
        this.atualSymbol = symbols.get(0);
        this.symbols = symbols;
        this.variaveis = new HashMap<>();
        this.method = new ArrayList<>();
        this.actualMethod = "main";
        this.indice = 0;
        this.tokiens = tokiens;
        this.action = "";
        this.pilhaNosPais = new Stack<>();
        
        pilhaNosPais.push("GOAL");
        
       
    }
	public boolean iniciar() throws ErroSintaxeException {
		try {
			analisadorSintatico(atualSymbol);
			TabelaVariaveis.adicionarVariaveis(variaveis, actualMethod);
			
			arvore.imprimirArvore();
			TabelaVariaveis.printVariaveis();
			return true;
		}catch(ErroSintaxeException e){
			System.out.println(e);
			return false;
		}
	}
	
	private void analisadorSintatico(Symbol atual) throws ErroSintaxeException {

		Symbol analisado = null;
		if (atual.getAction()!= null) {
			action = atual.getAction();
		}
		
		if ((atual instanceof NaoTerminal)) {
			if (atual.getType().equals("or")) {
				analisado = trataOr(atual);
				adicionaFilhos(pilhaNosPais.peek(), analisado.getName());
				analisadorSintatico(analisado);
				
				
			}
			else {
				for (String i : atual.getCaminho()) {
					 analisado = Symbol.getSymbol(i, symbols);
					 adicionaFilhos(pilhaNosPais.peek(), analisado.getName());
					 analisadorSintatico(analisado);
					 pilhaNosPais.pop();
				}
			}
		}
			
		else {
			if (atual.getCaminho().get(0).equals("")) {
				//Vazio, continua
			}
			else if ((atualTokien == null) && (atual!= null)){
				throw new ErroSintaxeException(atual.getCaminho().get(0));
				
			}
			else if (isBoolean(atualTokien.getPalavra())|| ARRAY.contains(atualTokien.getPalavra()) ||atual.getCaminho().get(0).equals("<INTEGER_LITERAL>") && (isDigit(atualTokien.getPalavra()))){
				
				action(atualTokien);
				indice +=1;
				atualTokien = contadorTokiens.next();
				
			}
			
			else if (atual.getCaminho().get(0).equals(atualTokien.getPalavra()) || TYPES.contains(atualTokien.getPalavra())) {
				
				 if(TYPES.contains(atualTokien.getPalavra())) {
					 TYPE_VAR = atualTokien.getPalavra();
					 if (tokiens.get(indice+1).getPalavra().equals(Symbol.getSymbol("OPEN_BRACKETS", symbols).getCaminho().get(0))) {
						 TYPE_VAR = "array";
					}
				}
				if (atualTokien.getPalavra().equals(Symbol.getSymbol("SEMICOLON", symbols).getCaminho().get(0))) {
					action = "";
				}
					
				indice +=1;
				atualTokien = contadorTokiens.hasNext()? contadorTokiens.next(): null;
			}
			else if (atual.getCaminho().get(0).equals("<IDENTIFIER>") && !(RESERVADOS.contains(atualTokien.getPalavra())) && (isIdentifier(atualTokien.getPalavra()))){
				trataVar();
				indice +=1;
				atualTokien = contadorTokiens.next();
				
			}
			else {
				throw new ErroSintaxeException(atualTokien);
			}
		}
	}
	private void trataVar() throws ErroSintaxeException {
		if (action.equals("saveMethod")) {
			TabelaVariaveis.adicionarVariaveis(variaveis,actualMethod);
			method.add(atualTokien.getPalavra());
			actualMethod = atualTokien.getPalavra();
			variaveis.clear();
		}
		else if (action.equals("saveVar")) {
			saveVar(atualTokien.getPalavra());
		}
		else if (action.equals("saveVarWithClear")) {
			saveVar(atualTokien.getPalavra());
			action = "";
		}
		else {
			action(atualTokien.getPalavra());
			
		}
		
	}
	private Symbol trataOr(Symbol simbol) {
		Symbol analisado;
		
		for (String i: simbol.getCaminho()){
			analisado = Symbol.getSymbol(i,symbols);

			if (atualTokien!= null && simbol.getOptions().get(i).contains(atualTokien.getLexema())) {
				return analisado;
				
			}
		}

		return Symbol.getSymbol("VAZIO", symbols);
	}
	
	private void saveVar(String token) throws ErroSintaxeException {
		 if (variaveis.containsKey(token)) {
			 throw new ErroSintaxeException(token,atualTokien.getLinha());
		 }
		variaveis.put(token, TYPE_VAR);
		if (TYPE_VAR.equals("class")) {
			TYPES.add(token);
		}
		
	}
	private void action (String token) throws ErroSintaxeException {
		
		if (variaveis.containsKey(token)) {
			if (action.equals("verifyClass")) {
				if (!(variaveis.get(token).equals("class"))) {
					throw new ErroSintaxeException(atualTokien.getLinha(), atualTokien.getColumn(),"class");
				}
				action = "";
			}

			else {
				switch (variaveis.get(token)) {
			    case "int":
			        action = "verifyInt";
			        
			        break;
			    case "boolean":
			        action = "verifyBoolean";
			        break;
			    default:
			        action = "verifyArray";
			        break;
				}
				
			}
		}
		else {
			throw new ErroSintaxeException(atualTokien, atualTokien.getLinha());
			
		}
	}
	private void action (Token token) throws ErroSintaxeException {
		if (!(action.equals(""))) {
			if (action.equals("verifyInt")) {
				if (!(isDigit(token.getPalavra()))){
					throw new ErroSintaxeException(token.getLinha());
				}
			}
			else if (action.equals("verifyBoolean")) {
				if (!(isBoolean(token.getPalavra()))){
					throw new ErroSintaxeException(token.getLinha());
				}
			}
			else if (action.equals("verifyArray")) {
				if (!(ARRAY.contains(token.getPalavra()))){
					throw new ErroSintaxeException(token.getLinha());
				}
			}
			action = "";
			
		}
	}
	public void adicionaFilhos(String parent, String atual) {
		if (!(OUT_ARVORE.contains(atual))) {
			arvore.adicionarFilho(parent, atual);
			pilhaNosPais.push(atual);
		}
	}
	
	
	private static boolean isIdentifier(String str) {
		return Pattern.matches("^[^\\d]\\w*$", str);
		
		
	}
	 private static boolean isDigit(String str) {
	        return Pattern.matches("\\d+", str);
	}
	 
	 private static boolean isBoolean(String str) {
		 return str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false");
	}
	 

}
