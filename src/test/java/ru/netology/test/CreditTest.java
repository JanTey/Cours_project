package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.SQLHelper.cleanDB;

public class CreditTest {

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
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.expectedCreditStatus("APPROVED");
    }

    @Test
    void RejectedIfDeclinedCardAndValidData() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidDeclinedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotification();
        SQLHelper.expectedCreditStatus("DECLINED");
    }

    @Test
    void SucceedIfCurrentMonthAndCurrentYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithCurrentMonthAndCurrentYear();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.expectedCreditStatus("APPROVED");
    }

    @Test
    void SucceedIfExpirationDateIs4YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithPlus4YearsFromCurrent();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkSuccessNotification();
        SQLHelper.expectedCreditStatus("APPROVED");
    }

    @Test
    void ErrorNotifyIfNumberContainsLetters() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithLettersInNumber();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidNumber();
    }

    @Test
    void ErrorNotifyIfEmptyMonth() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardMonthEmpty();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfEmptyMonth();
    }

    @Test
    void ErrorNotifyIfMonthValueIs00() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith00Month();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidMonth();
    }

    @Test
    void ErrorNotifyIfMonthValueIs13() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith13thMonth();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidMonth();
    }

    @Test
    void ErrorNotifyIfExpiredMonth() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithExpiredMonth();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfExpiredMonth();
    }

    @Test
    void ErrorNotifyIfEmptyYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardYearEmpty();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfEmptyYear();
    }

    @Test
    void ErrorNotifyIfExpirationDateIs6YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardWithPlus6YearsFromCurrent();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidYear();
    }

    @Test
    void ErrorNotifyIfExpiredYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithExpiredYear();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfExpiredYear();
    }

    @Test
    void ErrorNotifyIfEmptyHolder() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardHolderEmpty();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfEmptyHolder();
    }

    @Test
    void ErrorNotifyIfCyrillicHolderName() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithCyrillicName();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidHolder();
    }

    @Test
    void ErrorIfHolderNameContainsDigits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithDigitsInName();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidHolder();
    }

    @Test
    void ErrorIfHolderNameContainsSpecialSymbols() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithSpecialSymbolsInName();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidHolder();
    }

    @Test
    void ErrorNotifyIfEmptyCVC() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCardCVCEmpty();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfEmptyCVC();
    }

    @Test
    void ErrorNotifyIfCVCContains2Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVCWith2Digits();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidCVC();
    }

    @Test
    void ErrorNotifyIfCVCContainsLetters() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVCWithLetters();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidCVC();
    }
}