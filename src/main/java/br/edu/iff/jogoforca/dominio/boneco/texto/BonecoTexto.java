package br.edu.iff.jogoforca.dominio.boneco.texto;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;

public class BonecoTexto implements Boneco {

    private static BonecoTexto soleInstance;
    private BonecoTexto() {}

    public static BonecoTexto getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new BonecoTexto();
		}
		return soleInstance;
	}
    
    private static final String[] PARTES_BONECO = {
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

    @Override
    public void exibir(Object contexto, int partes) {
        System.out.println("=============");
        System.out.println("  Forca (" + partes + " erros)");
        System.out.println("=============");
        
        for (int i = 0; i < partes && i < PARTES_BONECO.length; i++) {
            System.out.println("- " + PARTES_BONECO[i]);
        }

        if (partes >= 10) {
            System.out.println("💀 Enforcado!");
        }
    }
}
