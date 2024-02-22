package il.co.shivhit.helper.inputValidators;

import android.view.View;

public class PasswordRule extends TextRule{
    public PasswordRule(View view, RuleOperation operation, String message) {
        super(view, operation, message);

        regularExpression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[a-zA-Z\\d!@#$%^&*()_+]{4,8}$";
        minimumLength = 4;
        maximumLength = 8;
    }

    public PasswordRule(View view, RuleOperation operation, String message, int minimumLength, int maximumLength) {
        this(view, operation, message, minimumLength, maximumLength, null);

        regularExpression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[a-zA-Z\\d!@#$%^&*()_+]{" + String.valueOf(minimumLength) + "," + String.valueOf(maximumLength) +"}$";
    }

    public PasswordRule(View view, RuleOperation operation, String message, int minimumLength, int maximumLength, String regularExpression) {
        super(view, operation, message, minimumLength, maximumLength, false);

        this.regularExpression = regularExpression;
    }

     public static boolean validate(PasswordRule rule){
        if (Validator.requiredValidator(rule)) {
            if (!TextRule.validate(rule)) {
                rule.isValid = false;
            }
            else
                rule.isValid = true;
        }
        else {
            rule.isValid = false;
        }

        return rule.isValid;
    }
}
