package br.com.compilador.minijava.model;

import java.util.ArrayList;
import java.util.List;

class Node {
    String valor;
    List<Node> filhos;

    public Node(String valor) {
        this.valor = valor;
        this.filhos = new ArrayList<>();
    }

    public void adicionarFilho(Node filho) {
        filhos.add(filho);
    }
}

public class ArvoreSintatica {
    Node raiz;

    public ArvoreSintatica(String valor) {
        this.raiz = new Node(valor);
    }

    public void adicionarFilho(String valorPai, String valorFilho) {
        Node noPai = encontrarNoPai(raiz, valorPai);
        if (noPai != null) {
            Node novoFilho = new Node(valorFilho);
            noPai.adicionarFilho(novoFilho);
        }
    }

    private Node encontrarNoPai(Node no, String valorPai) {
        if (no != null) {
            if (no.valor.equals(valorPai)) {
                return no;
            } else {
                for (int i = no.filhos.size() - 1; i >= 0; i--) {
                    Node filho = no.filhos.get(i);
                    Node resultado = encontrarNoPai(filho, valorPai);
                    if (resultado != null) {
                        return resultado;
                    }
                }
            }
        }
        return null;
    }

    public void imprimirArvore() {
        imprimirArvoreRecursivo(raiz, 0);
    }

    private void imprimirArvoreRecursivo(Node no, int nivel) {
        if (no != null) {
            for (Node filho : no.filhos) {
                imprimirArvoreRecursivo(filho, nivel + 1);
            }

            StringBuilder indentacao = new StringBuilder();
            for (int i = 0; i < nivel; i++) {
                indentacao.append("  ");
            }

            System.out.println(indentacao + "- " + no.valor);
        }
    }
}