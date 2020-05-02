package com.example.secondtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.secondtest.DatabaseContract.COLUMN_NAME_TITLE_USERS_1;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper myDb ;
    private User user;

    EditText login;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.myDb = new DatabaseHelper(this);

        this.login = findViewById(R.id.login);
        this.password = findViewById(R.id.password);
        this.user = new User(this.password.getText().toString(), this.login.getText().toString(), this);

        AddData();

        configureNextButtonCreateAccount();
        configureNextButtonConnect();
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void configureNextButtonConnect() {
        Button nextButton = (Button) findViewById(R.id.ConfirmConnection);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if((login.getText().toString().equals("")) || (login.getText().toString().equals(""))) {
                    showToast("You have to enter your login, and your password");
                }
                else if (!(user.exist(login.getText().toString()))){
                    showToast("You have got no account, create one");
                }
                else if (!(user.connection(login.getText().toString(), password.getText().toString()))){
                    showToast("You've made a mistake in your password");
                }
                else{
                    startActivity(new Intent(MainActivity.this, PageAccueil.class));
                }
            }
        });
    }

    private void configureNextButtonCreateAccount() {
        Button nextButton = (Button) findViewById(R.id.CreateAccount);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, PageCreationAccount.class));
            }
        });
    }

    public void AddData() {
        boolean isInserted = myDb.insertData();
        if(isInserted){
            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
        }
    }

}
