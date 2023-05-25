package com.VijayAssayCenter.activity;

import static com.VijayAssayCenter.utils.Constants.insertRegisterApp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.VijayAssayCenter.R;
import com.VijayAssayCenter.utils.BaseActivity;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UserRegister extends BaseActivity {

    LinearLayout lay_back;
    EditText et_name;
    EditText et_mobile;
    EditText et_email;
    EditText et_city;
    EditText et_gst;
    EditText et_frim_name;
    LinearLayout lay_submit;
    byte[] bytes;
    TextView tv_vitual_code;
    LinearLayout v_code_layout;
    String Name = "";
    String Mobile = "";
    String Email = "";
    String City = "";
    String GST = "";
    String FrimName = "";
    String ObjVariable = "";
    String regresponse = "";
    String BiTGSt = "";
    String BiTPanCart = "";
    String BiTLincese = "";
    TextView tv_gst_certificate;
    TextView tv_pancartd;
    TextView tv_lincenece;
    TextView tv_lable;
    String Name1 = "GST Certificate";
    String Name2 = "Pan Card";
    String Name3 = "Trade License";
    LinearLayout lay_help;

    private static final int FILE_SELECT_CODE_GST = 1;
    private static final int FILE_SELECT_CODE_PANCarD = 2;
    private static final int FILE_SELECT_CODE_TRAdeLinces = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        if (ContextCompat.checkSelfPermission(UserRegister.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserRegister.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(UserRegister.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        5);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        tv_lable = findViewById(R.id.tv_lable);
        tv_lable.setText(Constants.Lable);

        lay_help = findViewById(R.id.lay_help);
        lay_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String BookingNo1 = "+919331113116";

                if (BookingNo1.equals("")) {

                } else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + BookingNo1));
                    startActivity(callIntent);
                }
            }
        });

        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_email = findViewById(R.id.et_email);
        et_city = findViewById(R.id.et_city);
        et_gst = findViewById(R.id.et_gst);
        et_frim_name = findViewById(R.id.et_frim_name);
        tv_gst_certificate = findViewById(R.id.tv_gst_certificate);
        tv_pancartd = findViewById(R.id.tv_pancartd);
        tv_vitual_code = findViewById(R.id.tv_vitual_code);
        v_code_layout = findViewById(R.id.v_code_layout);
        tv_lincenece = findViewById(R.id.tv_lincenece);

        if (Constants.Lable.equals("Register")) {
            v_code_layout.setVisibility(View.GONE);

            et_name.setEnabled(true);
            et_mobile.setEnabled(true);
            et_frim_name.setEnabled(true);
            et_gst.setEnabled(true);
            tv_gst_certificate.setEnabled(true);
            tv_pancartd.setEnabled(true);
            tv_lincenece.setEnabled(true);

        } else {
            v_code_layout.setVisibility(View.VISIBLE);
            tv_vitual_code.setText(Common.getPreferenceString(UserRegister.this, "VirtualACCode", ""));

            et_name.setEnabled(false);
            et_mobile.setEnabled(false);
            et_frim_name.setEnabled(false);
            et_gst.setEnabled(false);
            tv_gst_certificate.setEnabled(false);
            tv_pancartd.setEnabled(false);
            tv_lincenece.setEnabled(false);


        }


        et_name.setText(Common.getPreferenceString(UserRegister.this, "DisplayName", ""));
        et_mobile.setText(Common.getPreferenceString(UserRegister.this, "Number", ""));
        et_email.setText(Common.getPreferenceString(UserRegister.this, "Email", ""));
        et_city.setText(Common.getPreferenceString(UserRegister.this, "City", ""));
        et_gst.setText(Common.getPreferenceString(UserRegister.this, "GST", ""));
        et_frim_name.setText(Common.getPreferenceString(UserRegister.this, "Firmname", ""));



        tv_gst_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String[] mimeTypes = {"image/*","application/pdf","application/doc"};

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(
                            Intent.createChooser(intent, "Select a File to Upload"),
                            FILE_SELECT_CODE_GST);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(UserRegister.this, "Please install a File Manager.",
                            Toast.LENGTH_SHORT).show();
                }

//               Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
//                chooseFile.setType("*/*");
//                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
//                startActivityForResult(chooseFile, 5);

//                final Uri data = FileProvider.getUriForFile(UserRegister.this, "myprovider", new File(file_path));
//                UserRegister.this.grantUriPermission(UserRegister.this.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                final Intent intent = new Intent(Intent.ACTION_VIEW)
//                        .setDataAndType(data, "*/*")
//                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                UserRegister.this.startActivity(intent);
            }
        });

        tv_pancartd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(
                            Intent.createChooser(intent, "Select a File to Upload"),
                            FILE_SELECT_CODE_PANCarD);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(UserRegister.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_lincenece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(
                            Intent.createChooser(intent, "Select a File to Upload"),
                            FILE_SELECT_CODE_TRAdeLinces);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(UserRegister.this, "Please install a File Manager.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        lay_back = (LinearLayout) findViewById(R.id.lay_back);
        lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lay_submit = findViewById(R.id.lay_submit);
        lay_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (et_name.getText().toString().trim().length() == 0) {
                    et_name.setError("User Name");
                    et_name.requestFocus();
                } else if (et_mobile.getText().toString().trim().length() == 0) {
                    et_mobile.setError("Mobile Number");
                    et_mobile.requestFocus();
                } else if (et_mobile.getText().toString().trim().length() < 10) {
                    et_mobile.setError("Invalid Mobile Number");
                    et_mobile.requestFocus();
                } else if (et_frim_name.getText().toString().trim().length() == 0) {
                    et_frim_name.setError("Firm Name");
                    et_frim_name.requestFocus();
                } else if (et_gst.getText().toString().trim().length() == 0) {
                    et_gst.setError("GST Number");
                    et_gst.requestFocus();
                } else {
                    if (isConnected()) {
                        Name = et_name.getText().toString().trim();
                        Mobile = et_mobile.getText().toString().trim();
                        Email = et_email.getText().toString().trim();
                        City = et_city.getText().toString().trim();
                        GST = et_gst.getText().toString().trim();
                        FrimName = et_frim_name.getText().toString().trim();

                        ObjVariable = "{\"Number\":\"" + Mobile + "\"," +
                                "\"GST\":\"" + GST + "\"," +
                                "\"Flag\":\"new\"," +
                                "\"Name\":\"" + Name + "\"," +
                                "\"Firmname\":\"" + FrimName + "\"," +
                                "\"Email\":\"" + Email + "\"," +
                                "\"RegisterDoc\":\"" + BiTGSt + "\"," +
                                "\"DocName\":\"" + Name1 + "\"," +
                                "\"RegisterDoc1\":\"" + BiTPanCart + "\"," +
                                "\"DocName1\":\"" + Name2 + "\"," +
                                "\"RegisterDoc2\":\"" + BiTLincese + "\"," +
                                "\"DocName2\":\"" + Name3 + "\"," +
                                "\"ClientId\":\"1\"," +
                                "\"AccountId\":\"0\"," +
                                "\"City\":\"" + City + "\"}";


                        Log.e("RegisterObject", ObjVariable);

                        new Register().execute();

                    } else {
                        Toast.makeText(UserRegister.this, "Not Connected to Internet!!!", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }

    private class Register extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress("");
        }

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject("http://tempuri.org/", insertRegisterApp); //insertRegisterApp //insertRegister
            request.addProperty("Obj", ObjVariable);
            Log.e("request", request.toString());
            try {
                regresponse = loadServiceTerminal(request, insertRegisterApp);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                Log.e("RegisterRespoce", s);

                dismissProgress();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("RegisterRespoce", "" + s);


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);

                            String ReturnMsg = json.getString("ReturnMsg");
//                            String data = json.getString("data");
                            Log.e("Otpttttt", "" + ReturnMsg);

                            if (ReturnMsg.equals("")) {
                                DialogFaid("failed", "Invalid Credentials");


                            } else {
                                SuccesDialog("Success", "Successful");
                                if (Constants.LoginScess.equals("Login")) {
                                    Intent Silver = new Intent(getActivity(), Login.class);
                                    startActivity(Silver);
                                    finish();
                                } else {

                                    JSONObject json_data = new JSONObject(ReturnMsg);
                                    Log.e("json_data", "" + json_data);

                                    String City = json_data.getString("City");
                                    String Email = json_data.getString("Email");
                                    String GST = json_data.getString("GST");
                                    String Name = json_data.getString("Name");
                                    String Number = json_data.getString("Number");
                                    String Firmname = json_data.getString("Firmname");
                                    String VirtualACCode = json_data.getString("VirtualACCode");
                                    Log.e("Firmname", Firmname);

                                    Common.setPreferenceString(UserRegister.this, "City", City);
                                    Common.setPreferenceString(UserRegister.this, "Email", Email);
                                    Common.setPreferenceString(UserRegister.this, "GST", GST);
                                    Common.setPreferenceString(UserRegister.this, "DisplayName", Name);
                                    Common.setPreferenceString(UserRegister.this, "Number", Number);
                                    Common.setPreferenceString(UserRegister.this, "Firmname", Firmname);
                                    Common.setPreferenceString(UserRegister.this, "VirtualACCode", VirtualACCode);


                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            } catch (Exception e) {
                Log.e("re_response", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE_GST:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.e("urrrrr", "" + uri);
                    Log.e("File Uri:", uri.toString());
                    // Get the path

                    String uriString = uri.toString();
                    Log.d("data", "onActivityResult: uri" + uriString);
                    //            myFile = new File(uriString);
                    //            ret = myFile.getAbsolutePath();
                    //Fpath.setText(ret);
                    try {
                        InputStream in = getContentResolver().openInputStream(uri);
                        bytes = getBytes(in);
                        Log.d("data", "onActivityResult: bytes size=" + bytes.length);
                        Log.d("data", "onActivityResult: Base64string=" + Base64.encodeToString(bytes, Base64.DEFAULT));
                        String ansValue = Base64.encodeToString(bytes, Base64.DEFAULT);
                        BiTGSt = Base64.encodeToString(bytes, Base64.DEFAULT);

                        Log.e("Document", BiTGSt);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                        Log.d("error", "onActivityResult: " + e.toString());
                    }


                }
                break;
            case FILE_SELECT_CODE_PANCarD: {
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.e("urrrrr", "" + uri);
                    Log.e("File Uri:", uri.toString());
                    // Get the path

                    String uriString = uri.toString();
                    Log.d("data", "onActivityResult: uri" + uriString);
                    //            myFile = new File(uriString);
                    //            ret = myFile.getAbsolutePath();
                    //Fpath.setText(ret);
                    try {
                        InputStream in = getContentResolver().openInputStream(uri);
                        bytes = getBytes(in);
                        Log.d("data", "onActivityResult: bytes size=" + bytes.length);
                        Log.d("data", "onActivityResult: Base64string=" + Base64.encodeToString(bytes, Base64.DEFAULT));
                        String ansValue = Base64.encodeToString(bytes, Base64.DEFAULT);
                        BiTPanCart = Base64.encodeToString(bytes, Base64.DEFAULT);

                        Log.e("Document", BiTPanCart);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                        Log.d("error", "onActivityResult: " + e.toString());
                    }


                }

            }

            case FILE_SELECT_CODE_TRAdeLinces: {
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.e("urrrrr", "" + uri);
                    Log.e("File Uri:", uri.toString());
                    // Get the path

                    String uriString = uri.toString();
                    Log.d("data", "onActivityResult: uri" + uriString);
                    //            myFile = new File(uriString);
                    //            ret = myFile.getAbsolutePath();
                    //Fpath.setText(ret);
                    try {
                        InputStream in = getContentResolver().openInputStream(uri);
                        bytes = getBytes(in);
                        Log.d("data", "onActivityResult: bytes size=" + bytes.length);
                        Log.d("data", "onActivityResult: Base64string=" + Base64.encodeToString(bytes, Base64.DEFAULT));
                        String ansValue = Base64.encodeToString(bytes, Base64.DEFAULT);
                        BiTLincese = Base64.encodeToString(bytes, Base64.DEFAULT);

                        Log.e("Document", BiTLincese);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                        Log.d("error", "onActivityResult: " + e.toString());
                    }


                }

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
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

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
