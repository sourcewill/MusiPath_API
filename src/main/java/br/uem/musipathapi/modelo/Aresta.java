package br.uem.musipathapi.modelo;

public class Aresta {
	
	private String de;
	private String para;
	
	public Aresta(String de, String para) {
		super();
		this.de = de;
		this.para = para;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((de == null) ? 0 : de.hashCode());
		result = prime * result + ((para == null) ? 0 : para.hashCode());
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
		Aresta other = (Aresta) obj;
		if (de == null) {
			if (other.de != null)
				return false;
		} else if (!de.equals(other.de))
			return false;
		if (para == null) {
			if (other.para != null)
				return false;
		} else if (!para.equals(other.para))
			return false;
		return true;
	}

	

}
