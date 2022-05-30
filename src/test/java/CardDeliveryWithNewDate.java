import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryWithNewDate {


    private DataGenerator.UserInfo user = DataGenerator.getUserInfo();


    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldValidWitchNewDate() {
        $("[placeholder='Город']").setValue(user.getCity());
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(user.getDate());
        $("[name='name']").setValue(user.getName());
        $("[name='phone']").setValue(user.getPhone());
        $(".checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification'] > .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldBe(Condition.visible, Duration.ofMillis(6000));
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + user.getDate()))
                .shouldBe(Condition.visible, Duration.ofMillis(6000));
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(user.getNewDate());
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] > .notification__title")
                .shouldHave(exactText("Необходимо подтверждение"))
                .shouldBe(Condition.visible, Duration.ofMillis(6000));
        $("[data-test-id='replan-notification'] > .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible, Duration.ofMillis(6000));
        $(withText("Перепланировать")).click();
        $("[data-test-id='success-notification'] > .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldBe(Condition.visible, Duration.ofMillis(6000));
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + user.getNewDate()))
                .shouldBe(Condition.visible, Duration.ofMillis(6000));
    }
}
