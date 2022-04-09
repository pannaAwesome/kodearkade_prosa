package de_syv_sma_dvarge;

public class LastCharacterReaction extends Reaction {

    public LastCharacterReaction(String reaction) {
        super(reaction);
    }
    
    @Override
    public String toString() {
        return reactionType;
    }
}
