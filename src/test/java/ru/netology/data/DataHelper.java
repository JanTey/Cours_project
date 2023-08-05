package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    private static Faker faker = new Faker(new Locale("en"));
    private static Faker fakerCyrillic = new Faker(new Locale("ru"));


    public static String approvedCard() {
        return "1111 2222 3333 4444";
    }

    public static String declinedCard() {
        return "5555 6666 7777 8888";
    }

    public static CardInfo getValidApprovedCard() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(3), generateYear(1),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getValidDeclinedCard() {
        return new CardInfo("5555 6666 7777 8888", generateMonth(3), generateYear(1),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getCardInfoCardNumberEmpty() {
        return new CardInfo(" ", generateMonth(3), generateYear(1),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getCardInfoCardMonthEmpty() {
        return new CardInfo("1111 2222 3333 4444", "", generateYear(1),
                generateValidHolder(), generateValidCVC());
    }
    public static CardInfo getCardInfoCardYearEmpty() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(3), "",
                generateValidHolder(), generateValidCVC());
    }
    public static CardInfo getCardInfoCardHolderEmpty() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(3), generateYear(1),
                "", generateValidCVC());
    }
    public static CardInfo getCardInfoCardCVCEmpty() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(3), generateYear(1),
                generateValidHolder(), "");
    }

    public static CardInfo getInvalidCardWith15Digits() {
        return new CardInfo(generateNumberWith15Digits(), generateMonth(3), generateYear(1),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getInvalidCardWith17Digits() {
        return new CardInfo(generateNumberWith17Digits(), generateMonth(3), generateYear(1),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getInvalidCardWith16RandomDigits() {
        return new CardInfo(generateNumberWith16RandomDigits(), generateMonth(3), generateYear(1),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getInvalidCardWithLettersInNumber() {
        return new CardInfo(generateNumberWithLetters(), generateMonth(3), generateYear(1),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getInvalidCardWith00Month() {
        return new CardInfo("1111 2222 3333 4444", "00", generateYear(1),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getInvalidCardWith13thMonth() {
        return new CardInfo("1111 2222 3333 4444", "13", generateYear(1),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getValidCardWithCurrentMonthAndCurrentYear() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(0), generateYear(0),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getInvalidCardWithExpiredMonth() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(-1), generateYear(0),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getValidCardWithPlus4YearsFromCurrent() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(0), generateYear(4),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getValidCardWithPlus5YearsFromCurrent() {
        return new CardInfo("41111 2222 3333 4444", generateMonth(0), generateYear(5),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getCardWithPlus6YearsFromCurrent() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(0), generateYear(6),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getInvalidCardWithExpiredYear() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(0), generateYear(-1),
                generateValidHolder(), generateValidCVC());
    }

    public static CardInfo getInvalidCardWithCyrillicName() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(3), generateYear(1),
                generateInvalidHolderInCyrillic(), generateValidCVC());
    }

    public static CardInfo getValidCardWithHyphenatedName() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(3), generateYear(1),
                generateValidHolderWithHyphenatedName(), generateValidCVC());
    }

    public static CardInfo getInvalidCardWithDigitsInName() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(3), generateYear(1),
                generateInvalidHolderWithDigits(), generateValidCVC());
    }

    public static CardInfo getInvalidCardWithSpecialSymbolsInName() {
        return new CardInfo("41111 2222 3333 4444", generateMonth(3), generateYear(1),
                generateInvalidHolderWithSymbols(), generateValidCVC());
    }

    public static CardInfo getInvalidCVCWith2Digits() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(3), generateYear(1),
                generateValidHolder(), generateInvalidCVCWith2Digits());
    }

    public static CardInfo getInvalidCVCWithLetters() {
        return new CardInfo("1111 2222 3333 4444", generateMonth(3), generateYear(1),
                generateValidHolder(), generateInvalidCVCWithLetters());
    }

    public static String generateNumberWith15Digits() {
        return faker.numerify("#### #### #### ###");
    }

    public static String generateNumberWith17Digits() {
        return faker.numerify("#### #### #### #### #");
    }

    public static String generateNumberWith16RandomDigits() {
        return faker.numerify("#### #### #### ####");
    }

    public static String generateNumberWithLetters() {
        return faker.numerify("AB## #### #### ####");
    }

    public static String generateMonth(int addMonths) {
        return LocalDate.now().plusMonths(addMonths).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear(int addYears) {
        return LocalDate.now().plusYears(addYears).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateValidHolder() {
        return faker.name().fullName().toUpperCase();
    }

    public static String generateValidHolderWithHyphenatedName() {
        return faker.name().firstName().toUpperCase() + " " + faker.name().lastName().toUpperCase() + "-"
                + faker.name().lastName().toUpperCase();
    }

    public static String generateInvalidHolderInCyrillic() {
        return fakerCyrillic.name().firstName().toUpperCase() + " " + fakerCyrillic.name().lastName().toUpperCase();
    }

    public static String generateInvalidHolderWithDigits() {
        return faker.name().firstName() + faker.numerify("#") + " " + faker.name().lastName().toUpperCase();
    }

    public static String generateInvalidHolderWithSymbols() {
        return faker.name().firstName() + "$" + faker.name().lastName().toUpperCase();
    }

    public static String generateValidCVC() {
        return faker.numerify("###");
    }

    public static String generateInvalidCVCWith2Digits() {
        return faker.numerify("##");
    }

    public static String generateInvalidCVCWithLetters() {
        return faker.numerify("ab#");
    }

    @Value
    @Setter
   public static class CardInfo {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }

}
