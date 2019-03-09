package vikash.kumar.tvsautomobilesolutions.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import vikash.kumar.tvsautomobilesolutions.R;
import vikash.kumar.tvsautomobilesolutions.json.DataResponse;
import vikash.kumar.tvsautomobilesolutions.json.TableDataResponse;
import vikash.kumar.tvsautomobilesolutions.model_pojo.DataModel;
import vikash.kumar.tvsautomobilesolutions.util.LoginSharedPreferences;


public class LoginActivity extends AppCompatActivity {
    private ProgressDialog progress;
    private TextInputLayout userNameTextInputLayout,passwordTextInputLayout;
    private EditText userName,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        if(LoginSharedPreferences.getArrayList(getApplicationContext())!=null){
            // call Main Activity
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        findingView();
        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait");
        progress.setMessage("We are verifying your credentials ");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }
    private void findingView(){
        userNameTextInputLayout=findViewById(R.id.userNameTextInputLayout);
        passwordTextInputLayout=findViewById(R.id.passwordTextInputLayout);
        userName=findViewById(R.id.userName);
        userName.addTextChangedListener(new CustomTextWatcher1(userName));
        password=findViewById(R.id.password);
        password.addTextChangedListener(new CustomTextWatcher2(password));
    }
    public void login(View view){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        boolean isEmptyField1=checkFieldsForEmpty1();
        boolean isEmptyField2=checkFieldsForEmpty2();
        if (isEmptyField1&&isEmptyField2){
            progress.show();
            JsonObject json = new JsonObject();
            json.addProperty("username", userName.getText().toString());
            json.addProperty("password", password.getText().toString());

            Ion.with(getApplicationContext())
                    .load("http://tvsfit.mytvs.in/reporting/vrm/api/test_new/int/gettabledata.php")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progress.dismiss();
                            Log.d("JSONObject",result.toString());
                            if (result.toString().contains("ErrorDescription")){
                                View parentLayout = findViewById(android.R.id.content);
                                Snackbar.make(parentLayout, "Your Credentials is wrong\n", Snackbar.LENGTH_LONG)
                                        .setAction("Try Again", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();
                            }else{

                                TableDataResponse response = new Gson().fromJson(result, TableDataResponse.class);
                                DataResponse dataResponse = new Gson().fromJson(response.getTABLEDATA(), DataResponse.class);
                                final List<String> resultList = new ArrayList<String>();
                                final List<List<String>> mainList1 = dataResponse.getData();
                                for (List<String> subList : mainList1) {
                                    resultList.addAll(subList);
                                }
                                String arr[]=new String[resultList.size()];
                                ArrayList<DataModel> list=new ArrayList<>();
                                int counter=0;
                                for (String str:resultList) {
                                        arr[counter++]=str;
                                }
                                for (int i=0;i<arr.length-4;){
                                    DataModel dm=new DataModel(
                                            arr[i++],arr[i++],arr[i++],arr[i++],arr[i++],arr[i++]);
                                    list.add(dm);
                                }
                              //  Log.d("hii",String.valueOf(list.size()));
                                LoginSharedPreferences.saveArrayList(getApplicationContext(),list);
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
    }

    private class CustomTextWatcher1 implements TextWatcher {
        private EditText mEditText;

        public CustomTextWatcher1(EditText e) {
            mEditText = e;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(userNameTextInputLayout.isErrorEnabled()){
                userNameTextInputLayout.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFieldsForEmpty1();
        }
    }
    private class CustomTextWatcher2 implements TextWatcher {
        private EditText mEditText;

        public CustomTextWatcher2(EditText e) {
            mEditText = e;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(passwordTextInputLayout.isErrorEnabled()){
                passwordTextInputLayout.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFieldsForEmpty2();
        }
    }
    private boolean checkFieldsForEmpty1(){
        boolean isEmpty=true;
        if (TextUtils.isEmpty(userName.getText())){
            userNameTextInputLayout.setError("Please enter username !!");
            isEmpty=false;
        }
        return isEmpty;
    }
    private boolean checkFieldsForEmpty2(){
        boolean isEmpty=true;
        if (TextUtils.isEmpty(password.getText())){
            passwordTextInputLayout.setError("Please enter password !!");
            isEmpty=false;
        }
        return isEmpty;
    }

}
