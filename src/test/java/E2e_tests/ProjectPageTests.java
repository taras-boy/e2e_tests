package E2e_tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Configuration;


public class ProjectPageTests extends BaseTestsClass {


    static Dotenv env = Dotenv.load();
    static String baseUrl = env.get("BASE_URL");
    static String email = env.get("EMAIL");
    static String password = env.get("PASSWORD");
    String targetGetProjectName = "Proj1";


    @BeforeAll
    static void openTestomatAndLogin(){
        open(baseUrl);
        loginUser(email,password);
    }


    @Test
    public void firstTest(){



        searchForProject(targetGetProjectName);
        sleep(5000);
        selectProject(targetGetProjectName);
        waitForProjectIsLoaded(targetGetProjectName);
    }

    private void waitForProjectIsLoaded(String targetGetProjectName) {
        $(".first h2").shouldHave(text(targetGetProjectName));
        $(".first [href*='readme']").shouldHave(text("Readme"));
        sleep(5000);
    }

    private void selectProject(String targetGetProjectName) {
        $(byText("Proj1")).click();
    }
    private void searchForProject(String targetGetProjectName) {
        //$("#search").setValue("Proj1");

        $("#search")
                .shouldBe(Condition.visible, Duration.ofSeconds(10)) // wait up to 10s for visibility
                .setValue(targetGetProjectName);
    }


    @Test
    public void secondTest(){

        searchForProject(targetGetProjectName);
        //$("test").shouldHave(text("0 tests"));
        $$("#grid ul li").filter(visible).shouldHave(CollectionCondition.size(1));
        $("[title=\"Proj1\"] p").shouldHave(text("0 tests"));

    }

    public static void loginUser(String email, String password){
        $("#content-desktop #user_email")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .setValue(email);
        $("#content-desktop #user_password").setValue(password);
        $("#content-desktop #user_remember_me").click();

        $("#content-desktop [name='commit']").click();
        $(".common-flash-success").shouldBe(Condition.visible);
    }

}
