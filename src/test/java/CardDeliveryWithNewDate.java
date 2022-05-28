import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryWithNewDate {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldValidWitchNewDate() {
        Configuration.timeout = 15000;
        $("[placeholder='Город']").setValue(DataGenerator.getUserInfo().getCity());
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(DataGenerator.getUserInfo().getDate());
        $("[name='name']").setValue(DataGenerator.getUserInfo().getName());
        $("[name='phone']").setValue(DataGenerator.getUserInfo().getPhone());
        $(".checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification'] > .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldBe(Condition.visible);
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + DataGenerator.getUserInfo().getDate()))
                .shouldBe(Condition.visible);
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(DataGenerator.getUserInfo().getNewDate());
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] > .notification__title")
                .shouldHave(exactText("Необходимо подтверждение"))
                .shouldBe(Condition.visible);
        $("[data-test-id='replan-notification'] > .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible);
        $(withText("Перепланировать")).click();
        $("[data-test-id='success-notification'] > .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldBe(Condition.visible);
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.getUserInfo().getNewDate()))
                .shouldBe(Condition.visible);
    }
}
