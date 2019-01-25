package com.vh.mi.mxautomation.api.features;

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

}
