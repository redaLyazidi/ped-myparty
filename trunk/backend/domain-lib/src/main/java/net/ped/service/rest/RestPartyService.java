package net.ped.service.rest;

import net.ped.dao.InterfacePartyDao;
import net.ped.dao.PartyDaoImpl;
import net.ped.model.Party;

public class RestPartyService implements InterfaceRestPartyService{

	InterfacePartyDao dao = new PartyDaoImpl();
	
	public Party getParty(int id) {
		Party party = new Party();
		try {
			party = dao.getParty(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return party;
	}

}
