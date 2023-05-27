package user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {

        public static User getRandomUser() {

            final String email = RandomStringUtils.randomAlphabetic(10)+ "@yandex.ru";
            final String password = RandomStringUtils.randomAlphabetic(8);
            final String name = "Иван";
            return new User(email, password, name);
        }

        public static User getSpecific() {
            final String email = "bestuser@yandex.ru";
            final String password = "123456";
            final String name = "Ivan123";
            return new User(email, password, name);
        }

        public static User getUserWithoutPassword() {
            final String email = RandomStringUtils.randomAlphabetic(6) + "@" + "yandex.ru";
            final String password = "";
            final String name = "Ivan123";
            return new User(email, password, name);
        }

        public static User getUserWithoutEmail() {
            final String password = RandomStringUtils.randomAlphabetic(6) + "@" + "yandex.ru";
            final String name = RandomStringUtils.randomAlphabetic(10);
            return new User(null, password, name);
        }

        public static User getUserWrongEmail() {
            final String email = RandomStringUtils.randomAlphabetic(10);
            final String password = RandomStringUtils.randomAlphabetic(10);
            final String name = RandomStringUtils.randomAlphabetic(10);
            return new User(email, password, name);
        }

        public static User getUserWrongPassword() {
            final String email = RandomStringUtils.randomAlphabetic(6) + "@" + "yandex.ru";
            final String password = RandomStringUtils.randomAlphabetic(1);
            final String name = RandomStringUtils.randomAlphabetic(10);
            return new User(email, password, name);
        }

    }

