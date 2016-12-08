package kea.intuition.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import kea.intuition.Intuition;
import kea.intuition.model.Company;
import kea.intuition.model.Note;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyContainer {
    private static ObservableList<Company> data;
    private static TableView tableStructure;
    private static FilteredList<Company> filteredList;
    private static SortedList<Company> sortedList;

    public static FilteredList<Company> getFilteredList() {
        return filteredList;
    }

    public static void setFilteredList(FilteredList<Company> filteredList) {
        CompanyContainer.filteredList = filteredList;
    }

    public static SortedList<Company> getSortedList() {
        return sortedList;
    }

    public static void setSortedList(SortedList<Company> sortedList) {
        CompanyContainer.sortedList = sortedList;
    }

    public static ObservableList<Company> getData() {
        return data;
    }

    public static void setData(ObservableList<Company> data) {
        CompanyContainer.data = data;
    }

    public static TableView getTableStructure() {
        return tableStructure;
    }

    public static void setTableStructure(TableView tableStructure) {
        CompanyContainer.tableStructure = tableStructure;
    }

    public static void addCompany(Company company) {
        data.add(company);
    }

    public static void editCompany(Company localCompany, Company newCompany) {
        // Attempt to save to database

        saveExistingCompanyToDb(newCompany);
        saveExistingNotesToDb(newCompany);


        // Refresh local data
        localCompany.setName(newCompany.getName());
        localCompany.setEmail(newCompany.getEmail());
        localCompany.setPhoneNumber(newCompany.getPhoneNumber());
        localCompany.setPhoneNumberCountryCallingCode(newCompany.getPhoneNumberCountryCallingCode());
        localCompany.setPhoneNumberPrefix(newCompany.getPhoneNumberPrefix());

        CompanyContainer.getTableStructure().refresh(); // Refreshes table view
    }

    public static ObservableList<Company> getCompaniesFromDb() {
        ObservableList<Company> data =  FXCollections.observableArrayList();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = Intuition.Config.getDb().getConnection().prepareStatement("select business_id, business_name, business_phone, business_email from business");

            rs = statement.executeQuery();
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }

        try {
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt(1));
                company.setName(rs.getString(2));
                company.setPhoneNumber(rs.getString(3));
                company.setEmail(rs.getString(4));
                data.add(company);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }

        return data;
    }

    public static Company getCompanyFromDb(int id) {
        ResultSet rs = null;
        Company company = new Company();

        PreparedStatement statement = null;

        // Prepare SQL statement
        try {
            statement = Intuition.Config.getDb().getConnection().prepareStatement("select business_id, business_name, business_phone, business_email from business where business_id = ?");
            statement.setInt(1, id);

            rs = statement.executeQuery();
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }

        try {
            while (rs.next()) {
                company.setId(rs.getInt(1));
                company.setName(rs.getString(2));
                company.setPhoneNumber(rs.getString(3));
                company.setEmail(rs.getString(4));
            }
        } catch (Exception ex) {

        }

        return company;
    }

    public static void saveExistingNotesToDb(Company company)
    {
        PreparedStatement statement = null;

        try {
            statement = Intuition.Config.getDb().getConnection().prepareStatement("INSERT note set note_comment = ?, note_timestamp = ?, note_reminder = ? where business_id = ?");
            statement.setString(1, String.valueOf(company.getNotes()));
            statement.setInt(2, 3);
            statement.setInt(3, 999);
            statement.setInt(4, company.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveExistingCompanyToDb(Company company) {
        PreparedStatement statement = null;

        try {
            statement = Intuition.Config.getDb().getConnection().prepareStatement("update business set business_name = ?, business_phone = ?, business_email = ? where business_id = ?");
            statement.setString(1, company.getName());
            statement.setString(2, company.getPhoneNumber());
            statement.setString(3, company.getEmail());
            statement.setInt(4, company.getId());

            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void removeCompany(Company company) {
        data.remove(company);
    }
}