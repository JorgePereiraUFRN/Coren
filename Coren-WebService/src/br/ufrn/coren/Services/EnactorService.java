package br.ufrn.coren.Services;

import br.ufrn.coren.Models.EnactorModel;
import context.arch.enactor.Enactor;

public class EnactorService {

	public Enactor createEnactor(EnactorModel enactorModel) {
		Enactor enactor = enactorModel.createEnactor();
		
		// salvar no bando de dados;
		
		return enactor;
	}
}
