package com.pacosanchez.qr_micro_business;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pacosanchez.qr_micro_business.model.Response;
import com.pacosanchez.qr_micro_business.network.NetworkUtil;
import com.pacosanchez.qr_micro_business.utils.Constants;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.pacosanchez.qr_micro_business.utils.Validation.validateEmail;
import static com.pacosanchez.qr_micro_business.utils.Validation.validateFields;


public class Login_Activity extends AppCompatActivity {

    public static final String TAG = Login_Activity.class.getSimpleName();


    EditText emailField;
    EditText passwordField;

    Button logInButton;
    Button cAccountButton;

    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        mSubscriptions = new CompositeSubscription();

        emailField = findViewById(R.id.emailText);
        passwordField = findViewById(R.id.passwordText);

        logInButton = findViewById(R.id.logInButton);
        cAccountButton = findViewById(R.id.cAccountButton);

        logInButton.setOnClickListener(view -> login());
        cAccountButton.setOnClickListener(view -> goToRegister());

        initSharedPreferences();

    }

    private void initSharedPreferences(){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void login(){

        setError();

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        int err = 0;

        if(!validateEmail(email)){
            err++;
            emailField.setError("El email no es valido");
        }

        if(!validateFields(password)){
            err++;
            passwordField.setError("Porfavor ingrese la contraseña");
        }

        if(err == 0){

            loginProcess(email,password);
        } else{

            Toast.makeText(this,"Las credenciales no son validas",Toast.LENGTH_SHORT).show();
        }
    }

    private void setError(){
        emailField.setError(null);
        passwordField.setError(null);
    }

    private void loginProcess(String email,String password){

        mSubscriptions.add(NetworkUtil.getRetrofit(email,password).login()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response){

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN,response.getToken());
        editor.putString(Constants.EMAIL,response.getMessage());
        editor.apply();

        emailField.setText(null);
        passwordField.setText(null);

        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
    }

    private void handleError(Throwable error){
        Log.d("App","No se armo");
        if(error instanceof HttpException){
            Gson gson = new GsonBuilder().create();

            try{
                String errorBody = ((HttpException)error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                Toast.makeText(this,response.getMessage(),Toast.LENGTH_LONG).show();
            } catch(IOException e){
               e.printStackTrace();
            }
        } else{
            Toast.makeText(this,"Error en la red",Toast.LENGTH_LONG).show();
        }
    }

    private void goToRegister(){

        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    public void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

}
