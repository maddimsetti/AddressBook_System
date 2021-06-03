package com.addressbook;
/**
 * @description Create Class for AddressBook Exception
 *
 */
public class AddressBookException extends Exception {
    //Created enum
    enum ExceptionType {
        DATABASE_EXCEPTION
    }
    public ExceptionType type;

    public AddressBookException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
