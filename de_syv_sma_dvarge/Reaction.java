package de_syv_sma_dvarge;

public abstract class Reaction {
    public String reactionType;
    public String character;

    public Reaction(String reaction, String characterName) {
        reactionType = reaction;
        character = characterName;
    }

    @Override
    public String toString() {
        return reactionType + " " + character;
    }
}
