package com.addressbook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Create Class for Defining the Address Book
 */
public class AddressBook {
    //variables
    private static final Scanner sc = new Scanner(System.in);
    private static final List<ContactPerson> person = new ArrayList<ContactPerson>();


    /**
     * Create Method to Add the Contact List.
     */
    public void addContactDetails () {
        System.out.println("enter the First Name");
        Scanner sc = new Scanner(System.in);
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

        person.add(new ContactPerson(firstName,lastName,address,city,state,zipCode,eMail,phoneNumber));
        System.out.println(person);
    }

    /**
     * Create Method to Edit the Contact using First Name.
     */
    public  void editContactDetailsByFirstName() {
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

    /**
     * Create Main Method for Implementing the Address Book
     */
    public static void main (String[] args) {
        System.out.println("Welcome to Address Book Program in AddressBook in Main Class");
        boolean option = false;
        while (true) {
            System.out.println("1.Create\n, 2.Edit\n,3.Delete\n, 4.Exit the loop");
            System.out.println("Enter the choice What you want do");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    AddressBook addContact = new AddressBook();
                    addContact.addContactDetails();
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
                case 4 -> System.exit(0);
                default -> {
                    System.out.println("Choice is incorrect");
                }
            }
        }
    }
}
