package br.edu.iff.jogoforca.dominio.rodada.emmemoria;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;
import br.edu.iff.repository.RepositoryException;

public class MemoriaRodadaRepository implements RodadaRepository {

    private List<Rodada> pool;
    private static MemoriaRodadaRepository soleInstance;

    public static MemoriaRodadaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaRodadaRepository();
        }
        return soleInstance;
    }

    private MemoriaRodadaRepository() {
        this.pool = new ArrayList<Rodada>();
    }

    @Override
    public long getProximoId() {
        return this.pool.size() + 1;
    }

    @Override
    public Rodada getPorId(long id) {
        for (Rodada rodada : this.pool) {
            if (rodada.getId() == id) {
                return rodada;
            }
        }
        return null;
    }

    @Override
    public Rodada[] getPorJogador(Jogador jogador) {
        List<Rodada> rodadasEscolhidos = new ArrayList<Rodada>();
        for (Rodada rodada : this.pool) {
            if (rodada.getJogador().equals(jogador)) {
                rodadasEscolhidos.add(rodada);
            }
        }
        return rodadasEscolhidos.toArray(new Rodada[rodadasEscolhidos.size()]);
    }

    @Override
    public void inserir(Rodada rodada) throws RepositoryException {
        if (rodada == null) {
            throw new RepositoryException("Não é possível inserir uma rodada nula!");
        }
        
        if (this.pool.contains(rodada)) {
            throw new RepositoryException("Rodada com ID " + rodada.getId() + " já existe no repositório!");
        }
        
        this.pool.add(rodada);
    }

    @Override
    public void atualizar(Rodada rodada) throws RepositoryException {
        if (rodada == null) {
            throw new RepositoryException("Não é possível atualizar uma rodada nula!");
        }
        
        if (!this.pool.contains(rodada)) {
            throw new RepositoryException("Rodada com ID " + rodada.getId() + " não encontrada para atualização!");
        }
        
        for (int i = 0; i < this.pool.size(); i++) {
            if (this.pool.get(i).getId() == rodada.getId()) {
                this.pool.set(i, rodada);
                return;
            }
        }
    }

    @Override
    public void remover(Rodada rodada) throws RepositoryException {
        if (rodada == null) {
            throw new RepositoryException("Não é possível remover uma rodada nula!");
        }
        
        if (!this.pool.contains(rodada)) {
            throw new RepositoryException("Rodada com ID " + rodada.getId() + " não encontrada para remoção!");
        }
    
        this.pool.remove(rodada);
    }
}