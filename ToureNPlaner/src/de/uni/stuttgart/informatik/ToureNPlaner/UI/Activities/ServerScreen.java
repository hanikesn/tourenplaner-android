package de.uni.stuttgart.informatik.ToureNPlaner.UI.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import de.uni.stuttgart.informatik.ToureNPlaner.Data.SessionData;
import de.uni.stuttgart.informatik.ToureNPlaner.Net.Observer;
import de.uni.stuttgart.informatik.ToureNPlaner.Net.Session;
import de.uni.stuttgart.informatik.ToureNPlaner.R;

public class ServerScreen extends Activity implements Observer {
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        SessionData.Instance.save(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serverscreen);
        SessionData.Instance.load(savedInstanceState);

        createSpinner();
        createButtons();
    }

    private void createButtons() {
        Button btnconfirm = (Button) findViewById(R.id.btnconfirm);
        btnconfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                String url = SessionData.Instance.getServerURL();
                Session session = new Session();
                try {
                    session.connect(url,ServerScreen.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Intent myIntent = new Intent(view.getContext(), LoginScreen.class);
                //startActivity(myIntent);
            }

        });

        Button btnSetURL = (Button) findViewById(R.id.btnSetUrl);
        //  User can change the server URL
        btnSetURL.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ServerScreen.this);

                alert.setTitle("type your URL");
                alert.setMessage("URL");

                // Set an EditText view to get user input
                final EditText input = new EditText(ServerScreen.this);
                input.setText(SessionData.Instance.getServerURL());
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SessionData.Instance.setServerURL(input.getText().toString());
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }

        });
    }

    private void createSpinner() {
        // TODO get amount of available servers

        // TODO should be dynamic
        String[] spinnerArray = new String[2];
        spinnerArray[0] = "Free-Server";
        spinnerArray[1] = "Pay-Server";

        // loads the spinnerArray into the spinnerdropdown
        Spinner spinner = (Spinner) findViewById(R.id.spinner_server);
        @SuppressWarnings({"rawtypes", "unchecked"})
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {
                SessionData.Instance.setChoosenAlgorithm(adapter.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });
    }

    @Override
    public void onCompleted(Object object) {
        Toast.makeText(getApplicationContext(),object.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Object object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
