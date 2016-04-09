package br.ufrn.coren.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="REFERENCE")
@SequenceGenerator(name="REFERENCE_SEQUENCE", sequenceName="REFERENCE_SEQUENCE", allocationSize=1, initialValue=0)
public class ReferenceModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFERENCE_SEQUENCE")
    private long id;
	
	@Column
	private String name;
	
	@OneToOne
	@JoinColumn(name="QUERY_ID", nullable = false)
	private QueryModel query;
	
	@OneToOne
	@JoinColumn(name="OUTCOME_ID", nullable = false)
	private OutcomeModel outcome;
	
	@Column(name="ENACTOR_ID")
	private long enactorId;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public QueryModel getQuery() {
		return query;
	}
	public void setQuery(QueryModel query) {
		this.query = query;
	}
	public OutcomeModel getOutcome() {
		return outcome;
	}
	public void setOutcome(OutcomeModel outcome) {
		this.outcome = outcome;
	}

	public long getEnactorId() {
		return enactorId;
	}

	public void setEnactorId(long enactorId) {
		this.enactorId = enactorId;
	}
	
}
