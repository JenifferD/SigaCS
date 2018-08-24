package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cab_parametrica database table.
 * 
 */
@Entity
@Table(name="cab_parametrica")
@NamedQuery(name="CabParametrica.findAll", query="SELECT c FROM CabParametrica c")
public class CabParametrica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cabp_cod")
	private String cabpCod;

	@Column(name="cabp_descrip")
	private String cabpDescrip;

	@Column(name="cabp_estado")
	private String cabpEstado;

	@Column(name="cabp_id")
	private Integer cabpId;

	//bi-directional many-to-one association to DetParametrica
	@OneToMany(mappedBy="cabParametrica")
	private List<DetParametrica> detParametricas;

	public CabParametrica() {
	}

	public String getCabpCod() {
		return this.cabpCod;
	}

	public void setCabpCod(String cabpCod) {
		this.cabpCod = cabpCod;
	}

	public String getCabpDescrip() {
		return this.cabpDescrip;
	}

	public void setCabpDescrip(String cabpDescrip) {
		this.cabpDescrip = cabpDescrip;
	}

	public String getCabpEstado() {
		return this.cabpEstado;
	}

	public void setCabpEstado(String cabpEstado) {
		this.cabpEstado = cabpEstado;
	}

	public Integer getCabpId() {
		return this.cabpId;
	}

	public void setCabpId(Integer cabpId) {
		this.cabpId = cabpId;
	}

	public List<DetParametrica> getDetParametricas() {
		return this.detParametricas;
	}

	public void setDetParametricas(List<DetParametrica> detParametricas) {
		this.detParametricas = detParametricas;
	}

	public DetParametrica addDetParametrica(DetParametrica detParametrica) {
		getDetParametricas().add(detParametrica);
		detParametrica.setCabParametrica(this);

		return detParametrica;
	}

	public DetParametrica removeDetParametrica(DetParametrica detParametrica) {
		getDetParametricas().remove(detParametrica);
		detParametrica.setCabParametrica(null);

		return detParametrica;
	}

}