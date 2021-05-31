package com.addressbook;

import java.io.IOException;
import java.util.*;

/**
 * Create Class for Defining the Address Book
 */
public class AddressBook {
    //Created Enum for the File_Io
    public enum IOService {
        CONSOLE_IO,FILE_IO
    }
    //variables
    private static final Scanner sc = new Scanner(System.in);
    public static List<ContactPerson> person = new ArrayList<ContactPerson>();
    public static HashMap<String, List<ContactPerson>> addressBookSystem = new HashMap<>();

    /**
     * Create Constructor for Initializing the variables with parameters
     */
    public AddressBook(List<ContactPerson> person) {
        this.person = person;
    }
    //Empty Constructor
    public AddressBook() { }
    /**
     * Create Method to Add the Contact List.
     */
    public void addContactDetails (Scanner consoleInputReader) {
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
            person.add(new ContactPerson(firstName, lastName, address, city, state, zipCode, eMail, phoneNumber)); //Storing the Contacts
            System.out.println(person); //Printing the Contacts
        }
    }

    /**
     * Create Method for Reading the AddressBook from Console
     */
    public List<ContactPerson> readAddressBook(IOService ioService) {
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
     * Create Method to Edit the Contact using First Name.
     */
    public void editContactDetailsByFirstName() {
        System.out.println("Enter First Name to verify and edit the Contact list");
        Scanner sc = new Scanner(System.in);
        String firstName = sc.nextLine();
        for (Iterator<ContactPerson> iterator = person.iterator(); iterator.hasNext();) {
            ContactPerson temp = iterator.next();
            if (temp.getFirstName().equalsIgnoreCase(firstName)) {
                System.out.println("1.First Name\n,2.Second Name\n,3.Address\n,4.City\n,5.State\n,6.Zip Code\n,7.Email Address\n,8.Phone Number\n");
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





}
