import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(features ="src/test/java/features",
        glue = "steps",
        plugin = {"pretty", "json:target/cucumber-report/cucumber.json"}
)
public class RunAtTest extends AbstractTestNGCucumberTests {

}
