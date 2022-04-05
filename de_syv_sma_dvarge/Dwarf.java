package de_syv_sma_dvarge;

import java.util.HashMap;
import java.util.Map;

public class Dwarf {
    String name;
    String description;
    Map<reactionTypes, Reaction> reactions;
    
    public Dwarf(String characterName, String characterDescription, LastCharacterReaction lastCharacterReaction, CallCharacterReaction callCharacterReaction, RemoveCharacterReaction removeCharacterReaction) {
        name = characterName;
        description = characterDescription;
        reactions = new HashMap<reactionTypes, Reaction>();
        reactions.put(reactionTypes.LastCharacter, lastCharacterReaction);
        reactions.put(reactionTypes.CallCharacter, callCharacterReaction);
        reactions.put(reactionTypes.RemoveCharacter, removeCharacterReaction);
    }

    @Override
    public String toString() {
        return "Hi! I am " + name + ", and I am best described as " + description;
    }

    public String react(reactionTypes reaction) {
        return name + " " + reactions.get(reaction).toString();
    }
}
