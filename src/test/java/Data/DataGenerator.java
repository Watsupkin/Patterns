package Data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private static Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    @Data
    @RequiredArgsConstructor
    public static class UserInfo {
        private final String city;
        private final String name;
        private final String phone;
        private final String date;
        private final String newDate;
    }

    public static UserInfo getUserInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Faker faker = new Faker(new Locale("ru"));
        return new UserInfo(faker.address().city(),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                LocalDate.now().plusDays(3).format(formatter),
                LocalDate.now().plusDays(7).format(formatter));

    }

    @Value
    @Data
    @AllArgsConstructor
    public static class UserInfoReg {
        private String login;
        private String password;
        private String status;
    }

    public static UserInfoReg getUserRegInfoRegActive() {
        return new UserInfoReg(faker.name().username(),
                faker.internet().password(),
                "active");
    }

    public static UserInfoReg getUserInfoRegBlocked() {
        return new UserInfoReg(faker.name().username(),
                faker.internet().password(),
                "blocked");
    }

    public static String getInvalidLogin() {
        return faker.name().username();
    }

    public static String getInvalidPassword() {
        return faker.internet().password();
    }
}
