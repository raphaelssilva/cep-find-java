package br.r2s.service.cep.model.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.r2s.service.cep.model.Cep;


@Component("cepValidator")
public class CepValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return Cep.class.equals(arg0);
	}

	@Override
	public void validate(Object objeto, Errors e) {
		Cep cep = (Cep) objeto;
		
		if(cep.getNumero()==null){
			e.rejectValue("numero", "nullable");
		}else if(cep.getNumero().isEmpty()){
			e.rejectValue("numero", "empty");
		}else{
			if(!cep.getNumero().matches("[0-9]+")){
				e.rejectValue("numero", "formatoInvalido");
			}
			
			if(cep.getNumero().length() != 8){
				e.rejectValue("numero", "formatoInvalido");
			}					
		}	
	}
}
