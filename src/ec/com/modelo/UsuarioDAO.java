package ec.com.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class UsuarioDAO extends ClaseDAO{
	public List<Usuario> getUsuario(String usuario,String clave) {
		List<Usuario> resultado; 

		Query query = getEntityManager().createNamedQuery("Usuario.buscarPatron");

		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		query.setParameter("usuario", usuario);
		query.setParameter("clave", clave);

		resultado = (List<Usuario>) query.getResultList();

		return resultado;
	}
	public List<Usuario> getValidarUsuario(String usuario,int usuId) {
		List<Usuario> resultado; 

		Query query = getEntityManager().createNamedQuery("Usuario.validarUsuario");

		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		query.setParameter("usuario", usuario);
		query.setParameter("usuId", usuId);

		resultado = (List<Usuario>) query.getResultList();

		return resultado;
	}
	public List<Usuario> getListaUsuarios(){
		List<Usuario> resultado = new ArrayList<Usuario>();
		Query query = getEntityManager().createNamedQuery("Usuario.findAll");
		resultado = (List<Usuario>) query.getResultList();
		return resultado;
	}
	
	public List<Usuario> getIdUsuario(int codigo){
		List<Usuario> resultado = new ArrayList<Usuario>();
		Query query = getEntityManager().createNamedQuery("Usuario.findPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("usuId", codigo);
		resultado = (List<Usuario>) query.getResultList();
		return resultado;
	}
	
	public List<Usuario> getBuscarUsuario(String busqueda) {
		List<Usuario> resultado; 
		Query query = getEntityManager().createNamedQuery("Usuario.BuscarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("cadena", "%" + busqueda + "%");
		resultado = (List<Usuario>) query.getResultList();
		return resultado;
	}
	
	
}
