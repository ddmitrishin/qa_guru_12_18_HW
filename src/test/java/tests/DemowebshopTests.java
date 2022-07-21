package tests;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

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

    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI)")
    void loginWithApiTest() {

        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", login)
                    .formParam("Password", password)
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(authCookieName);

            step("Open minimal content", () -> {
                open("/Themes/DefaultClean/Content/images/logo.png");
            });

            step("Set cookie to browser", () -> {
                Cookie authCookie = new Cookie(authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });

        step("Open main page with authorization", () -> {
            open("");
        });

        step("Verify successful authorization", () -> {
            $(".account").shouldHave(text(login));
        });
    }

}
