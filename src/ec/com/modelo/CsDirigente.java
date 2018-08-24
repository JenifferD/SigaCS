package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cs_dirigente database table.
 * 
 */
@Entity
@Table(name="cs_dirigente")
@NamedQuery(name="CsDirigente.findAll", query="SELECT c FROM CsDirigente c")
public class CsDirigente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dirig_id")
	private Integer dirigId;

	@Column(name="dirig_apellidos")
	private String dirigApellidos;

	@Column(name="dirig_cedula")
	private String dirigCedula;

	@Column(name="dirig_estado")
	private String dirigEstado;

	@Column(name="dirig_nombres")
	private String dirigNombres;

	@Column(name="pg_cargo_id")
	private Integer pgCargoId;

	public CsDirigente() {
	}

	public Integer getDirigId() {
		return this.dirigId;
	}

	public void setDirigId(Integer dirigId) {
		this.dirigId = dirigId;
	}

	public String getDirigApellidos() {
		return this.dirigApellidos;
	}

	public void setDirigApellidos(String dirigApellidos) {
		this.dirigApellidos = dirigApellidos;
	}

	public String getDirigCedula() {
		return this.dirigCedula;
	}

	public void setDirigCedula(String dirigCedula) {
		this.dirigCedula = dirigCedula;
	}

	public String getDirigEstado() {
		return this.dirigEstado;
	}

	public void setDirigEstado(String dirigEstado) {
		this.dirigEstado = dirigEstado;
	}

	public String getDirigNombres() {
		return this.dirigNombres;
	}

	public void setDirigNombres(String dirigNombres) {
		this.dirigNombres = dirigNombres;
	}

	public Integer getPgCargoId() {
		return this.pgCargoId;
	}

	public void setPgCargoId(Integer pgCargoId) {
		this.pgCargoId = pgCargoId;
	}

}