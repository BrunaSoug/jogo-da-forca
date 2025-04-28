package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;

public class Rodada extends ObjetoDominioImpl {
    private static int maxPalavras = 3;
    private static int maxErros = 10;
    private static int pontosQuandoDescobreTodasAsPalavras = 100;
    private static int pontosPorLetraEncoberta = 15;
    private static BonecoFactory bonecoFactory;
    private Item[] itens;
    private Letra[] erradas;
    private Jogador jogador;
    private boolean jaArriscou = false;
    private Boneco boneco;

    public static int getMaxPalavras() { return maxPalavras; }
    public static void setMaxPalavras(int max) { maxPalavras = max; }
    
    public static int getMaxErros() { return maxErros; }
    public static void setMaxErros(int max) { maxErros = max; }
    
    public static int getPontosQuandoDescobreTodasAsPalavras() { return pontosQuandoDescobreTodasAsPalavras; }
    public static void setPontosQuandoDescobreTodasAsPalavras(int pontos) { pontosQuandoDescobreTodasAsPalavras = pontos; }
    
    public static int getPontosPorLetraEncoberta() { return pontosPorLetraEncoberta; }
    public static void setPontosPorLetraEncoberta(int pontos) { pontosPorLetraEncoberta = pontos; }
    
    public static void setBonecoFactory(BonecoFactory factory) { bonecoFactory = factory; }
    public static BonecoFactory getBonecoFactory() { 
        if (bonecoFactory == null) {
            throw new IllegalStateException("BonecoFactory não foi configurado");
        }
        return bonecoFactory; 
    }

    private Rodada(long id, Palavra[] palavras, Jogador jogador) {
        super(id);
        if (bonecoFactory == null) {
            throw new IllegalStateException("Ops... BonecoFactory deve ser configurado antes de criar uma Rodada!");
        }
        
        this.jogador = jogador;
        this.itens = new Item[palavras.length];
        for (int i = 0; i < palavras.length; i++) {
            this.itens[i] = Item.criar(i, palavras[i]);
        }
        this.erradas = new Letra[0];
        this.boneco = bonecoFactory.getBoneco();
        
        validarMesmoTema(palavras);
    }

    private Rodada(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
        super(id);
        this.itens = itens;
        this.erradas = erradas;
        this.jogador = jogador;
        this.boneco = bonecoFactory.getBoneco();
    }

    public static Rodada criar(long id, Palavra[] palavras, Jogador jogador) {
        return new Rodada(id, palavras, jogador);
    }

    public static Rodada reconstituir(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
        return new Rodada(id, itens, erradas, jogador);
    }

    public Jogador getJogador() { return jogador; }
    public Tema getTema() { return itens.length > 0 ? itens[0].getPalavra().getTema() : null; }
    public Palavra[] getPalavras() {
        Palavra[] palavras = new Palavra[itens.length];
        for (int i = 0; i < itens.length; i++) {
            palavras[i] = itens[i].getPalavra();
        }
        return palavras;
    }
    public int getNumPalavras() { return itens.length; }

    public void tentar(char codigo) {
        if (encerrou()) return;
        
        boolean acertou = false;
        for (Item item : itens) {
            if (item.tentar(codigo)) {
                acertou = true;
            }
        }
        
        if (!acertou) {
            Letra letraErrada = getPalavras()[0].getLetraFactory().getLetra(codigo);
            adicionarLetraErrada(letraErrada);
        }
        
        verificarFimRodada();
    }

    public void arriscar(String[] palavras) {
        if (encerrou() || jaArriscou) return;
        
        jaArriscou = true;
        boolean acertouTodas = true;
        
        for (int i = 0; i < itens.length; i++) {
            itens[i].arriscar(palavras[i]);
            if (!itens[i].acertou()) {
                acertouTodas = false;
            }
        }
        
        if (acertouTodas) {
            for (Item item : itens) {
                for (int i = 0; i < item.getPalavra().getTamanho(); i++) {
                    item.tentar(item.getPalavra().getLetra(i).getCodigo());
                }
            }
        }
        
        verificarFimRodada();
    }

 
    public void exibirItens(Object contexto) {
        for (Item item : itens) {
            item.exibir(contexto);
        }
    }

    public void exibirBoneco(Object contexto) {
        boneco.exibir(contexto, getQuantidadeErros());
    }

    public void exibirPalavras(Object contexto) {
        for (Palavra palavra : getPalavras()) {
            palavra.exibir(contexto);
        }
    }

    public void exibirLetrasErradas(Object contexto) {
        for (Letra letra : erradas) {
            letra.exibir(contexto);
        }
    }

    // Métodos de consulta
    public Letra[] getTentativas() {
        Letra[] tentativas = new Letra[getQuantidadeAcertos() + getQuantidadeErros()];
        System.arraycopy(getCertas(), 0, tentativas, 0, getQuantidadeAcertos());
        System.arraycopy(erradas, 0, tentativas, getQuantidadeAcertos(), getQuantidadeErros());
        return tentativas;
    }

    public Letra[] getCertas() {
        int totalCertas = getQuantidadeAcertos();
        Letra[] certas = new Letra[totalCertas];
        int index = 0;
        for (Item item : itens) {
            for (Letra letra : item.getLetrasDescobertas()) {
                certas[index++] = letra;
            }
        }
        return certas;
    }

    public Letra[] getErradas() { return erradas; }

 
    public int calcularPontos() {
        if (descobriu()) {
            int pontos = pontosQuandoDescobreTodasAsPalavras;
            pontos += contarLetrasEncobertas() * pontosPorLetraEncoberta;
            return pontos;
        }
        return 0;
    }

    public boolean encerrou() {
        return jaArriscou || descobriu() || getQuantidadeErros() >= maxErros;
    }

    public boolean descobriu() {
        for (Item item : itens) {
            if (!item.descobriu()) {
                return false;
            }
        }
        return true;
    }

    public boolean arriscou() { return jaArriscou; }

    public boolean atingiuMaxErros() {
        return getQuantidadeErros() >= maxErros;
    }

    public int getQuantidadeTentativasRestantes() {
        return maxErros - getQuantidadeErros();
    }

    public int getQuantidadeErros() { return erradas.length; }

    public int getQuantidadeAcertos() {
        int acertos = 0;
        for (Item item : itens) {
            acertos += item.getLetrasDescobertas().length;
        }
        return acertos;
    }

    public int getQuantidadeTentativas() {
        return getQuantidadeAcertos() + getQuantidadeErros();
    }

    private void adicionarLetraErrada(Letra letra) {
        Letra[] novasErradas = new Letra[erradas.length + 1];
        System.arraycopy(erradas, 0, novasErradas, 0, erradas.length);
        novasErradas[erradas.length] = letra;
        erradas = novasErradas;
    }

    private int contarLetrasEncobertas() {
        int count = 0;
        for (Item item : itens) {
            count += item.getLetrasEncobertas().length;
        }
        return count;
    }

    private void validarMesmoTema(Palavra[] palavras) {
        if (palavras.length == 0) return;
        Tema tema = palavras[0].getTema();
        for (Palavra palavra : palavras) {
            if (!palavra.getTema().equals(tema)) {
                throw new IllegalArgumentException("Todas as palavras devem ser do mesmo tema");
            }
        }
    }

    private void verificarFimRodada() {
        if (encerrou()) {
            jogador.atualizarPontuacao(calcularPontos());
        }
    }
}