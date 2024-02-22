package il.co.shivhit.helper.inputValidators;

import android.view.View;
import android.widget.EditText;

public class NumberRule extends Rule{

    private double lowBound;
    private double upBound;
    private boolean isInt;

     public NumberRule(View view, RuleOperation operation, String message, int lowBound, int upBound) {
        super(view, operation, message);

        this.lowBound = lowBound;
        this.upBound = upBound;
        isInt = true;
    }

    public NumberRule(View view, RuleOperation operation, String message, double lowBound, double upBound) {
        super(view, operation, message);

        this.lowBound = lowBound;
        this.upBound = upBound;
        isInt = false;
    }

     public double getLowBound() {
        return lowBound;
    }

    public double getUpBound() {
        return upBound;
    }

    public boolean getIsInt() { return isInt; }

    public boolean isInteger(double number){
        return Math.ceil(number) == Math.floor(number);
    }

    public static boolean validate(NumberRule rule){
        if(Validator.requiredValidator(rule)){
            String value =  ((EditText) rule.getView()).getText().toString();
            int     checkedInt;
            int     intLowBound;
            int     intUpBound;
            double  checkedDouble;

            if (rule.getIsInt()) {
                try{
                    checkedInt = Integer.valueOf(value);
                    intLowBound = (int)rule.getLowBound();
                    intUpBound  = (int)rule.getUpBound();

                    if (intLowBound <= checkedInt && checkedInt <= intUpBound)
                        rule.isValid = true;
                    else
                        rule.isValid = false;
                }
                catch (Exception e){
                    rule.isValid = false;
                }
            }
            else {
                try{
                    checkedDouble = Double.valueOf(value);

                    if (rule.getLowBound() <= checkedDouble && checkedDouble <= rule.getUpBound())
                        rule.isValid = true;
                    else
                        rule.isValid = false;
                }
                catch (Exception e){
                    rule.isValid = false;
                }
            }

            return rule.isValid;
        }
        else
            return false;
    }
}
