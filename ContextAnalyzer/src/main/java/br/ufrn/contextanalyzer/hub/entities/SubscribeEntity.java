/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.contextanalyzer.hub.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jorge
 */
@XmlRootElement
@Entity
@Table(name="SUBSCRIBE")
@SequenceGenerator(name="SUBSCRIBE_SEQUENCE", sequenceName="SUBSCRIBE_SEQUENCE", allocationSize=1, initialValue=0)
public class SubscribeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUBSCRIBE_SEQUENCE")
	private long id;

    private String address;
	
    private int port;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    @Override
    public boolean equals(Object o) {
    	if (!(o instanceof SubscribeEntity)) {
            return false;
        }
        SubscribeEntity s = (SubscribeEntity) o;
        return s.getAddress().equals(address) && s.getPort() == port;
    }
    
}
