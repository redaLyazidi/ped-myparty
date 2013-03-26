package net.ped.service.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@SuppressWarnings("serial")
@XmlRootElement
public class JsonList<T> implements java.io.Serializable {
	
	public List<T> list = new ArrayList<T>();
	
	public JsonList() { // Jaxb needs this
	}
}
