package test;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPartyTest {

	private static final Logger LOG = LoggerFactory.getLogger(MyPartyTest.class);
	
	@Rule public TestName name = new TestName();
	
	static PartyDaoImpl dao;
	static Calendar dateParty, dateBegin, dateEnd;
	static Adress adress;
	static Artist artist1, artist2;
	static List<Artist> listArtists;
	static Party party;
	
	@BeforeClass
	public static void setUp() throws Exception {
		dao = new PartyDaoImpl();
		dateParty = new GregorianCalendar(2013, 04, 15, 21, 30);
		dateBegin = new GregorianCalendar(2013, 04, 01, 10, 30);
		dateEnd = new GregorianCalendar(2013, 04, 10, 11, 30);
		adress = new Adress("18 rue des plantes", "Bordeaux", "France");
		artist1 = new Artist("Jean", "variete");
		artist2 = new Artist("Robert", "variete");
		listArtists = new ArrayList<Artist>();
		listArtists.add(artist1);
		listArtists.add(artist2);
		party = new Party("Le concert du saucisson", dateParty, dateBegin, dateEnd, "succes enorme en France", 200, "variete", 25.50, adress, listArtists);
	}
	
	@Test
	public void testAddParty(){
		try {
			dao.addParty(party);
			assertFalse(dao.getAllParties().isEmpty());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testAddParty");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateParty(){
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
	
	@Test
	public void testDeleteParty(){
		try {
			dao.deleteParty(1);
			assertTrue(dao.getAllParties().isEmpty());
		} catch (Exception e) {
			LOG.error("erreur lors de l'execution de la methode testDeleteParty");
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
