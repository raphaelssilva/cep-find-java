package br.r2s.service.cep.model;

public class Cep {
	
	public Cep(){}
	
	
	public Cep(String numero, String logradouro, String bairro, String localidade, String uf) {
		super();
		this.numero = numero;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
	}


	String numero;
	String logradouro;
	String bairro;
	String localidade;
	String uf;

	public String getNumero() {
		return numero;
	}

		public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}
