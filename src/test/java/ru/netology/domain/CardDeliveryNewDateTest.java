package ru.netology.domain;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryNewDateTest {
    private Meeting meeting = DataGenerator.Registration.generate();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    void openURL() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(meeting.getCity());
        form.$("[data-test-id=date] input").setValue(formatter.format(meeting.getDateFirstMeeting()));
        form.$("[data-test-id=name] input").setValue(meeting.getFirstName() + " " + meeting.getLastName());
        form.$("[data-test-id=phone] input").setValue("+7" + meeting.getPhoneNumber());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        form.$("[data-test-id=date] input").setValue(formatter.format(meeting.getDateSecondMeeting()));
        form.$(".button").click();
        $$(".button").find(exactText("Перепланировать")).click();
        $(".notification_status_ok").shouldBe(exist);
    }
}
