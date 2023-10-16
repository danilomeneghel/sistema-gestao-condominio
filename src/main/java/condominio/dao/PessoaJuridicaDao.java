package condominio.dao;

import condominio.domain.Condominio;
import condominio.domain.PessoaJuridica;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PessoaJuridicaDao extends PagingAndSortingRepository<PessoaJuridica, Long> {

	Boolean existsByCnpjAndCondominio(String cnpj, Condominio condominio);

	Boolean existsByCnpjAndCondominioAndIdPessoaNot(String cnpj, Condominio condominio, Long idPessoa);

}
