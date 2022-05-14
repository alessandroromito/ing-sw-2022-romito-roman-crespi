package it.polimi.ingsw.server.enumerations;

public enum ActionPhaseState {
    MOVE_MOTHER_NATURE,
    MOVE_STUDENT1,
    MOVE_STUDENT2,
    MOVE_STUDENT3,
    PICK_CLOUD;

    public ActionPhaseState next(ActionPhaseState actionPhaseState){
        switch (actionPhaseState) {
            case MOVE_STUDENT1 -> { return MOVE_STUDENT2;
            }
            case MOVE_STUDENT2 -> { return MOVE_STUDENT3;
            }
            case MOVE_STUDENT3 -> { return MOVE_MOTHER_NATURE;
            }
            case MOVE_MOTHER_NATURE -> { return MOVE_STUDENT1;
            }
            default -> {
                return PICK_CLOUD;
            }
        }
    }
}
