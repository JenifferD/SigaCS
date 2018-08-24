package ec.com.modelo;

import java.util.List;

import javax.persistence.Query;

public class PersonaDAO extends ClaseDAO{
	
	public List<CsPersona> getListarPersona(String busqueda) {	
	List<CsPersona> resultado; 
	Query query = getEntityManager().createNamedQuery("CsPersona.BuscarPersona");
	query.setHint("javax.persistence.cache.storeMode", "REFRESH");
	query.setParameter("cadena", "%" + busqueda + "%");
	resultado = (List<CsPersona>) query.getResultList();
	return resultado;
	}

	public CsPersona getPersona(int id) {	
		CsPersona resultado; 
		Query query = getEntityManager().createNamedQuery("CsPersona.BuscarPersonaById");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", id);
		resultado = (CsPersona) query.getSingleResult();
		return resultado;
		}
	
}
