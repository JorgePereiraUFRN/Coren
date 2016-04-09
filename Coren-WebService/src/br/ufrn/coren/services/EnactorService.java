package br.ufrn.coren.services;

import br.ufrn.coren.models.EnactorModel;
import context.arch.enactor.Enactor;

public class EnactorService {

	public Enactor createEnactor(EnactorModel enactorModel) {
		Enactor enactor = enactorModel.createEnactor();
		return enactor;
	}
}
