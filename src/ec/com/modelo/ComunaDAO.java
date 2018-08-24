package ec.com.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class ComunaDAO extends ClaseDAO{
	public List<Comuna> getListaComuna(){
		List<Comuna> resultado = new ArrayList<Comuna>();
		Query query = getEntityManager().createNamedQuery("Comuna.findAll");
		resultado = (List<Comuna>) query.getResultList();
		return resultado;
	}
}
