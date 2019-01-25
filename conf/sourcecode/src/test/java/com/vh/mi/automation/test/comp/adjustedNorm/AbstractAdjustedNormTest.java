package com.vh.mi.automation.test.comp.adjustedNorm;

import com.google.common.collect.ImmutableList;
import com.vh.mi.automation.api.comp.adjustedNorm.AdjustedNormOptions;
import com.vh.mi.automation.api.comp.adjustedNorm.IAdjustedNorm;
import com.vh.mi.automation.test.base.BaseTest;
import com.vh.mi.automation.test.utils.DataProviderUtils;
import org.fest.assertions.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author pakshrestha
 */
public abstract class AbstractAdjustedNormTest extends BaseTest {
    private static final int HIGHEST_PRIORITY = -1;

    private IAdjustedNorm adjustedNorm;

    public void setUp() {
        super.setUp();
        adjustedNorm = getAdjustedNormComponent();
    }

    protected abstract IAdjustedNorm getAdjustedNormComponent();

    @Test(priority = HIGHEST_PRIORITY)
    public void testThatNoneIsSelectedInitially() {
        Assertions.assertThat(adjustedNorm.getSelectedOption()).isEqualTo(AdjustedNormOptions.NONE.getDisplayText());
    }

    @Test
    public void testThatAdjustedNormComponentHasThreeOptions() {
        Assertions.assertThat(adjustedNorm.getOptions()).isEqualTo(AdjustedNormOptions.getMenuTexts());
    }

    @Test(dataProvider = "MenuOptions")
    public void testThatOptionsCanBeSelected(AdjustedNormOptions option) {
        adjustedNorm.selectOption(option);
        Assertions.assertThat(adjustedNorm.getSelectedOption()).isEqualTo(option.getDisplayText());
    }

    @DataProvider(name = "MenuOptions")
    protected Object[][] menuOptionProvider() {
        return DataProviderUtils.getObjects(ImmutableList.copyOf(AdjustedNormOptions.values()).reverse());
    }

}
