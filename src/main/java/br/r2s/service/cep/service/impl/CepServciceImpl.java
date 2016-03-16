package br.r2s.service.cep.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.r2s.service.cep.model.Cep;
import br.r2s.service.cep.repository.CepRepository;
import br.r2s.service.cep.service.CepService;

@Service
public class CepServciceImpl implements CepService{
	
	private CepRepository cepRepository;
	
	@Autowired
	public void setCepRepository(CepRepository cepRepository) {
		this.cepRepository = cepRepository;
	}
	
	public Cep get(String numero){
		String numeroFormadado = numero;
		
		Cep cep = cepRepository.getByNumero(numero);
		if(cep==null){
			for(int i = 8; i>0;i--){
				numeroFormadado = formatNumero(numero.substring(0, i-1));
				
				cep = cepRepository.getByNumero(numeroFormadado);
				
				if(cep!=null){
					break;
				}
			}
		}
		
		return cep;
	}	
	
	private String formatNumero(String num) {
		String numeroFormatado = num;
		for (int i = num.length(); i < 8; i++) {
			numeroFormatado += "0";
		}

		return numeroFormatado;
	}
}
