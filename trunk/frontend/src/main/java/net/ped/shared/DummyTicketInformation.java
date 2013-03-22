package net.ped.shared;

import net.ped.model.Customer;
import net.ped.model.Party;
import net.ped.model.Ticket;

public class DummyTicketInformation extends TicketInformation {
	@Override
	public Ticket checkAuthorizedAccess() {
		
		Customer c = new Customer("Peyronnelle Clémentine",
				"Wong Wun Gatchunk", "clems@hotmail.fr");
		Ticket ticket = new Ticket(new Party(), c, "0EE3156789AB5D2A");
		return ticket;
	}
	
	@Override
	public boolean checkValidTicketInformation() {
		return true;
	}
}