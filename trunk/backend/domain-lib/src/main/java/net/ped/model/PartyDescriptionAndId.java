package net.ped.model;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class PartyDescriptionAndId implements java.io.Serializable {
	public String summary;
	public int id;
	
	public PartyDescriptionAndId() { // Jaxb needs this
	}
	
	public PartyDescriptionAndId(Party p) {
		summary = p.getTitle() + '\t' + p.getPlace();
		id = p.getId();
	}
}