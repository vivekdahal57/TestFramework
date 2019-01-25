package com.vh.mi.automation.groovy

import com.vh.mi.automation.api.constants.SwitchBoard

/**
 * @author nimanandhar
 */
class ModuleBuilder {
    private String id
    private String linkName
    private String pageId
    private String pageTitle
    private SwitchBoard switchBoard

    ModuleBuilder() {
    }

    ModuleBuilder id(String id) {
        this.id = id
        return this
    }

    ModuleBuilder linkName(String linkName) {
        this.linkName = linkName
        return this
    }

    ModuleBuilder pageId(String pageId) {
        this.pageId = pageId
        return this
    }

    ModuleBuilder pageTitle(String pageTitle) {
        this.pageTitle = pageTitle
        return this
    }

    ModuleBuilder switchBoard(SwitchBoard switchBoard) {
        this.switchBoard = switchBoard
        return this
    }

    Module build() {
        assert id && linkName && switchBoard

        def map = [
                'id'         : id,
                'linkName'   : linkName,
                'switchBoard': switchBoard,
                'pageId'     : pageId,
                'pageTitle'  : pageTitle
        ]

        return new Module(map)


    }
}
