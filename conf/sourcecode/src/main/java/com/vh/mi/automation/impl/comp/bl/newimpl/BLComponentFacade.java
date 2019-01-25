package com.vh.mi.automation.impl.comp.bl.newimpl;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.comp.bl.BL;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelSelectionComponent;
import com.vh.mi.automation.api.comp.bl.IBusinessLevelsComponent;
import com.vh.mi.automation.impl.comp.pagination.PaginationComponentFacade;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class BLComponentFacade {
    private final IBusinessLevelsComponent businessLevelsComponent;
    private final Integer maxBusinessLevels;

    public BLComponentFacade(IBusinessLevelsComponent businessLevelsComponent) {
        Preconditions.checkNotNull(businessLevelsComponent);
        this.businessLevelsComponent = businessLevelsComponent;
        this.maxBusinessLevels = this.businessLevelsComponent.getMaxLevels();
    }

    public int getMaxLevels() {
        return maxBusinessLevels;
    }

    public List<String> getValues(Integer level) {
        Preconditions.checkArgument(level <= maxBusinessLevels, "The level provided " + level + " is greater than the number of business levels in the businesss level component");

        IBusinessLevelsComponent.IBusinessLevel businessLevelComponent = businessLevelsComponent.getBusinessLevelComponent(BL.get(level));
        IBusinessLevelSelectionComponent blSelectionComponent = businessLevelComponent.doOpenSelection();
        PaginationComponentFacade paginationComponent = new PaginationComponentFacade(blSelectionComponent.getPaginationComponent());

        IBusinessLevelSelectionComponent.ISelectionGridContainer selectionGridContainer = blSelectionComponent.getSelectionGridContainer();
        List<String> availableOptions = new ArrayList<>();
        do {
            availableOptions.addAll(selectionGridContainer.getSelectionOptionsOnCurrentPage());
        } while (paginationComponent.nextPage());

        blSelectionComponent.doClose();
        return availableOptions;
    }

    public void selectValues(Integer level, List<String> values) {
        Preconditions.checkArgument(level <= maxBusinessLevels, "The level provided " + level + " is greater than the number of business levels in the businesss level component");
        Set<String> valuesToSelect = new HashSet<>(values);

        IBusinessLevelsComponent.IBusinessLevel businessLevelComponent = businessLevelsComponent.getBusinessLevelComponent(BL.get(level));
        IBusinessLevelSelectionComponent blSelectionComponent = businessLevelComponent.doOpenSelection();
        PaginationComponentFacade paginationComponent = new PaginationComponentFacade(blSelectionComponent.getPaginationComponent());

        IBusinessLevelSelectionComponent.ISelectionGridContainer selectionGridContainer = blSelectionComponent.getSelectionGridContainer();
        List<String> availableOptions = new ArrayList<>();
        do {
            List<String> selectionOptionsOnCurrentPage = selectionGridContainer.getSelectionOptionsOnCurrentPage();
            Iterator<String> iterator = valuesToSelect.iterator();
            while (iterator.hasNext()) {
                String valueToSelect = iterator.next();
                if (selectionOptionsOnCurrentPage.contains(valueToSelect)) {
                    selectionGridContainer.doCheckOption(valueToSelect);
                    iterator.remove(); //remove this value as this has been selected
                }
            }
        } while (paginationComponent.nextPage() && !valuesToSelect.isEmpty());

        blSelectionComponent.doClose();
        if (!valuesToSelect.isEmpty()) {
            throw new Error("The following options are not available in level " + level + ":" + valuesToSelect);
        }
    }

}
