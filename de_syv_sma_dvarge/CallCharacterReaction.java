package de_syv_sma_dvarge;

public class CallCharacterReaction extends Reaction {

    public CallCharacterReaction(String reaction, String characterName) {
        super(reaction, characterName);
    }
    
    @Override
    public String toString() {
        return reactionType + "s, and " + character + " comes to the rescue!";
    }
}
