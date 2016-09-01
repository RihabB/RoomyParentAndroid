package tn.orange.odc.appparent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.orange.odc.appparent.Entites.user;
import tn.orange.odc.appparent.Entites.webServieceRep;
import tn.orange.odc.appparent.Network.ProjectApi;
import tn.orange.odc.appparent.Network.UserService;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.edit_login)
    EditText  loginEditText;

    @Bind(R.id.edit_password)
    EditText passEditText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }



    private boolean isValidEmail(String email) {
        if (email != null && email.length() >= 1) {
            return true;
        }
        return false;
    }


    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 1) {
            return true;
        }
        return false;
    }



    public void checkLogin(View arg0) {

        final String email = loginEditText.getText().toString();
        if (!isValidEmail(email)) {
            //Set error message for email field
            loginEditText.setError("Nom d'utilisateur invalide");
        }

        final String pass = passEditText.getText().toString();
        if (!isValidPassword(pass)) {
            //Set error message for password field
            passEditText.setError("Le mot de passe ne doit pas etre vide");
        }

        if (isValidEmail(email) && isValidPassword(pass)) {

            Call<webServieceRep> rep = ProjectApi.getInstance().getUserServ().Connect(email,pass);
            rep.enqueue(new Callback<webServieceRep>() {
                @Override
                public void onResponse(Call<webServieceRep> call, Response<webServieceRep> response) {
                    if(response.body().success) {

                        Toast.makeText(LoginActivity.this, "Connexion réussite", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                        resetFields();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Utilisateur invalide", Toast.LENGTH_SHORT).show();
                    }

                }


                @Override
                public void onFailure(Call<webServieceRep> call, Throwable t) {

                    Toast.makeText(LoginActivity.this, "Erreur lors de la Connexion " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }


            });

        }

    }
    void resetFields(){

        loginEditText.setText("");
        passEditText.setText("");

    }
    public void inscritPage(View arg0) {
        Toast.makeText(LoginActivity.this, "Inscription", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LoginActivity.this,InscritActivity.class);
        startActivity(i);



    }
}
