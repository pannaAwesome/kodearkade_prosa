package de_syv_sma_dvarge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class theSevenSmallDwarfs {
    public static void main(String args[]) throws FileNotFoundException {
        DwarfList dwarfs = create_dwarfs_from_csv();
        DwarfList dwarfsInStory = new DwarfList();
        int currentDwarfAmount = 0;
        int numberOfDwarfs = dwarfs.size();

        System.out.println("A story of the " + numberOfDwarfs + " dwarfs...");
        System.out.println("Our story begins when two of the dwarfs meet up");
        choose_dwarfs(dwarfs, dwarfsInStory);
        System.out.println();
        System.out.println();
        System.out.println("Dwarfs in story: " + dwarfsInStory.toString());
        System.out.println();
        while (!dwarfs.isEmpty() && currentDwarfAmount != dwarfsInStory.size()) {
            currentDwarfAmount = dwarfsInStory.size();
            for (int i = 0; i < dwarfsInStory.size(); i++) {
                if (i == dwarfsInStory.size()-1) {
                    System.out.println(dwarfsInStory.get(i).react());
                } else {
                    reactToNeighbour(dwarfsInStory.get(i), dwarfsInStory.get(i+1), dwarfsInStory, dwarfs);
                    System.out.println();
                    System.out.println("Dwarfs in story: " + dwarfsInStory.toString());
                }
            }
            System.out.println();
        }
        System.out.println("And so ends the story of the " + numberOfDwarfs + " dwarfs.");
    }

    private static DwarfList create_dwarfs_from_csv() throws FileNotFoundException {
        DwarfList dwarfs = new DwarfList();

        Scanner scanner = new Scanner(new File("./de_syv_sma_dvarge/dwarfs.csv"));
        String[] header = scanner.nextLine().split(";");
        while (scanner.hasNext()) {
            String[] dwarf_data = scanner.nextLine().split(";");
            Map<String, Reaction> reactions = new HashMap<String,Reaction>();
            for (int i = 2; i < header.length; i++) {
                reactions.put(header[i], create_reaction(dwarf_data[i], header[i]));
            }
            Dwarf dwarf = new Dwarf(dwarf_data[0], dwarf_data[1], reactions);
            dwarfs.add(dwarf);
        }

        return dwarfs;
    }

    private static Reaction create_reaction(String reaction_string, String character) {
        String[] reaction_arr = reaction_string.split(":");
        if (reaction_arr.length == 1) {
            return new LastCharacterReaction(reaction_arr[0]);
        }else if (reaction_arr[1].equals("call")) {
            return new CallCharacterReaction(reaction_arr[0], reaction_arr[2]);
        } else {
            return new RemoveCharacterReaction(reaction_arr[0], character);
        }
    }

    private static void choose_dwarfs(DwarfList dwarfs, DwarfList dwarfsInStory) {
        for (int i = 0; i < 2; i++) {
            int randomDwarfIndex = (int) Math.floor(Math.random()*dwarfs.size());
            Dwarf dwarf = dwarfs.remove(randomDwarfIndex);
            System.out.println(dwarf.presentation());
            dwarfsInStory.add(dwarf);
        }
    }

    private static void reactToNeighbour(Dwarf currentDwarf, Dwarf nextDwarf, DwarfList dwarfsInStory, DwarfList dwarfs) {
        System.out.println(currentDwarf.react(nextDwarf.name));
        
        Reaction reaction = currentDwarf.reactions.get(nextDwarf.name);

        if (reaction instanceof CallCharacterReaction) {
            CallCharacterReaction callReaction = (CallCharacterReaction) reaction;
            if (dwarfsInStory.contains(callReaction.callCharacter)) {
                System.out.println(callReaction.callCharacter + " is already here, and therefore " + currentDwarf.toString() + " is content again");
            } else {
                Dwarf dwarf = dwarfs.findDwarf(callReaction.callCharacter);
                dwarfsInStory.add(dwarf);
                dwarfs.remove(dwarf);
                System.out.println("The dwarfs are then joined by " + dwarf.toString() + " " + dwarf.description);
                System.out.println();
            }
        } else {
            dwarfsInStory.remove(nextDwarf);
            dwarfs.add(nextDwarf);
        }
    }
}
