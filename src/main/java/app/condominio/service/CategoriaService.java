package app.condominio.service;

import app.condominio.domain.Categoria;

import java.util.List;

public interface CategoriaService extends CrudService<Categoria, Long> {

	public List<Categoria> listarReceitas();

	public List<Categoria> listarDespesas();

}
