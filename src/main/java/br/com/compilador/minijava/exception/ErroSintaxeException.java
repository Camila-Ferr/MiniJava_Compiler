package br.com.compilador.minijava.exception;

import br.com.compilador.minijava.model.Token;

public class ErroSintaxeException extends Exception {
	
    public ErroSintaxeException(Token atualTokien) {
        super(String.format("Ocorreu um erro sintático no código na linha %d coluna %d e tokien %s.",atualTokien.getLinha(),atualTokien.getColumn(),atualTokien.getPalavra()));
    }
    
    public ErroSintaxeException(String symbol) {
        super(String.format("O código está incompleto, é esperado %s ao final do código. ", symbol));
    }
    
    public ErroSintaxeException(Token atualTokien, int linha) {
        super(String.format("O identificador %s na linha %d não foi declarado.",atualTokien.getPalavra(),linha));
    }

	public ErroSintaxeException(int linha, int coluna, String classe) {
		super(String.format("Na linha %d, coluna %d era esperado uma variável de tipo %s.",linha,coluna, classe));
	}
	
	public ErroSintaxeException(int linha) {
		super(String.format("Operação inválida na linha %d",linha));
	}
	
	public ErroSintaxeException(String variavel, int linha) {
		super(String.format("Variável %s já existente. Erro na linha %d",variavel, linha));
	}
    
    
}
