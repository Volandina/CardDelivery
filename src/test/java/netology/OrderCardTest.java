package netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class OrderCardTest {
    @BeforeEach
    public void openForm() {
        open("http://localhost:9999/");
    }
    private String generateDay(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldValid() {
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = generateDay(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Ирина Нечаева");
        $("[data-test-id='phone'] input").val("+79999999999");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//div[@class=\"notification__title\"]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").should(exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldInValidCity() {
        $x("//input[@placeholder=\"Город\"]").val("Богданович");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = generateDay(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Ирина Нечаева");
        $("[data-test-id='phone'] input").val("+79999999999");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=city] .input__sub").should(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNoName() {
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = generateDay(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("");
        $("[data-test-id='phone'] input").val("+79999999999");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldInValidName() {
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = generateDay(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Irina Nechaeva");
        $("[data-test-id='phone'] input").val("+79999999999");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldInvalidDate() {
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = generateDay(1, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Ирина Нечаева");
        $("[data-test-id='phone'] input").val("+79999999999");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=date] .input__sub").should(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldInvalidPhone() {
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = generateDay(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Ирина Нечаева");
        $("[data-test-id='phone'] input").val("+7999999999");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub").should(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }


    @Test
    void shouldNoPhone() {
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = generateDay(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Ирина Нечаева");
        $("[data-test-id='phone'] input").val("");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNoAgre() {
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys("DELETE");
        String meetingDate = generateDay(3, "dd.MM.yyyy");
        $x("//input[@placeholder=\"Дата встречи\"]").val(meetingDate);
        $("[data-test-id='name'] input").val("Ирина Нечаева");
        $("[data-test-id='phone'] input").val("+79999999999");
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=agreement] .checkbox__text").should(exactText("Я соглашаюсь с условиями обработки и использования  моих персональных данных"));
    }
}