package tp3.info.iut.acy.fr.webservicebasics;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnConnected = (Button)findViewById(R.id.btnConnect);
        btnConnected.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // récupère les differents éléments
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);
        TextView connectionStatus = (TextView)findViewById(R.id.connectedMessage);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);

        // permet de reinitialiser le bouton et la TextView
        connectionStatus.setText("Non Connecté");
        progressBar.setProgress(0);

        Log.d("Connexion", "Connect Button Pressed !");

        Credential credential = new Credential();
        credential.username=username.getText().toString();
        credential.password=password.getText().toString();

        HttpRequestTaskManager result = new HttpRequestTaskManager();
        result.setProgressBar(progressBar);
        result.setConnectionStatus(connectionStatus);
        result.execute(credential);
        Log.d("HttpRequestTaskManager", String.valueOf(result));
    }
}

