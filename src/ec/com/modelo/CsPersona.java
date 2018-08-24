package ec.com.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import java.util.Arrays;
import java.util.List;


/**
 * The persistent class for the cs_persona database table.
 * 
 */


@Entity
@Table(name="cs_persona")
@NamedQueries({
@NamedQuery(name="CsPersona.findAll", query="SELECT c FROM CsPersona c"),
@NamedQuery(name="CsPersona.BuscarPersona", query="SELECT c FROM CsPersona c where lower(c.persApellidos) like :cadena or lower(c.persNombres) like :cadena order by c.persId"),
@NamedQuery(name="CsPersona.BuscarPersonaById", query="SELECT c FROM CsPersona c where c.persId= :patron"),
})

public class CsPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pers_id")
	private Integer persId;

	@Column(name="pers_apellidos")
	private String persApellidos;

	@Column(name="pers_cedula")
	private String persCedula;

	@Column(name="pers_direccion")
	private String persDireccion;

	@Column(name="pers_edad")
	private Integer persEdad;

	@Column(name="pers_estado")
	private String persEstado;

	@Column(name="pers_fech_nac")
	private Timestamp persFechNac;
	
	@Column(name="pers_foto")
	private byte[] persFoto;

	@Column(name="pers_lugar_nac")
	private String persLugarNac;

	@Column(name="pers_lugar_trabajo")
	private String persLugarTrabajo;

	@Column(name="pers_nombres")
	private String persNombres;

	@Column(name="pers_telefono")
	private String persTelefono;

	@Column(name="pg_barrio")
	private Integer pgBarrio;

	@Column(name="pg_est_civil")
	private Integer pgEstCivil;

	@Column(name="pg_genero")
	private Integer pgGenero;

	@Column(name="pg_niv_edu")
	private Integer pgNivEdu;

	@Column(name="pg_profesion")
	private Integer pgProfesion;

	//bi-directional many-to-one association to CsAfiliado
	@OneToMany(mappedBy="csPersona",cascade=CascadeType.ALL)
	private List<CsAfiliado> csAfiliados;

	//bi-directional many-to-one association to CsCargaFamiliar
	@OneToMany(mappedBy="csPersona",cascade=CascadeType.ALL)
	private List<CsCargaFamiliar> csCargaFamiliars;

	public CsPersona() {
	}

	public Integer getPersId() {
		return this.persId;
	}

	public void setPersId(Integer persId) {
		this.persId = persId;
	}

	public String getPersApellidos() {
		return this.persApellidos;
	}

	public void setPersApellidos(String persApellidos) {
		this.persApellidos = persApellidos;
	}

	public String getPersCedula() {
		return this.persCedula;
	}

	public void setPersCedula(String persCedula) {
		this.persCedula = persCedula;
	}

	public String getPersDireccion() {
		return this.persDireccion;
	}

	public void setPersDireccion(String persDireccion) {
		this.persDireccion = persDireccion;
	}

	public Integer getPersEdad() {
		return this.persEdad;
	}

	public void setPersEdad(Integer persEdad) {
		this.persEdad = persEdad;
	}

	public String getPersEstado() {
		return this.persEstado;
	}

	public void setPersEstado(String persEstado) {
		this.persEstado = persEstado;
	}

	public Timestamp getPersFechNac() {
		return this.persFechNac;
	}

	public void setPersFechNac(Timestamp persFechNac) {
		this.persFechNac = persFechNac;
	}

	public byte[] getPersFoto() {
		return this.persFoto;
	}

	public void setPersFoto(byte[] persFoto) {
		this.persFoto = persFoto;
	}

	public String getPersLugarNac() {
		return this.persLugarNac;
	}

	public void setPersLugarNac(String persLugarNac) {
		this.persLugarNac = persLugarNac;
	}

	public String getPersLugarTrabajo() {
		return this.persLugarTrabajo;
	}

	public void setPersLugarTrabajo(String persLugarTrabajo) {
		this.persLugarTrabajo = persLugarTrabajo;
	}

	public String getPersNombres() {
		return this.persNombres;
	}

	public void setPersNombres(String persNombres) {
		this.persNombres = persNombres;
	}

	public String getPersTelefono() {
		return this.persTelefono;
	}

	public void setPersTelefono(String persTelefono) {
		this.persTelefono = persTelefono;
	}

	public Integer getPgBarrio() {
		return this.pgBarrio;
	}

	public void setPgBarrio(Integer pgBarrio) {
		this.pgBarrio = pgBarrio;
	}

	public Integer getPgEstCivil() {
		return this.pgEstCivil;
	}

	public void setPgEstCivil(Integer pgEstCivil) {
		this.pgEstCivil = pgEstCivil;
	}

	public Integer getPgGenero() {
		return this.pgGenero;
	}

	public void setPgGenero(Integer pgGenero) {
		this.pgGenero = pgGenero;
	}

	public Integer getPgNivEdu() {
		return this.pgNivEdu;
	}

	public void setPgNivEdu(Integer pgNivEdu) {
		this.pgNivEdu = pgNivEdu;
	}

	public Integer getPgProfesion() {
		return this.pgProfesion;
	}

	public void setPgProfesion(Integer pgProfesion) {
		this.pgProfesion = pgProfesion;
	}

	public List<CsAfiliado> getCsAfiliados() {
		return this.csAfiliados;
	}

	public void setCsAfiliados(List<CsAfiliado> csAfiliados) {
		this.csAfiliados = csAfiliados;
	}

	public CsAfiliado addCsAfiliado(CsAfiliado csAfiliado) {
		getCsAfiliados().add(csAfiliado);
		csAfiliado.setCsPersona(this);

		return csAfiliado;
	}

	public CsAfiliado removeCsAfiliado(CsAfiliado csAfiliado) {
		getCsAfiliados().remove(csAfiliado);
		csAfiliado.setCsPersona(null);

		return csAfiliado;
	}

	public List<CsCargaFamiliar> getCsCargaFamiliars() {
		return this.csCargaFamiliars;
	}

	public void setCsCargaFamiliars(List<CsCargaFamiliar> csCargaFamiliars) {
		this.csCargaFamiliars = csCargaFamiliars;
	}

	public CsCargaFamiliar addCsCargaFamiliar(CsCargaFamiliar csCargaFamiliar) {
		getCsCargaFamiliars().add(csCargaFamiliar);
		csCargaFamiliar.setCsPersona(this);

		return csCargaFamiliar;
	}

	public CsCargaFamiliar removeCsCargaFamiliar(CsCargaFamiliar csCargaFamiliar) {
		getCsCargaFamiliars().remove(csCargaFamiliar);
		csCargaFamiliar.setCsPersona(null);

		return csCargaFamiliar;
	}
	


	@Override
	public String toString() {
		return "CsPersona [persId=" + persId + ", persApellidos=" + persApellidos + ", persCedula=" + persCedula
				+ ", persDireccion=" + persDireccion + ", persEdad=" + persEdad + ", persEstado=" + persEstado
				+ ", persFechNac=" + persFechNac + ", persLugarNac="
				+ persLugarNac + ", persLugarTrabajo=" + persLugarTrabajo + ", persNombres=" + persNombres
				+ ", persTelefono=" + persTelefono + ", pgBarrio=" + pgBarrio + ", pgEstCivil=" + pgEstCivil
				+ ", pgGenero=" + pgGenero + ", pgNivEdu=" + pgNivEdu + ", pgProfesion=" + pgProfesion
				+ ", csAfiliados=" + csAfiliados + ", csCargaFamiliars=" + csCargaFamiliars + "]";
	}
	
	

}