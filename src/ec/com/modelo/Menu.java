package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the menu database table.
 * 
 */
@Entity
@Table(name="Menu")
@NamedQueries({
	@NamedQuery(name="Menu.findAll", query="SELECT m FROM Menu m ORDER BY m.menuPosicion, m.menuId"),
	@NamedQuery(name="Menu.buscarMenu", query="SELECT m FROM Menu m where m.menuIdpadre <> 0 ORDER BY m.menuIdpadre"),
	@NamedQuery(name="Menu.BuscarPadre", query="SELECT m FROM Menu m where m.menuIdpadre = 0"),
})

public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="menu_id")
	private Integer menuId;

	@Column(name="menu_descripcion")
	private String menuDescripcion;

	@Column(name="menu_estado")
	private String menuEstado;

	@Column(name="menu_form_asoc")
	private Boolean menuFormAsoc;

	@Column(name="menu_idpadre")
	private Integer menuIdpadre;

	@Column(name="menu_nombre_form")
	private String menuNombreForm;

	@Column(name="menu_posicion")
	private Integer menuPosicion;

	//bi-directional many-to-one association to Acceso
	@OneToMany(mappedBy="menu")
	private List<Acceso> accesos;

	public Menu() {
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuDescripcion() {
		return this.menuDescripcion;
	}

	public void setMenuDescripcion(String menuDescripcion) {
		this.menuDescripcion = menuDescripcion;
	}

	public String getMenuEstado() {
		return this.menuEstado;
	}

	public void setMenuEstado(String menuEstado) {
		this.menuEstado = menuEstado;
	}

	public Boolean getMenuFormAsoc() {
		return this.menuFormAsoc;
	}

	public void setMenuFormAsoc(Boolean menuFormAsoc) {
		this.menuFormAsoc = menuFormAsoc;
	}

	public Integer getMenuIdpadre() {
		return this.menuIdpadre;
	}

	public void setMenuIdpadre(Integer menuIdpadre) {
		this.menuIdpadre = menuIdpadre;
	}

	public String getMenuNombreForm() {
		return this.menuNombreForm;
	}

	public void setMenuNombreForm(String menuNombreForm) {
		this.menuNombreForm = menuNombreForm;
	}

	public Integer getMenuPosicion() {
		return this.menuPosicion;
	}

	public void setMenuPosicion(Integer menuPosicion) {
		this.menuPosicion = menuPosicion;
	}

	public List<Acceso> getAccesos() {
		return this.accesos;
	}

	public void setAccesos(List<Acceso> accesos) {
		this.accesos = accesos;
	}

	public Acceso addAcceso(Acceso acceso) {
		getAccesos().add(acceso);
		acceso.setMenu(this);

		return acceso;
	}

	public Acceso removeAcceso(Acceso acceso) {
		getAccesos().remove(acceso);
		acceso.setMenu(null);

		return acceso;
	}
	
	@Override
	public String toString()
	{
		return this.menuDescripcion;
	}



	

}