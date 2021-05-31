package com.addressbook;

import java.util.Scanner;

/**
 * Create Class for Defining the Address Book
 */
public class AddressBook {
    //variables
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Create Method to Add the Contact List.
     */
    public void addContactDetails () {
        System.out.println("enter the First Name");
        String firstName = sc.nextLine();
        System.out.println("enter the Last Name");
        String lastName = sc.nextLine();
        System.out.println("enter the Address");
        String address = sc.nextLine();
        System.out.println("enter the City");
        String city = sc.nextLine();
        System.out.println("enter the State");
        String state = sc.nextLine();
        System.out.println("enter the Zip Code");
        int zipCode = sc.nextInt();
        sc.nextLine();
        System.out.println("enter the Email address");
        String eMail = sc.nextLine();
        System.out.println("enter the Phone Number");
        String phoneNumber = sc.nextLine();

        ContactPerson contact = new ContactPerson(firstName,lastName,address,city,state,zipCode,eMail,phoneNumber);
        contact.addressBook();
    }

    /**
     * Create Main Method for Implementing the Address Book
     */
    public static void main (String[] args) {
        System.out.println("Welcome to Address Book Program in AddressBook in Main Class");
        AddressBook book = new AddressBook();
        book.addContactDetails();
    }
}
