package waqas.airtennis.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import waqas.airtennis.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button b = (Button) findViewById(R.id.bt_signup);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_signup:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                LoginActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                break;
        }
    }
}
