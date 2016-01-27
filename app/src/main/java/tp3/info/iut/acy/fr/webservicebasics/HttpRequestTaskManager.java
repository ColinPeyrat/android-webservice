package tp3.info.iut.acy.fr.webservicebasics;

import android.os.AsyncTask;
import org.json.JSONObject;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONException;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequestTaskManager extends AsyncTask<Credential, Integer, JSONObject> {

    TextView connectionStatus;
    ProgressBar bar;
    private static final String FLAG_SUCCESS = "success";
    private static final String FLAG_MESSAGE = "message";
    private static final String LOGIN_URL = "http://colin-peyrat.com/api/tp3-android/index.php";

    // definition du setter pour faire le lien avec le TextView depuis la MainActivity
    public void setConnectionStatus(TextView textView) {
        this.connectionStatus = textView;
    }

    // pareil mais pour la progressBar
    public void setProgressBar(ProgressBar bar) {
        this.bar = bar;
    }

    @Override
    protected JSONObject doInBackground(Credential... params) {
        JSONObject jsonResponse= new JSONObject();

        try{
            // met la progressBar à 10%
            publishProgress(10);
            URL url = new URL(LOGIN_URL);
            HttpURLConnection connection = (HttpURLConnection )url.openConnection();

            //recupere la premiere ligne du tableau
            Credential credential = params[0];

            // regle la connection et tous les parametres requis
            connection.setRequestMethod("POST");
            String urlParameters  = "username="+credential.username+"&password="+credential.password;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            connection.setRequestProperty("Content-Length", "" + postData.length);

            try( DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
                wr.write( postData );
            }

            // met la progressBar à 30%
            publishProgress(30);

            // envoie des données
            Log.d("doInBackground", "ready to send request...");
            connection.connect();

            // met la progressBar à 60%
            publishProgress(60);

            // decode response
            InputStream in = new BufferedInputStream(connection.getInputStream());
            jsonResponse = new JSONObject(convertStreamToString(in));

        }  catch (IOException e) {
            Log.e("IOException", "Error");
        }  catch(JSONException e){
            Log.e("JSONException", "Error");
        }  catch (NetworkOnMainThreadException e){
            Log.e("ThreadException", "android > 3.0!!");
        }
        Log.i("doInBackground", jsonResponse.toString());

        // met la progressBar à 100%
        publishProgress(100);
        return jsonResponse;

    }

    @Override
    protected void onPostExecute( JSONObject result){
        Log.i("onPostExecute", "fini");

        //obligé de mettre un TryAndCatch pour une conversion de jSON
        try{
            Log.d("result",result.getString(FLAG_SUCCESS));
            int loginOK = result.getInt(FLAG_SUCCESS);
            connectionStatus.setText(result.getString(FLAG_MESSAGE));

            // check if connection status is OK
            if(loginOK!=0)
            {
                connectionStatus.setText("Connecté");
            }
            else
            {
                connectionStatus.setText("Erreur de connexion");
            }

        }  catch(JSONException e){
            Log.e("JSONException", "Error");
        }  catch (NetworkOnMainThreadException e){
            Log.e("ThreadException", "android > 3.0!!");
        }
    }

    // méthode appelé a chaque "publishProgress()"
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        bar.setProgress(values[0]);
        Log.d("onProgressUpdate","Appelé");
    }

    //méthode pour convertir la réponse du serveur
    public String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}


