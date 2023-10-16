package condominio.service;

import condominio.domain.Categoria;

import java.util.List;

public interface CategoriaService extends CrudService<Categoria, Long> {

	public List<Categoria> listarReceitas();

	public List<Categoria> listarDespesas();

}
