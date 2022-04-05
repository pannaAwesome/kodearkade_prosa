package de_syv_sma_dvarge;

public class RemoveCharacterReaction extends Reaction {

    public RemoveCharacterReaction(String reaction, String characterName) {
        super(reaction, characterName);
    }
    
    @Override
    public String toString() {
        return reactionType + "s, which " + character + " finds otterly annoying, and therefore " + character + " leaves...";
    }
}
