package br.r2s.service.cep.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.r2s.service.cep.model.Cep;
import br.r2s.service.cep.service.CepService;

@RestController
public class CepController {
	@Autowired
	private CepService cepService;
	
	@Autowired
    @Qualifier("cepValidator")
    private Validator validator;
 
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }
	@RequestMapping("/cep/buscar/{numero}")
    public Object get(@ModelAttribute @Validated Cep cep, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws IOException {       
		String acrHeaders = request.getHeader("Access-Control-Request-Headers");
		String acrMethod = request.getHeader("Access-Control-Request-Method");

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", acrHeaders);
		response.setHeader("Access-Control-Allow-Methods", acrMethod);
		if(bindingResult.hasErrors()){
			response.setStatus(HttpStatus.PRECONDITION_FAILED.value());
			return "CEP Invalido";
		}
		cep = cepService.get(cep.getNumero());
		
		if(cep==null){
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return "CEP não encotrado";
		}
		return cepService.get(cep.getNumero());
    }
}
