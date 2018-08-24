package ec.com.modelo;
import java.util.List;
import javax.persistence.Query;
public class AccesoDAO extends ClaseDAO {
	
	public List<Acceso> getAcceso(int idPerfil){
		List<Acceso> resultado; 
		Query query = getEntityManager().createNamedQuery("Acceso.BuscarAcceso");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", idPerfil);
		resultado = (List<Acceso>) query.getResultList();
		return resultado;
	}
	public List<Acceso> getAccesoPerfil(int idPerfil){
		List<Acceso> resultado; 
		Query query = getEntityManager().createNamedQuery("Acceso.buscarAccesoPerfil");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", idPerfil);
		resultado = (List<Acceso>) query.getResultList();
		return resultado;
	}
	

}
