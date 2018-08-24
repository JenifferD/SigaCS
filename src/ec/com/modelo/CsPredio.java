package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cs_predio database table.
 * 
 */
@Entity
@Table(name="cs_predio")
@NamedQueries({
@NamedQuery(name="CsPredio.findAll", query="SELECT c FROM CsPredio c"),
@NamedQuery(name="CsPredio.BuscarPredio", query="SELECT c FROM CsPredio c where lower(c.predNombre) like lower(:cadena) order by c.predId"),

})
public class CsPredio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pred_id")
	private Integer predId;

	@Column(name="pg_tpredio")
	private Integer pgTpredio;

	@Column(name="pred_area_total")
	private double predAreaTotal;

	@Column(name="pred_estado")
	private String predEstado;

	@Column(name="pred_este_mts")
	private double predEsteMts;

	@Column(name="pred_lindeste")
	private String predLindeste;

	@Column(name="pred_lindnort")
	private String predLindnort;

	@Column(name="pred_lindoeste")
	private String predLindoeste;

	@Column(name="pred_lindsur")
	private String predLindsur;

	@Column(name="pred_nombre")
	private String predNombre;

	@Column(name="pred_nort_mts")
	private double predNortMts;

	@Column(name="pred_oeste_mts")
	private double predOesteMts;

	@Column(name="pred_sur_mts")
	private double predSurMts;

	//bi-directional many-to-one association to CsAsignacion
	@OneToMany(mappedBy="csPredio")
	private List<CsAsignacion> csAsignacions;

	public CsPredio() {
	}

	public Integer getPredId() {
		return this.predId;
	}

	public void setPredId(Integer predId) {
		this.predId = predId;
	}

	public Integer getPgTpredio() {
		return this.pgTpredio;
	}

	public void setPgTpredio(Integer pgTpredio) {
		this.pgTpredio = pgTpredio;
	}

	public double getPredAreaTotal() {
		return this.predAreaTotal;
	}

	public void setPredAreaTotal(double predAreaTotal) {
		this.predAreaTotal = predAreaTotal;
	}

	public String getPredEstado() {
		return this.predEstado;
	}

	public void setPredEstado(String predEstado) {
		this.predEstado = predEstado;
	}

	public double getPredEsteMts() {
		return this.predEsteMts;
	}

	public void setPredEsteMts(double predEsteMts) {
		this.predEsteMts = predEsteMts;
	}

	public String getPredLindeste() {
		return this.predLindeste;
	}

	public void setPredLindeste(String predLindeste) {
		this.predLindeste = predLindeste;
	}

	public String getPredLindnort() {
		return this.predLindnort;
	}

	public void setPredLindnort(String predLindnort) {
		this.predLindnort = predLindnort;
	}

	public String getPredLindoeste() {
		return this.predLindoeste;
	}

	public void setPredLindoeste(String predLindoeste) {
		this.predLindoeste = predLindoeste;
	}

	public String getPredLindsur() {
		return this.predLindsur;
	}

	public void setPredLindsur(String predLindsur) {
		this.predLindsur = predLindsur;
	}

	public String getPredNombre() {
		return this.predNombre;
	}

	public void setPredNombre(String predNombre) {
		this.predNombre = predNombre;
	}

	public double getPredNortMts() {
		return this.predNortMts;
	}

	public void setPredNortMts(double predNortMts) {
		this.predNortMts = predNortMts;
	}

	public double getPredOesteMts() {
		return this.predOesteMts;
	}

	public void setPredOesteMts(double predOesteMts) {
		this.predOesteMts = predOesteMts;
	}

	public double getPredSurMts() {
		return this.predSurMts;
	}

	public void setPredSurMts(double predSurMts) {
		this.predSurMts = predSurMts;
	}

	public List<CsAsignacion> getCsAsignacions() {
		return this.csAsignacions;
	}

	public void setCsAsignacions(List<CsAsignacion> csAsignacions) {
		this.csAsignacions = csAsignacions;
	}

	public CsAsignacion addCsAsignacion(CsAsignacion csAsignacion) {
		getCsAsignacions().add(csAsignacion);
		csAsignacion.setCsPredio(this);

		return csAsignacion;
	}

	public CsAsignacion removeCsAsignacion(CsAsignacion csAsignacion) {
		getCsAsignacions().remove(csAsignacion);
		csAsignacion.setCsPredio(null);

		return csAsignacion;
	}

}