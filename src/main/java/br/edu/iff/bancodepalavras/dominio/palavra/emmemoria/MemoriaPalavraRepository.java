package br.edu.iff.bancodepalavras.dominio.palavra.emmemoria;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.repository.RepositoryException;

public class MemoriaPalavraRepository implements PalavraRepository {

    private static MemoriaPalavraRepository soleInstance;
    private final List<Palavra> pool;

    private MemoriaPalavraRepository() {
        pool = new ArrayList<>();
    }
    
    public static MemoriaPalavraRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaPalavraRepository();
        }
        return soleInstance;
    }
    
    @Override
    public Palavra getPorId(long id) {
        for (Palavra palavra : pool) {
            if (palavra.getId() == id) {
                return palavra;
            }
        }
        return null;
    }
    
    @Override
    public Palavra[] getPorTema(Tema tema) {
        List<Palavra> result = new ArrayList<>();
        for (Palavra palavra : pool) {
            if (palavra.getTema().equals(tema)) {
                result.add(palavra);
            }
        }
        return result.toArray(new Palavra[0]);
    }
    
    @Override
    public Palavra[] getTodas() {
        return pool.toArray(new Palavra[pool.size()]);
    }
    
    @Override
    public Palavra getPalavra(String palavra) {
        for (Palavra p : pool) {
            if (p.comparar(palavra) || p.toString().equalsIgnoreCase(palavra)) {
                return p;
            }
        }
        return null;
    }
    
    @Override
    public void inserir(Palavra palavra) throws RepositoryException {
        if (palavra == null) {
            throw new RepositoryException("Palavra não pode ser nula!");
        }
        if (pool.contains(palavra)) {
            throw new RepositoryException("Palavra '" + palavra + "' já existe!");
        }
        pool.add(palavra);
    }
    
    @Override
    public void atualizar(Palavra palavra) throws RepositoryException {
        if (palavra == null) {
            throw new RepositoryException("Palavra não pode ser nula!");
        }
        int index = pool.indexOf(palavra);
        if (index == -1) {
            throw new RepositoryException("Palavra '" + palavra + "' não encontrada!");
        }
        pool.set(index, palavra);
    }
    
    @Override
    public void remover(Palavra palavra) throws RepositoryException {
        if (palavra == null) {
            throw new RepositoryException("Palavra não pode ser nula!");
        }
        if (!pool.remove(palavra)) {
            throw new RepositoryException("Palavra '" + palavra + "' não encontrada!");
        }
    }
    
    @Override
    public long getProximoId() {
        return pool.size() + 1;
    }
}