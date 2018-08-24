package ec.com.modelo;

import java.util.List;

import javax.persistence.Query;


public class CsPredioDAO extends ClaseDAO {
	
	public List<CsPredio> getBuscarPredio(String nombre) {
		List<CsPredio> resultado; 
		Query query = getEntityManager().createNamedQuery("CsPredio.BuscarPredio");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("cadena", "%" + nombre + "%");		
		resultado = (List<CsPredio>) query.getResultList();
		return resultado;
	}
	

}


