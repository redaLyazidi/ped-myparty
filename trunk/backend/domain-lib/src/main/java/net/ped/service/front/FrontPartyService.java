package net.ped.service.front;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.ped.dao.InterfacePartyDao;
import net.ped.dao.PartyDaoImpl;
import net.ped.model.Artist;
import net.ped.model.Party;
import net.ped.model.User;

public class FrontPartyService implements InterfaceFrontPartyService{
	
	private InterfacePartyDao dao = new PartyDaoImpl();
	private static FrontPartyService instance;
	
	private FrontPartyService() {
		instance = this;
	}
	
	public static FrontPartyService getInstance() {
		if(instance == null) {
			instance = new FrontPartyService();
		}
		
		return instance;
	}
	
	
	public void addArtist(Artist a){
		try {
			dao.addArtist(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteArtist(int id){
		try {
			dao.deleteArtist(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Artist getArtistByName(String name){
		Artist artist = new Artist();
		try {
			artist = dao.getArtistByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return artist;
	}
	
	public List<Artist> getAllArtists(){
		List<Artist> list = new ArrayList<Artist>();
		try {
			list = dao.getAllArtists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

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
	
	public void ValidateParty(int id){
		Party party = new Party();
		try {
			party = dao.getParty(id);
			party.setValidated(true);
			dao.updateParty(party);
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
	
	public boolean containsParty(int id){
		boolean exist = false;
		try {
			exist = dao.containsParty(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exist;
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
	
	public List<Party> getAllPartiesNotValidated(){
		List<Party> list = new ArrayList<Party>();
		try {
			list = dao.getAllPartiesNotValidated();
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
	
	public int getNbPartiesNotBegun() { 
		int nbParties = 0;
	try {
		nbParties = dao.getNbPartiesNotBegun();
	} catch (Exception e) {
		e.printStackTrace();
	}
		return nbParties;
	}

	public List<Party> getPartiesCriteria(int startPosition, int length, String place, Double priceBegin, Double priceEnd,
			Calendar date, Calendar time){
		List<Party> list = new ArrayList<Party>();
		
		try {
			list = dao.getPartiesCriteria(startPosition, length, place, priceBegin, priceEnd, date, time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getNbPartiesCriteria(String place, Double priceBegin, Double priceEnd,
			Calendar date, Calendar time){
		int nbParties = 0;
		
		try {
			nbParties = dao.getNbPartiesCriteria(place, priceBegin, priceEnd, date, time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nbParties;
	}
	
	public void addUser(User u){
		try {
			dao.addUser(u);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User getUser(int id){
		User u = new User();
		try {
			u = dao.getUser(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public User login(String login, String password) {
		User u = new User();
		try {
			u = dao.login(login, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
}
