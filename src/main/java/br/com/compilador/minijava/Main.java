package br.com.compilador.minijava;

import java.util.ArrayList;

import br.com.compilador.minijava.model.State;
import br.com.compilador.minijava.model.Symbol;
import br.com.compilador.minijava.model.Token;
import br.com.compilador.minijava.service.Analyzer;
import br.com.compilador.minijava.service.Parser;
import br.com.compilador.minijava.service.Reader;

public class Main {
    public static void main(String[] args) {
        try {
        	ArrayList<State> states = Reader.readerJsonLexical("./src/main/resources/lexical.json");
			String arquivoJava = Reader.readJavaFile("./src/main/resources/Tests/Test5.java");
        	Analyzer lexico = new Analyzer(states);
			
			lexico.analyzeFile(arquivoJava);
			printResultadoLexico(lexico.getTokens());
			
			ArrayList<Symbol> symbols = Reader.readerJsonRules("./src/main/resources/syntatic.json");
			symbols = Reader.readerJsonRules("./src/main/resources/syntatic.json", symbols);
			Parser parser = new Parser(lexico.getTokens(), symbols);

			System.out.println("Análise Sintática : " + parser.iniciar());
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
    }



	public static void printResultadoLexico(ArrayList<Token> analisadorLexico) {
    	for (Token i: analisadorLexico) {
    		System.out.println(String.format("Linha : %d, Coluna: %d, Tipo: %s, Palavra: %s", i.getLinha(),i.getColumn(), i.getLexema(), i.getPalavra()));
    	}
    }
        public static void printSymbol(ArrayList<Symbol> simbols) {
        	for (Symbol i: simbols) {
        		System.out.println(i.getName());
        		System.out.println(i.getCaminho());
        	}
    	
    	
    }
}
