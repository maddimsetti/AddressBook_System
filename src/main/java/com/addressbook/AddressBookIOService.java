package com.addressbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddressBookIOService {
    //Variables
    public static String ADDRESS_BOOK_FILE_NAME = "addressBook-file.txt";
    /**
     * Create Method for Writing the Address Book Data to File
     */
    public void writeData(List<ContactPerson> person) {
        StringBuffer contactBuffer = new StringBuffer();
        person.forEach(addressBook -> {
            String contactDataString = addressBook.toString().concat("\n");
            contactBuffer.append(contactDataString);
        });
        try {
            Files.write(Paths.get(ADDRESS_BOOK_FILE_NAME), contactBuffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Create Method for Reading the Address Book from Console
     */
    public List<ContactPerson> readData() {
        List<ContactPerson> person = new ArrayList<>();
        try {
            Files.lines(new File(ADDRESS_BOOK_FILE_NAME).toPath()).map(line -> line.trim()).forEach(line -> System.out.println(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return person;
    }
}
