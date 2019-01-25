
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.api.ILoginPage;
import pages.api.IWelcomePage;
import pages.impl.WelcomePage;

/**
 * Created by i82325 on 1/24/2019.
 */
public class TestFile extends BaseTest {
    ILoginPage loginPage;
    @Override
    public boolean skipLogin() {
        return true;
    }

    @BeforeClass
    public void setUp() {
        super.setUp();
        loginPage = testApplication.open(context.getApplication());
        loginPage.doWaitTillFullyLoaded(context.getDefaultWaitTimeout());
    }

    @Test
    public void testMethod(){
        IWelcomePage welcomePage=loginPage.searchTextInGoogle("Ebanking in Nepal");
    }
}
