package com.addressbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Create Class for Defining the Address Book
 */
public class AddressBook {

    //Created Enum
    public enum IOService {
        CONSOLE_IO,FILE_IO,DB_IO
    }
    //variables
    private static final String SAMPLE_CSV_FILE_PATH = "O:\\Intellij\\AddressBook_System\\src\\test\\resources\\contacts.csv";
    private static final String SAMPLE_JSON_FILE_PATH = "O:\\Intellij\\AddressBook_System\\src\\test\\resources\\contacts.json";
    public static List<ContactPerson> person = new ArrayList<>();
    public static Map<String,List<ContactPerson>> personByCity;
    public static Map<String,List<ContactPerson>> personByState;
    private AddressBookDBService addressBookDBService;
    //Constructor with single parameter
    public AddressBook() {
        addressBookDBService = AddressBookDBService.getInstance();
    }
    /**
     * Create Constructor for Initializing the variables with parameters
     */
    public AddressBook(List<ContactPerson> person) {
        this();
        this.person = person;
        this.personByCity = new HashMap<>();
        this.personByState= new HashMap<>();

    }

    /**
     * Create Method to Add the Contact List.
     */
    public void addContactDetails (Scanner consoleInputReader) {
        List<ContactPerson> addContactDetails = new ArrayList<>();
        System.out.println("How Many Contacts Do You Want to Enter In Address Book");
        int numberOfContacts = consoleInputReader.nextInt();
        for (int i = 1; i <= numberOfContacts; i++) {
            System.out.println("enter the First Name");
            consoleInputReader.nextLine();
            String firstName = consoleInputReader.nextLine();
            System.out.println("enter the Last Name");
            String lastName = consoleInputReader.nextLine();
            System.out.println("enter the Address");
            String address = consoleInputReader.nextLine();
            System.out.println("enter the City");
            String city = consoleInputReader.nextLine();
            System.out.println("enter the State");
            String state = consoleInputReader.nextLine();
            System.out.println("enter the Zip Code");
            int zipCode = consoleInputReader.nextInt();
            consoleInputReader.nextLine();
            System.out.println("enter the Email address");
            String eMail = consoleInputReader.nextLine();
            System.out.println("enter the Phone Number");
            String phoneNumber = consoleInputReader.nextLine();

            String pName = firstName + lastName;
            for (Iterator<ContactPerson> iterator = person.iterator(); iterator.hasNext(); ) {
                ContactPerson temp = iterator.next();
                String contactName = temp.getFirstName() + temp.getLastName();
                if (contactName.equals(pName)) {
                    System.out.println("Sorry this contact already exists.");
                    return; // the name exists, so we exit the method.
                }
            }
            // Otherwise... you've checked all the elements, and have not found a duplicate
            person.add(new ContactPerson(firstName, lastName, address, city, state, zipCode, eMail, phoneNumber));//Storing the Contacts
            System.out.println(person); //Printing the Contacts

            addContactDetails.add(new ContactPerson(firstName, lastName, address, city, state, zipCode, eMail, phoneNumber));

            if(!personByState.containsKey(state)){
                personByState.put(state,addContactDetails);
            }

            if(!personByCity.containsKey(city)){
                personByCity.put(city,addContactDetails);
            }
        }
    }

    /**
     * Create Method for Reading the AddressBook from Console
     */
    public List<ContactPerson> readAddressBookData(IOService ioService) {
        if (ioService.equals(AddressBook.IOService.CONSOLE_IO))
            this.person = new AddressBookIOService().readData();
        return person;
    }

    /**
     * Create Main Method for Writing the addressBook to a File
     */
    public void writeAddressBook(AddressBook.IOService ioService) throws IOException {
        if(ioService.equals(IOService.CONSOLE_IO))
            System.out.println("\n Writing Employee PayRoll Roaster to Console\n " +person);
        else if (ioService.equals(IOService.FILE_IO))
            new AddressBookIOService().writeData(person);
    }
    /**
     * Create Method for Reading the AddressBook from DataBase
     */
    public List<ContactPerson> readAddressBook(IOService ioService) throws AddressBookException {
        if (ioService.equals(IOService.DB_IO))
            this.person = addressBookDBService.readData();
        return this.person;
    }

    /**
     * Create Method for Writing the addressBook contacts to csv
     */
    public void writeAddressBookContactsToCSV() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE_PATH));) {
            StatefulBeanToCsvBuilder<ContactPerson> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<ContactPerson> beanWriter = builder.build();
            beanWriter.write(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Create Method for Checking Contacts Sync with DataBase
     */
    public boolean checkContactsInSyncWithDB(String firstName) throws AddressBookException {
        List<ContactPerson> contactPersonList = addressBookDBService.getAddressBookDetails(firstName);
        return contactPersonList.equals(addressBookDBService.getAddressBookDetails(firstName));
    }
    /**
     * Create Method for Update the Contacts
     */
    public void updateContact(String firstName,int zipCode) throws AddressBookException {
        int result = addressBookDBService.updateContact(firstName, zipCode);
        if (result == 0) return;
        ContactPerson contactData = this.getContactData(firstName);
        if (contactData != null) {
            contactData.zipCode = zipCode;
        }
    }
    /**
     * Create Method for getting the data from contact Person
     */
    private ContactPerson getContactData (String firstName) {
        return this.person.stream().filter(addressBookData -> addressBookData.firstName.equals(firstName))
                .findFirst().orElse(null);
    }
    /**
     * Create Method for getting the data from DataBase
     */
    public List<ContactPerson> readAddressBookContactsForDataRange(LocalDate startDate, LocalDate endDate) throws AddressBookException {
        return addressBookDBService.getAddressBookContactsForDataRange(startDate,endDate);
    }

    public int readContactByCity(String city) throws AddressBookException {
        return addressBookDBService.getCountByCity(city);
    }

    /**
     * Create Method for Reading the addressBook contacts from a csv
     */
    public void readAddressBookContactsFromCSV() throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                System.out.println("First Name - " + nextRecord[0]);
                System.out.println("Last Name - " + nextRecord[1]);
                System.out.println("Address - " + nextRecord[1]);
                System.out.println("City - " + nextRecord[1]);
                System.out.println("State - " + nextRecord[1]);
                System.out.println("Email - " + nextRecord[1]);
                System.out.println("Phone - " + nextRecord[1]);
                System.out.println("Zip - " + nextRecord[1]);
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }
    /**
     * Create Method for Writing the addressBook contacts from Json File
     */
    public void writeContactsToJsonFile() throws IOException {
        {
            Gson gson = new Gson();
            String json = gson.toJson(person);
            FileWriter writer = new FileWriter(SAMPLE_JSON_FILE_PATH);
            writer.write(json);
            writer.close();
        }
    }
    /**
     * Create Method for Reading the addressBook contacts from Json File
     */
    public void readContactsFromJsonFile() throws IOException {
        List<ContactPerson> contactPersonList = null;
        try (Reader reader = Files.newBufferedReader(Path.of(SAMPLE_JSON_FILE_PATH));) {
            Gson gson = new Gson();
            contactPersonList = new ArrayList<ContactPerson>(Arrays.asList(gson.fromJson(reader, ContactPerson[].class)));
            for (ContactPerson contactList : contactPersonList) {
                System.out.println("First Name : " + contactList.getFirstName());
                System.out.println("Last Name : " + contactList.getLastName());
                System.out.println("Address : " + contactList.getAddress());
                System.out.println("City : " + contactList.getCity());
                System.out.println("State : " + contactList.getState());
                System.out.println("ZipCode : " + contactList.getZipCode());
                System.out.println("Email : " + contactList.getMail());
                System.out.println("Phone number : " + contactList.getPhoneNumber());
            }
        }
    }

    /**
     * Create Method to Edit the Contact using First Name.
     */
    public void editContactDetailsByFirstName() {
        System.out.println("Enter First Name to verify and edit the Contact list");
        Scanner sc = new Scanner(System.in);
        String firstName = sc.nextLine();
        for (Iterator<ContactPerson> iterator = person.iterator(); iterator.hasNext();) {
            ContactPerson temp = iterator.next();
            if (temp.getFirstName().equalsIgnoreCase(firstName)) {
                System.out.println(" 1.First Name\n 2.Second Name\n 3.Address\n 4.City\n 5.State\n 6.Zip Code\n 7.Email Address\n 8.Phone Number\n");
                System.out.println("Enter the choice What you want to Edit");
                int choice = sc.nextInt();
                switch (choice) {       // choosing which option as to edit
                    case 1 -> {
                        System.out.println("Enter the New First Name");
                        Scanner sc1 = new Scanner(System.in);
                        firstName = sc1.nextLine();
                        temp.setFirstName(firstName);
                    }
                    case 2 -> {
                        System.out.println("Enter the New Last Name");
                        Scanner sc2 = new Scanner(System.in);
                        String lastName = sc2.nextLine();
                        temp.setLastName(lastName);
                    }
                    case 3 -> {
                        System.out.println("Enter the Address");
                        Scanner sc3 = new Scanner(System.in);
                        String address = sc3.nextLine();
                        temp.setAddress(address);
                    }
                    case 4 -> {
                        System.out.println("Enter the New City");
                        Scanner sc4 = new Scanner(System.in);
                        String city = sc4.nextLine();
                        temp.setCity(city);
                    }
                    case 5 -> {
                        System.out.println("Enter the New State");
                        Scanner sc5 = new Scanner(System.in);
                        String state = sc5.nextLine();
                        temp.setState(state);
                    }
                    case 6 -> {
                        System.out.println("Enter the New Zip Code");
                        Scanner sc6 = new Scanner(System.in);
                        int zipCode = sc6.nextInt();
                        temp.setZipCode(zipCode);
                    }
                    case 7 -> {
                        System.out.println("Enter the New Email Address");
                        Scanner sc7 = new Scanner(System.in);
                        String eMail = sc7.nextLine();
                        temp.setMail(eMail);
                    }
                    case 8 -> {
                        System.out.println("Enter the New Phone Number");
                        Scanner sc8 = new Scanner(System.in);
                        String phoneNumber = sc8.nextLine();
                        temp.setPhoneNumber(phoneNumber);
                    }
                }
                System.out.println("Contacts are Updated");
                System.out.println(person);
                return;
            }
        }
        System.out.println("No Contact Found To Edit");
    }

    /**
     * Create Method to Delete the Contact. Will work  as there is no  contacts with  first name.
     */
    public static void deleteContactByFirstName() {
        System.out.println("Enter the First Name to verify and delete the contact");
        Scanner sc = new Scanner(System.in);
        String firstName = sc.nextLine();
        int flag = 0;
        for (Iterator<ContactPerson> iterator = person.iterator(); iterator.hasNext();) {
            ContactPerson temp = iterator.next();
            if (temp.getFirstName().equalsIgnoreCase(firstName)){
                iterator.remove();
                System.out.println("The Contact with First Name " +firstName+ " Deleted Successfully");
                return;
            }
        }
        System.out.println("No contact With First Name " +firstName+ " will found" );
    }

    /**
     * Create Method to Search the Contact By Using City Name
     */
    public void searchPersonByCity(String city) {
        List<ContactPerson> search = person.stream().filter(first -> first.getCity().equals(city)).collect(Collectors.toList());
        for (ContactPerson contacts : search ) {
            System.out.println("FirstName: " + contacts.getFirstName() + " LastName: " + contacts.getLastName());
        }
    }

    /**
     * Create Method to Search the Contact By Using State Name
     */
    public void searchPersonByState(String state) {
        List<ContactPerson> search = person.stream().filter(first -> first.getState().equals(state)).collect(Collectors.toList());
        for (ContactPerson contacts : search ) {
            System.out.println("FirstName: " + contacts.getFirstName() + " LastName: " + contacts.getLastName());
        }
    }
}
