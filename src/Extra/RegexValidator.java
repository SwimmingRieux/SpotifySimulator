package Extra;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegexValidator {
    public static boolean phoneValidator(String phoneNumber)
    {
        Pattern pattern = Pattern.compile("^09+\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    public static boolean emailValidator(String email)
    {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._+-]+@[a-zA-Z0-9._]+\\.[a-zA-z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean passwordValidator(String password)
    {
        // ?= ---> exists
        // .* ---> . means any character, * means any number of times
        // .*[a] > means any character any number of times (possibly zero) followed by an a
        // \S means anything other than whitespace
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+-=])(?=\\S+$).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
