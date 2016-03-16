package br.r2s.service.cep.repository.impl;

import br.r2s.service.cep.model.Cep;
import br.r2s.service.cep.repository.CepRepository;
import br.r2s.service.cep.util.RedisUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CepRepositoryImpl implements CepRepository {
	Map<String, Cep> ceps = null;

	Gson gson;

	public CepRepositoryImpl(){
		gson = new GsonBuilder().create();
	}

	@Override
	public Cep getByNumero(String numero) {
		RedisUtil redisUtil = RedisUtil.getInstancia();

		Cep cep = null;
		String cepJson = redisUtil.get(numero);
		System.out.println(cepJson);
		if(!StringUtils.isEmpty(cepJson)){
			cep = gson.fromJson(cepJson, Cep.class);
		}else{
			String html = this.getHtml(numero);
			if(html!=null&&!html.isEmpty()){
				cep = this.parseCep(html);
			}

			if(cep!=null&&redisUtil.isConnect()){
				redisUtil.set(numero, gson.toJson(cep));
			}
		}

		return cep;
	}



	public String getHtml(String numero) {
		
		String html = null;
		try{
			String url = "http://m.correios.com.br/movel/buscaCepConfirma.do";

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("cepEntrada", numero));
			urlParameters.add(new BasicNameValuePair("tipoCep", ""));
			urlParameters.add(new BasicNameValuePair("cepTemp", ""));
			urlParameters.add(new BasicNameValuePair("metodo", "buscarCep"));

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(post);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			html = result.toString();
		}catch(Exception e){
			System.err.println("Erro ao obter");
		}		
		return html;
	}

	Cep parseCep(String html) {
		Cep cep = null;
		try {
			Document doc = Jsoup.parse(html);

			Elements elements = doc.select(".respostadestaque");

			String rua = getRua(elements);
			String bairro = getBairro(elements);
			String cidade = getCidade(elements);
			String uf = getUf(elements);
			String numeroCep = getCep(elements);

			cep = new Cep(numeroCep, rua, bairro, cidade, uf);

		} catch (Exception e) {
			cep = null;
		}

		return cep;

	}

	private String getCep(Elements elements) {
		return elements.get(3).text();
	}

	private String getUf(Elements elements) {
		return elements.get(2).text().split("/")[1];
	}

	private String getCidade(Elements elements) {
		return elements.get(2).text().split("/")[0];
	}

	private String getBairro(Elements elements) {
		return elements.get(1).text();
	}

	private String getRua(Elements elements) {
		return elements.get(0).text().trim();
	}

	private void popularCeps() {
		if (ceps == null || ceps.isEmpty()) {
			ceps = new HashMap<String, Cep>();

			ceps.put("12345678", new Cep("12346578", "Rua a", "Agui Branca", "Vitoria", "ES"));
			ceps.put("12346500", new Cep("12346500", "Rua X", "Castelo branco", "Vitoria", "ES"));
			ceps.put("121232", new Cep("121232", "Rua Nilton", "Joquei Clube", "Vitoria", "ES"));
			ceps.put("982345", new Cep("982345", "Rua P", "Bela Auroro", "Vitoria", "ES"));
			ceps.put("29141280", new Cep("29141280", "Rua Padre Pires", "Boa Sorte", "Cariacica", "ES"));
			ceps.put("13565090", new Cep("13565090", "Rua Ray Wesley", "Joquei Clube", "São Carlos", "SP"));
			ceps.put("23456789", new Cep("23456789", "Rua a", "Itaparica", "Vitoria", "SP"));
			ceps.put("1234580", new Cep("1234580", "Rua E", "Jardineiro", "São Paulo", "SP"));

		}
	}

}
