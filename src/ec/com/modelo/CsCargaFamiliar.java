package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cs_carga_familiar database table.
 * 
 */
@Entity
@Table(name="cs_carga_familiar")
@NamedQuery(name="CsCargaFamiliar.findAll", query="SELECT c FROM CsCargaFamiliar c")

public class CsCargaFamiliar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="carg_id")
	private Integer cargId;

	@Column(name="carg_estado")
	private String cargEstado;

	@Column(name="pg_parent")
	private Integer pgParent;

	//bi-directional many-to-one association to CsAfiliado
	@ManyToOne
	@JoinColumn(name="afil_id")
	private CsAfiliado csAfiliado;

	//bi-directional many-to-one association to CsPersona
	@ManyToOne
	@JoinColumn(name="pers_id")
	private CsPersona csPersona;

	public CsCargaFamiliar() {
	}

	public Integer getCargId() {
		return this.cargId;
	}

	public void setCargId(Integer cargId) {
		this.cargId = cargId;
	}

	public String getCargEstado() {
		return this.cargEstado;
	}

	public void setCargEstado(String cargEstado) {
		this.cargEstado = cargEstado;
	}

	public Integer getPgParent() {
		return this.pgParent;
	}

	public void setPgParent(Integer pgParent) {
		this.pgParent = pgParent;
	}

	public CsAfiliado getCsAfiliado() {
		return this.csAfiliado;
	}

	public void setCsAfiliado(CsAfiliado csAfiliado) {
		this.csAfiliado = csAfiliado;
	}

	public CsPersona getCsPersona() {
		return this.csPersona;
	}

	public void setCsPersona(CsPersona csPersona) {
		this.csPersona = csPersona;
	}

	@Override
	public String toString() {
		return "CsCargaFamiliar [cargId=" + cargId + ", cargEstado=" + cargEstado + ", pgParent=" + pgParent
				+ ", csAfiliado=" + csAfiliado + ", csPersona=" + csPersona + "]";
	}
	

}