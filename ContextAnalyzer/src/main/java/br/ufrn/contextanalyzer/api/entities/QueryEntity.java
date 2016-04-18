package br.ufrn.contextanalyzer.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="QUERY")
@SequenceGenerator(name="QUERY_SEQUENCE", sequenceName="QUERY_SEQUENCE", allocationSize=1, initialValue=0)
public class QueryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUERY_SEQUENCE")
    private long id;
	
	private String nome;
	
	private String value;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
