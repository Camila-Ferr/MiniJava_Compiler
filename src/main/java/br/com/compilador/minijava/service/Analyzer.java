package br.com.compilador.minijava.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.compilador.minijava.model.Rule;
import br.com.compilador.minijava.model.State;
import br.com.compilador.minijava.model.Token;

public class Analyzer {
	private static final String GLOBAL = "Global";

    private State currentState;
    private String currentLexeme;
    private ArrayList<Token> tokens;
    private Token currentToken;
    private String buffer;
    private ArrayList<State> states;
    private int line;
    private int column;

    public Analyzer(ArrayList<State> states) {
        this.currentState = State.getByName(GLOBAL, states);
        this.currentLexeme = "";
        this.tokens = new ArrayList<>();
        this.currentToken = null;
        this.buffer = "";
        this.states = states;
        this.line = 1;
        this.column = 1;
    }

    public void analyzeFile(String entrada) {
            buffer = entrada;

            while (buffer != null && !buffer.isEmpty()) {
                processLexeme();
            }
    }

    private void processLexeme() {
        List<Rule> listMatch = new ArrayList<>();
        Matcher match;
        String currentLexeme = null;
        String bufferTmp = buffer;
        
        for (Rule rule : currentState.getExpressions()) {
                String expression = rule.getRegularExpression();
            
                match = Pattern.compile(expression).matcher(bufferTmp);


                if (match.find()) {
                    if (match.start() != 0) {
                        bufferTmp = bufferTmp.substring(0, match.start());
                    } else {

                        bufferTmp = bufferTmp.substring(match.start(), match.end());
                        buffer = buffer.substring(match.end());
                        currentLexeme = bufferTmp;
                        listMatch.add(rule);
                        actionProcessing(rule, currentLexeme);
                        break;
                    }
                }
            }

        if (currentLexeme == null) {
            buffer = buffer.substring(bufferTmp.length());
            currentLexeme = bufferTmp;
            currentToken = null;

        }
    }

    private void actionProcessing(Rule rule, String currentLexeme) {
        if ("Return_token".equals(rule.getProcessing())) {
            currentToken = new Token(rule.getTokenId(),currentLexeme, line,column);
            tokens.add(currentToken);
            currentToken = null;
            column += currentLexeme.length();
            
        } else if ("Jump".equals(rule.getProcessing())) {
            currentState = State.getByName(rule.getTargetState(), states);
            column += currentLexeme.length();

        } else if ("Ignore".equals(rule.getProcessing())) {
        	column += currentLexeme.length();
        	
        }else if ("Ignore_with_line".equals(rule.getProcessing())) {
            line += 1;
            column = 1;
        }
        else if ("Jump_with_line".equals(rule.getProcessing())){
        	currentState = State.getByName(rule.getTargetState(), states);
        	line += 1;
        	column = 1;
        }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }
}
