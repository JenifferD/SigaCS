package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the acceso database table.
 * 
 */
@Entity
@Table(name="Acceso")
@NamedQueries({
	@NamedQuery(name="Acceso.findAll", query="SELECT a FROM Acceso a"),
	@NamedQuery(name="Acceso.buscarAccesoPerfil", query="SELECT a FROM Acceso a where a.perfil.perId = (:patron) order by a.menu.menuId asc"),
	@NamedQuery(name="Acceso.BuscarAcceso", query="SELECT a FROM Acceso a "
			+ "WHERE a.perfil.perId = (:patron)  and a.accesoEstado = 'A' and a.menu.menuIdpadre <> 0 ")
})
public class Acceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="acceso_id")
	private Integer accesoId;

	@Column(name="acceso_estado")
	private String accesoEstado;

	//bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name="menu_id")
	private Menu menu;

	//bi-directional many-to-one association to Perfil
	@ManyToOne
	@JoinColumn(name="per_id")
	private Perfil perfil;

	public Acceso() {
	}

	public Integer getAccesoId() {
		return this.accesoId;
	}

	public void setAccesoId(Integer accesoId) {
		this.accesoId = accesoId;
	}

	public String getAccesoEstado() {
		return this.accesoEstado;
	}

	public void setAccesoEstado(String accesoEstado) {
		this.accesoEstado = accesoEstado;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}