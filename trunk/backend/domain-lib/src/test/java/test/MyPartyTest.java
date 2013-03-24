package test;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ped.dao.BillingDaoImpl;
import net.ped.dao.RESTDaoImpl;
import net.ped.model.Artist;
import net.ped.model.Customer;
import net.ped.model.Party;
import net.ped.model.Ticket;
import net.ped.model.User;
import net.ped.service.front.FrontBillingService;
import net.ped.service.front.FrontPartyService;
import net.ped.service.rest.RestPartyService;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyPartyTest {

	private static final Logger LOG = LoggerFactory.getLogger(MyPartyTest.class);
	
	@Rule public TestName name = new TestName();
	
	static FrontPartyService service;
	static FrontBillingService serviceBilling;
	static Calendar dateParty1, timeParty1, dateBegin1, dateEnd1;
	static Calendar dateParty2, timeParty2, dateBegin2, dateEnd2;
	static Calendar dateParty3, timeParty3, dateBegin3, dateEnd3;
	static Artist artist1, artist2, artist3;
	static List<Artist> listArtists1, listArtists2, listArtists3;
	static Party party1, party2, party3;
	
	static User user;
	static Customer customer;
	
	@BeforeClass
	public static void setUp() throws Exception {
		service = FrontPartyService.getInstance();
		serviceBilling = FrontBillingService.getInstance();
		dateParty1 = new GregorianCalendar(2013, 04, 15);
		timeParty1 = new GregorianCalendar(0, 0, 0, 20, 30, 0);
		dateBegin1 = new GregorianCalendar(2013, 04, 01);
		dateEnd1 = new GregorianCalendar(2013, 04, 10);
		
		dateParty2 = new GregorianCalendar(2013, 03, 15);
		timeParty2 = new GregorianCalendar(0, 0, 0, 21, 30, 0);
		dateBegin2 = new GregorianCalendar(2013, 03, 01);
		dateEnd2 = new GregorianCalendar(2013, 03, 10);
		
		dateParty3 = new GregorianCalendar(2013, 02, 15);
		timeParty3 = new GregorianCalendar(0, 0, 0, 22, 30, 0);
		dateBegin3 = new GregorianCalendar(2013, 02, 01);
		dateEnd3 = new GregorianCalendar(2013, 02, 10);
		
		artist1 = new Artist("JOHNNY HALLYDAY", "VARIETE");
		artist2 = new Artist("COEUR DE PIRATE", "POP-ROCK");
		artist3 = new Artist("INDOCHINE", "POP-ROCK");
		listArtists1 = new ArrayList<Artist>();
		listArtists1.add(artist1);
		listArtists2 = new ArrayList<Artist>();
		listArtists2.add(artist2);
		listArtists3 = new ArrayList<Artist>();
		listArtists3.add(artist3);
		party1 = new Party("JOHNNY HALLYDAY en tournée dans toute la France", dateParty1, timeParty1, dateBegin1, dateEnd1, "succes enorme en France", 200, "variete", 25.50, "johnny-hallyday.jpg", "95, Cours Maréchal Juin", "Bordeaux", "33000", "PATINOIRE MERIADECK", listArtists1);
		party2 = new Party("COEUR DE PIRATE en tournée Solo", dateParty2, timeParty2, dateBegin2, dateEnd2, "succes enorme en France", 200, "pop-rock", 50.50, "coeur-de-pirate.jpg", "10, rue de Grassi", "Bordeaux", "33000", "THEATRE FEMINA", listArtists2);
		party3 = new Party("INDOCHINE Black City Tour 2", dateParty3, timeParty3,dateBegin3, dateEnd3, "succes enorme en France", 200, "pop-rock", 100.50, "indochine.jpg", "95, Cours Maréchal Juin", "Bordeaux", "33000", "PATINOIRE MERIADECK", listArtists3);
	
		user = new User("Jean", "Dujardin", "jean", "123", "admin");
		customer = new Customer("Patrick", "George", "pg@gmail.com");
	}
	
	@Test
	public void testA_AddArtist(){
		try {
			service.addArtist(artist1);
			service.addArtist(artist2);
			service.addArtist(artist3);
			assertEquals("INDOCHINE", service.getArtistByName("INDOCHINE").getName());
			assertFalse(service.getAllArtists().isEmpty());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testAddArtist");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testB_AddParty(){
		try {
			service.addParty(party1);
			service.addParty(party2);
			service.addParty(party3);
			assertFalse(service.getAllParties().isEmpty());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testAddParty");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testC_UpdateParty(){
		try {
			Party p1 = service.getParty(1);
			p1.setNbPlace(500);
			service.updateParty(p1);
			Party p2 = service.getParty(1);
			assertEquals(500, p2.getNbPlace());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testupdateParty");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testD_PartiesNotValidated(){
		try {
			assertEquals(3, service.getAllPartiesNotValidated().size());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testValidatePart");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testE_ValidateParty(){
		try {
			service.ValidateParty(1);
			service.ValidateParty(2);
			assertEquals(1, service.getAllPartiesNotValidated().size());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testValidatePart");
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testF_DeleteParty(){
		try {
			service.deleteParty(1);
			assertTrue(service.getAllParties().isEmpty());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testDeleteParty");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testG_PartiesNotBegun(){
		try {
			assertFalse(service.getPartiesNotBegun().isEmpty());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testPartiesNotBegun");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testH_PartiesNotBegunMaxResult(){
		List<Party> list = new ArrayList<Party>();
		try {
			list = service.getPartiesNotBegunMaxResult(0, 3);
			assertEquals(2, list.size());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testPartiesNotBegunMaxResult");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testI_containsParty(){
		assertEquals(true, service.containsParty(1));
	}
	
	@Test
	public void testJ_PartiesCriteria(){
		List<Party> list = new ArrayList<Party>();
		Calendar calendar = new GregorianCalendar(2013, 04, 15);
		Calendar time = new GregorianCalendar(0, 0, 0, 20, 30, 00);
		String place="PATINOIRE MERIADECK";
		try {
			list = service.getPartiesCriteria(0, 5, place, 25.50, 30.00, calendar, time);
			assertEquals(1, list.size());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testPartiesCriteria");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testK_addUser(){
		service.addUser(user);
		assertEquals("Jean", service.getUser(1).getFirstname());
	}
	
	@Test
	public void testL_addCustomer(){
		serviceBilling.addCustomer(customer);
		serviceBilling.addCustomer(customer);
		assertEquals(1,serviceBilling.getCustomer("Patrick", "George", "pg@gmail.com").getId());
		assertEquals(1,serviceBilling.getCustomerById(1).getId());
	}
	
	@Test
	public void testM_addTicket(){
		serviceBilling.addTicket(1, 1);
		//assertEquals(1,serviceBilling.getTicket(1, 1).getId());
	}
	
	@After
	public void afterTests() throws Exception {
		Class driverClass = Class.forName("org.h2.Driver");
		Connection jdbcConnection = DriverManager.getConnection("jdbc:h2:mem://localhost:9101/dbunit", "sa", "");
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
		// full database export
		IDataSet fullDataSet = connection.createDataSet();
		FlatXmlDataSet.write(fullDataSet, new FileOutputStream("target/"+name.getMethodName()+".xml"));
		FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("target/"+"test.dtd"));
	}
	
}
