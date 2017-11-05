package br.com.network.tiever.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@SequenceGenerator(name="convite_sequence", sequenceName="sequence_convite", initialValue=1, allocationSize=1)
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"usuario_de_id", "usuario_para_id"})) 
public class Convite implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="convite_sequence", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Usuario usuarioPara;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Usuario usuarioDe;

	public Convite() {
		super();
	}

	public Convite(Usuario usuarioPara, Usuario usuarioDe) {
		super();
		this.usuarioPara = usuarioPara;
		this.usuarioDe = usuarioDe;
	}

	public Long getId() {
		return id;
	}

	public Usuario getUsuarioPara() {
		return usuarioPara;
	}

	public void setUsuarioPara(Usuario usuarioPara) {
		this.usuarioPara = usuarioPara;
	}

	public Usuario getUsuarioDe() {
		return usuarioDe;
	}

	public void setUsuarioDe(Usuario usuarioDe) {
		this.usuarioDe = usuarioDe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((usuarioDe == null) ? 0 : usuarioDe.hashCode());
		result = prime * result + ((usuarioPara == null) ? 0 : usuarioPara.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Convite other = (Convite) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (usuarioDe == null) {
			if (other.usuarioDe != null)
				return false;
		} else if (!usuarioDe.equals(other.usuarioDe))
			return false;
		if (usuarioPara == null) {
			if (other.usuarioPara != null)
				return false;
		} else if (!usuarioPara.equals(other.usuarioPara))
			return false;
		return true;
	}

	
}
