package de_syv_sma_dvarge;

public class LastCharacterReaction extends Reaction {

    public LastCharacterReaction(String reaction, String characterName) {
        super(reaction, characterName);
    }
    
    @Override
    public String toString() {
        return reactionType;
    }
}
