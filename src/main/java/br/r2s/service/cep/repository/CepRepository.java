package br.r2s.service.cep.repository;

import br.r2s.service.cep.model.Cep;

public interface CepRepository {
	Cep getByNumero(String numero);
}
