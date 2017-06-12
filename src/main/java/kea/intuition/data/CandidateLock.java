package kea.intuition.data;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import kea.intuition.Intuition;
import kea.intuition.IntuitionLockEvent;
import kea.intuition.Tools;
import kea.intuition.controller.CandidateSingularDisplay;
import kea.intuition.model.Candidate;

public class CandidateLock {
    private boolean locked;
    private Label label;
    private CandidateSingularDisplay candidateSingularDisplay;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;

        if (Intuition.Screens.getIndexScreen() == null) {
            Intuition.Config.setDbLock(locked);
        } else {
            IntuitionLockEvent.fireEvent(candidateSingularDisplay.getStage(), new IntuitionLockEvent(null, null, IntuitionLockEvent.LOCK_CHANGED_ROOT_EVENT, locked));
        }

        if (this.locked) {
            label.setText("locked");
        } else {
            label.setText("unlocked");
        }
    }

    public CandidateLock(CandidateSingularDisplay candidateSingularDisplay) {
        this.label = new Label();
        this.candidateSingularDisplay = candidateSingularDisplay;
        setLocked(true);

        setOnClick();
    }

    private void setOnClick() {
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isLocked()) {
                    setLocked(false);
                    candidateSingularDisplay.setUnlockedLayout();
                }else {
                    // Check whether the hash from before editing and a hash from the DB right now matches. We realise that this solution is not perfect, perhaps some sort of atomic lock would do the job.
                    if (Tools.validateCandidateHash(CandidateContainer.getCandidateFromDb(candidateSingularDisplay.getCandiate().getId()), candidateSingularDisplay.getIntegrityHash())) {
                        System.out.println("The hashes corresponds.");
                        Candidate newCandidate = new Candidate();

                        newCandidate.setId(candidateSingularDisplay.getCandiate().getId());
                        newCandidate.setName("");


                        if (!Tools.validateCandidateHash(newCandidate,candidateSingularDisplay.getIntegrityHash())) {
                            System.out.println("Changed in data detected. Proceeding to save changes.");
                            //CandidateContainer.editCandidate(candidateSingularDisplay.getCandidate(), newCandidate);
                            candidateSingularDisplay.setIntegrityHash(Tools.getCandidateHash(newCandidate));
                            CandidateContainer.setCurrentCandidateHash(candidateSingularDisplay.getIntegrityHash());
                        } else {
                            System.out.println("No change has been detected. Unlocking.");
                        }
                    } else {
                        System.out.println("The hashes does not correspond with each other.");
                        System.out.println("Someone else has made a change during this editing session.");
                    }


                    setLocked(true);
                    candidateSingularDisplay.setLockedLayout();
                    //CandidateContainer.getTableStructure().refresh(); // Refreshes table view
                }
            }
        });
    }

    public Label getLabel() {
        return label;
    }
}
