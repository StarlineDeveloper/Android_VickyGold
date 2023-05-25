package com.VijayAssayCenter.activity;

import static com.VijayAssayCenter.utils.Constants.GetLoginDetails;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.BaseActivity;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Login extends BaseActivity {


    EditText et_user_id;
    EditText et_password;
    LinearLayout lay_login;
    String UserId="";
    String Password="";
    String ObjVariable="";
    String regresponse="";
    TextView tv_forgot;

    LinearLayout lay_create;
    LinearLayout lay_back;

    CheckBox checkbox;
    String SaveValue="Yes";

    String OldUserName="";
    String OldPassword="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        et_user_id=findViewById(R.id.et_user_id);
        et_password=findViewById(R.id.et_password);

        tv_forgot=findViewById(R.id.tv_forgot);

        lay_login=findViewById(R.id.lay_login);

        checkbox=findViewById(R.id.checkbox);

        OldUserName=Common.getPreferenceString(Login.this,"OldUserName","");
        OldPassword=Common.getPreferenceString(Login.this,"OldPassword","");
        et_user_id.setText(OldUserName);
        et_password.setText(OldPassword);
        if(OldUserName.equals("")){
            checkbox.setChecked(true);
        }else {
            checkbox.setChecked(true);

        }


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if (buttonView.isChecked()) {
                    SaveValue="Yes";
                }
                else {
                    SaveValue="Not";
                }

            }
        });

        lay_back=findViewById(R.id.lay_back);
        lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lay_create=findViewById(R.id.lay_create);
        lay_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.Lable="Register";
                Constants.LoginScess="Login";
                Intent Silver=new Intent(getActivity(), UserRegister.class);
                Silver.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Silver);
            }
        });

        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ForgotPassword=new Intent(getActivity(), ForgotPassword.class);
                ForgotPassword.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ForgotPassword);
            }
        });

        lay_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_user_id.getText().toString().trim().length() == 0) {
                    et_user_id.setError("Login ID");
                    et_user_id.requestFocus();
                }else if(et_password.getText().toString().trim().length() == 0){
                    et_password.setError("Password");
                    et_password.requestFocus();
                }else {
                    if (isConnected()) {
                        UserId=et_user_id.getText().toString().trim();
                        Password=et_password.getText().toString().trim();

                        ObjVariable="{" +
                                "\"ClientId\":1," +
                                "\"Password\":\""+Password+"\"," +
                                "\"Firmname\":\""+"vickygold"+"\"," +
                                "\"loginid\":"+UserId+"}";
                        new LoginApi().execute();

                        Log.e("ObjVariable",ObjVariable);



                    } else {
                        Toast.makeText(Login.this, "Not Connected to Internet!!!", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }

    private class LoginApi extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress("");
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", GetLoginDetails);
            request.addProperty("Obj",ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, GetLoginDetails);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("LoginResponce", s);

                dismissProgress();

                        final JSONObject json;
                        try {
                            json = new JSONObject(s);

                            String ReturnCode = json.getString("ReturnCode");
                            if(ReturnCode.equals("200")){
                                String Data = json.getString("Data");
                                String TokenLogin = json.getString("Token");

                                JSONArray jArray = new JSONArray(Data);
                                for(int i=0;i<jArray.length();i++){
                                    JSONObject json_data = jArray.getJSONObject(i);

                                    String Access=json_data.getString("Access");
                                    String AccountID=json_data.getString("AccountID");
                                    String Balance=json_data.getString("Balance");
                                    String City =json_data.getString("City");
                                    String Email =json_data.getString("Email");
                                    String GroupID =json_data.getString("GroupID");
                                    String GroupName =json_data.getString("GroupName");
                                    String GST =json_data.getString("GST");
                                    String LoginId =json_data.getString("LoginId");
                                    String Name =json_data.getString("Name");
                                    String Number =json_data.getString("Number");
                                    String password =json_data.getString("password");
                                    String Status =json_data.getString("Status");
                                    String status_code =json_data.getString("status_code");
                                    String UserName =json_data.getString("UserName");
                                    String Firmname =json_data.getString("Firmname");
                                    String VirtualACCode =json_data.getString("VirtualACCode");

                                    Common.setPreferenceString(Login.this,"Access",Access);
                                    Common.setPreferenceString(Login.this,"AccountID",AccountID);
                                    Common.setPreferenceString(Login.this,"Balance",Balance);
                                    Common.setPreferenceString(Login.this,"City",City);
                                    Common.setPreferenceString(Login.this,"Email",Email);
                                    Common.setPreferenceString(Login.this,"GroupID",GroupID);
                                    Common.setPreferenceString(Login.this,"GroupName",GroupName);
                                    Common.setPreferenceString(Login.this,"GST",GST);
                                    Common.setPreferenceString(Login.this,"LoginId",LoginId);
                                    Common.setPreferenceString(Login.this,"LoginId",LoginId);
                                    Common.setPreferenceString(Login.this,"Name",Name);
                                    Common.setPreferenceString(Login.this,"Number",Number);
                                    Common.setPreferenceString(Login.this,"password",password);
                                    Common.setPreferenceString(Login.this,"Status",Status);
                                    Common.setPreferenceString(Login.this,"status_code",status_code);
                                    Common.setPreferenceString(Login.this,"UserName",UserName);
                                    Common.setPreferenceString(Login.this,"Firmname",Firmname);
                                    Common.setPreferenceString(Login.this,"VirtualACCode",VirtualACCode);
                                    Common.setPreferenceString(Login.this,"DisplayName",Name);
                                    Common.setPreferenceString(Login.this,"TokenLogin",TokenLogin);
                                    Log.e("AccountID",AccountID);

                                    SuccesDialog("Success","Successful");

                                    if(SaveValue.equals("Yes")){
                                        Common.setPreferenceString(Login.this,"OldUserName",UserId);
                                        Common.setPreferenceString(Login.this,"OldPassword",Password);
                                    }else {
                                        Common.setPreferenceString(Login.this,"OldUserName","");
                                        Common.setPreferenceString(Login.this,"OldPassword","");
                                    }

                                    Intent MainActivity=new Intent(Login.this, MainActivity.class);
                                    MainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(MainActivity);




                                }


                            }else {
                                String ReturnMsg = json.getString("ReturnMsg");
                                DialogFaid("Vicky Jewellery",ReturnMsg);
                                //DialogFaid("Vicky Jewellery","Please contact To Admin");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("LoginResponce", e.getMessage());

                        }


            } catch (Exception e) {
                Log.e("LoginResponce", e.getMessage());
                e.printStackTrace();
                Log.e("LoginResponce", e.getMessage());

            }
        }
    }

//    public static String loadService(SoapObject request, String method) throws IOException, XmlPullParserException {
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = true;
//        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/Terminal.asmx");
//        transport.call("http://tempuri.org/" + method, envelope);
//        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//        return response.toString();
//    }

}
