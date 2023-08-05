package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    private SelenideElement paymentButton = $("[class^='App_appContainer'] > .button:first-of-type");
    private SelenideElement creditButton = $("[class^='App_appContainer'] > .button:last-of-type");
    private SelenideElement formHead = $("[class^='App_appContainer'] > h3.heading");
    private SelenideElement formBody = $("form");

    public MainPage() {
        paymentButton.shouldBe(Condition.visible);
        creditButton.shouldBe(Condition.visible);
        formHead.shouldBe(Condition.hidden);
        formBody.shouldBe(Condition.hidden);
    }

    public FormPage openPaymentForm() {
        paymentButton.click();
        formHead.shouldBe(Condition.visible).shouldHave(Condition.text("Оплата по карте"));
        formBody.shouldBe(Condition.visible);
        return new FormPage();
    }

    public FormPage openCreditForm() {
        creditButton.click();
        formHead.shouldBe(Condition.visible).shouldHave(Condition.text("Кредит по данным карты"));
        formBody.shouldBe(Condition.visible);
        return new FormPage();
    }
}
