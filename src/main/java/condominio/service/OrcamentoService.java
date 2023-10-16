package condominio.service;

import condominio.domain.Categoria;
import condominio.domain.Orcamento;
import condominio.domain.Periodo;
import condominio.domain.Subcategoria;
import condominio.domain.enums.TipoCategoria;

import java.math.BigDecimal;

public interface OrcamentoService extends CrudService<Orcamento, Long> {

	public BigDecimal somaOrcamentos(Periodo periodo, TipoCategoria tipo);

	public BigDecimal somaOrcamentos(Periodo periodo, Categoria categoria);

	public Orcamento ler(Periodo periodo, Subcategoria subcategoria);

}
