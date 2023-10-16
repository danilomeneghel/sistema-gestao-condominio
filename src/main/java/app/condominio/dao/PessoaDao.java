package app.condominio.dao;

import app.condominio.domain.Condominio;
import app.condominio.domain.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PessoaDao extends PagingAndSortingRepository<Pessoa, Long> {

	// LATER ordenação do sobrenome?
	Page<Pessoa> findAllByCondominioOrderByNome(Condominio condominio, Pageable pagina);

}
