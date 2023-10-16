package app.condominio.dao;

import app.condominio.domain.Categoria;
import app.condominio.domain.Condominio;
import app.condominio.domain.enums.TipoCategoria;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoriaDao extends PagingAndSortingRepository<Categoria, Long> {

	Boolean existsByOrdemAndCondominio(String ordem, Condominio condominio);

	Boolean existsByOrdemAndCondominioAndIdCategoriaNot(String ordem, Condominio condominio, Long idCategoria);

	List<Categoria> findAllByCondominioAndTipo(Condominio condominio, TipoCategoria tipo);

}
