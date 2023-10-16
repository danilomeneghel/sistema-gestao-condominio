package condominio.service;

import condominio.dao.CondominioDao;
import condominio.domain.Condominio;
import condominio.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@Transactional
public class CondominioServiceImpl implements CondominioService {

	// FIXME ao ler/editar/excluir, tratar casos do usuário alterar o ID na URL ou
	// fonte e enxergar entidades de outro usuário (todas as classes)

	@Autowired
	private CondominioDao condominioDao;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public Condominio salvar(Condominio condominio) {
		if (condominio.getIdCondominio() == null) {
			// Atualizar o ID do condomínio no cadastro do síndico
			Usuario sindico = usuarioService.lerLogado();
			sindico.setCondominio(condominio);
			usuarioService.editar(sindico);

			padronizar(condominio);
			return condominioDao.save(condominio);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Condominio ler() {
		return usuarioService.lerLogado().getCondominio();
	}

	@Override
	public Condominio editar(Condominio condominio) {
		padronizar(condominio);
		return condominioDao.save(condominio);
	}

	@Override
	public void excluir(Condominio condominio) {
		condominioDao.delete(condominio);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Condominio ler(Long id) {
		// LATER implementar ao fazer o usuário tipo ADMIN
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Condominio> listar() {
		// LATER implementar ao fazer o usuário tipo ADMIN
		return null;
	}

	@Override
	public Page<Condominio> listarPagina(Pageable pagina) {
		// LATER criar este método quando fizer página de listar condomínios (admin)
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Condominio entidade, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		if (entidade.getIdCondominio() == null) {
			// CNPJ não pode repetir
			if (entidade.getCnpj() != null && condominioDao.existsByCnpj(entidade.getCnpj())) {
				validacao.rejectValue("cnpj", "Unique");
			}
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
			// CNPJ não pode repetir
			if (entidade.getCnpj() != null
					&& condominioDao.existsByCnpjAndIdCondominioNot(entidade.getCnpj(), entidade.getIdCondominio())) {
				validacao.rejectValue("cnpj", "Unique");
			}
		}
		// VALIDAÇÕES EM AMBOS
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Condominio entidade) {
		// Nada a padronizar por enquanto
	}

}
