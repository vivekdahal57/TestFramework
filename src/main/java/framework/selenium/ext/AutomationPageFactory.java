package framework.selenium.ext;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * PageFactory that walks up class hierarchy of the given page object instance
 * and instantiates WebElement of page objects in the super class annotated with
 * {@link PageObject} .
 * 
 * @author bibdahal
 *
 */
public class AutomationPageFactory extends PageFactory {
	/**
	 * 
	 * @param driver
	 *            The driver that will be used to look up the elements
	 * @param compositePage
	 *            Composite page object that consists of other page objects.
	 */
	public static void initElementsAllObjects(WebDriver driver,
			Object compositePage) {
		initElements(driver, compositePage);

		Class<?> superClazz = compositePage.getClass().getSuperclass();
		while (superClazz != Object.class) {
			Field[] allFields = superClazz.getDeclaredFields();

			for (Field field : allFields) {
				if (field.isAnnotationPresent(PageObject.class)) {

					try {
						// obtain the existing instance referred to by this
						// Field, may throw NPE if not instantiated yet.
						Object obj = field.get(compositePage);
						initElements(driver, obj);
					} catch (NullPointerException npe) {
						// composite object not instantiated yet, create a new
						// instance and set the value.
						Class<?> clazz = field.getType();
						Object proxyObj = initElements(driver, clazz);
						try {
							field.set(compositePage, proxyObj);
						} catch (IllegalArgumentException
								| IllegalAccessException e) {
							throw new RuntimeException(e);
						}

					} catch (IllegalAccessException ex) {
						throw new RuntimeException(ex);
					}
				}
			}
			superClazz = superClazz.getClass().getSuperclass();

		}
	}

	/**
	 * 
	 * @param driver
	 *            The driver that will be used to look up the elements
	 * @param pageClass
	 *            Page Class that will first be initialized before initializing
	 *            WebElement
	 * @return An instantiated instance of the class pageClass
	 */
	public static <T> T initElementsAllObjects(WebDriver driver,
			Class<T> pageClass) {
		T compositePage = initElements(driver, pageClass);
		initElementsAllObjects(driver, compositePage);
		return compositePage;
	}

}
