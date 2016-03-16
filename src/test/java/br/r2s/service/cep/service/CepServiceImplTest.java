package br.r2s.service.cep.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.Assert;

import br.r2s.service.cep.model.Cep;
import br.r2s.service.cep.repository.CepRepository;
import br.r2s.service.cep.service.impl.CepServciceImpl;

public class CepServiceImplTest {
	public class CepRepositoyStanb implements CepRepository{
		public Map<String,Cep> ceps = new HashMap<String, Cep>();
		
		@Override
		public Cep getByNumero(String numero) {
			return ceps.get(numero);
		}
	}
	CepRepositoyStanb cepRepositoyStanb = new CepRepositoyStanb();
	
	CepServciceImpl cepService = new CepServciceImpl();
	
	public CepServiceImplTest(){
		cepRepositoyStanb = new CepRepositoyStanb();
		cepService = new CepServciceImpl();
		cepService.setCepRepository(cepRepositoyStanb);
	}
	
	@Test
	public void testarGetCepNaoEncontrado(){
		String numero = "12345678";
		
		cepRepositoyStanb.ceps.clear();
		
		Assert.isNull(cepService.get(numero), "Dado um cep valido, caso não encontrar retornar null");		
		
	}

	@Test
	public void testarGetCepNumeroCurto(){
		String numero = "12345678";
		
		cepRepositoyStanb.ceps.put("12345600", new Cep());
		
		Assert.notNull(cepService.get(numero), "Dado um cep valido, que não exista o endereco, sera substituido um digito pela esquerda por zero ate que seja encontrado um endereco");				
	}	
}