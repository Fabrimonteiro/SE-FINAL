package pt.ulisboa.tecnico.learnjava.bank.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.learnjava.bank.domain.Bank;
import pt.ulisboa.tecnico.learnjava.bank.domain.Client;
import pt.ulisboa.tecnico.learnjava.bank.domain.Person;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;

public class ConstructorMethodTest {
	private static final String ADDRESS_2 = "Bairro.";
	private static final String PHONE_NUMBER_2 = "912324541";
	private static final String NIF_2 = "987654321";
	private static final String LAST_NAME_2 = "Horta";
	private static final String FIRST_NAME_2 = "Aníbal";
	private static final int AGE_2 = 33;
	private Bank bank;
	private Client client;
	private Person person2;
	private Person person3;

	@Before
	public void setUp() throws BankException {
		this.bank = new Bank("CGD");
	}

	@Test
	public void success() throws ClientException {
		this.person2= new Person(FIRST_NAME_2,LAST_NAME_2 ,ADDRESS_2, AGE_2);
		this.client = new Client(this.bank, NIF_2, PHONE_NUMBER_2, this.person2);

		assertEquals(this.bank, client.getBank());
		assertEquals(FIRST_NAME_2, person2.getFirstName());
		assertEquals(LAST_NAME_2, person2.getLastName());
		assertEquals(NIF_2, client.getNif());
		assertEquals(PHONE_NUMBER_2, client.getPhoneNumber());
		assertEquals(ADDRESS_2, person2.getAddress());
		assertTrue(this.bank.isClientOfBank(client));
	}

	@Test(expected = ClientException.class)
	public void negativeAge() throws ClientException {
		this.person3 = new Person(FIRST_NAME_2,LAST_NAME_2 ,ADDRESS_2, -1);
		new Client(this.bank, NIF_2, PHONE_NUMBER_2, this.person3);
	}

	@Test(expected = ClientException.class)
	public void no9DigitsNif() throws ClientException {
		this.person3 = new Person(FIRST_NAME_2,LAST_NAME_2 ,ADDRESS_2, AGE_2);
		new Client(this.bank, "12345678A", PHONE_NUMBER_2, this.person3);
	}

	@Test(expected = ClientException.class)
	public void no9DigitsPhoneNumber() throws ClientException {
		this.person3 = new Person(FIRST_NAME_2,LAST_NAME_2 ,ADDRESS_2, AGE_2);
		new Client(this.bank, NIF_2, "A87654321", this.person3);
	}

	public void twoClientsSameNif() throws ClientException {
		this.person2 = new Person(FIRST_NAME_2,LAST_NAME_2 ,ADDRESS_2, AGE_2);
		new Client(this.bank, NIF_2, PHONE_NUMBER_2, this.person2);
		try {
			new Client(this.bank, NIF_2, PHONE_NUMBER_2, this.person2);
			fail();
		} catch (ClientException e) {
			assertEquals(1, this.bank.getTotalNumberOfClients());
		}
	}

	@After
	public void tearDown() {
		Bank.clearBanks();
	}

}
