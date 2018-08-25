package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the comuna database table.
 * 
 */
@Entity
@Table(name="comuna")
@NamedQueries({
@NamedQuery(name="Comuna.findAll", query="SELECT c FROM Comuna c"),
@NamedQuery(name="Comuna.MostrarComuna", query="SELECT c FROM Comuna c where c.comuRuc = (:patron) "),
})
public class Comuna implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="comu_id")
	private Integer comuId;

	@Column(name="comu_barrio")
	private String comuBarrio;

	@Column(name="comu_descrip")
	private String comuDescrip;

	@Column(name="comu_direccion")
	private String comuDireccion;

	@Column(name="comu_email")
	private String comuEmail;

	@Column(name="comu_estado")
	private String comuEstado;

	@Column(name="comu_logo")
	private String comuLogo;

	@Column(name="comu_razon_soc")
	private String comuRazonSoc;

	@Column(name="comu_ruc")
	private String comuRuc;

	@Column(name="comu_telefono")
	private String comuTelefono;

	public Comuna() {
	}

	public Integer getComuId() {
		return this.comuId;
	}

	public void setComuId(Integer comuId) {
		this.comuId = comuId;
	}

	public String getComuBarrio() {
		return this.comuBarrio;
	}

	public void setComuBarrio(String comuBarrio) {
		this.comuBarrio = comuBarrio;
	}

	public String getComuDescrip() {
		return this.comuDescrip;
	}

	public void setComuDescrip(String comuDescrip) {
		this.comuDescrip = comuDescrip;
	}

	public String getComuDireccion() {
		return this.comuDireccion;
	}

	public void setComuDireccion(String comuDireccion) {
		this.comuDireccion = comuDireccion;
	}

	public String getComuEmail() {
		return this.comuEmail;
	}

	public void setComuEmail(String comuEmail) {
		this.comuEmail = comuEmail;
	}

	public String getComuEstado() {
		return this.comuEstado;
	}

	public void setComuEstado(String comuEstado) {
		this.comuEstado = comuEstado;
	}

	public String getComuLogo() {
		return this.comuLogo;
	}

	public void setComuLogo(String comuLogo) {
		this.comuLogo = comuLogo;
	}

	public String getComuRazonSoc() {
		return this.comuRazonSoc;
	}

	public void setComuRazonSoc(String comuRazonSoc) {
		this.comuRazonSoc = comuRazonSoc;
	}

	public String getComuRuc() {
		return this.comuRuc;
	}

	public void setComuRuc(String comuRuc) {
		this.comuRuc = comuRuc;
	}

	public String getComuTelefono() {
		return this.comuTelefono;
	}

	public void setComuTelefono(String comuTelefono) {
		this.comuTelefono = comuTelefono;
	}

}