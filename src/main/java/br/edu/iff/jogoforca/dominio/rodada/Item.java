package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.dominio.ObjetoDominioImpl;

public class Item extends ObjetoDominioImpl {

    private Palavra palavra;
    private boolean[] posicoesDescobertas;
    private String palavraArriscada = null;

    protected static Item criar(int id, Palavra palavra) {
        return new Item(id, palavra);
    }

    public static Item reconstituir(int id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
        return new Item(id, palavra, posicoesDescobertas, palavraArriscada);
    }

    private Item(int id, Palavra palavra) {
        super(id);
        this.palavra = palavra;
        this.posicoesDescobertas = new boolean[this.palavra.getTamanho()];
    }

    private Item(int id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
        super(id);
        this.palavra = palavra;
        this.posicoesDescobertas = new boolean[this.palavra.getTamanho()];
        for (int posicao : posicoesDescobertas) {
            this.posicoesDescobertas[posicao] = true;
        }
        this.palavraArriscada = palavraArriscada;
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public Letra[] getLetrasDescobertas() {
        Letra[] letras = new Letra[this.palavra.getTamanho()];
        int index = 0;
        for (int i = 0; i < this.palavra.getTamanho(); i++) {
            if (this.posicoesDescobertas[i]) {
                letras[index++] = this.palavra.getLetra(i);
            }
        }
        return letras;
    }

    public Letra[] getLetrasEncobertas() {
        Letra[] letras = new Letra[this.palavra.getTamanho()];
        int index = 0;
        for (int i = 0; i < this.palavra.getTamanho(); i++) {
            if (!this.posicoesDescobertas[i]) {
                letras[index++] = this.palavra.getLetra(i);
            }
        }
        return letras;
    }

    public int qtdeLetrasEncobertas() {
        int count = 0;
        for (boolean descoberta : this.posicoesDescobertas) {
            if (!descoberta) count++;
        }
        return count;
    }

    public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
        return this.qtdeLetrasEncobertas() * valorPorLetraEncoberta;
    }

    public boolean descobriu() {
        return this.acertou() || this.qtdeLetrasEncobertas() == 0;
    }

    public void exibir(Object contexto) {
        this.palavra.exibir(contexto, this.posicoesDescobertas);
    }

    protected boolean tentar(char codigo) {
        int[] posicoes = palavra.tentar(codigo);
        for (int posicao : posicoes) {
            posicoesDescobertas[posicao] = true;
        }
        return posicoes.length > 0;
    }

    protected void arriscar(String palavra) {
        this.palavraArriscada = palavra;
    }

    public String getPalavraArriscada() {
        return this.palavraArriscada;
    }

    public boolean arriscou() {
        return this.palavraArriscada != null;
    }

    public boolean acertou() {
        return this.palavra.comparar(this.palavraArriscada);
    }
}