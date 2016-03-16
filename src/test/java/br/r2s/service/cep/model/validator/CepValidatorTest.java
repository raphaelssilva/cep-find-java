package br.r2s.service.cep.model.validator;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.MapBindingResult;

import br.r2s.service.cep.model.Cep;

@RunWith(SpringJUnit4ClassRunner.class)
public class CepValidatorTest {
	
	public class ErrorsCep extends MapBindingResult{

		public ErrorsCep() {
			super(new HashMap<String, Object>(), "cep");
		}
		
		public void clean(){
			this.getTargetMap().clear();
		}
		
		private static final long serialVersionUID = 1L;		
		
	}
	
	private CepValidator cepValidator = new CepValidator();
	private Cep cep = new Cep();
	private ErrorsCep errors = new ErrorsCep();
	
	@Test
	public void testValidarNumeroCepNull() {		
		
		configurarTestCep(errors, cep, null);
		
		cepValidator.validate(cep, errors);
		 		
		Assert.isTrue(hasError(errors, "numero", "nullable"), "retorno TRUE - numero não pode ser null");		
	}
	
	@Test
	public void testValidarNumeroCepVazio() {		
		
		configurarTestCep(errors, cep, "");
		
		cepValidator.validate(cep, errors);
		 		
		Assert.isTrue(hasError(errors, "numero", "empty"), "retorno TRUE - numero não pode ser vazio");		
	}
	
	@Test
	public void testValidarNumeroCepLetra() {		
		
		configurarTestCep(errors, cep, "cepinvalido");
		
		cepValidator.validate(cep, errors);
		 		
		Assert.isTrue(hasError(errors, "numero", "formatoInvalido"), "retorno TRUE - numero não pode ser formado por letra");		
	}
	
	@Test
	public void testValidarNumeroCepNumeroMaiorInvalido() {		
		
		configurarTestCep(errors, cep, "123456789");
		
		cepValidator.validate(cep, errors);
		 		
		Assert.isTrue(hasError(errors, "numero", "formatoInvalido"), "retorno TRUE - numero não pode ser maior que 8 digitos");		
	}
	
	@Test
	public void testValidarNumeroCepNumeroMenorInvalido() {		
		
		configurarTestCep(errors, cep, "1234567");
		
		cepValidator.validate(cep, errors);
		 		
		Assert.isTrue(hasError(errors, "numero", "formatoInvalido"), "retorno TRUE - numero não pode ser menor que 8 digitos");		
	}
	
	public void configurarTestCep(ErrorsCep errors, Cep cep, String numero){
		errors.clean();
		cep.setNumero(numero);
	}
	
	public boolean hasError(ErrorsCep errors, String campo, String codeError){
		return errors.getFieldError(campo)!=null&&errors.getFieldError(campo).getCode().contains(codeError);
	}

}


