package com.addressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

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
}
