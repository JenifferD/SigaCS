package ec.com.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cs_afiliado database table.
 * 
 */@Entity
 @Table(name="cs_afiliado")
@NamedQueries({
@NamedQuery(name="CsAfiliado.MostrarAfiliado", query="SELECT a FROM CsAfiliado a where a.csPersona.persId = (:patron) "),
})
public class CsAfiliado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="afil_id")
	private Integer afilId;

	@Column(name="afil_calif")
	private String afilCalif;

	@Column(name="afil_estado")
	private String afilEstado;

	@Column(name="afil_fecha")
	private Timestamp afilFecha;

	//bi-directional many-to-one association to CsPersona
	@ManyToOne
	@JoinColumn(name="pers_id")
	private CsPersona csPersona;

	//bi-directional many-to-one association to CsAsignacion
	@OneToMany(mappedBy="csAfiliado",cascade=CascadeType.ALL)
	private List<CsAsignacion> csAsignacions;

	//bi-directional many-to-one association to CsCargaFamiliar
	@OneToMany(mappedBy="csAfiliado",cascade=CascadeType.ALL)
	private List<CsCargaFamiliar> csCargaFamiliars;

	//bi-directional many-to-one association to CsDocumento
	@OneToMany(mappedBy="csAfiliado")
	private List<CsDocumento> csDocumentos;

	//bi-directional many-to-one association to CsVivienda
	@OneToMany(mappedBy="csAfiliado")
	private List<CsVivienda> csViviendas;

	public CsAfiliado() {
	}

	public Integer getAfilId() {
		return this.afilId;
	}

	public void setAfilId(Integer afilId) {
		this.afilId = afilId;
	}

	public String getAfilCalif() {
		return this.afilCalif;
	}

	public void setAfilCalif(String afilCalif) {
		this.afilCalif = afilCalif;
	}

	public String getAfilEstado() {
		return this.afilEstado;
	}

	public void setAfilEstado(String afilEstado) {
		this.afilEstado = afilEstado;
	}

	public Timestamp getAfilFecha() {
		return this.afilFecha;
	}

	public void setAfilFecha(Timestamp afilFecha) {
		this.afilFecha = afilFecha;
	}

	public CsPersona getCsPersona() {
		return this.csPersona;
	}

	public void setCsPersona(CsPersona csPersona) {
		this.csPersona = csPersona;
	}

	public List<CsAsignacion> getCsAsignacions() {
		return this.csAsignacions;
	}

	public void setCsAsignacions(List<CsAsignacion> csAsignacions) {
		this.csAsignacions = csAsignacions;
	}

	public CsAsignacion addCsAsignacion(CsAsignacion csAsignacion) {
		getCsAsignacions().add(csAsignacion);
		csAsignacion.setCsAfiliado(this);

		return csAsignacion;
	}

	public CsAsignacion removeCsAsignacion(CsAsignacion csAsignacion) {
		getCsAsignacions().remove(csAsignacion);
		csAsignacion.setCsAfiliado(null);

		return csAsignacion;
	}

	public List<CsCargaFamiliar> getCsCargaFamiliars() {
		return this.csCargaFamiliars;
	}

	public void setCsCargaFamiliars(List<CsCargaFamiliar> csCargaFamiliars) {
		this.csCargaFamiliars = csCargaFamiliars;
	}

	public CsCargaFamiliar addCsCargaFamiliar(CsCargaFamiliar csCargaFamiliar) {
		getCsCargaFamiliars().add(csCargaFamiliar);
		csCargaFamiliar.setCsAfiliado(this);

		return csCargaFamiliar;
	}

	public CsCargaFamiliar removeCsCargaFamiliar(CsCargaFamiliar csCargaFamiliar) {
		getCsCargaFamiliars().remove(csCargaFamiliar);
		csCargaFamiliar.setCsAfiliado(null);

		return csCargaFamiliar;
	}

	public List<CsDocumento> getCsDocumentos() {
		return this.csDocumentos;
	}

	public void setCsDocumentos(List<CsDocumento> csDocumentos) {
		this.csDocumentos = csDocumentos;
	}

	public CsDocumento addCsDocumento(CsDocumento csDocumento) {
		getCsDocumentos().add(csDocumento);
		csDocumento.setCsAfiliado(this);

		return csDocumento;
	}

	public CsDocumento removeCsDocumento(CsDocumento csDocumento) {
		getCsDocumentos().remove(csDocumento);
		csDocumento.setCsAfiliado(null);

		return csDocumento;
	}

	public List<CsVivienda> getCsViviendas() {
		return this.csViviendas;
	}

	public void setCsViviendas(List<CsVivienda> csViviendas) {
		this.csViviendas = csViviendas;
	}

	public CsVivienda addCsVivienda(CsVivienda csVivienda) {
		getCsViviendas().add(csVivienda);
		csVivienda.setCsAfiliado(this);

		return csVivienda;
	}

	public CsVivienda removeCsVivienda(CsVivienda csVivienda) {
		getCsViviendas().remove(csVivienda);
		csVivienda.setCsAfiliado(null);

		return csVivienda;
	}

	@Override
	public String toString() {
		return "CsAfiliado [afilId=" + afilId + ", afilCalif=" + afilCalif + ", afilEstado=" + afilEstado
				+ ", afilFecha=" + afilFecha + ", csPersona=" + csPersona + ", csAsignacions=" + csAsignacions
				+ ", csCargaFamiliars=" + csCargaFamiliars + ", csDocumentos=" + csDocumentos + ", csViviendas="
				+ csViviendas + "]";
	}


	
}