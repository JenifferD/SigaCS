package ec.com.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cs_anio_fiscal database table.
 * 
 */
@Entity
@Table(name="cs_anio_fiscal")
@NamedQuery(name="CsAnioFiscal.findAll", query="SELECT c FROM CsAnioFiscal c")
public class CsAnioFiscal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="anio_id")
	private Integer anioId;

	@Column(name="anio_desc")
	private Integer anioDesc;

	@Column(name="anio_estado")
	private String anioEstado;

	//bi-directional many-to-one association to CsPrediosXCobrar
	@OneToMany(mappedBy="csAnioFiscal")
	private List<CsPrediosXCobrar> csPrediosXCobrars;

	public CsAnioFiscal() {
	}

	public Integer getAnioId() {
		return this.anioId;
	}

	public void setAnioId(Integer anioId) {
		this.anioId = anioId;
	}

	public Integer getAnioDesc() {
		return this.anioDesc;
	}

	public void setAnioDesc(Integer anioDesc) {
		this.anioDesc = anioDesc;
	}

	public String getAnioEstado() {
		return this.anioEstado;
	}

	public void setAnioEstado(String anioEstado) {
		this.anioEstado = anioEstado;
	}

	public List<CsPrediosXCobrar> getCsPrediosXCobrars() {
		return this.csPrediosXCobrars;
	}

	public void setCsPrediosXCobrars(List<CsPrediosXCobrar> csPrediosXCobrars) {
		this.csPrediosXCobrars = csPrediosXCobrars;
	}

	public CsPrediosXCobrar addCsPrediosXCobrar(CsPrediosXCobrar csPrediosXCobrar) {
		getCsPrediosXCobrars().add(csPrediosXCobrar);
		csPrediosXCobrar.setCsAnioFiscal(this);

		return csPrediosXCobrar;
	}

	public CsPrediosXCobrar removeCsPrediosXCobrar(CsPrediosXCobrar csPrediosXCobrar) {
		getCsPrediosXCobrars().remove(csPrediosXCobrar);
		csPrediosXCobrar.setCsAnioFiscal(null);

		return csPrediosXCobrar;
	}

}