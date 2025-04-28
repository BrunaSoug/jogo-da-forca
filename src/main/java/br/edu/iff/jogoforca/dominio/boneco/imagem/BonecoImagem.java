package br.edu.iff.jogoforca.dominio.boneco.imagem;

public class BonecoImagem {
    private static BonecoImagem soleInstance;
    private BonecoImagem() {}

    public static BonecoImagem getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoImagem();
        }
        return soleInstance;
    }
    
    public void exibir(Object contexto, int partes) {}
}