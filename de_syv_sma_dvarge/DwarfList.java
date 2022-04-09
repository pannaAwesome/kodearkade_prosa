package de_syv_sma_dvarge;

import java.util.ArrayList;

public class DwarfList extends ArrayList<Dwarf> {

    public boolean contains(String name) {
        for (Dwarf dwarf : this) {
            if (dwarf.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Dwarf findDwarf(String name) {
        for (Dwarf dwarf : this) {
            if (dwarf.name.equals(name)) {
                return dwarf;
            }
        }
        return null;
    }
}
