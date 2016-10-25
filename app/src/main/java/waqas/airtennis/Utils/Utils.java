package waqas.airtennis.Utils;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

public class Utils {

    public static ActionBar setBackButton(ActionBar actionBar) {

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        return actionBar;
    }

    public static boolean verifyPhoneNumber(String num) {
        return (num.length() >= 11 && num.length() <= 13);
    }

    public static boolean verifyName(String name) {
        return (name.length() >= 3 && name.length() < 16);
    }

    public static void makeToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean verifyPassword(String text) {
        return (text.length() >= 6 && text.length() <= 15);
    }
}


