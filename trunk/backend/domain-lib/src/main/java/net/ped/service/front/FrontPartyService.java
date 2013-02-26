package net.ped.service.front;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.ped.dao.InterfacePartyDao;
import net.ped.dao.PartyDaoImpl;
import net.ped.model.Party;

public class FrontPartyService implements InterfaceFrontPartyService{
	
	InterfacePartyDao dao = new PartyDaoImpl();

	public void addParty(Party p){
		try {
			dao.addParty(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateParty(Party p){
		try {
			dao.updateParty(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteParty(int id){
		try {
			dao.deleteParty(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Party getParty(int id){
		Party party = new Party();
		try {
			party = dao.getParty(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return party;
	}
	
	public List<Party> getAllParties(){
		List<Party> list = new ArrayList<Party>();
		try {
			list = dao.getAllParties();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Party> getPartiesNotBegun() {
		List<Party> list = new ArrayList<Party>();
		try {
			list = dao.getPartiesNotBegun();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Party> getPartiesNotBegunMaxResult(int startPosition, int length) {
		List<Party> list = new ArrayList<Party>();
		try {
			list = dao.getPartiesNotBegunMaxResult(startPosition, length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Party> getPartiesCriteria(Double priceBegin, Double priceEnd,
			Calendar date, Calendar time){
		List<Party> list = new ArrayList<Party>();
		try {
			list = dao.getPartiesCriteria(priceBegin, priceEnd, date, time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
