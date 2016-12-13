package kea.intuition.data;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import kea.intuition.Intuition;
import kea.intuition.IntuitionLockEvent;
import kea.intuition.Tools;
import kea.intuition.controller.CompanySingularDisplay;
import kea.intuition.model.Company;

public class Lock {
    private boolean locked;
    private Label label;
    private CompanySingularDisplay companySingularDisplay;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;

        if (Intuition.Screens.getIndexScreen() == null) {
            Intuition.Config.setDbLock(locked);
        } else {
            IntuitionLockEvent.fireEvent(companySingularDisplay.getStage(), new IntuitionLockEvent(null, null, IntuitionLockEvent.LOCK_CHANGED_ROOT_EVENT, locked));
        }

        if (this.locked) {
            label.setText("locked");
        } else {
            label.setText("unlocked");
        }
    }

    public Lock(CompanySingularDisplay companySingularDisplay) {
        this.label = new Label();
        this.companySingularDisplay = companySingularDisplay;
        setLocked(true);

        setOnClick();
    }

    private void setOnClick() {
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isLocked()) {
                    setLocked(false);
                    companySingularDisplay.setUnlockedLayout();
                }else {
                    // Check whether the hash from before editing and a hash from the DB right now matches. We realise that this solution is not perfect, perhaps some sort of atomic lock would do the job.
                    if (Tools.validateCompanyHash(CompanyContainer.getCompanyFromDb(companySingularDisplay.getCompany().getId()), companySingularDisplay.getIntegrityHash())) {
                        System.out.println("The hashes corresponds.");
                        Company newCompany = new Company();

                        newCompany.setId(companySingularDisplay.getCompany().getId());
                        newCompany.setName(companySingularDisplay.getModifiedValues().getCompanyNameField().getText());
                        newCompany.setPhoneNumber(companySingularDisplay.getModifiedValues().getCompanyPhoneNumberField().getText());
                        newCompany.setEmail(companySingularDisplay.getModifiedValues().getCompanyEmailField().getText());


                        if (!Tools.validateCompanyHash(newCompany,companySingularDisplay.getIntegrityHash())) {
                            System.out.println("Changed in data detected. Proceeding to save changes.");
                            CompanyContainer.editCompany(companySingularDisplay.getCompany(), newCompany);
                            companySingularDisplay.setIntegrityHash(Tools.getCompanyHash(newCompany));
                            CompanyContainer.setCurrentCompanyHash(companySingularDisplay.getIntegrityHash());
                        } else {
                            System.out.println("No change has been detected. Unlocking.");
                        }
                    } else {
                        System.out.println("The hashes does not correspond with each other.");
                        System.out.println("Someone else has made a change during this editing session.");
                    }


                    setLocked(true);
                    companySingularDisplay.setLockedLayout();
                    //CompanyContainer.getTableStructure().refresh(); // Refreshes table view
                }
            }
        });
    }

    public Label getLabel() {
        return label;
    }
}