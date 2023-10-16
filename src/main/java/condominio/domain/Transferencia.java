package condominio.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "transferencias")
@PrimaryKeyJoinColumn(name = "idmovimento")
public class Transferencia extends Movimento {

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcontainversa")
	private Conta contaInversa;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmovimentoinverso")
	private Movimento movimentoInverso;

	public Conta getContaInversa() {
		return contaInversa;
	}

	public void setContaInversa(Conta contaInversa) {
		this.contaInversa = contaInversa;
	}

	public Movimento getMovimentoInverso() {
		return movimentoInverso;
	}

	public void setMovimentoInverso(Movimento movimentoInverso) {
		this.movimentoInverso = movimentoInverso;
	}

	@Override
	public String detalhe() {
		if (getReducao()) {
			return "Transferência de saída";
		} else {
			return "Transferência de entrada";
		}
	}

}
