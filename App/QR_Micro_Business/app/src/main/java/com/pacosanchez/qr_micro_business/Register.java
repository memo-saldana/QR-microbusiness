package com.pacosanchez.qr_micro_business;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import com.pacosanchez.qr_micro_business.model.Business;
import com.pacosanchez.qr_micro_business.model.Response;
import com.pacosanchez.qr_micro_business.network.NetworkUtil;

import java.io.IOException;

import static com.pacosanchez.qr_micro_business.utils.Validation.validateFields;
import static com.pacosanchez.qr_micro_business.utils.Validation.validateEmail;

public class Register extends AppCompatActivity {

    public static final String TAG = Register.class.getSimpleName();

    private EditText etOwnerName;
    private EditText etBusinessName;
    private EditText etEmail;
    private EditText etPassword;
    private Button bJoin;

    private CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mSubscriptions = new CompositeSubscription();

        etOwnerName = findViewById(R.id.oNameField);
        etBusinessName = findViewById(R.id.bNameField);
        etEmail = findViewById(R.id.emaiField);
        etPassword = findViewById(R.id.passwordField);
        bJoin = findViewById(R.id.joinButton);

        bJoin.setOnClickListener(view -> register());

    }

    private void register(){

        setError();

        String name = etOwnerName.getText().toString();
        String businessName = etBusinessName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        int err = 0;

        if(!validateFields(name)){
            err++;
            etOwnerName.setError("Porfavor ingrese su nombre");
        }

        if(!validateEmail(email)){
            err++;
            etEmail.setError("Ingrese un email valido");
        }

        if(!validateFields(password)){
            err++;
            etPassword.setError("El campo de contraseña no debe estar vacio");
        }

        if(err==0){
            Business user = new Business();
            user.setOwner(name);
            user.setBusinessName(businessName);
            user.setEmail(email);
            user.setPassword(password);

            registerProcess(user);
        } else{
            showToastMessage("Huy un error en su informacion!");
        }

    }

    private void registerProcess(Business user){
        mSubscriptions.add(NetworkUtil.getRetrofit().register(user)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response){
        Toast.makeText(Register.this,response.getMessage(),Toast.LENGTH_LONG).show();
    }

    private void handleError(Throwable error){
        if(error instanceof HttpException){
            Gson gson = new GsonBuilder().create();

            try{
                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                Toast.makeText(Register.this,response.getMessage(),Toast.LENGTH_LONG).show();
            } catch(IOException e){
                e.printStackTrace();
            }
        } else{
            Toast.makeText(Register.this,"Hubo un error en la red!",Toast.LENGTH_LONG).show();
        }
    }

    private void showToastMessage(String message){

        Toast.makeText(Register.this,message,Toast.LENGTH_SHORT).show();
    }

    private void setError(){
        etOwnerName.setError(null);
        etBusinessName.setError(null);
        etEmail.setError(null);
        etPassword.setError(null);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
