package br.com.compilador.minijava.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabelaVariaveis {
	static ArrayList<Map<String, String>> listaDeVariaveis = new ArrayList<>();
	static ArrayList<String> methods = new ArrayList<>();
	
	
	public static void adicionarVariaveis(Map<String, String> variaveis, String method) {
		Map<String, String> copiaVariaveis = new HashMap<>(variaveis);
		listaDeVariaveis.add(copiaVariaveis);
		methods.add(method);
	}
	public static void printVariaveis() {
		for (int i = 0; i < listaDeVariaveis.size(); i++) {
			Map<String, String> variaveis = listaDeVariaveis.get(i);
	        System.out.println();
			System.out.println("Método : "+methods.get(i));
			System.out.println();
			
			for (Map.Entry<String, String> entry : variaveis.entrySet()) {
				System.out.println("Variável: " + entry.getKey() + ", Tipo: " + entry.getValue());
			}
		}
		System.out.println();
	}

}
