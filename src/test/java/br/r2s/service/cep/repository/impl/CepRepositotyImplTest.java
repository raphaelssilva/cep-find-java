package br.r2s.service.cep.repository.impl;

import java.io.IOException;

import br.r2s.service.cep.model.Cep;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.util.Assert;

public class CepRepositotyImplTest {
	CepRepositoryImpl cepRepository;
	
	public CepRepositotyImplTest(){
		cepRepository = new CepRepositoryImpl();
	}

	@Test
	public void getCep(){

		String numeroCep = "13565090";

		long time = System.currentTimeMillis();

		Cep cep = cepRepository.getByNumero(numeroCep);
		System.out.println(System.currentTimeMillis() - time);
		Assert.notNull(cep);
	}

	/*
	@Test
	public void testgetHtml() throws ClientProtocolException, IOException{
		String numero = "29141280";
		
		String html = cepRepository.getHtml(numero);
		
		System.out.println(html);
		
		Assert.notNull(html);
	}
	
	@Test
	public void parseHtml() throws ClientProtocolException, IOException{
		 String numero = "29141280";
		
		 String html = cepRepository.getHtml(numero);
		
		 Document doc =  Jsoup.parse(html);
		 
		 Elements elements = doc.select(".respostadestaque");
		 
		 String rua = elements.get(0).text().trim();
		 String bairro = elements.get(1).text();
		 String cidade = elements.get(2).text().split("/")[0];
		 String uf = elements.get(2).text().split("/")[1];
		 
		 System.out.println("rua:"+rua+" bairro:"+bairro+" cidade:"+cidade+" uf:"+uf);
		
	}*/
	
}