package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cs_vivienda database table.
 * 
 */
@Entity
@Table(name="cs_vivienda")
@NamedQuery(name="CsVivienda.findAll", query="SELECT c FROM CsVivienda c")
public class CsVivienda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="vivi_id")
	private Integer viviId;

	@Column(name="pg_etviv_id")
	private Integer pgEtvivId;

	@Column(name="pg_mtviv_id")
	private Integer pgMtvivId;

	@Column(name="pg_tpviv_id")
	private Integer pgTpvivId;

	@Column(name="vivi_agua_tub")
	private Boolean viviAguaTub;

	@Column(name="vivi_ener_elec")
	private Boolean viviEnerElec;

	@Column(name="vivi_estado")
	private String viviEstado;

	@Column(name="vivi_manz")
	private Integer viviManz;

	@Column(name="vivi_num")
	private Integer viviNum;

	@Column(name="vivi_num_dorm")
	private Integer viviNumDorm;

	@Column(name="vivi_serv_hig")
	private Boolean viviServHig;

	//bi-directional many-to-one association to CsAfiliado
	@ManyToOne
	@JoinColumn(name="afil_id")
	private CsAfiliado csAfiliado;

	public CsVivienda() {
	}

	public Integer getViviId() {
		return this.viviId;
	}

	public void setViviId(Integer viviId) {
		this.viviId = viviId;
	}

	public Integer getPgEtvivId() {
		return this.pgEtvivId;
	}

	public void setPgEtvivId(Integer pgEtvivId) {
		this.pgEtvivId = pgEtvivId;
	}

	public Integer getPgMtvivId() {
		return this.pgMtvivId;
	}

	public void setPgMtvivId(Integer pgMtvivId) {
		this.pgMtvivId = pgMtvivId;
	}

	public Integer getPgTpvivId() {
		return this.pgTpvivId;
	}

	public void setPgTpvivId(Integer pgTpvivId) {
		this.pgTpvivId = pgTpvivId;
	}

	public Boolean getViviAguaTub() {
		return this.viviAguaTub;
	}

	public void setViviAguaTub(Boolean viviAguaTub) {
		this.viviAguaTub = viviAguaTub;
	}

	public Boolean getViviEnerElec() {
		return this.viviEnerElec;
	}

	public void setViviEnerElec(Boolean viviEnerElec) {
		this.viviEnerElec = viviEnerElec;
	}

	public String getViviEstado() {
		return this.viviEstado;
	}

	public void setViviEstado(String viviEstado) {
		this.viviEstado = viviEstado;
	}

	public Integer getViviManz() {
		return this.viviManz;
	}

	public void setViviManz(Integer viviManz) {
		this.viviManz = viviManz;
	}

	public Integer getViviNum() {
		return this.viviNum;
	}

	public void setViviNum(Integer viviNum) {
		this.viviNum = viviNum;
	}

	public Integer getViviNumDorm() {
		return this.viviNumDorm;
	}

	public void setViviNumDorm(Integer viviNumDorm) {
		this.viviNumDorm = viviNumDorm;
	}

	public Boolean getViviServHig() {
		return this.viviServHig;
	}

	public void setViviServHig(Boolean viviServHig) {
		this.viviServHig = viviServHig;
	}

	public CsAfiliado getCsAfiliado() {
		return this.csAfiliado;
	}

	public void setCsAfiliado(CsAfiliado csAfiliado) {
		this.csAfiliado = csAfiliado;
	}

}