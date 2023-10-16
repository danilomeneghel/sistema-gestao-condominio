package condominio.dao;

import condominio.domain.Condominio;
import condominio.domain.PessoaFisica;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PessoaFisicaDao extends PagingAndSortingRepository<PessoaFisica, Long> {

	Boolean existsByCpfAndCondominio(String cpf, Condominio condominio);

	Boolean existsByCpfAndCondominioAndIdPessoaNot(String cpf, Condominio condominio, Long idPessoa);

}
