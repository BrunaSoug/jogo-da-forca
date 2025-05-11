import java.util.Scanner;

import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorNaoEncontradoException;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.repository.RepositoryException;

public class ProgramaPrincipal {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Aplicacao aplicacao = Aplicacao.getSoleInstance();
            aplicacao.configurar();

            TemaRepository temaRepository = aplicacao.getRepositoryFactory().getTemaRepository();
            JogadorRepository jogadorRepository = aplicacao.getRepositoryFactory().getJogadorRepository();
            JogadorFactory jogadorFactory = aplicacao.getJogadorFactory();
            TemaFactory temaFactory = aplicacao.getTemaFactory();

            inserirTemasEPalavras(temaRepository, temaFactory);
            iniciarJogo(jogadorRepository, jogadorFactory);

        } catch (RepositoryException e) {
            System.err.println("Erro de repositório: " + e.getMessage());
        }
    }

    private static void inserirTemasEPalavras(TemaRepository temas, TemaFactory temaFactory) throws RepositoryException {
        String[] listaDeTemas = { "Jogos", "Times", "Frutas", "Filmes" };

        for (String tema : listaDeTemas) {
            temas.inserir(temaFactory.getTema(tema));
        }

        String[][] palavrasPorTema = {
            { "minecraft", "valorant", "fortnite", "zelda", "pokemon" },
            { "flamengo", "palmeiras", "chelsea", "real", "barcelona" },
            { "banana", "maca", "uva", "laranja", "melancia" },
            { "avatar", "titanic", "batman", "matrix", "interestelar" }
        };

        for (int i = 0; i < listaDeTemas.length; i++) {
            for (String palavra : palavrasPorTema[i]) {
                PalavraAppService.getSoleInstance().novaPalavra(
                    palavra,
                    temas.getPorNome(listaDeTemas[i])[0].getId()
                );
            }
        }
    }

    private static void iniciarJogo(JogadorRepository jogadores, JogadorFactory jogadorFactory) {
        try {
            String nomeJogador = obterJogador(jogadores, jogadorFactory);
            boolean continuarJogando = true;
            int pontuacaoTotal = 0;
    
            while (continuarJogando) {
                Rodada rodada = RodadaAppService.getSoleInstance().novaRodada(nomeJogador);
                String[] palavrasArriscadas = new String[rodada.getPalavra().length];
                Object contexto = null;
    
                while (!rodada.encerrou()) {
                    exibirStatusRodada(rodada, contexto);
    
                    System.out.println("\nEscolha uma opção:");
                    System.out.println(" [0] Tentar uma letra");
                    System.out.println(" [1] Arriscar as palavras");
                    System.out.print("Sua escolha: ");
                    String escolha = scanner.nextLine();
    
                    switch (escolha) {
                        case "0":
                            tentarLetra(rodada);
                            break;
                        case "1":
                            arriscarPalavras(rodada, palavrasArriscadas);
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                    }
                }
    
                exibirResultadoFinal(rodada, contexto);
                RodadaAppService.getSoleInstance().salvarRodada(rodada);
    
                int pontosRodada = rodada.calcularPontos();
                pontuacaoTotal += pontosRodada;
    
                if (rodada.descobriu()) {
                    System.out.println("Pontuação nesta rodada: " + pontosRodada);
                    System.out.println("Pontuação total acumulada: " + pontuacaoTotal);
                    System.out.print("\nDeseja jogar outra rodada? (s/n): ");
                    String resposta = scanner.nextLine().trim().toLowerCase();
                    continuarJogando = resposta.equals("s");
                } else {
                    System.out.println("\nJogo encerrado. Sua pontuação total: " + pontuacaoTotal);
                    continuarJogando = false;
                }
            }
    
        } catch (RepositoryException | JogadorNaoEncontradoException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
    
    private static String obterJogador(JogadorRepository jogadores, JogadorFactory jogadorFactory) throws RepositoryException {
        System.out.print("\nDigite seu nome para iniciar: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            nome = "Jogador";
        }

        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
        jogadores.inserir(jogadorFactory.getJogador(nome));
        return nome;
    }

    private static void tentarLetra(Rodada rodada) {
        System.out.print("\nDigite uma letra: ");
        String entrada = scanner.nextLine().trim().toLowerCase();

        if (entrada.isEmpty() || entrada.length() != 1 || entrada.charAt(0) < 'a' || entrada.charAt(0) > 'z') {
            System.out.println("Letra inválida. Tente novamente.");
        } else {
            rodada.tentar(entrada.charAt(0));
        }
    }

    private static void arriscarPalavras(Rodada rodada, String[] tentativas) {
        System.out.println("\n## Arriscar palavras ##");
        for (int i = 0; i < tentativas.length; i++) {
            System.out.print("Digite a " + (i + 1) + "ª palavra: ");
            tentativas[i] = scanner.nextLine().toLowerCase().trim();
        }
        rodada.arriscar(tentativas);
    }

    private static void exibirStatusRodada(Rodada rodada, Object contexto) {
        System.out.println("\n================================");
        System.out.println("Tema: " + rodada.getTema().getNome());
        rodada.exibirItens(contexto);
        System.out.println("\nLetras erradas:");
        rodada.exibirLetrasErradas(contexto);
        System.out.println("\nBoneco:");
        rodada.exibirBoneco(contexto);
        System.out.println("Tentativas restantes: " + rodada.getQtdeTentativasRestantes());
        System.out.println("================================");
    }

    private static void exibirResultadoFinal(Rodada rodada, Object contexto) {
        System.out.println("\n========== FIM DE JOGO ==========");
        if (rodada.descobriu()) {
            System.out.println(":) Parabéns, " + rodada.getJogador().getNome() + "! Você venceu!");
        } else {
            System.out.println(":( Que pena, " + rodada.getJogador().getNome() + ". Você perdeu.");
        }

        System.out.println("Tentativas realizadas: " + rodada.getQtdeTentativas());
        System.out.println("Letras acertadas: " + rodada.getQtdeAcertos());
        System.out.println("Pontuação final: " + rodada.calcularPontos());

        if (!rodada.descobriu()) {
            System.out.println("\nAs palavras corretas eram:");
            rodada.exibirPalavras(contexto);
        }
        System.out.println("===================================");
    }
}
