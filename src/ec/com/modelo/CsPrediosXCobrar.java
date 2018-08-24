package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cs_predios_x_cobrar database table.
 * 
 */
@Entity
@Table(name="cs_predios_x_cobrar")
@NamedQuery(name="CsPrediosXCobrar.findAll", query="SELECT c FROM CsPrediosXCobrar c")
public class CsPrediosXCobrar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pre_cob")
	private Integer preCob;

	@Column(name="pg_estado")
	private Integer pgEstado;

	@Column(name="pre_cob_est")
	private String preCobEst;

	@Column(name="pre_cob_valor")
	private double preCobValor;

	//bi-directional many-to-one association to CsAnioFiscal
	@ManyToOne
	@JoinColumn(name="anio_id")
	private CsAnioFiscal csAnioFiscal;

	//bi-directional many-to-one association to CsAsignacion
	@ManyToOne
	@JoinColumn(name="asig_id")
	private CsAsignacion csAsignacion;

	public CsPrediosXCobrar() {
	}

	public Integer getPreCob() {
		return this.preCob;
	}

	public void setPreCob(Integer preCob) {
		this.preCob = preCob;
	}

	public Integer getPgEstado() {
		return this.pgEstado;
	}

	public void setPgEstado(Integer pgEstado) {
		this.pgEstado = pgEstado;
	}

	public String getPreCobEst() {
		return this.preCobEst;
	}

	public void setPreCobEst(String preCobEst) {
		this.preCobEst = preCobEst;
	}

	public double getPreCobValor() {
		return this.preCobValor;
	}

	public void setPreCobValor(double preCobValor) {
		this.preCobValor = preCobValor;
	}

	public CsAnioFiscal getCsAnioFiscal() {
		return this.csAnioFiscal;
	}

	public void setCsAnioFiscal(CsAnioFiscal csAnioFiscal) {
		this.csAnioFiscal = csAnioFiscal;
	}

	public CsAsignacion getCsAsignacion() {
		return this.csAsignacion;
	}

	public void setCsAsignacion(CsAsignacion csAsignacion) {
		this.csAsignacion = csAsignacion;
	}

}