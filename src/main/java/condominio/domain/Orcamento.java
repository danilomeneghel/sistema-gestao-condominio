package condominio.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
@Entity
@Table(name = "orcamentos")
public class Orcamento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idorcamento")
	private Long idOrcamento;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idperiodo")
	private Periodo periodo;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsubcategoria")
	private Subcategoria subcategoria;

	@NotNull
	@Min(0)
	private BigDecimal orcado;

	public Long getIdOrcamento() {
		return idOrcamento;
	}

	public void setIdOrcamento(Long idOrcamento) {
		this.idOrcamento = idOrcamento;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public BigDecimal getOrcado() {
		return orcado;
	}

	public void setOrcado(BigDecimal orcado) {
		this.orcado = orcado;
	}

	@Override
	public String toString() {
		return periodo.toString() + " - " + subcategoria.getDescricao();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idOrcamento == null) ? 0 : idOrcamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Orcamento other = (Orcamento) obj;
		if (idOrcamento == null) {
			if (other.idOrcamento != null) {
				return false;
			}
		} else if (!idOrcamento.equals(other.idOrcamento)) {
			return false;
		}
		return true;
	}

}