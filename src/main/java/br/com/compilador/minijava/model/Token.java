package br.com.compilador.minijava.model;

public class Token {
	private String lexema;
    private String palavra;
    private Integer line;
    private Integer column;

    public Token(String lexema, String palavra, Integer line, Integer column) {
        this.lexema = lexema;
        this.palavra = palavra;
        this.line = line;
        this.column = column;
    }

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public Integer getLinha() {
		return line;
	}

	public void setLinha(Integer linha) {
		this.line = linha;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}



}