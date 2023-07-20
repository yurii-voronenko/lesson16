package cc.robotdreams;

import cc.robotdreams.exceptions.InvalidLoginException;
import cc.robotdreams.exceptions.InvalidPasswordException;
import cc.robotdreams.exceptions.InvalidUsernameException;

import java.util.*;
import java.util.function.Consumer;

public class Main
{
    static private boolean exitFlag = false;

    static private List<User> users = new ArrayList<>();

    public static void main(String[] args) {

        // Exception - CHECKED
        // Error / RuntimeException - UNCHECKED
        Map<String, Consumer<Scanner>> actions = new LinkedHashMap<>();
        actions.put("Реєстрація",   Main::registration);
        actions.put("Вхід",         Main::login);
        actions.put("Вихід",        Main::exit);

        try {
            users.add(new User("admin", "12345"));
        } catch (Throwable e) { /* Ignore */ }

        Scanner sc = new Scanner(System.in);

        // Daemonize
        while(true) {
            System.out.println("Введіть дію: " + actions.keySet());
            String action = sc.nextLine();
            for(Map.Entry<String, Consumer<Scanner>> pair : actions.entrySet()) {
                if (pair.getKey().equalsIgnoreCase(action)) {
                    try {
                        pair.getValue().accept(sc);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            if (exitFlag)
                break;

            sleep(100);
        }
    }

    static public void registration(Scanner sc) {
        System.out.println("///////////// РЕЄСТРАЦІЯ /////////////");

        String username = enterUsername(sc);
        String password = enterPassword(sc);

        System.out.print("Повторіть пароль:");
        String password2 = sc.nextLine();
        if (password.isEmpty() || password2.isEmpty() || !password.equals(password2)) {
            System.err.print("Паролі не співпадають");
        }
        try {
            User user = new User(username, password);
            users.add(user);
        } catch (Throwable e) {
            System.err.println("Щось пішло не так: " + e.getMessage());
        }

    }

    static private String enterUsername(Scanner sc) {
        System.out.print("Введіть ім'я користувача:");
        String username = sc.nextLine();
        try {
            User.checkUsername(username);
            return username;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.flush();
            return enterUsername(sc);
        }
    }

    static private String enterPassword(Scanner sc) {
        System.out.print("Введіть пароль:");
        String password = sc.nextLine();
        try {
            User.checkPassword(password);
            return password;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.flush();
            return enterPassword(sc);
        }
    }


    static public void login(Scanner sc) {
        System.out.println("///////////// ВХІД /////////////");

        String username = enterUsername(sc);
        String password = enterPassword(sc);

        boolean found = false;
        for (User user : users) {
            if (user.username.equalsIgnoreCase(username)) {
                try {
                    user.login(password);
                    System.out.println("Вхід виконано успішно");
                } catch (InvalidLoginException e) {
                    System.err.println("Не вірно введений пароль");
                } finally {
                    found = true;
                }
                break;
            }
        }
        if (!found)
            System.err.println("Користувача не знайдено");

    }

    static public void exit(Scanner sc) {
        System.out.println("///////////// ВИХІД З ПРОГРАМИ /////////////");
        exitFlag = true;
    }



    static private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable e) {
            /* Ignore */
        }
    }
}