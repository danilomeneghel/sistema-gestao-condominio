package condominio.dao;

import condominio.domain.Condominio;
import condominio.domain.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ContaDao extends PagingAndSortingRepository<Conta, Long> {

	Page<Conta> findAllByCondominioOrderBySiglaAsc(Condominio condominio, Pageable pagina);

	Boolean existsBySiglaAndCondominio(String sigla, Condominio condominio);

	Boolean existsBySiglaAndCondominioAndIdContaNot(String sigla, Condominio condominio, Long idConta);

	@Query("select sum(saldoAtual) from #{#entityName} c where c.condominio = :condominio")
	BigDecimal sumSaldoAtualByCondominio(@Param("condominio") Condominio condominio);

}
