package br.edu.iff.jogoforca.dominio.jogador.embdr;

import java.util.List;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;

public class BDRJogadorRepository implements JogadorRepository {

	private static BDRJogadorRepository soleInstance;

	public static BDRJogadorRepository getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new BDRJogadorRepository();
		}
		return soleInstance;
	}

	private BDRJogadorRepository() {

	}

	@Override
	public long getProximoId() {
		return 0;
	}

	@Override
	public Jogador getPorId(long id) {
		return null;
	}

	@Override
	public Jogador getPorNome(String nome) {
		return null;
	}

	@Override
	public void inserir(Jogador jogador) throws RepositoryException {

	}

	@Override
	public void atualizar(Jogador jogador) throws RepositoryException {

	}

	@Override
	public void remover(Jogador jogador) throws RepositoryException {

	}

    @Override
    public List<Jogador> todos() throws RepositoryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}