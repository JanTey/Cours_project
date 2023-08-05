package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDB;

class PaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    @AfterEach
    public void teardrop() {
        cleanDB();
    }

    @Test
    void SucceedIfApprovedCardAndValidData() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.expectedPaymentStatus("APPROVED");
    }

    @Test
    void RejectedIfDeclinedCardAndValidData() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidDeclinedCard();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotification();
        SQLHelper.expectedPaymentStatus("DECLINED");
    }

    @Test
    void SucceedIfCurrentMonthAndCurrentYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithCurrentMonthAndCurrentYear();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.expectedPaymentStatus("APPROVED");
    }

    @Test
    void SucceedIfExpirationDateIs4YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithPlus4YearsFromCurrent();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.expectedPaymentStatus("APPROVED");
    }

    @Test
    void SucceedIfExpirationDateIs5YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithPlus5YearsFromCurrent();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.expectedCreditStatus("APPROVED");
    }

    @Test
    void SucceedIfHyphenatedHolderName() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithHyphenatedName();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.expectedCreditStatus("APPROVED");
    }

    @Test
    void ErrorNotifyIfEmptyNumber() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardNumberEmpty();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfEmptyNumber();
    }

    @Test
    void ErrorNotifyIfNumberContains15Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith15Digits();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidNumber();
    }

    @Test
    void ErrorNotifyIfNumberContains17Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith17Digits();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidNumber();
    }

    @Test
    void ErrorNotifyIfNumberContains16RandomDigits() {
        var mainPage = new MainPage();
    }

    @Test
    void ErrorNotifyIfExpiredYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithExpiredYear();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfExpiredYear();
    }

    @Test
    void ErrorNotifyIfEmptyHolder() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardHolderEmpty();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfEmptyHolder();
    }

    @Test
    void ErrorNotifyIfCyrillicHolderName() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithCyrillicName();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidHolder();
    }

    @Test
    void ErrorIfHolderNameContainsDigits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithDigitsInName();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidHolder();
    }

    @Test
    void ErrorIfHolderNameContainsSpecialSymbols() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithSpecialSymbolsInName();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidHolder();
    }

    @Test
    void ErrorNotifyIfEmptyCVC() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardCVCEmpty();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfEmptyCVC();
    }

    @Test
    void ErrorNotifyIfCVCContains2Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVCWith2Digits();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidCVC();
    }

    @Test
    void ErrorNotifyIfCVCContainsLetters() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVCWithLetters();
        var formPage = mainPage.openPaymentForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidCVC();
    }
}