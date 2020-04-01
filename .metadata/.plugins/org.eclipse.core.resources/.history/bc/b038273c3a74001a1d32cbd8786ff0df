package pt.ulisboa.tecnico.learnjava.bank.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.learnjava.bank.domain.Account;
import pt.ulisboa.tecnico.learnjava.bank.domain.Bank;
import pt.ulisboa.tecnico.learnjava.bank.domain.Client;
import pt.ulisboa.tecnico.learnjava.bank.domain.Person;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;

public class DeleteAccountMethodTest {
	private static final String ADDRESS_2 = "Bairro.";
	private static final String PHONE_NUMBER_2 = "912324541";
	private static final String NIF_2 = "987654321";
	private static final String LAST_NAME_2 = "Horta";
	private static final String FIRST_NAME_2 = "Aníbal";
	private static final int AGE_2 = 33;
	
	private Bank bank;
	private Client client;
	private Account account;
	private Services services;
	private Person person2;
	

	@Before
	public void setUp() throws BankException, ClientException, AccountException {
		this.services = new Services();
		this.bank = new Bank("CGD");
		this.person2= new Person(FIRST_NAME_2,LAST_NAME_2 ,ADDRESS_2, AGE_2);
		this.client = new Client(this.bank, NIF_2, PHONE_NUMBER_2, this.person2);
		String iban = this.bank.createAccount(Bank.AccountType.CHECKING, this.client, 100, 0);
		this.account = this.services.getAccountByIban(iban);
	}

	@Test
	public void success() throws BankException, AccountException {
		this.bank.deleteAccount(this.account);

		assertEquals(0, this.bank.getTotalNumberOfAccounts());
		assertFalse(this.client.hasAccount(this.account));
	}

	@After
	public void tearDown() {
		Bank.clearBanks();
	}

}
