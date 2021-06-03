package com.addressbook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

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
}
