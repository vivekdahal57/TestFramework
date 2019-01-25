package com.vh.mi.automation.api.comp.quintileByGroupSize;

import java.util.List;

/**
 * @author pakshrestha
 */
public interface IQuintileByGroupSize {

    String getSelectedOption();

    List<String> getOptions();

    void selectOption(QuintileOptions options);

}

