package condominio.service;

import condominio.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface MovimentoService extends CrudService<Movimento, Long> {

	public BigDecimal somaLancamentosEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim, Boolean reducao);

	public BigDecimal somaLancamentosEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim,
			Subcategoria subcategoria);

	public BigDecimal somaLancamentosPeriodo(Collection<Conta> contas, Periodo periodo, Subcategoria subcategoria);

	public BigDecimal somaLancamentosPeriodo(Collection<Conta> contas, Periodo periodo, Categoria categoria);

	public BigDecimal somaLancamentosDesde(Collection<Conta> contas, LocalDate inicio, Boolean reducao);

	public List<Lancamento> listarLancamentosEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim);

}
