package br.ufrn.contextanalyzer.api.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufrn.contextanalyzer.api.utils.EntityAPIUtil;
import context.arch.storage.Attribute;
import context.arch.storage.AttributeNameValue;	

@XmlRootElement
@Entity
@Table(name="ATTRIBUTE")
@SequenceGenerator(name="ATTRIBUTE_SEQUENCE", sequenceName="ATTRIBUTE_SEQUENCE", allocationSize=1, initialValue=0)
public class AttributeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTRIBUTE_SEQUENCE")
    private long id;
	
	private String name;
	
	private String type;
	
	private String value;
	
	private boolean constant;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="ATTRIBUTE_ID")
	private List<AttributeEntityHistory> attributeEntityHistory;
	
	@ManyToOne
	private WidgetEntity widget;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isConstant() {
		return constant;
	}
	
	public void setConstant(boolean constant) {
		this.constant = constant;
	}

	public List<AttributeEntityHistory> getAttributeEntityHistory() {
		return attributeEntityHistory;
	}

	public void setAttributeEntityHistory(List<AttributeEntityHistory> attributeEntityHistory) {
		this.attributeEntityHistory = attributeEntityHistory;
	}

	public WidgetEntity getWidget() {
		return widget;
	}

	public void setWidget(WidgetEntity widget) {
		this.widget = widget;
	}

	@SuppressWarnings("unchecked")
	public <T extends Comparable<? super T>> Attribute<T> toAttribute() {
		Attribute<T> att;		
		
		Class<T> attType = (Class<T>) EntityAPIUtil.toClass(this.type);
		if (this.constant) {
			T value = (T) EntityAPIUtil.toValue(attType, this.value);
			att = AttributeNameValue.instance(this.name, value);
		}
		else {
			att = Attribute.instance(this.name, attType);
		}
		
		return att;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Comparable<? super T>> AttributeNameValue<T> toAttributeNameValue() {
		AttributeNameValue<T> att;		
		
		Class<T> attType = (Class<T>) EntityAPIUtil.toClass(this.type);
		T value = (T) EntityAPIUtil.toValue(attType, this.value);
		att = AttributeNameValue.instance(this.name, value);
		
		return att;
	}
	
	@Override
    public boolean equals(Object o) {
		if (!(o instanceof AttributeEntity)) {
            return false;
        }
        AttributeEntity s = (AttributeEntity) o;
        return s.getName().equals(name) && s.getType().equals(type) && s.isConstant() == constant;
    }
	
}
