package br.edu.iff.bancodepalavras.dominio.letra;

public abstract class Letra {
    private char codigo;  

    protected Letra(char codigo) {
        this.codigo = codigo;
    }

    public char getCodigo() {
        return codigo;
    }

    public abstract void exibir(Object contexto);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Letra)) return false;
        Letra outra = (Letra) o;
        return this.codigo == outra.codigo 
               && this.getClass() == outra.getClass();
    }

    @Override
    public int hashCode() {
        return Character.hashCode(codigo) + this.getClass().hashCode();
    }

    @Override
    public final String toString() {
        return String.valueOf(codigo);
    }
}