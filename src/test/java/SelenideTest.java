import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {
    public static void main(String[] args) {
        open("https://google.com");
        $("[name=q]").setValue("Selenide").pressEnter();
    }
}