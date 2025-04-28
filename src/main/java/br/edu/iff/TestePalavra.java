package br.edu.iff;


import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;


public class TestePalavra {


    // Mock simples de LetraFactory para testes
    static class LetraFactoryMock implements LetraFactory {
        @Override
        public Letra getLetra(char codigo) {
            return new LetraMock(codigo);
        }


        @Override
        public Letra getLetraEncoberta() {
            return new LetraMock('_');
        }
    }


    // Mock simples de Letra para testes
    static class LetraMock extends Letra {
        public LetraMock(char codigo) {
            super(codigo);
        }


        @Override
        public void exibir(Object contexto) {
            System.out.print(this.getCodigo());
        }
    }


    public static void main(String[] args) {
        // 1. Configurar a LetraFactory
        LetraFactoryMock factory = new LetraFactoryMock();
        Palavra.setLetraFactory(factory);


        // 2. Criar um tema para teste
        Tema tema = Tema.criar(1, "Animais");


        // 3. Testar criação de palavra
        System.out.println("=== TESTE CRIAÇÃO ===");
        Palavra palavra = Palavra.criar(1, "CACHORRO", tema);
        System.out.println("Palavra criada: " + palavra.toString());
        System.out.println("Tema: " + palavra.getTema().getNome());
        System.out.println("Tamanho: " + palavra.getTamanho());


        // 4. Testar exibição
        System.out.println("\n=== TESTE EXIBIÇÃO ===");
        System.out.print("Palavra completa: ");
        palavra.exibir(System.out);
       
        System.out.print("\nPalavra encoberta: ");
        boolean[] posicoes = new boolean[palavra.getTamanho()];
        palavra.exibir(System.out, posicoes);


        // 5. Testar tentativas de letras
        System.out.println("\n\n=== TESTE TENTATIVAS ===");
        testarTentativa(palavra, 'C');
        testarTentativa(palavra, 'A');
        testarTentativa(palavra, 'Z'); // Letra que não existe
        testarTentativa(palavra, 'O');
        testarTentativa(palavra, 'R');


        // 6. Testar comparação
       // Testes atualizados para case-insensitive
testarComparacao(palavra, "CACHORRO", true);   // Maiúsculas → true
testarComparacao(palavra, "cachorro", true);   // Minúsculas → true
testarComparacao(palavra, "CaChOrRo", true);   // Misturado → true
testarComparacao(palavra, "GATO", false);      // Palavra diferente → false


        // 7. Testar reconstrução
        System.out.println("\n=== TESTE RECONSTRUÇÃO ===");
        Palavra reconstruida = Palavra.reconstruir(2, "ELEFANTE", tema);
        System.out.print("Palavra reconstruída: ");
        reconstruida.exibir(System.out);


        // 8. Testar letras individuais
        System.out.println("\n\n=== TESTE LETRAS INDIVIDUAIS ===");
        testarGetLetra(palavra, 0); // 'C'
        testarGetLetra(palavra, 3); // 'H'
        testarGetLetra(palavra, -1); // Posição inválida
        testarGetLetra(palavra, 10); // Posição inválida
    }


    private static void testarTentativa(Palavra palavra, char letra) {
        System.out.print("\nTentando letra '" + letra + "': ");
        int[] posicoes = palavra.tentar(letra);
        if (posicoes.length > 0) {
            System.out.print("Encontrada nas posições: ");
            for (int pos : posicoes) {
                System.out.print(pos + " ");
            }
        } else {
            System.out.print("Letra não encontrada");
        }
    }


    private static void testarComparacao(Palavra palavra, String comparacao, boolean esperado) {
        boolean resultado = palavra.comparar(comparacao);
        System.out.println("Comparando com '" + comparacao + "': " +
                         (resultado == esperado ? "OK" : "FALHA") +
                         " (Resultado: " + resultado + ", Esperado: " + esperado + ")");
    }


    private static void testarGetLetra(Palavra palavra, int posicao) {
        System.out.print("\nObtendo letra na posição " + posicao + ": ");
        try {
            Letra letra = palavra.getLetra(posicao);
            System.out.println("Letra: " + letra.getCodigo());
        } catch (RuntimeException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
}
