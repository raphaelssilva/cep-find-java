package br.r2s.service.cep.service;

import org.springframework.stereotype.Service;

import br.r2s.service.cep.model.Cep;

@Service
public interface CepService {
	
	Cep get(String numero);
}
