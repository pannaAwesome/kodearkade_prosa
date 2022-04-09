package de_syv_sma_dvarge;

public class CallCharacterReaction extends Reaction {
    String callCharacter;
    public CallCharacterReaction(String reaction, String callString) {
        super(reaction);
        callCharacter = callString;
    }
    
    @Override
    public String toString() {
        return reactionType + ", and " + callCharacter + " comes to the rescue!";
    }
}
