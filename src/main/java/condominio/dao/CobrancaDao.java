package condominio.dao;

import condominio.domain.Cobranca;
import condominio.domain.Condominio;
import condominio.domain.Moradia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CobrancaDao extends PagingAndSortingRepository<Cobranca, Long> {

	Boolean existsByNumeroAndParcelaAndDataEmissaoAndMoradiaAndCondominio(String numero, String parcela,
			LocalDate dataEmissao, Moradia moradia, Condominio condominoi);

	Boolean existsByNumeroAndParcelaAndDataEmissaoAndMoradiaAndCondominioAndIdCobrancaNot(String numero, String parcela,
			LocalDate dataEmissao, Moradia moradia, Condominio condominoi, Long idCobranca);

	@Query("select sum(total) from #{#entityName} c where c.condominio = :condominio and c.dataRecebimento is null and c.dataVencimento < :data")
	BigDecimal sumTotalByCondominioAndDataVencimentoBeforeAndDataRecebimentoIsNull(
			@Param("condominio") Condominio condominio, @Param("data") LocalDate data);

	List<Cobranca> findAllByCondominioAndDataVencimentoBeforeAndDataRecebimentoIsNullOrderByMoradiaAscDataVencimentoAsc(
			Condominio condominio, LocalDate data);

	Page<Cobranca> findAllByCondominioOrderByDataEmissaoDescMoradiaAscNumeroAscParcelaAsc(Condominio condominio,
			Pageable pagina);
}
