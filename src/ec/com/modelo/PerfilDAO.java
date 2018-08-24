package ec.com.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class PerfilDAO extends ClaseDAO{
	public List<Perfil> getListaPerfil(){
		List<Perfil> resultado = new ArrayList<Perfil>();
		Query query = getEntityManager().createNamedQuery("Perfil.findAll");
		resultado = (List<Perfil>) query.getResultList();
		return resultado;
	}
	
	public List<Perfil> getAllListaPerfil(){
		List<Perfil> resultado = new ArrayList<Perfil>();
		Query query = getEntityManager().createNamedQuery("Perfil.findAllPerfiles");
		resultado = (List<Perfil>) query.getResultList();
		return resultado;
	}
	public List<Perfil> getPerfil(int codigo){
		List<Perfil> resultado = new ArrayList<Perfil>();
		Query query = getEntityManager().createNamedQuery("Perfil.findPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("perId", codigo);
		resultado = (List<Perfil>) query.getResultList();
		return resultado;
	}
	
	public List<Perfil> getBuscarPerfil(String perNombre) {
		List<Perfil> resultado; 
		Query query = getEntityManager().createNamedQuery("Perfil.BuscarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("cadena", "%" + perNombre + "%");
		resultado = (List<Perfil>) query.getResultList();
		return resultado;
	}
	
	public List<Perfil> getUltimoPerfil(){
		List<Perfil> resultado = new ArrayList<Perfil>();
		Query query = getEntityManager().createNamedQuery("Perfil.BuscarUltimoPerfil");
		resultado = (List<Perfil>) query.getResultList();
		return resultado;
	}
}
