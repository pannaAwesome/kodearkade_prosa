package de_syv_sma_dvarge;

public class RemoveCharacterReaction extends Reaction {
    String character;
    public RemoveCharacterReaction(String reaction, String characterName) {
        super(reaction);
        character = characterName;
    }
    
    @Override
    public String toString() {
        return reactionType + ", which " + character + " finds otterly annoying, and therefore " + character + " leaves...";
    }
}
