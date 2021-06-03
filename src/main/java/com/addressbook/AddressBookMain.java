package com.addressbook;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.addressbook.AddressBook.person;

public class AddressBookMain {
    private static String addressBookName;
    //Variables
    private static AddressBook addressBook = new AddressBook(person);
    private static final Scanner scan = new Scanner(System.in);
    public static HashMap<String, AddressBook> addressBookSystem = new HashMap<>();
    private static String state;
    private static String city;

    /**
     * Create Method for Implementing the Address Book
     */
    public static void addressBook() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, AddressBookException {
        boolean choose = false;
        while (true) {
            System.out.println(" 1.Create\n 2.Edit\n 3.Delete\n 4.Write Data To File\n 5.Read Data from Console\n " +
                    "6.Write Contacts to CSV\n 7.Read Contacts from CSV\n 8.Write Contacts to JSON File\n " +
                    "9.Read Contacts From JSON File\n 10.Exit the loop");
            System.out.println("Enter the choice What you want do");
            int choice = scan.nextInt();
            switch (choice) {
                case 1 -> {
                    Scanner consoleInputReader = new Scanner(System.in);
                    addressBook.addContactDetails(consoleInputReader);
                }
                case 2 -> {
                    addressBook.editContactDetailsByFirstName();
                }
                case 3 -> {
                    addressBook.deleteContactByFirstName();       //Calling Delete Contact Method
                }
                case 4 -> {
                    addressBook.writeAddressBook(AddressBook.IOService.FILE_IO);
                }
                case 5 -> {
                    addressBook.readAddressBook(AddressBook.IOService.CONSOLE_IO);
                }
                case 6 -> {
                    addressBook.writeAddressBookContactsToCSV();
                }
                case 7 -> {
                    addressBook.readAddressBookContactsFromCSV();
                }
                case 8 -> {
                    addressBook.writeContactsToJsonFile();
                }
                case 9 -> {
                    addressBook.readContactsFromJsonFile();
                }
                case 10 -> {
                    System.out.println("*******THANK YOU***********");
                }
                default -> {
                    System.out.println("Choice is incorrect");
                }
            }
            System.out.println("Do you wish to continue, Say Yes or No");
            scan.nextLine();
            String option = scan.nextLine();
            if(option.equals("yes"))
                choose = false;
            else
                break;
        }
    }

    /**
     * Create Method for Implementing the Main Address Book
     */
    public static void mainAddressBook() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, AddressBookException {
        AddressBookMain addressBookMain = new AddressBookMain();
        boolean option = false;
        while (true) {
            System.out.println(" 1.Creating AddressBook\n 2.Search by City\n 3.Search by State\n 4.View Person By City\n 5.View Person By State\n 6.Count By city\n " +
                    "7.Count By State\n 8.Sort Contact By Name\n 9.Sort Contact By City\n 10.Sort Contact By State\n 11.Sort Contact By ZipCode\n 12.Exit the Loop");
            System.out.println("Enter The Choice");
            int choice = scan.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter the Name of the Address Book");
                    scan.nextLine();
                    String addressBookName = scan.nextLine();
                    if (addressBookSystem.containsKey(addressBookName)) {
                        System.out.println("This Address Book Already Exists,Please Try With Different Name");
                    } else {
                        addressBookMain.addressBook();
                        addressBookSystem.put(addressBookName,addressBook);
                    }
                }
                case 2 -> {
                    city();
                    addressBookMain.searchByCity(city);
                }
                case 3 -> {
                    state();
                    addressBookMain.searchByState(state);
                }
                case 4 -> {
                    city();
                    addressBookMain.viewPersonByCity(city);
                }
                case 5 -> {
                   state();
                    addressBookMain.viewPersonByState(state);
                }
                case 6 -> {
                    city();
                    addressBookMain.countPersonByCity(city);
                }
                case 7 -> {
                    state();
                    addressBookMain.countPersonByState(state);
                }
                case 8 -> {
                    sortByPersonsName();
                }
                case 9 -> {
                    sortContactsByCity();
                }
                case 10 -> {
                    sortContactsByState();
                }
                case 11 -> {
                    sortContactsByZipCode();
                }
                case 12 -> System.exit(0);
            }
        }
    }

    /**
     * Create Method for state to follow DRY principal
     */
    public static void state() {
        System.out.println("Enter the Name of StateName");
        scan.nextLine();
        state = scan.nextLine();
    }
    /**
     * Create Method for city to follow DRY principal
     */
    public static void city() {
        System.out.println("Enter the Name of CityName");
        scan.nextLine();
        city = scan.nextLine();
    }

    /**
     * Create Method for Search person By state
     */
    private void searchByState(String state) {
        for (Map.Entry<String, AddressBook> book : addressBookSystem.entrySet()) {
            AddressBook value = book.getValue();
            System.out.println("The AddressBookName: " + book.getKey());
            value.searchPersonByState(state);
        }
    }
    /**
     * Create Method for Search Person By City
     */
    private static void searchByCity(String city) {
        for (Map.Entry<String, AddressBook> book : addressBookSystem.entrySet()) {
            AddressBook value = book.getValue();
            System.out.println("The AddressBookName: " + book.getKey());
            value.searchPersonByCity(city);
        }
    }
    /**
     * Create Method for View Person By City
     */
    private void viewPersonByCity(String city) {
        for (Map.Entry<String,AddressBook> book : addressBookSystem.entrySet()) {
            AddressBook value = book.getValue();
            List<ContactPerson> contactPeople = value.personByCity.entrySet().stream().filter(find -> find.getKey().equals(city)).map(Map.Entry::getValue).findFirst().orElse(null);
            for(ContactPerson contact: contactPeople){
                System.out.println("The First Name: " +contact.getFirstName() + " The Last Name: "+ contact.getLastName());
            }
        }
    }
    /**
     * Create Method for View Person By State
     */
    private void viewPersonByState(String State) {
        for (Map.Entry<String, AddressBook> book : addressBookSystem.entrySet()) {
            AddressBook value = book.getValue();
            List<ContactPerson> contactPeople = value.personByState.entrySet().stream().filter(find -> find.getKey().equals(State)).map(Map.Entry::getValue).findFirst().orElse(null);
            for(ContactPerson contact: contactPeople){
                System.out.println("From AddressBook: " +book.getKey()+ " \nFirst Name: " + contact.getFirstName() + " Last Name: " + contact.getLastName());
            }
        }
    }
    /**
     * Create Method for count person By State
     */
    public void countPersonByState(String state) {
        int countPersonByState = 0;
        for(Map.Entry<String, AddressBook> entry: addressBookSystem.entrySet()){
            for(int i=0;i<(entry.getValue()).person.size();i++) {
                ContactPerson contact= entry.getValue().person.get(i);
                if(state.equals(contact.getState())) {
                    countPersonByState++;
                }
            }
        }
        System.out.println("Count Of Persons in state "+state+": "+countPersonByState);
    }
    /**
     * Create Method for count Person By city
     */
    public void countPersonByCity(String city) {
        int countPersonByCity = 0;
        for(Map.Entry<String, AddressBook> entry: addressBookSystem.entrySet()){
            for(int i=0;i<(entry.getValue()).person.size();i++) {
                ContactPerson contact= entry.getValue().person.get(i);
                if(city.equals(contact.getCity())) {
                    countPersonByCity++;
                }
            }
        }
        System.out.println("Count Of Persons in City "+city+": "+countPersonByCity);
    }
    /**
     * Create Method for List by sorting with Persons Name;
     */
    public static void sortByPersonsName () {
        for (Map.Entry<String,AddressBook>entry:addressBookSystem.entrySet()){
            AddressBook value = entry.getValue();
            List<ContactPerson> sortedList = value.person.stream().sorted(Comparator.comparing(ContactPerson::getFirstName)).collect(Collectors.toList());
            for(ContactPerson contact:sortedList){
                System.out.println("The First Name: "+contact.getFirstName()+ " The Last Name: "+contact.getLastName());
            }
        }
    }
    /**
     * Create Method for List by sorting with city;
     */
    public static void sortContactsByCity () {
        for (Map.Entry<String,AddressBook>entry:addressBookSystem.entrySet()){
            AddressBook value = entry.getValue();
            List<ContactPerson> sortedList = value.person.stream().sorted(Comparator.comparing(ContactPerson::getCity)).collect(Collectors.toList());
            for(ContactPerson contact:sortedList){
                System.out.println("The First Name: "+contact.getFirstName()+ " The Last Name: "+contact.getLastName());
            }
        }
    }
    /**
     * Create Method for List by sorting with state;
     */
    private static void sortContactsByState() {
        for (Map.Entry<String,AddressBook>entry:addressBookSystem.entrySet()){
            AddressBook value = entry.getValue();
            List<ContactPerson> sortedList = value.person.stream().sorted(Comparator.comparing(ContactPerson::getState)).collect(Collectors.toList());
            for(ContactPerson contact:sortedList){
                System.out.println("The First Name: "+contact.getFirstName()+ " The Last Name: "+contact.getLastName());
            }
        }
    }
    /**
     * Create Method for List by sorting with zipCode;
     */
    private static void sortContactsByZipCode() {
        for (Map.Entry<String,AddressBook>entry:addressBookSystem.entrySet()){
            AddressBook value = entry.getValue();
            List<ContactPerson> sortedList = value.person.stream().sorted(Comparator.comparing(ContactPerson::getZipCode)).collect(Collectors.toList());
            for(ContactPerson contact:sortedList){
                System.out.println("The First Name: "+contact.getFirstName()+ " The Last Name: "+contact.getLastName());
            }
        }
    }

    /**
     * Create Main Method for Implementing the Address Book Main System
     */
    public static void main (String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, AddressBookException {
        System.out.println("Welcome to Address Book Program in AddressBook in Main Class");
        AddressBookMain addressBookMain = new AddressBookMain();
        addressBookMain.mainAddressBook();
    }

}
