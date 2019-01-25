package com.vh.mi.automation.api.pages.common;

import com.vh.mi.automation.api.comp.IPreferencesBar;
import com.vh.mi.automation.api.features.IAmWebComponent;

/**
 * 
 * @author i80448
 *
 */
public interface IMIPage extends IAmWebComponent {
	/**
	 * Gets static page title string.
	 * 
	 * @return
	 */
	public String getPageTitle();

	/**
	 * Gets static page id String.
	 * 
	 * @return
	 */
	public String getPageId();

	/**
	 * Gets page title rendered in the page. If correctly rendered then it
	 * should equal to the title returned by {@link #getPageTitle()}.
	 * 
	 * @return
	 */
	public String getDisplayedPageTitle();


    public ILoginPage doLogOut();

	public <T> T logoutExpectingPage(Class<T> pageObjectClass, Integer waitTime);

	public IPreferencesBar getPreferencesBar();

}
