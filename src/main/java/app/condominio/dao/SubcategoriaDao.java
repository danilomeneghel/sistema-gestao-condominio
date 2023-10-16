package app.condominio.dao;

import app.condominio.domain.Categoria;
import app.condominio.domain.Subcategoria;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface SubcategoriaDao extends PagingAndSortingRepository<Subcategoria, Long> {

	List<Subcategoria> findAllByCategoriaPaiInOrderByCategoriaPai_OrdemAscDescricao(Collection<Categoria> categoriaPai);

	int countByCategoriaPaiIn(Collection<Categoria> categoriaPai);

	Boolean existsByDescricaoAndCategoriaPai(String descricao, Categoria categoriaPai);

	Boolean existsByDescricaoAndCategoriaPaiAndIdSubcategoriaNot(String descricao, Categoria categoriaPai,
			Long idSubcategoria);

}
