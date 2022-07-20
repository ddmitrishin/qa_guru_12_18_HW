package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class DemowebshopTests extends TestBase {

    static String login = userLogin;
    static String password = userPassword;
    static String authCookieName = authCookie;

    @Test
    @DisplayName("Successful authorization to some demowebshop (UI)")
    void loginTest() {

        step("Open login page", () -> {
            open("/login");
        });

        step("Fill login form", () -> {
            $("#Email").setValue(login);
            $("#Password").setValue(password).pressEnter();
        });

        step("Verify successful authorization", () -> {
            $(".account").shouldHave(text(login));
        });
    }

}
