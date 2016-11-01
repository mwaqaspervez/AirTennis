package waqas.airtennis.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.net.URISyntaxException;

import waqas.airtennis.R;
import waqas.airtennis.Utils.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Socket mSocket;
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = (JSONObject) args[0];

                        // RECEIVE JSON DATA HERE
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            mSocket = IO.socket("http://192.172.19.49:3000");
            Emitter emitter = new Emitter();

        } catch (URISyntaxException e) {
            Utils.makeToast(this, e.getMessage());
            e.printStackTrace();
        }


        findViewById(R.id.bt_signup).setOnClickListener(this);
        findViewById(R.id.signup_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_signup:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                LoginActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                break;

            case R.id.signup_login:
               /* new IntentIntegrator(this).
                        setBeepEnabled(false).
                        initiateScan();*/
                mSocket.connect();
                mSocket.emit("Want to Connect", "");
                onNewMessage.call();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null)
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

}
