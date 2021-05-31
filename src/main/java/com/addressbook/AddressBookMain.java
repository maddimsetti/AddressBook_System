package com.addressbook;

import java.io.IOException;
import java.util.Scanner;

import static com.addressbook.AddressBook.addressBookSystem;
import static com.addressbook.AddressBook.person;

public class AddressBookMain {
    //Variables
    AddressBook addressBook = new AddressBook();
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Create Method for Implementing the Address Book
     */
    public static void addressBook() throws IOException {
        boolean option = false;
        while (true) {
            System.out.println("1.Create\n, 2.Edit\n, 3.Delete\n, 4.Write Data To File\n, 5.Read Data from Console\n,  6.Exit the loop");
            System.out.println("Enter the choice What you want do");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    AddressBook addContact = new AddressBook(person);
                    Scanner consoleInputReader = new Scanner(System.in);
                    addContact.addContactDetails(consoleInputReader);
                    option = true;
                }
                case 2 -> {
                    AddressBook editContact = new AddressBook();
                    editContact.editContactDetailsByFirstName();
                    option = true;
                }
                case 3 -> {
                    AddressBook deleteContact = new AddressBook();
                    deleteContact.deleteContactByFirstName();       //Calling Delete Contact Method
                    option = true;
                }
                case 4 -> {
                    AddressBook addressBook = new AddressBook();
                    addressBook.writeAddressBook(AddressBook.IOService.FILE_IO);
                }
                case 5 -> {
                    AddressBook addressBook = new AddressBook();
                    addressBook.readAddressBook(AddressBook.IOService.CONSOLE_IO);
                }
                case 6 -> System.exit(0);
                default -> {
                    System.out.println("Choice is incorrect");
                }
            }
        }
    }

    /**
     * Create Main Method for Implementing the Address Book Main System
     */
    public static void main (String[] args) throws IOException {
        System.out.println("Welcome to Address Book Program in AddressBook in Main Class");
        System.out.println("Enter the Name of the Address Book");
        sc.nextLine();
        String addressBookName = sc.nextLine();
        if (addressBookSystem.containsKey(addressBookName)) {
            System.out.println("This Address Book Already Exists");
        } else {
            AddressBookMain addAddressBook = new AddressBookMain();
            addressBookSystem.put(addressBookName, person);
            addAddressBook.addressBook();
        }
    }

}
