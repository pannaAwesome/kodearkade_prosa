package de_syv_sma_dvarge;

public abstract class Reaction {
    public String reactionType;

    public Reaction(String reaction) {
        reactionType = reaction;
    }

    @Override
    public String toString() {
        return reactionType;
    }
}
