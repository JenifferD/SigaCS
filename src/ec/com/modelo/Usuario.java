package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQueries({
	@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u order by u.usuId"),
	@NamedQuery(name="Usuario.buscarPatron", 
	            query="SELECT u FROM Usuario u "
	            		+ "WHERE u.usuUsuario = (:usuario) AND u.usuClave = (:clave) and u.usuEstado = 'A'"),
	@NamedQuery(name="Usuario.validarUsuario", query="SELECT u FROM Usuario u "
    		+ "WHERE u.usuUsuario = (:usuario) AND u.usuId <> (:usuId) and u.usuEstado = 'A'"),
	
	@NamedQuery(name="Usuario.BuscarPatron", query="SELECT u FROM Usuario u where lower(u.usuNombres) like :cadena or lower(u.usuApellidos) like :cadena order by u.usuId "),
	
})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="usu_id")
	private Integer usuId;

	@Column(name="usu_apellidos")
	private String usuApellidos;

	@Column(name="usu_cedula")
	private String usuCedula;

	@Column(name="usu_clave")
	private String usuClave;

	@Column(name="usu_direccion")
	private String usuDireccion;

	@Column(name="usu_email")
	private String usuEmail;

	@Column(name="usu_estado")
	private String usuEstado;

	@Column(name="usu_foto")
	private byte[] usuFoto;

	@Column(name="usu_nombres")
	private String usuNombres;

	@Column(name="usu_telefono")
	private String usuTelefono;

	@Column(name="usu_usuario")
	private String usuUsuario;

	//bi-directional many-to-one association to Perfil
	@ManyToOne
	@JoinColumn(name="per_id")
	private Perfil perfil;

	public Usuario() {
	}

	public Integer getUsuId() {
		return this.usuId;
	}

	public void setUsuId(Integer usuId) {
		this.usuId = usuId;
	}

	public String getUsuApellidos() {
		return this.usuApellidos;
	}

	public void setUsuApellidos(String usuApellidos) {
		this.usuApellidos = usuApellidos;
	}

	public String getUsuCedula() {
		return this.usuCedula;
	}

	public void setUsuCedula(String usuCedula) {
		this.usuCedula = usuCedula;
	}

	public String getUsuClave() {
		return this.usuClave;
	}

	public void setUsuClave(String usuClave) {
		this.usuClave = usuClave;
	}

	public String getUsuDireccion() {
		return this.usuDireccion;
	}

	public void setUsuDireccion(String usuDireccion) {
		this.usuDireccion = usuDireccion;
	}

	public String getUsuEmail() {
		return this.usuEmail;
	}

	public void setUsuEmail(String usuEmail) {
		this.usuEmail = usuEmail;
	}

	public String getUsuEstado() {
		return this.usuEstado;
	}

	public void setUsuEstado(String usuEstado) {
		this.usuEstado = usuEstado;
	}

	public byte[] getUsuFoto() {
		return this.usuFoto;
	}

	public void setUsuFoto(byte[] usuFoto) {
		this.usuFoto = usuFoto;
	}

	public String getUsuNombres() {
		return this.usuNombres;
	}

	public void setUsuNombres(String usuNombres) {
		this.usuNombres = usuNombres;
	}

	public String getUsuTelefono() {
		return this.usuTelefono;
	}

	public void setUsuTelefono(String usuTelefono) {
		this.usuTelefono = usuTelefono;
	}

	public String getUsuUsuario() {
		return this.usuUsuario;
	}

	public void setUsuUsuario(String usuUsuario) {
		this.usuUsuario = usuUsuario;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString()
	{
			return this.usuClave;
	}

	
}