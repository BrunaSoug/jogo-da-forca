package br.edu.iff.repository;

public class RepositoryException extends Exception {
    
    private static final long serialVersionUID = 1L;  

    public RepositoryException() {
        super("Erro no repositório!");
    }

    public RepositoryException(String mensagem) {
        super(mensagem);
    }

    public RepositoryException(Throwable causa) {
        super(causa);
    }

    public RepositoryException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}