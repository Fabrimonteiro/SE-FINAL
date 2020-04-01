package pt.ulisboa.tecnico.learnjava.bank.bank;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.learnjava.bank.domain.Bank;
import pt.ulisboa.tecnico.learnjava.bank.domain.Client;
import pt.ulisboa.tecnico.learnjava.bank.domain.Person;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;

public class DeleteClientMethodTest {
	private static final String ADDRESS_2 = "Bairro.";
	private static final String PHONE_NUMBER_2 = "912324541";
	private static final String NIF_2 = "987654321";
	private static final String LAST_NAME_2 = "Horta";
	private static final String FIRST_NAME_2 = "Aníbal";
	private static final int AGE_2 = 33;
	

	private Bank bank;
	private Client client;
	private Person person2;

	@Before
	public void setUp() throws BankException, ClientException {
		this.bank = new Bank("CGD");
		this.person2= new Person(FIRST_NAME_2,LAST_NAME_2 ,ADDRESS_2, AGE_2);
		this.client = new Client(this.bank, NIF_2, PHONE_NUMBER_2, this.person2);
	}

	@Test
	public void success() throws BankException, AccountException {
		this.bank.deleteClient(NIF_2);

		assertEquals(0, this.bank.getTotalNumberOfClients());
	}

	@Test
	public void successClientWithAccounts() throws BankException, AccountException, ClientException {
		this.bank.createAccount(Bank.AccountType.CHECKING, this.client, 100, 0);
		this.bank.createAccount(Bank.AccountType.CHECKING, this.client, 100, 0);

		this.bank.deleteClient(NIF_2);

		assertEquals(0, this.bank.getTotalNumberOfClients());
		assertEquals(0, this.bank.getTotalNumberOfAccounts());
	}

	@Test(expected = BankException.class)
	public void noClientForNif() throws BankException, AccountException {
		this.bank.deleteClient("123456780");
	}

	@After
	public void tearDown() {
		Bank.clearBanks();
	}

}
