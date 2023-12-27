package br.com.compilador.minijava.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import br.com.compilador.minijava.model.NaoTerminal;
import br.com.compilador.minijava.model.Rule;
import br.com.compilador.minijava.model.State;
import br.com.compilador.minijava.model.Symbol;
import br.com.compilador.minijava.model.Terminal;

public class Reader {

    public static ArrayList<State> readerJsonLexical(String jsonString) {

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(jsonString));
            JSONObject jsonObject = (JSONObject) obj;

            ArrayList<State> statesList = new ArrayList<>();

            JSONArray statesArray = (JSONArray) jsonObject.get("states");
            Iterator<JSONObject> statesIterator = statesArray.iterator();
            while (statesIterator.hasNext()) {
                JSONObject stateJson = statesIterator.next();
                String stateName = (String) stateJson.get("name");

                ArrayList<Rule> rulesList = new ArrayList<>();
                JSONArray expressionsArray = (JSONArray) stateJson.get("expressions");
                Iterator<JSONObject> expressionsIterator = expressionsArray.iterator();
                while (expressionsIterator.hasNext()) {
                    JSONObject expressionJson = expressionsIterator.next();
                    String regularExpression = (String) expressionJson.get("regular_expression");
                    String processing = (String) expressionJson.get("processing");
                    String tokenId = (String) expressionJson.get("token_id");
                    String targetState = (String) expressionJson.get("target_state");

                    Rule rule = new Rule(regularExpression, processing, tokenId,targetState);
                    rulesList.add(rule);
                }

                State state = new State(stateName, rulesList);
                statesList.add(state);
            }
            return statesList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Symbol> readerJsonRules(String jsonString) {

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(jsonString));
            JSONObject jsonObject = (JSONObject) obj;

            ArrayList<Symbol> symbolList = new ArrayList<>();

            JSONArray nonTerminal = (JSONArray) jsonObject.get("NonTerminal");
            NaoTerminal naoTerminal;
            Iterator<JSONObject> nonTerminalIterator = nonTerminal.iterator();
            while (nonTerminalIterator.hasNext()) {
                JSONObject symbolJson = nonTerminalIterator.next();
                
                String name = (String) symbolJson.get("name");
                ArrayList<String> symbols = (ArrayList<String>) symbolJson.get("symbols");
                String type = (String) symbolJson.get("type");
                Map<String, List<String>> options =  (Map<String, List<String>>) symbolJson.get("terminais");
                String action = (String) symbolJson.get("action");
                
                if ((options!= null)) {
                	naoTerminal = new NaoTerminal(name, symbols, type, options);
                }
                else if ((action!= null)) {
                	naoTerminal = new NaoTerminal(name, symbols, type, action);
                }
                
                else {
                	naoTerminal = new NaoTerminal(name, symbols, type);
                }
                symbolList.add(naoTerminal);
            }
            return symbolList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Symbol> readerJsonRules(String jsonString, ArrayList<Symbol> symbolList) {

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(jsonString));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray terminal = (JSONArray) jsonObject.get("Terminal");
            Iterator<JSONObject> terminalIterator = terminal.iterator();
            while (terminalIterator.hasNext()) {
                JSONObject symbolJson = terminalIterator.next();
                
                String name = (String) symbolJson.get("name");
                ArrayList<String> symbols = (ArrayList<String>) symbolJson.get("symbols");
                Terminal terminalTemp = new Terminal(name, symbols);
                symbolList.add(terminalTemp);
            }
            return symbolList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	public static String readJavaFile(String file) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }
}