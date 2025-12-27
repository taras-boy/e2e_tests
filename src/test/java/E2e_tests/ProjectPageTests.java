package E2e_tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ProjectPageTests extends BaseTestsClass {

    static Dotenv env = Dotenv.load();
    static String baseUrl = env.get("BASE_URL");
    static String email = env.get("EMAIL");
    static String password = env.get("PASSWORD");
    String targetGetProjectName = "Proj1";

    @BeforeEach
    void openLoginAndAuthenticate() {
        // Clear cookies to start fresh
        clearBrowserCookies();

        open(baseUrl);

        $("#content-desktop #user_email")
                .shouldBe(visible, Duration.ofSeconds(10))
                .setValue(email);
        $("#content-desktop #user_password")
                .shouldBe(visible)
                .setValue(password);
        $("#content-desktop #user_remember_me").shouldBe(visible).click();
        $("#content-desktop [name='commit']").shouldBe(visible).click();

        // Wait until login succeeds
        $(".common-flash-success").shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    public void firstTest() {
        searchForProject(targetGetProjectName);
        selectProject(targetGetProjectName);
        waitForProjectIsLoaded(targetGetProjectName);
    }

    @Test
    public void secondTest() {
        searchForProject(targetGetProjectName);
        $$("#grid ul li").filter(visible)
                .shouldHave(CollectionCondition.size(1));
        $("[title='Proj1'] p").shouldHave(text("0 tests"));
    }

    private void waitForProjectIsLoaded(String projectName) {
        $(".first h2").shouldHave(text(projectName), Duration.ofSeconds(10));
        $(".first [href*='readme']").shouldHave(text("Readme"));
    }

    private void selectProject(String projectName) {
        $(byText(projectName)).click();
    }

    private void searchForProject(String projectName) {
        $("#search")
                .shouldBe(visible, Duration.ofSeconds(10))
                .setValue(projectName);
    }
}