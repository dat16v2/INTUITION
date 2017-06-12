package kea.intuition.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import kea.intuition.model.Candidate;

public class CandidateContainer {
    private static ObservableList<Candidate> data;
    private static TableView tableStructure;
    private static FilteredList<Candidate> filteredList;
    private static SortedList<Candidate> sortedList;
    private static String currentCandidateHash;

    public static String getCurrentCandidateHash() {
        return currentCandidateHash;
    }

    public static void setCurrentCandidateHash(String currentCandidateHash) {
        CandidateContainer.currentCandidateHash = currentCandidateHash;
    }

    public static FilteredList<Candidate> getFilteredList() {
        return filteredList;
    }

    public static void setFilteredList(FilteredList<Candidate> filteredList) {
        CandidateContainer.filteredList = filteredList;
    }

    public static SortedList<Candidate> getSortedList() {
        return sortedList;
    }

    public static void setSortedList(SortedList<Candidate> sortedList) {
        CandidateContainer.sortedList = sortedList;
    }

    public static ObservableList<Candidate> getData() {
        return data;
    }

    public static void setData(ObservableList<Candidate> data) {
        CandidateContainer.data = data;
    }

    public static TableView getTableStructure() {
        return tableStructure;
    }

    public static void setTableStructure(TableView tableStructure) {
        CandidateContainer.tableStructure = tableStructure;
    }

    public static ObservableList<Candidate> getCandidatesFromDb() {
        ObservableList<Candidate> data =  FXCollections.observableArrayList();

        data.add( new Candidate(1, "Asam Ali", "12345678", "asam@kea.dk", "Loooooong description" ) );
        data.add( new Candidate(2, "Emil Clausen", "12345678", "emil@kea.dk", "dfgsfdgsd dfsg dsfg" ) );
        data.add( new Candidate(3, "Phuong Thai", "12345678", "phuong@kea.dk", "dsfg sdfg dsfg" ) );
        data.add( new Candidate(4, "Simon Konstantyner", "12345678", "simon@kea.dk", "dsfgdsf gsdfg" ) );

        return data;
    }

    public static Candidate getCandidateFromDb(int id) {
        return new Candidate(1, "Asam Ali", "12345678", "asam@kea.dk", "Loooooong description" );
    }

    public static void removeCandidate(Candidate candidate) {
        data.remove(candidate);
    }
}
