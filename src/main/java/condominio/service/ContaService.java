package condominio.service;

import condominio.domain.Conta;

import java.math.BigDecimal;

public interface ContaService extends CrudService<Conta, Long> {

	public BigDecimal saldoAtual();

}
