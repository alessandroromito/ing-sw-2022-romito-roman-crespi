package it.polimi.ingsw.server.enumerations;

public enum ActionPhaseState {
    USE_EFFECT,
    MOVE_MOTHER_NATURE,
    MOVE_STUDENT1,
    MOVE_STUDENT2,
    MOVE_STUDENT3,
    MOVE_STUDENT4,
    PICK_CLOUD;

    public ActionPhaseState next(ActionPhaseState actionPhaseState){
        switch (actionPhaseState) {
            case USE_EFFECT -> {
                return MOVE_STUDENT1;
            }
            case MOVE_STUDENT1 -> { return MOVE_STUDENT2;
            }
            case MOVE_STUDENT2 -> { return MOVE_STUDENT3;
            }
            case MOVE_STUDENT3 -> { return MOVE_STUDENT4;
            }
            case MOVE_STUDENT4 -> { return MOVE_MOTHER_NATURE;
            }
            case MOVE_MOTHER_NATURE -> { return PICK_CLOUD;
            }
            case PICK_CLOUD -> { return USE_EFFECT;
            }
        }
        return actionPhaseState;
    }
}
