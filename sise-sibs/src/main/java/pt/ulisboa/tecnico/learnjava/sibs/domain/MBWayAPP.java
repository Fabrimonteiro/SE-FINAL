package pt.ulisboa.tecnico.learnjava.sibs.domain;

import java.util.Scanner;

import pt.ulisboa.tecnico.learnjava.bank.domain.Bank;
import pt.ulisboa.tecnico.learnjava.bank.domain.Client;
import pt.ulisboa.tecnico.learnjava.bank.domain.Person;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;

public class MBWayAPP {

	private static final String ADDRESS = "Ave.";

	// I change the verifier in the Create client to Accept numbers with one digit
	// to be easier to test it
	private static final String PHONE_NUMBER_1 = "111";
	private static final String PHONE_NUMBER_2 = "222";
	private static final String PHONE_NUMBER_3 = "333";
	private static final String PHONE_NUMBER_4 = "444";
	private static final String PHONE_NUMBER_5 = "555";

	private static final String NIF_1 = "123456789";
	private static final String NIF_2 = "123456788";
	private static final String NIF_3 = "123456787";
	private static final String NIF_4 = "123456786";
	private static final String NIF_5 = "123456785";

	private static final String LAST_NAME = "Silva";

	private static final String FIRST_NAME_1 = "A";
	private static final String FIRST_NAME_2 = "B";
	private static final String FIRST_NAME_3 = "C";
	private static final String FIRST_NAME_4 = "D";
	private static final String FIRST_NAME_5 = "E";
	
	public static Bank sourceBank;
	public static Bank targetBank;
	


	public static void main(String[] args) throws AccountException, ClientException, BankException {

		// Initialization parameters needed

		Services services = new Services();
		Sibs sibs = new Sibs(100, services);
		sourceBank = new Bank("CGD");
		targetBank = new Bank("BPI");
		
		Person person1 = new Person(FIRST_NAME_1,LAST_NAME ,ADDRESS, 33);
		Person person2 = new Person(FIRST_NAME_2,LAST_NAME ,ADDRESS, 33);
		Person person3 = new Person(FIRST_NAME_3,LAST_NAME ,ADDRESS, 33);
		Person person4 = new Person(FIRST_NAME_4,LAST_NAME ,ADDRESS, 33);
		Person person5 = new Person(FIRST_NAME_5,LAST_NAME ,ADDRESS, 33);
		

		Client Client1 = new Client(sourceBank, NIF_1, PHONE_NUMBER_1, person1);
		Client Client2 = new Client(sourceBank, NIF_2, PHONE_NUMBER_2, person2);
		Client Client3 = new Client(targetBank, NIF_3, PHONE_NUMBER_3, person3);
		Client Client4 = new Client(targetBank, NIF_4, PHONE_NUMBER_4, person4);
		Client Client5 = new Client(targetBank, NIF_5, PHONE_NUMBER_5, person5);


		String iban1 = sourceBank.createAccount(Bank.AccountType.CHECKING, Client1, 1000, 0);
		String iban2 = sourceBank.createAccount(Bank.AccountType.CHECKING, Client2, 1000, 0);

		String iban3 = targetBank.createAccount(Bank.AccountType.CHECKING, Client3, 1000, 0);
		String iban4 = targetBank.createAccount(Bank.AccountType.CHECKING, Client4, 1000, 0);
		String iban5 = targetBank.createAccount(Bank.AccountType.CHECKING, Client5, 1000, 0);

		System.out.println(iban1 + " " + iban2 + " " + iban3 + " " + iban4 + " " + iban5 + "\n\n ");

		///

		MBWayAccount model = new MBWayAccount();
		MBWayAccountView view = new MBWayAccountView();
		MBWayController controller = new MBWayController(model, view, services);

		// Tenho que adicionar os Banco/ Contas /CLientes

		controller.viewMenu();

		Scanner scan = new Scanner(System.in);
		String i = scan.nextLine();

		while (!i.equals("exit")) {

			if (i.equals("associate-mbway")) {
				controller.getView().askForIban();
				Scanner ib = new Scanner(System.in);
				String Iban = ib.nextLine();

				controller.getView().askForPhoneNumber();
				Scanner pn = new Scanner(System.in);
				String phoneNumber = pn.nextLine();

				controller.associateMbWay(Iban, phoneNumber);

			} else if (i.equals("confirm-mbway")) {
				controller.getView().pleaseSummitActivationCode();

				Scanner cd = new Scanner(System.in);
				String inputCode = cd.nextLine();

				controller.confirmMbWay(inputCode);

			} else if (i.equals("mbway-transfer")) {

				controller.getView().askForPhoneNumber();
				Scanner srPN = new Scanner(System.in);
				String sourcePhoneNumber = srPN.nextLine();

				controller.getView().askForTargetPhoneNumber();
				Scanner tgPN = new Scanner(System.in);
				String targetPhoneNumber = tgPN.nextLine();

				controller.getView().askForAmount();
				Scanner amt = new Scanner(System.in);
				int amount = amt.nextInt();

				controller.MBWayTransfer(sourcePhoneNumber, targetPhoneNumber, amount);

			} else if (i.equals("mbway-split-bill")) {
				// The phone number of the establishement charging the bill
				controller.getView().askForTargetPhoneNumber();
				Scanner responsiblePN = new Scanner(System.in);
				String targetPhoneNumber = responsiblePN.nextLine();
				controller.setTargetPhoneNumber(targetPhoneNumber);

				// The bill
				controller.getView().askForBill();
				Scanner b = new Scanner(System.in);
				int bill = b.nextInt();

				int soma = 0;
				int counterOfFriends = 0;

				// As for your phoneNumber
				controller.getView().askForPhoneNumber();
				Scanner yPN = new Scanner(System.in);
				String yourPhoneNumber = yPN.nextLine();
				controller.setYourPhoneNumber(yourPhoneNumber);

				// The amount that you want to pay
				controller.getView().askForAmount();
				Scanner amount1 = new Scanner(System.in);
				int amount = amount1.nextInt();

				controller.addTransfer(yourPhoneNumber, amount);

				controller.getView().menuOptionsSplitBill();
				Scanner scan1 = new Scanner(System.in);
				String a = scan1.nextLine();

				while (!a.equals("Terminate")) {

					counterOfFriends += 1;

					// Input friends phone Number
					controller.getView().askForFriendsPhoneNumber();
					Scanner friend = new Scanner(System.in);
					String friendPhoneNumber = friend.nextLine();

					controller.getView().askForAmount();
					Scanner am = new Scanner(System.in);
					int amountfriend = am.nextInt();

					soma += amount;

					controller.addTransfer(yourPhoneNumber, amountfriend);

				}

				controller.MBWaySplitBill(counterOfFriends, bill);
			}

			else if (i.equals("mbway-addfriend")) {
				controller.getView().askForPhoneNumber();
				Scanner yourPhone = new Scanner(System.in);
				String phoneNumber = yourPhone.nextLine();

				controller.getView().askForFriendsPhoneNumber();
				Scanner newFriendPhoneNumber = new Scanner(System.in);
				String friendPhoneNumber = newFriendPhoneNumber.nextLine();

				controller.addFriend(phoneNumber, friendPhoneNumber);

			} else {
				controller.viewInvalidInputMenu();
			}

			controller.viewMenu();
			Scanner scan1 = new Scanner(System.in);
			i = scan.nextLine();

		}

		System.out.println("MBWAY was terminated");
	}
}