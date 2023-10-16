package app.condominio.dao;

import app.condominio.domain.Condominio;
import app.condominio.domain.PessoaJuridica;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PessoaJuridicaDao extends PagingAndSortingRepository<PessoaJuridica, Long> {

	Boolean existsByCnpjAndCondominio(String cnpj, Condominio condominio);

	Boolean existsByCnpjAndCondominioAndIdPessoaNot(String cnpj, Condominio condominio, Long idPessoa);

}
