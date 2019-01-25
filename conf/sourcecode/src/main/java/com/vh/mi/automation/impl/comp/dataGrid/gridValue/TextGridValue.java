package com.vh.mi.automation.impl.comp.dataGrid.gridValue;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;

/**
 * Created by nimanandhar on 11/19/2014.
 */
public class TextGridValue extends AbstractGridValue {

    public TextGridValue(String value) {
        super(value);
    }

    @Override
    public boolean isDataValid() {
        return !(value.isEmpty() || value.equals("-") || value.toLowerCase().equals("blinded") || value.equals("N/A"));
    }

    @Override
    public int compareTo(IGridValue o) {
        Preconditions.checkArgument(o instanceof TextGridValue);
        TextGridValue comparedValue = (TextGridValue) o;

        if (value.isEmpty()) {
            return 1; //empty value sort last ? see VHAMILGACY-12211
        }

        String value1 = "";
        if (isDataValid()) {
            value1 = getValue();
        }

        String value2 = "";
        if (comparedValue.isDataValid()) {
            value2 = comparedValue.getValue();
        }
        return value1.toLowerCase().compareTo(value2.toLowerCase());
    }

}
