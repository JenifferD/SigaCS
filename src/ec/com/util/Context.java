package ec.com.util;

import java.sql.Connection;

import ec.com.modelo.Acceso;
import ec.com.modelo.CsAfiliado;
import ec.com.modelo.CsCargaFamiliar;
import ec.com.modelo.CsDocumento;
import ec.com.modelo.CsPersona;
import ec.com.modelo.Usuario;
import javafx.stage.Stage;

public class Context {
	private final static Context instance = new Context();
	Connection conexion = null;
	private String usuario;
	private int codigo;
	private String perfil;
	private int idPerfil;
	private String clave;
	private Stage stage;
	private Stage stageModal;
	private Usuario usuarios;
	private Acceso accesos;
	private CsCargaFamiliar CargaFamiliares;
	private CsDocumento Documentos;
	private CsAfiliado Afiliado;
	private int codigo_afil;
	private String ruc;
	
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	
	public int getCodigo_afil() {
		return codigo_afil;
	}
	public void setCodigo_afil(int codigo_afil) {
		this.codigo_afil = codigo_afil;
	}
	public CsAfiliado getAfiliado() {
		return Afiliado;
	}
	public void setAfiliado(CsAfiliado afiliado) {
		Afiliado = afiliado;
	}
	public CsDocumento getDocumentos() {
		return Documentos;
	}
	public void setDocumentos(CsDocumento documentos) {
		Documentos = documentos;
	}
	public CsCargaFamiliar getCargaFamiliares() {
		return CargaFamiliares;
	}
	public void setCargaFamiliares(CsCargaFamiliar cargaFamiliares) {
		CargaFamiliares = cargaFamiliares;
	}
	
	public Acceso getAccesos() {
		return accesos;
	}
	public void setAccesos(Acceso accesos) {
		this.accesos = accesos;
	}
	public Connection getConexion() {
		return conexion;
	}
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public Stage getStageModal() {
		return stageModal;
	}
	public void setStageModal(Stage stageModal) {
		this.stageModal = stageModal;
	}
	public Usuario getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Usuario usuarios) {
		this.usuarios = usuarios;
	}
	public static Context getInstance() {
		return instance;
	}
	public int getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}
	
	
	
	
	
}
