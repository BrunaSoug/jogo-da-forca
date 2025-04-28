package br.edu.iff.jogoforca.dominio.boneco.texto;

public class BonecoTexto {

    private static BonecoTexto soleInstance;
    private BonecoTexto() {}

    public static BonecoTexto getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoTexto();
        }
        return soleInstance;
    }

    private static final String[] partesBoneco = {
        "Cabeça",
        "Olho Esquerdo",
        "Olho Direito",
        "Nariz",
        "Boca",
        "Tronco",
        "Braço Esquerdo",
        "Braço Direito",
        "Perna Esquerda",
        "Perna Direita"
    };

    public void exibir(Object contexto, int partes) {
        System.out.println("=============");
        System.out.println("  Forca (" + partes + " erros)");
        System.out.println("=============");
        
        for (int i = 0; i < partes && i < partesBoneco.length; i++) {
            System.out.println("- " + partesBoneco[i]);
        }

        if (partes >= 10) {
            System.out.println("💀 Enforcado!");
        }
    }
}
