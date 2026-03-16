package com.pao.laboratory03.bonus.model;

public enum Status {
    TODO {
        @Override
        public boolean canTransitionTo(Status next) {
            return next == IN_PROGRESS;
        }
    },
    IN_PROGRESS {
        @Override
        public boolean canTransitionTo(Status next) {
            return next == DONE;
        }
    },
    DONE {
        @Override
        public boolean canTransitionTo(Status next) {
            return false; // Nu se mai poate merge inapoi din DONE
        }
    };

    public abstract boolean canTransitionTo(Status next);
}