package de_syv_sma_dvarge;

import java.util.Map;

public class Dwarf{
    String name;
    String description;
    Map<String, Reaction> reactions;
    
    public Dwarf(String characterName, String characterDescription, Map<String, Reaction> reactionsMap) {
        name = characterName;
        description = characterDescription;
        reactions = reactionsMap;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        Dwarf otherDwarf = (Dwarf) obj;
        return otherDwarf.name.equals(this.name);
    }

    public String presentation() {
        return "Hi! I am " + name + ", and I am best described as " + description;
    }

    public String react(String character_name) {
        return name + " " + reactions.get(character_name).toString();
    }

    public String react() {
        return name + " " + reactions.get(name).toString();
    }

    public String doNotReact() {
        return name + " is pretty content with the situation";
    }
}
