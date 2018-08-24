package ec.com.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class DetParametricaDAO extends ClaseDAO{
	public List<DetParametrica> getListAll(){
		List<DetParametrica> resultado = new ArrayList<DetParametrica>();
		Query query = getEntityManager().createNamedQuery("DetParametrica.findAll");
		resultado = (List<DetParametrica>) query.getResultList();
		return resultado;
	}
	
	
}
