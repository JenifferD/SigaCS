package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cs_asignacion database table.
 * 
 */
@Entity
@Table(name="cs_asignacion")
@NamedQuery(name="CsAsignacion.findAll", query="SELECT c FROM CsAsignacion c")
public class CsAsignacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="asig_id")
	private Integer asigId;

	@Column(name="asig_estado")
	private String asigEstado;

	@Temporal(TemporalType.DATE)
	@Column(name="asig_fech")
	private Date asigFech;

	//bi-directional many-to-one association to CsAfiliado
	@ManyToOne
	@JoinColumn(name="afil_id")
	private CsAfiliado csAfiliado;

	//bi-directional many-to-one association to CsPredio
	@ManyToOne
	@JoinColumn(name="pred_id")
	private CsPredio csPredio;

	//bi-directional many-to-one association to CsDocumento
	@OneToMany(mappedBy="csAsignacion")
	private List<CsDocumento> csDocumentos;

	//bi-directional many-to-one association to CsPrediosXCobrar
	@OneToMany(mappedBy="csAsignacion")
	private List<CsPrediosXCobrar> csPrediosXCobrars;

	public CsAsignacion() {
	}

	public Integer getAsigId() {
		return this.asigId;
	}

	public void setAsigId(Integer asigId) {
		this.asigId = asigId;
	}

	public String getAsigEstado() {
		return this.asigEstado;
	}

	public void setAsigEstado(String asigEstado) {
		this.asigEstado = asigEstado;
	}

	public Date getAsigFech() {
		return this.asigFech;
	}

	public void setAsigFech(Date asigFech) {
		this.asigFech = asigFech;
	}

	public CsAfiliado getCsAfiliado() {
		return this.csAfiliado;
	}

	public void setCsAfiliado(CsAfiliado csAfiliado) {
		this.csAfiliado = csAfiliado;
	}

	public CsPredio getCsPredio() {
		return this.csPredio;
	}

	public void setCsPredio(CsPredio csPredio) {
		this.csPredio = csPredio;
	}

	public List<CsDocumento> getCsDocumentos() {
		return this.csDocumentos;
	}

	public void setCsDocumentos(List<CsDocumento> csDocumentos) {
		this.csDocumentos = csDocumentos;
	}

	public CsDocumento addCsDocumento(CsDocumento csDocumento) {
		getCsDocumentos().add(csDocumento);
		csDocumento.setCsAsignacion(this);

		return csDocumento;
	}

	public CsDocumento removeCsDocumento(CsDocumento csDocumento) {
		getCsDocumentos().remove(csDocumento);
		csDocumento.setCsAsignacion(null);

		return csDocumento;
	}

	public List<CsPrediosXCobrar> getCsPrediosXCobrars() {
		return this.csPrediosXCobrars;
	}

	public void setCsPrediosXCobrars(List<CsPrediosXCobrar> csPrediosXCobrars) {
		this.csPrediosXCobrars = csPrediosXCobrars;
	}

	public CsPrediosXCobrar addCsPrediosXCobrar(CsPrediosXCobrar csPrediosXCobrar) {
		getCsPrediosXCobrars().add(csPrediosXCobrar);
		csPrediosXCobrar.setCsAsignacion(this);

		return csPrediosXCobrar;
	}

	public CsPrediosXCobrar removeCsPrediosXCobrar(CsPrediosXCobrar csPrediosXCobrar) {
		getCsPrediosXCobrars().remove(csPrediosXCobrar);
		csPrediosXCobrar.setCsAsignacion(null);

		return csPrediosXCobrar;
	}

}