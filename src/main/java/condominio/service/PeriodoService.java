package condominio.service;

import condominio.domain.Periodo;

import java.time.LocalDate;

public interface PeriodoService extends CrudService<Periodo, Long> {

	public boolean haPeriodo(LocalDate data);

	public Periodo ler(LocalDate data);

}
