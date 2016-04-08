package br.ufrn.coren.Entities.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class NonConstAttribute {

	@Id
	@GeneratedValue
	protected long id;
	protected String attName;
	protected AttributeType type;
	
	public String getAttName() {
		return attName;
	}
	public void setAttName(String attName) {
		this.attName = attName;
	}
	public  AttributeType getType() {
		return type;
	}
	public  void setType(AttributeType type) {
		this.type = type;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	
	
}
