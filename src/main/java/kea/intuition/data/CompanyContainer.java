package kea.intuition.data;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import kea.intuition.model.Company;

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

    public static void getCompanyFromDb(int value) {

    }

    public static void removeCompany(Company company) {
        data.remove(company);
    }
}