package br.ufrn.contextanalyzer.api.entities;

import javax.persistence.CascadeType;
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
public class ReferenceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFERENCE_SEQUENCE")
    private long id;
	
	private String name;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="QUERY_ID", nullable = false)
	private QueryEntity query;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OUTCOME_ID", nullable = false)
	private OutcomeEntity outcome;
	
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
	public QueryEntity getQuery() {
		return query;
	}
	public void setQuery(QueryEntity query) {
		this.query = query;
	}
	public OutcomeEntity getOutcome() {
		return outcome;
	}
	public void setOutcome(OutcomeEntity outcome) {
		this.outcome = outcome;
	}
	
}
