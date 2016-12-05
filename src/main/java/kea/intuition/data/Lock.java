package kea.intuition.data;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import kea.intuition.Tools;
import kea.intuition.controller.CompanySingularDisplay;

public class Lock {
    private boolean locked;
    private Label label;
    private CompanySingularDisplay companySingularDisplay;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;

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
                    setLocked(true);
                    companySingularDisplay.setLockedLayout();
                    System.out.println(Tools.getCompanyHash(companySingularDisplay.getCompany()));

                    if (Tools.validateCompanyHash(companySingularDisplay.getCompany(), Tools.getCompanyHash(companySingularDisplay.getCompany()))) {
                        System.out.println("The hashes corresponds.");
                    } else {
                        System.out.println("The hashes does not correspond with each other.");
                    }
                    //CompanyContainer.getTableStructure().refresh(); // Refreshes table view
                }
            }
        });
    }

    public Label getLabel() {
        return label;
    }
}