package app.condominio.dao;

import app.condominio.domain.Condominio;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CondominioDao extends PagingAndSortingRepository<Condominio, Long> {

	Boolean existsByCnpj(String cnpj);

	Boolean existsByCnpjAndIdCondominioNot(String cnpj, Long idCondominio);

}
