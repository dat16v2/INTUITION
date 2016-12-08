package kea.intuition.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import kea.intuition.Intuition;
import kea.intuition.model.Company;
import kea.intuition.model.Note;

import java.sql.*;
import java.util.ArrayList;

public class CompanyContainer {
    private static ObservableList<Company> data;
    private static TableView tableStructure;
    private static FilteredList<Company> filteredList;
    private static SortedList<Company> sortedList;
    private static String currentCompanyHash;

    public static String getCurrentCompanyHash() {
        return currentCompanyHash;
    }

    public static void setCurrentCompanyHash(String currentCompanyHash) {
        CompanyContainer.currentCompanyHash = currentCompanyHash;
    }

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
                company.setNotes();
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

    public static ArrayList<Note> getCompanyNotesFromDb(int id) {
        ResultSet rs = null;
        ArrayList<Note> list = new ArrayList<Note>();

        PreparedStatement statement = null;

        // Prepare SQL statement
        try {
            statement = Intuition.Config.getDb().getConnection().prepareStatement("select * from note where business_id = ? ORDER BY note_id DESC");
            statement.setInt(1, id);

            rs = statement.executeQuery();
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }

        try {
            while (rs.next()) {
                Note note = new Note();

                note.setId(rs.getInt(1));
                note.setCompanyId(rs.getInt(2));
                note.setUserId(rs.getInt(3));
                note.setComment(rs.getString(4));

                Timestamp ts = rs.getTimestamp(5);
                note.setTimestamp(ts.getTime());

                list.add(note);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        return list;
    }

    public static void saveExistingNotesToDb(Note note)
    {
        PreparedStatement statement = null;

        try {
            statement = Intuition.Config.getDb().getConnection().prepareStatement("INSERT INTO note (business_id, note_login_id, note_comment) VALUES (?, ?, ?)");
            statement.setInt(1, note.getCompanyId());
            statement.setInt(2, note.getUserId());
            statement.setString(3, note.getComment());

            statement.executeUpdate();
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