package it.polimi.ingsw.server.enumerations;

public enum ActionPhaseState {
    MOVE_MOTHER_NATURE,
    MOVE_STUDENT1,
    MOVE_STUDENT2,
    MOVE_STUDENT3,
    PICK_CLOUD;

    public ActionPhaseState next(ActionPhaseState actionPhaseState){
        switch (actionPhaseState) {
            case MOVE_MOTHER_NATURE -> { return MOVE_STUDENT1;
            }
            case MOVE_STUDENT1 -> { return MOVE_STUDENT2;
            }
            case MOVE_STUDENT2 -> { return MOVE_STUDENT3;
            }
            case MOVE_STUDENT3 -> { return PICK_CLOUD;
            }
            case PICK_CLOUD -> { return MOVE_MOTHER_NATURE;
            }
        }
        return actionPhaseState;
    }
}
