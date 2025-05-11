package br.edu.iff.jogoforca.dominio.jogador.emmemoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;

public class MemoriaJogadorRepository implements JogadorRepository {
    private List<Jogador> pool;
     private Map<Long, Jogador> jogadores = new HashMap<>();
	private static MemoriaJogadorRepository soleInstance;

    private MemoriaJogadorRepository() {
		this.pool = new ArrayList<Jogador>();
	}

    public static MemoriaJogadorRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaJogadorRepository();
        }
        return soleInstance;
    }

    @Override
    public Jogador getPorId(long id) {
        for (Jogador jogador : this.pool) {
            if (jogador.getId() == id) {
                return jogador;
            }
        }
        return null;
    }

    @Override
    public Jogador getPorNome(String nome) {
        if (nome == null) return null;
        for (Jogador jogador : this.pool) {
            if (jogador.getNome().equalsIgnoreCase(nome)) {
                return jogador;
            }
        }
        return null;
    }

    @Override
    public void inserir(Jogador jogador) throws RepositoryException {
        if (jogador == null) {
            throw new RepositoryException("Jogador não pode ser nulo!");
        }
        
        if (this.getPorId(jogador.getId()) != null) {
            throw new RepositoryException("Jogador com ID " + jogador.getId() + " já existe!");
        }
        
        if (this.getPorNome(jogador.getNome()) != null) {
            throw new RepositoryException("Jogador com nome " + jogador.getNome() + " já existe!");
        }
        
        this.pool.add(jogador);
    }

    @Override
    public void atualizar(Jogador jogador) throws RepositoryException {
        if (jogador == null) {
            throw new RepositoryException("Jogador não pode ser nulo!");
        }
        
        Jogador existente = this.getPorId(jogador.getId());
        if (existente == null) {
            throw new RepositoryException("Jogador não encontrado para atualização!");
        }
        
        Jogador comMesmoNome = this.getPorNome(jogador.getNome());
        if (comMesmoNome != null && comMesmoNome.getId() != jogador.getId()) {
            throw new RepositoryException("Já existe outro jogador com o nome " + jogador.getNome());
        }
        
        int index = this.pool.indexOf(existente);
        this.pool.set(index, jogador);
    }

    @Override
    public void remover(Jogador jogador) throws RepositoryException {
        if (jogador == null) {
            throw new RepositoryException("Jogador não pode ser nulo!");
        }
        
        if (!this.pool.remove(jogador)) {
            throw new RepositoryException("Jogador não encontrado para remoção!");
        }
    }
	
    @Override
	public long getProximoId() {
		return this.pool.size() + 1;
	}

    @Override
    public List<Jogador> todos() throws RepositoryException {
        return new ArrayList<>(jogadores.values());
    }
}