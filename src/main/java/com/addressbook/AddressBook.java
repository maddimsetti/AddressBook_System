package com.addressbook;
/**
 * Create Class for Defining the Address Book
 */
public class AddressBook {
    String firstName, lastName, address, city, state, eMail, phoneNumber;
    int zipCode;
    /**
     * Create Constructor for Initializing the objects
     */
    public AddressBook(String firstName, String lastName, String address, String city, String state,
                         int zipCode, String eMail, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
    }
    //Printing the AddressBook
    public void addressBook() {
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Address: " + address);
        System.out.println("City: " + city);
        System.out.println("State: " + state);
        System.out.println("ZipCode: " + zipCode);
        System.out.println("Email Address: " + eMail);
        System.out.println("Phone Number: " + phoneNumber);
    }

    /**
     * Create Main Method for Implementing the Address Book
     */
    public static void main (String[] args) {
        System.out.println("Welcome to Address Book Program in AddressBook in Main Class");
        AddressBook book = new AddressBook(null,null,null,null,null,0,null,null);
        book.addressBook();
    }
}
