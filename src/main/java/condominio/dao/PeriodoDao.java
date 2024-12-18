package condominio.dao;

import condominio.domain.Condominio;
import condominio.domain.Periodo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;

public interface PeriodoDao extends PagingAndSortingRepository<Periodo, Long> {

	Page<Periodo> findAllByCondominioOrderByInicioDesc(Condominio condominio, Pageable pagina);

	boolean existsByCondominioAndInicioLessThanEqualAndFimGreaterThanEqual(Condominio condominio, LocalDate inicio,
			LocalDate fim);

	boolean existsByCondominioAndInicioLessThanEqualAndFimGreaterThanEqualAndIdPeriodoNot(Condominio condominio,
			LocalDate inicio, LocalDate fim, Long idPeriodo);

	boolean existsByCondominioAndInicioAfterAndFimBefore(Condominio condominio, LocalDate inicio, LocalDate fim);

	boolean existsByCondominioAndInicioAfterAndFimBeforeAndIdPeriodoNot(Condominio condominio, LocalDate inicio,
			LocalDate fim, Long idPeriodo);

	Periodo findOneByCondominioAndInicioLessThanEqualAndFimGreaterThanEqual(Condominio condominio, LocalDate inicio,
			LocalDate fim);

}
