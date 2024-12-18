package condominio.dao;

import condominio.domain.Bloco;
import condominio.domain.Moradia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface MoradiaDao extends PagingAndSortingRepository<Moradia, Long> {

	List<Moradia> findAllByBlocoInOrderByBlocoAscSiglaAsc(Collection<Bloco> bloco);

	Page<Moradia> findAllByBlocoInOrderByBlocoAscSiglaAsc(Collection<Bloco> bloco, Pageable pagina);

	Boolean existsBySiglaAndBloco(String sigla, Bloco bloco);

	Boolean existsBySiglaAndBlocoAndIdMoradiaNot(String sigla, Bloco bloco, Long idMoradia);

}
