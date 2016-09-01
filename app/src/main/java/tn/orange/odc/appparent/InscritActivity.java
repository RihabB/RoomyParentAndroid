package tn.orange.odc.appparent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.orange.odc.appparent.Entites.register;
import tn.orange.odc.appparent.Entites.webServieceRep;
import tn.orange.odc.appparent.Network.ProjectApi;
import tn.orange.odc.appparent.Entites.registerRep;

/**
 * Created by opc on 22/08/2016.
 */
public class InscritActivity extends AppCompatActivity {


    @Bind(R.id.login)
    EditText login;
    @Bind(R.id.nom)
    EditText nom;
    @Bind(R.id.pwd)
    EditText pwd;
    @Bind(R.id.cpwd)
    EditText cpwd;
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.idrobot)
    EditText idrobot;
    @Bind(R.id.nomrobot)
    EditText nomrobot;

    String resultat;

    register utilisateur;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.inscrit_activity);
        ButterKnife.bind(this);

    }


    private boolean isValidEmail(String email) {
        if (email != null && email.length() >= 1) {
            return true;
        }
        return false;
    }

    private boolean isValideNom(String nom) {
        if (nom != null && nom.length() >= 1) {
            return true;
        }
        return false;
    }

    private boolean isValidelogin(String login) {
        if (login != null && login.length() >= 1) {
            return true;
        }
        return false;
    }

    private boolean isValidIDRobot(String idrobot) {
        if (idrobot != null && idrobot.length() >= 1) {
            return true;
        }
        return false;
    }

    private boolean isValidnomRobot(String nomrobot) {
        if (nomrobot != null && nomrobot.length() >= 1) {
            return true;
        }
        return false;
    }

    // validating password
    private boolean isValidPassword(String pwd) {
        if (pwd != null && pwd.length() >= 1 ) {
            return true;
        }
        return false;
    }


    private boolean isSamePass(String pwd, String cpwd) {

        if (pwd == cpwd) {
            return true;
        }
        return false;
    }

    public void checkInscrit(View arg0) {

        final String n = nom.getText().toString();
        if (!isValideNom(n)) {
            //Set error message for email field
            login.setError("Nom et prenom invalide");
        }
        final String l = login.getText().toString();
        if (!isValidEmail(l)) {
            //Set error message for email field
            login.setError("Nom d'utilisateur invalide");
        }
        final String e = email.getText().toString();
        if (!isValidEmail(e)) {
            //Set error message for email field
            email.setError("Nom d'utilisateur invalide");
        }

        final String pass = pwd.getText().toString();
        if (!isValidPassword(pass)) {
            //Set error message for password field
            pwd.setError("Les mot de passe ne doit pas etre vide");
        }
        final String pass2 = cpwd.getText().toString();
        if (!isValidPassword(pass2)) {
            //Set error message for password field
            pwd.setError("Les mot de passe ne doit pas etre vide");
        }
        if(!isSamePass(pass,pass2)){
            pwd.setError("Les mots de passes doivent etre identiques");
            cpwd.setError("Les mots de passes doivent etre identiques");
        }

        final String id = idrobot.getText().toString();
        if (!isValidIDRobot(id)) {
            //Set error message for password field
            idrobot.setError("ID Robot ne doit pas etre vide");
        }


        final String nrobot = nomrobot.getText().toString();

        if (isValidEmail(e) && isValidPassword(pass) && isValideNom(n) && isValidelogin(l) && isSamePass(pass,pass2)  && isValidIDRobot(id) && isValidPassword(pass2) ) {


            utilisateur.nom_prenom=n;
            utilisateur.password=pass;
            utilisateur.username=l;
            utilisateur.email=e;
            utilisateur.reference=id;
            utilisateur.nom=nrobot;

            Call<registerRep> reponse = ProjectApi.getInstance().getUserServ().addUser(utilisateur);
            reponse.enqueue(new Callback<registerRep>() {
                @Override
                public void onResponse(Call<registerRep> call, Response<registerRep> response) {


                        Toast.makeText(InscritActivity.this, "Inscription réussite", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(InscritActivity.this,MainActivity.class);
                        startActivity(i);
                        resetFields();
                }


                @Override
                public void onFailure(Call<registerRep> call, Throwable t) {

                    Toast.makeText(InscritActivity.this, "Erreur lors de la Connexion ", Toast.LENGTH_SHORT).show();
                }


            });
            Toast.makeText(InscritActivity.this, "Données enregistrer", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(InscritActivity.this,MainActivity.class);
            startActivity(i);
            resetFields();

        }

    }


    public void backlogin (View arg0) {

        Toast.makeText(InscritActivity.this, "Se connecter", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(InscritActivity.this,LoginActivity.class);
        startActivity(i);
        resetFields();
    }

    void resetFields() {

        login.setText("");
        nom.setText("");
        email.setText("");
        pwd.setText("");
        cpwd.setText("");
        idrobot.setText("");
        nomrobot.setText("");
    }
    public void QRcode (View arg0) {
        Toast.makeText(InscritActivity.this, "QR Code", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(InscritActivity.this,QRcode.class);
        startActivityForResult(i,1);
        resetFields();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }
}
