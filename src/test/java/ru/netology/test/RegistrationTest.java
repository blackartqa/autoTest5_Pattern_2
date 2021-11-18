package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationData;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class RegistrationTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }


    @Test
    void shouldAuthorizationSuccess() {
        RegistrationData client = DataGenerator.Registration.userRegistration("active");
        $("[data-test-id='login'] input").setValue(client.getLogin());
        $("[data-test-id='password'] input").setValue(client.getPassword());
        $(withText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(visible);

    }

    @Test
    void shouldAuthorizationLoginNotCorrect() {
        RegistrationData client = DataGenerator.Registration.userRegistration("active");
        $("[data-test-id='login'] input").setValue(DataGenerator.Registration.generateRandomLogin());
        $("[data-test-id='password'] input").setValue(client.getPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));

    }

    @Test
    void shouldAuthorizationPasswordNotCorrect() {
        RegistrationData client = DataGenerator.Registration.userRegistration("active");
        $("[data-test-id='login'] input").setValue(client.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.Registration.generateRandomPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldBlockedUser() {
        RegistrationData client = DataGenerator.Registration.userRegistration("blocked");
        $("[data-test-id='login'] input").setValue(client.getLogin());
        $("[data-test-id='password'] input").setValue(client.getPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Пользователь заблокирован"));

    }
}