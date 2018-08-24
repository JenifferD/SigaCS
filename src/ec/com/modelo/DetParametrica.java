package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the det_parametrica database table.
 * 
 */
@Entity
@Table(name="det_parametrica")
@NamedQueries({
	@NamedQuery(name="DetParametrica.findAll", query="Select d from DetParametrica d "),
	
})
public class DetParametrica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="detp_id")
	private Integer detpId;

	@Column(name="detp_descip")
	private String detpDescip;

	@Column(name="detp_estado")
	private String detpEstado;

	@Column(name="detp_valor")
	private double detpValor;

	//bi-directional many-to-one association to CabParametrica
	@ManyToOne
	@JoinColumn(name="cabp_cod")
	private CabParametrica cabParametrica;

	public DetParametrica() {
	}

	public Integer getDetpId() {
		return this.detpId;
	}

	public void setDetpId(Integer detpId) {
		this.detpId = detpId;
	}

	public String getDetpDescip() {
		return this.detpDescip;
	}

	public void setDetpDescip(String detpDescip) {
		this.detpDescip = detpDescip;
	}

	public String getDetpEstado() {
		return this.detpEstado;
	}

	public void setDetpEstado(String detpEstado) {
		this.detpEstado = detpEstado;
	}

	public double getDetpValor() {
		return this.detpValor;
	}

	public void setDetpValor(double detpValor) {
		this.detpValor = detpValor;
	}

	public CabParametrica getCabParametrica() {
		return this.cabParametrica;
	}

	public void setCabParametrica(CabParametrica cabParametrica) {
		this.cabParametrica = cabParametrica;
	}

	@Override
	public String toString()
	{
		return this.detpDescip;
	}
}