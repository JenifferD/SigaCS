package ec.com.modelo;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.*;


/**
 * The persistent class for the cs_documento database table.
 * 
 */
@Entity
@Table(name="cs_documento")
@NamedQuery(name="CsDocumento.findAll", query="SELECT c FROM CsDocumento c")
public class CsDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="doc_id")
	private Integer docId;

	@Column(name="doc_estado")
	private String docEstado;

	@Column(name="doc_evidencia")
	private byte[] docEvidencia;

	@Column(name="doc_nombre")
	private String docNombre;

	//bi-directional many-to-one association to CsAfiliado
	@ManyToOne
	@JoinColumn(name="afil_id")
	private CsAfiliado csAfiliado;

	//bi-directional many-to-one association to CsAsignacion
	@ManyToOne
	@JoinColumn(name="asig_id")
	private CsAsignacion csAsignacion;

	public CsDocumento() {
	}

	public Integer getDocId() {
		return this.docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public String getDocEstado() {
		return this.docEstado;
	}

	public void setDocEstado(String docEstado) {
		this.docEstado = docEstado;
	}

	public byte[] getDocEvidencia() {
		return this.docEvidencia;
	}

	public void setDocEvidencia(byte[] docEvidencia) {
		this.docEvidencia = docEvidencia;
	}

	public String getDocNombre() {
		return this.docNombre;
	}

	public void setDocNombre(String docNombre) {
		this.docNombre = docNombre;
	}

	public CsAfiliado getCsAfiliado() {
		return this.csAfiliado;
	}

	public void setCsAfiliado(CsAfiliado csAfiliado) {
		this.csAfiliado = csAfiliado;
	}

	public CsAsignacion getCsAsignacion() {
		return this.csAsignacion;
	}

	public void setCsAsignacion(CsAsignacion csAsignacion) {
		this.csAsignacion = csAsignacion;
	}

	@Override
	public String toString() {
		return "CsDocumento [docId=" + docId + ", docEstado=" + docEstado + ", docEvidencia="
				+ Arrays.toString(docEvidencia) + ", docNombre=" + docNombre + ", csAfiliado=" + csAfiliado
				+ ", csAsignacion=" + csAsignacion + "]";
	}

	
}