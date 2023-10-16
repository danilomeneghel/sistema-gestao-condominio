package condominio.service;

import condominio.domain.Condominio;

public interface CondominioService extends CrudService<Condominio, Long> {

	public Condominio ler();

}
