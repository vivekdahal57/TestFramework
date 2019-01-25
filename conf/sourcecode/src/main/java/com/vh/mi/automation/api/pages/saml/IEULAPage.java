package com.vh.mi.automation.api.pages.saml;

import com.vh.mi.automation.api.pages.common.IMIPage;
import com.vh.mi.automation.impl.selenium.AbstractWebComponent;

/**
 * @author nimanandhar
 */
public interface IEULAPage extends IMIPage {
    <T extends AbstractWebComponent> T agree(Class<T> pageObjectClass, int waitTimeSeconds);
}
