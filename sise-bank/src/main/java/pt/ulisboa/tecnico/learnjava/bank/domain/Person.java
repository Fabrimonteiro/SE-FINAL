package pt.ulisboa.tecnico.learnjava.bank.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;

public class Person {
	
	private final String firstName;
	private final String lastName;
	private final String address;
	private int age;
	private Collection<? extends Account> accounts;

	public Person (String firstName, String lastName,  String address, int age) throws ClientException {
	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
	this.age = age;
	
	if (age < 0) {
		throw new ClientException();
	}
	
}
	public void happyBirthDay() throws BankException, AccountException, ClientException {
		this.age++;

		if (this.age == 18) {
			Set<Account> accounts = new HashSet<Account>(this.accounts);
			for (Account account : accounts) {
				YoungAccount youngAccount = (YoungAccount) account;
				youngAccount.upgrade();
			}
		}
	}



	public String getFirstName() {
		return this.firstName;
	}


	public String getLastName() {
		return this.lastName;
	}


	public String getAddress() {
		return this.address;
	}
	
	public int getAge() {
		return this.age;
	}
	public int setAge() {
		return this.age;
	}
}