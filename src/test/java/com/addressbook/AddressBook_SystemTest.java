package com.addressbook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BooleanSupplier;

public class AddressBook_SystemTest {
    /**
     * @description create Method for Testing the AddressBook Contacts When Reading Files should match the contact Count
     *
     */
    @Test
    public void givenAddressBookInDB_WhenRetrieved_ShouldMatchContactCount () throws AddressBookException {
        AddressBook addressBook = new AddressBook();
        List<ContactPerson> contactPersonList = addressBook.readAddressBook(AddressBook.IOService.DB_IO);
        Assertions.assertEquals(8,contactPersonList.size());
    }
    /**
     * @description create Method for Testing the AddressBook Contacts When Updated should match
     *
     */
    @Test
    public void givenNewZipCodeForContactPerson_WhenUpdated_ShouldMatch() throws AddressBookException {
        AddressBook addressBook = new AddressBook();
        addressBook.updateContact("Krishna",544789);
        boolean result = addressBook.checkContactsInSyncWithDB("Krishna");
        Assertions.assertTrue(result);
    }
    /**
     * @description create Method for Testing the AddressBook Contacts When Retrieved should match the data range
     *
     */
    @Test
    public void givenAddressBookInDB_WhenRetrieved_ShouldMatchCountInGivenRange() throws AddressBookException {
        AddressBook addressBook = new AddressBook();
        List<ContactPerson> contactPersonList = addressBook.readAddressBook(AddressBook.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2018,01,01);
        LocalDate endDate = LocalDate.now();
        List<ContactPerson> contactsForDataRange = addressBook.readAddressBookContactsForDataRange(startDate,endDate);
        Assertions.assertEquals(2, contactsForDataRange.size());
    }

    @Test
    public void givenAddressBookInDB_WhenRetrieved_ShouldReturnCountOfCity() throws AddressBookException {
        AddressBook addressBook = new AddressBook();
        List<ContactPerson> contactPersonList = addressBook.readAddressBookData(AddressBook.IOService.DB_IO);
        int contactsInCity =addressBook.readContactByCity("Bangalore");
        Assertions.assertEquals(2,contactsInCity);
    }
}
