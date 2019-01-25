package com.vh.mi.automation.api.comp.bl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by i80448 on 9/9/2014.
 */
public enum BL {
    LEVEL1(1), LEVEL2(2), LEVEL3(3), LEVEL4(4), LEVEL5(5), LEVEL6(6);

    private int index;
    private static Map<Integer, BL> lookup = new HashMap<>();

    static {
        for (BL bl : BL.values()) {
            lookup.put(bl.getIndex(), bl);
        }
    }

    private BL(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static BL get(int index) {
        return lookup.get(index);
    }
}
