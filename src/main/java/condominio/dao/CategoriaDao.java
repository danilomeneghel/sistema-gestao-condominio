package condominio.dao;

import condominio.domain.Categoria;
import condominio.domain.Condominio;
import condominio.domain.enums.TipoCategoria;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoriaDao extends PagingAndSortingRepository<Categoria, Long> {

	Boolean existsByOrdemAndCondominio(String ordem, Condominio condominio);

	Boolean existsByOrdemAndCondominioAndIdCategoriaNot(String ordem, Condominio condominio, Long idCategoria);

	List<Categoria> findAllByCondominioAndTipo(Condominio condominio, TipoCategoria tipo);

}
