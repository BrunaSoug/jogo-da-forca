package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.dominio.ObjetoDominioImpl;

public class Jogador extends ObjetoDominioImpl {
    private String nome;
    private int pontuacao;

    protected Jogador(long id, String nome) {
        super(id);
        this.nome = nome;
        this.pontuacao = 0;
    }

    private Jogador(long id, String nome, int pontuacao) {
        super(id);
        this.nome = nome;
        this.pontuacao = pontuacao;
    }

    public static Jogador criar(long id, String nome) {
        validarNome(nome);
        return new Jogador(id, nome);
    }

    public static Jogador reconstituir(long id, String nome, int pontuacao) {
        validarNome(nome);
        return new Jogador(id, nome, pontuacao);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void atualizarPontuacao(int pontos) {
        if (pontos < 0) {
            throw new IllegalArgumentException("Pontos não podem ser negativos!");
        }
        this.pontuacao += pontos;
    }

    private static void validarNome(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome do jogador não pode ser nulo!");
        }
        if (nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do jogador não pode ser vazio!");
        }
    }
}