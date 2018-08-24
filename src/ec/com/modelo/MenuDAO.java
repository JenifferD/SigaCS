package ec.com.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class MenuDAO extends ClaseDAO{
	public List<Menu> getListaMenu(){
		List<Menu> resultado = new ArrayList<Menu>();
		Query query = getEntityManager().createNamedQuery("Menu.findAll");
		resultado = (List<Menu>) query.getResultList();
		return resultado;
	}
	public List<Menu> getListaMenuAccesos(){
		List<Menu> resultado = new ArrayList<Menu>();
		Query query = getEntityManager().createNamedQuery("Menu.buscarMenu");
		resultado = (List<Menu>) query.getResultList();
		return resultado;
	}
	
	public List<Menu> getMenuPadre(){
		List<Menu> resultado = new ArrayList<Menu>();
		Query query = getEntityManager().createNamedQuery("Menu.BuscarPadre");
		resultado = (List<Menu>) query.getResultList();
		return resultado;
	}
}
