package waqas.airtennis.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import waqas.airtennis.R;
import waqas.airtennis.Utils.Utils;

public class SignupActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    String text;
    private EditText phone, username, name, password;
    private RadioButton male, female;
    private boolean isNameOk = false, isUsernameOk = false, isPhoneOk = false, isPasswordOk = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        Utils.setBackButton(getSupportActionBar());
    }

    private void init() {
        phone = (EditText) findViewById(R.id.ed_phone);
        username = (EditText) findViewById(R.id.ed_username);
        name = (EditText) findViewById(R.id.ed_name);
        password = (EditText) findViewById(R.id.ed_password);
        male = (RadioButton) findViewById(R.id.rb_male);
        female = (RadioButton) findViewById(R.id.rb_female);
        phone.setOnFocusChangeListener(this);
        username.setOnFocusChangeListener(this);
        name.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);


        findViewById(R.id.signup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameOk) {
                    if (isUsernameOk) {
                        if (isPasswordOk) {
                            if (isPhoneOk) {
                                if (male.isChecked() || female.isChecked()) {
                                    Utils.makeToast(SignupActivity.this, "Signing Up");
                                } else
                                    Utils.makeToast(SignupActivity.this, "Please specify you gender");
                            } else
                                Utils.makeToast(SignupActivity.this, "Phone Number should be minimum 11 numbers");
                        } else
                            Utils.makeToast(SignupActivity.this, "Password length must be minimum of 6 characters");
                    } else
                        Utils.makeToast(SignupActivity.this, "Username not valid OR already exist");
                } else
                    Utils.makeToast(SignupActivity.this, "Name length must be in 3 - 16 letters.");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b) {
            switch (view.getId()) {
                case R.id.ed_phone:
                    text = ((EditText) view).getText().toString();

                    if (text.length() != 0)
                        isPhoneOk = setAccepted(phone, Utils.verifyPhoneNumber(text));
                    break;
                case R.id.ed_name:

                    text = ((EditText) view).getText().toString();
                    if (text.length() != 0)
                        isNameOk = setAccepted(name, Utils.verifyName(text));
                    break;
                case R.id.ed_username:
                    text = ((EditText) view).getText().toString();
                    if (text.length() != 0)
                        isUsernameOk = setAccepted(username, verifyUsername(text));

                    break;
                case R.id.ed_password:
                    text = ((EditText) view).getText().toString();
                    if (text.length() != 0)
                        isPasswordOk = setAccepted(password, Utils.verifyPassword(text));
                    break;
            }
        }
    }

    private boolean verifyUsername(String text) {
        return text.isEmpty();
    }

    public boolean setAccepted(EditText editText, boolean b) {
        if (b) {
            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.ic_ok), null);
            return true;
        } else
            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.ic_error), null);
        return false;
    }
}
