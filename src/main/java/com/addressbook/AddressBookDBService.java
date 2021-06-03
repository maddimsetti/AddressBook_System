package com.addressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private String sql;
    private List<ContactPerson> addressBookList = new ArrayList<>();
    private PreparedStatement contactDataStatement;
    private static AddressBookDBService addressBookDBService;
    private AddressBookDBService() { }
    /**
     * Create Static instance Method
     */
    public static AddressBookDBService getInstance() {
        if (addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }

    /**
     * @description Create Method for get connection with Database sql
     *
     */
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressBook_services?useSSL=false";
        String userName = "root";
        String password = "admin";
        Connection con;
        System.out.println("Connecting to database:" +jdbcURL);
        con = DriverManager.getConnection(jdbcURL,userName,password);
        System.out.println("Connection is Successful!!!!!" +con);
        return con;
    }

    /**
     * @description Create Method for Reading the AddressBook Data From DataBase
     *
     */
    public List<ContactPerson> readData() throws AddressBookException {
        String sql = "SELECT * FROM addressBook";
        List<ContactPerson> addressBookList = new ArrayList<>();
        try ( Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(),AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
        return addressBookList;
    }
    /**
     * @description Create Method for getting the AddressBook Data From DataBase
     *
     */
    private List<ContactPerson> getAddressBookData(ResultSet resultSet) throws AddressBookException {
        List<ContactPerson> addressBookPersonList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                int zipCode = resultSet.getInt("zipCode");
                String eMail_Address = resultSet.getString("eMail_Address");
                String phone_Number = resultSet.getString("phone_Number");
                addressBookPersonList.add(new ContactPerson(firstName,lastName,address,city,state,zipCode,eMail_Address,phone_Number));
            }
        } catch (SQLException e) {
            throw  new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
        return addressBookPersonList;
    }

    /**
     * Create Method for updating the contacts
     */
    public int updateContact(String name, int zipCode) throws AddressBookException {
        return this.updateContactUsingStatement(name,zipCode);
    }
    //Updating the contacts using Statement method
    private int updateContactUsingStatement(String name, int zipCode) throws AddressBookException {
        String sql = "UPDATE addressBook set zipCode = 544789 WHERE firstName = 'Sowmith' ";
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(),AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
        return 0;
    }
    /**
     * Create Method for getting the AddressBook Details
     * @param firstName
     */
    public List<ContactPerson> getAddressBookDetails(String firstName) throws AddressBookException {
        List<ContactPerson> contactPersonList = null;
        if (this.contactDataStatement == null) {
            this.prepareStatementForContactData();
        }
        try {
            contactDataStatement.setString(1,firstName);
            ResultSet resultSet = contactDataStatement.executeQuery();
            contactPersonList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactPersonList;
    }
    //Create method for Preparing Statement for contact Data
    private void prepareStatementForContactData () throws AddressBookException {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM addressBook WHERE firstName = ?";
            contactDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new AddressBookException(e.getMessage(),AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
    }
    /**
     * Create Method for getting the data in between the DataRange
     */
    public List<ContactPerson> getAddressBookContactsForDataRange(LocalDate startDate, LocalDate endDate) throws AddressBookException {
        sql = "SELECT * FROM addressBook WHERE start BETWEEN CAST('2018-01-01' AS DATE) AND DATE(NOW())";
        getAddressBookList();
        return addressBookList;
    }
    /**
     * Create Method for getting the count of city
     */
    public int getCountByCity(String city) throws AddressBookException {
        int count = 0;
        sql = "SELECT * FROM addressBook where city = 'Bangalore'";
        getAddressBookList();
        count = (int) addressBookList.stream().count();
        return count;
    }
    /**
     * Create Method for getting the addressBook List
     */
    public List<ContactPerson> getAddressBookList () throws AddressBookException {
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookList = this.getAddressBookData(resultSet);
        } catch (SQLException | AddressBookException e) {
            throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
        }
        return addressBookList;
    }

}
