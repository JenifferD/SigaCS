package ec.com.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class CsAfiliadoDAO extends ClaseDAO {
	  
	public List<CsAfiliado> getMostrarAfiliado(int idAfiliado){
		List<CsAfiliado> resultado = new ArrayList<CsAfiliado>();
		Query query = getEntityManager().createNamedQuery("CsAfiliado.MostrarAfiliado");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", idAfiliado);
		resultado = (List<CsAfiliado>) query.getResultList();
		return resultado;
	}
	    
}
