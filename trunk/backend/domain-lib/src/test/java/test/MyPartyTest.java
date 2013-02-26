package test;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.ped.dao.PartyDaoImpl;
import net.ped.model.Adress;
import net.ped.model.Artist;
import net.ped.model.Party;

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
	
	static PartyDaoImpl dao;
	static Calendar dateParty1, timeParty1, dateBegin1, dateEnd1;
	static Calendar dateParty2, timeParty2, dateBegin2, dateEnd2;
	static Calendar dateParty3, timeParty3, dateBegin3, dateEnd3;
	static Adress adress;
	static Artist artist1, artist2, artist3;
	static List<Artist> listArtists1, listArtists2, listArtists3;
	static Party party1, party2, party3;
	
	@BeforeClass
	public static void setUp() throws Exception {
		dao = new PartyDaoImpl();
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
		
		adress = new Adress("18 rue des plantes", "Bordeaux", "33600");
		artist1 = new Artist("Jean", "variete");
		artist2 = new Artist("Robert", "variete");
		artist3 = new Artist("George", "variete");
		listArtists1 = new ArrayList<Artist>();
		listArtists1.add(artist1);
		listArtists2 = new ArrayList<Artist>();
		listArtists2.add(artist2);
		listArtists3 = new ArrayList<Artist>();
		listArtists3.add(artist3);
		party1 = new Party("Le concert du saucisson", dateParty1, timeParty1, dateBegin1, dateEnd1, "succes enorme en France", 200, "variete", 25.50, adress, listArtists1);
		party2 = new Party("Le concert du jambon", dateParty2, timeParty2, dateBegin2, dateEnd2, "succes enorme en France", 200, "variete", 50.50, adress, listArtists2);
		party3 = new Party("Le concert du pate", dateParty3, timeParty3,dateBegin3, dateEnd3, "succes enorme en France", 200, "variete", 100.50, adress, listArtists3);
	}
	
	@Test
	public void testA_AddParty(){
		try {
			dao.addParty(party1);
			dao.addParty(party2);
			dao.addParty(party3);
			assertFalse(dao.getAllParties().isEmpty());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testAddParty");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testB_UpdateParty(){
		try {
			Party p1 = dao.getParty(1);
			p1.setNbPlace(500);
			dao.updateParty(p1);
			Party p2 = dao.getParty(1);
			assertEquals(500, p2.getNbPlace());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testupdateParty");
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testC_DeleteParty(){
		try {
			dao.deleteParty(1);
			assertTrue(dao.getAllParties().isEmpty());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testDeleteParty");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testD_PartiesNotBegun(){
		try {
			dao.getPartiesNotBegun();
			assertFalse(dao.getAllParties().isEmpty());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testPartiesNotBegun");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testE_PartiesNotBegunMaxResult(){
		List<Party> list = new ArrayList<Party>();
		try {
			list = dao.getPartiesNotBegunMaxResult(1, 2);
			assertEquals(2, list.size());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testPartiesNotBegunMaxResult");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testF_PartiesCriteria(){
		List<Party> list = new ArrayList<Party>();
		Calendar calendar = new GregorianCalendar(2013, 04, 15);
		Calendar time = new GregorianCalendar(0, 0, 0, 20, 30, 00);
		try {
			list = dao.getPartiesCriteria(25.50, 30.00, calendar, time);
			assertEquals(1, list.size());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testPartiesCriteria");
			e.printStackTrace();
		}
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
