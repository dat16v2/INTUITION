package kea.intuition.data;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import kea.intuition.Intuition;
import kea.intuition.model.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyContainer {
    private static ObservableList<Company> data;
    private static TableView tableStructure;

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

        // Refresh local data
        localCompany.setName(newCompany.getName());
        localCompany.setEmail(newCompany.getEmail());
        localCompany.setPhoneNumber(newCompany.getPhoneNumber());
        localCompany.setPhoneNumberCountryCallingCode(newCompany.getPhoneNumberCountryCallingCode());
        localCompany.setPhoneNumberPrefix(newCompany.getPhoneNumberPrefix());
        localCompany.setScore(newCompany.getScore());

        CompanyContainer.getTableStructure().refresh(); // Refreshes table view
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

    public static void removeCompany(Company company) {
        data.remove(company);
    }
}