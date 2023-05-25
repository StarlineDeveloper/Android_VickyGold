package com.VijayAssayCenter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
/*import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;*/
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.VijayAssayCenter.Constant;
import com.VijayAssayCenter.Fragmet.BankInfo;
import com.VijayAssayCenter.Fragmet.BullionRate;
import com.VijayAssayCenter.Fragmet.CoinOrdes;
import com.VijayAssayCenter.Fragmet.CoinRate;
import com.VijayAssayCenter.Fragmet.YourOrder;
import com.VijayAssayCenter.NetworkChangeReceiver;
import com.VijayAssayCenter.R;
import com.VijayAssayCenter.adapter.NavigationDrawerAdapter;
import com.VijayAssayCenter.utils.BaseActivity;
import com.VijayAssayCenter.utils.Common;
import com.VijayAssayCenter.utils.Constants;
import com.VijayAssayCenter.utils.ScrollTextView;
import com.VijayAssayCenter.utils.ScrollTextViewTwo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class MainActivity extends BaseActivity {


    FrameLayout ivDrawer;
    ListView drawerListView;

    int loginid = 0;

    ImageView iv_menu;
    String Firmname = "vickygold";
    int RemainGold = 0;
    int RemainSilver = 0;


    io.socket.client.Socket mSocket;

    String regresponse = "";

    private static final String URLNew = "https://vickygold.co.in:10001";


    DrawerLayout drawer_layout;

    public static Activity activity;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View listHeaderView;
    private TextView tvMobileNo;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SessionManager sessionManager;

    LinearLayout lay_profile_page;
    LinearLayout lay_order_limit;
    LinearLayout lay_bank_details;
    LinearLayout lay_delevry_location;
    LinearLayout lay_contact_us;
    LinearLayout lay_update_news;
    LinearLayout lay_tutarials;
    TextView lay_log_out;
    LinearLayout lay_update_profile;
    LinearLayout lay_ll;
    LinearLayout ll_marqee22;

    private ScrollTextView tvMarquee;
    private ScrollTextViewTwo tvMarquee22;
    private ScrollTextView tvMarqueeGlod;
    private ScrollTextView tvMarqueeSlider;
    private ScrollTextView tvMarquee3;

    TextView tv_no_1;
    TextView tv_no_2;
    TextView tv_no_3;
    TextView tv_no_4;
    TextView tv_no_5;
    TextView tv_no_6;
    TextView tv_no_7;
    AlertDialog dialogcommna;

    String whatsapp_no1 = "";
    private BroadcastReceiver mNetworkReceiver;


    ImageView iv_go_whatsapp;
    TextView tv_looo;
    private LinearLayout txt_no_internet;

    String UserName = "";
    String Name = "";
    String FrimName = "";

    TextView tv_user_name;
    TextView frim_name_display;
    String FristTimeMobile = "";
    LinearLayout ll_marqee_Top;
    LinearLayout lay_abouts;
    LinearLayout buy_user;
    String ObjVariable = "";
    String Fromdate = "";
    String Todate = "";
    private int[] tabsIcon = {R.drawable.b2, R.drawable.b3,
            R.drawable.b4, R.drawable.b3, R.drawable.b1};

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ivDrawer = (FrameLayout) findViewById(R.id.ivDrawer);
        drawerListView = (ListView) findViewById(R.id.drawerListView);
        initDrawer();


        txt_no_internet = findViewById(R.id.txt_no_internet);
        sessionManager = new SessionManager(MainActivity.this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(Constants.page);
        //Log.e("Token",FirebaseInstanceId.getInstance().getToken());


        tvMarquee = findViewById(R.id.tvMarquee);
        tvMarquee22 = findViewById(R.id.tvMarquee22);
        tvMarqueeGlod = findViewById(R.id.tvMarqueeGlod);
        tvMarqueeSlider = findViewById(R.id.tvMarqueeSlider);
        tvMarquee3 = findViewById(R.id.tvMarquee3);
        buy_user = findViewById(R.id.buy_user);


        tvMarquee3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String UserName = Common.getPreferenceString(MainActivity.this, "UserName", "");
                Log.e("UserName", UserName);
                if (UserName.equals("")) {
                    Intent TreadeInsert = new Intent(activity, Login.class);
                    TreadeInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(TreadeInsert);
                } else {
                    Constants.Lable = "Edit Profile";
                    Constants.LoginScess = "";
                    Intent PaymentBankDetails = new Intent(MainActivity.this, UserRegister.class);
                    PaymentBankDetails.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(PaymentBankDetails);
                }
            }
        });

        tv_user_name = findViewById(R.id.tv_user_name);
        frim_name_display = findViewById(R.id.frim_name_display);

        ll_marqee_Top = findViewById(R.id.ll_marqee_Top);

        UserName = Common.getPreferenceString(MainActivity.this, "UserName", "");
        Name = Common.getPreferenceString(MainActivity.this, "DisplayName", "");
        FristTimeMobile = Common.getPreferenceString(MainActivity.this, "FristTimeMobile", "");
        FrimName = Common.getPreferenceString(MainActivity.this, "Firmname", "");
        if (UserName.equals("") && FristTimeMobile.equals("")) {
            tv_user_name.setText("Welcome Guest");
            frim_name_display.setText("Welcome Guest");

            tvMarqueeGlod.setText("" + "\"Gold Booking: XXX gms\"" + "");
            tvMarqueeSlider.setText("" + "\"Silver Booking: XXX kgs\"" + "");
            tvMarquee3.setText("\"Hi, Please sign in\"");

        } else {
            if (UserName.equals("")) {
                tv_user_name.setText(FristTimeMobile);
                frim_name_display.setText("");

                tvMarquee3.setText("\"Hi, Please sign in\"");
            } else {
                tv_user_name.setText(Name);
                frim_name_display.setText(FrimName);
                tvMarquee3.setText("Hi, " + Name + " Ji");


                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String formattedDate = df.format(c);

                Todate = df.format(c);
                Fromdate = df.format(c);
                loginid = Integer.parseInt(Common.getPreferenceString(getActivity(), "LoginId", ""));


                ObjVariable = "{" +
                        "ClientID:1," +
                        "\"Firmname\":\"" + Firmname + "\"," +
                        "\"Fromdate\":\"" + Fromdate + "\"," +
                        "\"Todate\":\"" + Todate + "\"," +
                        "loginid:" + loginid + "}";
                Log.e("Objest", ObjVariable);


                new GetOpenOrderDetails().execute();

            }
        }


        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_go_whatsapp = findViewById(R.id.iv_go_whatsapp);
        ivDrawer = findViewById(R.id.ivDrawer);


//        if (!sessionManager.getPrefrence(Constant.SHOWCASE, "").equals("true")) {
//            new
//
//                    TapTargetSequence((Activity) this)
//                    .targets(TapTarget.forView(ivDrawer, "Side Menu", "Tab here to open side menu.")
//                                    .outerCircleColor(R.color.blacknormal)
//                                    .tintTarget(false)
//                                    .cancelable(false),
//
//
//                            TapTarget.forView(iv_go_whatsapp, "WhatsApp chat", "Tap here to open WhatsApp chat.")
//                                    .outerCircleColor(R.color.semi_transparent)
//                                    .targetCircleColor(R.color.white)
//                                    .descriptionTextColor(R.color.white)
//                                    .titleTextColor(R.color.white)
//                                    .tintTarget(false)
//                                    .cancelable(false),
//
//                            TapTarget.forView(iv_menu, "Touch to Call", "Tab here to open list of Booking Number's.")
//                                    .outerCircleColor(R.color.dark_gray)
//                                    .targetCircleColor(R.color.white)
//                                    .descriptionTextColor(R.color.white)
//                                    .titleTextColor(R.color.white)
//                                    .tintTarget(false)
//                                    .cancelable(false)
//
//                    ).listener(new TapTargetSequence.Listener() {
//
//
//
//                @Override
//                public void onSequenceFinish() {
//                    sessionManager.setPrefrences(Constant.SHOWCASE, "true");
//                }
//
//                @Override
//                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
//                }
//
//                @Override
//                public void onSequenceCanceled(TapTarget lastTarget) {
//                }
//            }).start();
//        }


//        viewPager.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                return true;
//            }
//        });
//


        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
//        dynamicSetTabLayoutMode(tabLayout);
//        setupTabIcons();
        tabLayout.getTabAt(0).setIcon(R.drawable.b2);
        tabLayout.getTabAt(1).setIcon(R.drawable.b3);
        tabLayout.getTabAt(2).setIcon(R.drawable.b4);
        tabLayout.getTabAt(3).setIcon(R.drawable.b3);
        tabLayout.getTabAt(4).setIcon(R.drawable.b1);

        tabLayout.getTabAt(0).setText("Bullion Rate");
        tabLayout.getTabAt(1).setText("Your Order");
        tabLayout.getTabAt(2).setText("Coin Rate");
        tabLayout.getTabAt(3).setText("Coin Orders");
        tabLayout.getTabAt(4).setText("Bank Info");

        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(4).getIcon().setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);


                ObjVariable = "{" +
                        "ClientID:1," +
                        "\"Firmname\":\"" + Firmname + "\"," +
                        "\"Fromdate\":\"" + Fromdate + "\"," +
                        "\"Todate\":\"" + Todate + "\"," +
                        "loginid:" + loginid + "}";
                Log.e("Objest", ObjVariable);


                new GetOpenOrderDetails().execute();
                if (tab.getPosition() == 0) {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        tv_looo = findViewById(R.id.tv_looo);
//        tv_looo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        iv_go_whatsapp = findViewById(R.id.iv_go_whatsapp);
        iv_go_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri mUri = Uri.parse("https://api.whatsapp.com/send?phone="+whatsapp_no1+"&text=''");
//                Intent intent = new Intent("android.intent.action.VIEW", mUri);
//                intent.setPackage("com.whatsapp");
//                startActivity(intent);


                Log.e("whatsapp_no1", whatsapp_no1);
                //String url = "https://api.whatsapp.com/send?phone=" +whatsapp_no1;

                try {
                    String msgurl = "https://api.whatsapp.com/send?phone=" + whatsapp_no1 + "&text=";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(msgurl));
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                }

//                String url = "https://api.whatsapp.com/send?phone=" + whatsapp_no1 + "&text=";
//                try {
//                    PackageManager pm = MainActivity.this.getPackageManager();
//                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    startActivity(i);
//                } catch (PackageManager.NameNotFoundException e) {
//                    Toast.makeText(MainActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
            }
        });

//         setupTabIcons();
        GetInfoClient();


        mNetworkReceiver = new NetworkChangeReceiver();


        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.contact_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    tv_no_1 = dialog.findViewById(R.id.tv_no_1);
                    tv_no_2 = dialog.findViewById(R.id.tv_no_2);
                    tv_no_3 = dialog.findViewById(R.id.tv_no_3);
                    tv_no_4 = dialog.findViewById(R.id.tv_no_4);
                    tv_no_5 = dialog.findViewById(R.id.tv_no_5);
                    tv_no_6 = dialog.findViewById(R.id.tv_no_6);
                    tv_no_7 = dialog.findViewById(R.id.tv_no_7);

                    final String BookingNo1 = Common.getPreferenceString(MainActivity.this, "BookingNo1", "");
                    final String BookingNo2 = Common.getPreferenceString(MainActivity.this, "BookingNo2", "");
                    final String BookingNo3 = Common.getPreferenceString(MainActivity.this, "BookingNo3", "");
                    final String BookingNo4 = Common.getPreferenceString(MainActivity.this, "BookingNo4", "");
                    final String BookingNo5 = Common.getPreferenceString(MainActivity.this, "BookingNo5", "");
                    final String BookingNo6 = Common.getPreferenceString(MainActivity.this, "BookingNo6", "");
                    final String BookingNo7 = Common.getPreferenceString(MainActivity.this, "BookingNo7", "");

                    if (BookingNo1.equals("") || BookingNo1.equals("null")) {
                        tv_no_1.setVisibility(View.GONE);
                    } else {
                        tv_no_1.setVisibility(View.VISIBLE);

                    }

                    if (BookingNo2.equals("") || BookingNo2.equals("null")) {
                        tv_no_2.setVisibility(View.GONE);
                    } else {
                        tv_no_2.setVisibility(View.VISIBLE);

                    }

                    if (BookingNo3.equals("") || BookingNo3.equals("null")) {
                        tv_no_3.setVisibility(View.GONE);
                    } else {
                        tv_no_3.setVisibility(View.VISIBLE);

                    }

                    if (BookingNo4.equals("") || BookingNo4.equals("null")) {
                        tv_no_4.setVisibility(View.GONE);
                    } else {
                        tv_no_4.setVisibility(View.VISIBLE);

                    }

                    if (BookingNo5.equals("") || BookingNo5.equals("null")) {
                        tv_no_5.setVisibility(View.GONE);
                    } else {
                        tv_no_5.setVisibility(View.VISIBLE);

                    }

                    if (BookingNo6.equals("") || BookingNo6.equals("null")) {
                        tv_no_6.setVisibility(View.GONE);
                    } else {
                        tv_no_6.setVisibility(View.VISIBLE);

                    }

                    if (BookingNo7.equals("") || BookingNo7.equals("null")) {
                        tv_no_7.setVisibility(View.GONE);
                    } else {
                        tv_no_7.setVisibility(View.VISIBLE);

                    }


                    tv_no_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            call(MainActivity.this, BookingNo1.replaceAll("[^0-9+]", ""));
                            call(MainActivity.this,splitContactNumber(BookingNo1));

                        }
                    });


                    tv_no_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            call(MainActivity.this, BookingNo2.replaceAll("[^0-9+]", ""));
                            call(MainActivity.this,splitContactNumber(BookingNo2));
                        }
                    });

                    tv_no_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            call(MainActivity.this, BookingNo3.replaceAll("[^0-9+]", ""));
                            call(MainActivity.this,splitContactNumber(BookingNo3));
                        }
                    });

                    tv_no_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            call(MainActivity.this, BookingNo4.replaceAll("[^0-9+]", ""));
                            call(MainActivity.this,splitContactNumber(BookingNo4));

                        }
                    });

                    tv_no_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            call(MainActivity.this, BookingNo5.replaceAll("[^0-9+]", ""));
                            call(MainActivity.this,splitContactNumber(BookingNo5));
                        }
                    });

                    tv_no_6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            call(MainActivity.this, BookingNo6.replaceAll("[^0-9+]", ""));
                            call(MainActivity.this,splitContactNumber(BookingNo6));
                        }
                    });

                    tv_no_7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            call(MainActivity.this, BookingNo7.replaceAll("[^0-9+]", ""));
                            call(MainActivity.this,splitContactNumber(BookingNo7));
                        }
                    });

                    tv_no_1.setText(BookingNo1);
                    tv_no_2.setText(BookingNo2);
                    tv_no_3.setText(BookingNo3);
                    tv_no_4.setText(BookingNo4);
                    tv_no_5.setText(BookingNo5);
                    tv_no_6.setText(BookingNo6);
                    tv_no_7.setText(BookingNo7);


                    LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.layout);
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                    Window window = dialog.getWindow();
                    window.setGravity(Gravity.CENTER);
                    dialog.setCanceledOnTouchOutside(true);

                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        lay_abouts = findViewById(R.id.lay_abouts);
        lay_abouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent PaymentBankDetails = new Intent(MainActivity.this, Aboust.class);
                PaymentBankDetails.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(PaymentBankDetails);
                drawer_layout.closeDrawers();

            }
        });

        lay_profile_page = (LinearLayout) findViewById(R.id.lay_profile_page);
        lay_profile_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeDrawers();
            }
        });

       /* lay_tutarials=findViewById(R.id.lay_tutarials);
        lay_tutarials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();

                Intent PaymentBankDetails=new Intent(MainActivity.this,TutarilsNew.class);
                PaymentBankDetails.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(PaymentBankDetails);
            }
        });
*/

        lay_order_limit = (LinearLayout) findViewById(R.id.lay_order_limit);
        lay_order_limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeDrawers();

                viewPager.setCurrentItem(0);


                /*Intent ModifyOrderLimit=new Intent(MainActivity.this,ModifyOrderLimit.class);
                ModifyOrderLimit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ModifyOrderLimit);*/
            }
        });
        lay_bank_details = (LinearLayout) findViewById(R.id.lay_bank_details);
        lay_bank_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeDrawers();

                Intent PaymentBankDetails = new Intent(MainActivity.this, PaymentBankDetails.class);
                PaymentBankDetails.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(PaymentBankDetails);
            }
        });

        lay_ll = findViewById(R.id.lay_ll);
        ll_marqee22 = findViewById(R.id.ll_marqee22);
        lay_update_profile = findViewById(R.id.lay_update_profile);
        lay_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();

                final String UserName = Common.getPreferenceString(MainActivity.this, "UserName", "");
                Log.e("UserName", UserName);
                if (UserName.equals("")) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Vicky Jewellery Works")
                            .setMessage("Please login.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })
                            .show();
                } else {
                    Constants.Lable = "Edit Profile";
                    Constants.LoginScess = "";
                    Intent PaymentBankDetails = new Intent(MainActivity.this, UserRegister.class);
                    PaymentBankDetails.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(PaymentBankDetails);
                }

            }
        });


        lay_delevry_location = (LinearLayout) findViewById(R.id.lay_delevry_location);
        lay_delevry_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeDrawers();


                Intent MetalDeliveryLocation = new Intent(MainActivity.this, MetalDeliveryLocation.class);
                MetalDeliveryLocation.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(MetalDeliveryLocation);
            }
        });

        lay_update_news = findViewById(R.id.lay_update_news);
        lay_update_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeDrawers();

                Intent UpdateNews = new Intent(MainActivity.this, UpdateNews.class);
                UpdateNews.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(UpdateNews);
            }
        });

        lay_contact_us = (LinearLayout) findViewById(R.id.lay_contact_us);
        lay_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer_layout.closeDrawers();


                Intent CONTACTUS = new Intent(MainActivity.this, CONTACTUS.class);
                CONTACTUS.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(CONTACTUS);
            }
        });

        lay_log_out = (TextView) findViewById(R.id.lay_log_out);


        final String UserName = Common.getPreferenceString(MainActivity.this, "UserName", "");
        Log.e("UserName", UserName);
        if (UserName.equals("")) {
            lay_log_out.setText("Sign In");
        } else {
            lay_log_out.setText("Sign Out");

        }
        lay_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserName.equals("")) {
                    drawer_layout.closeDrawers();
                    Intent TreadeInsert = new Intent(MainActivity.this, Login.class);
                    TreadeInsert.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(TreadeInsert);
                } else {
                    drawer_layout.closeDrawers();
                    Logout();

                }

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BullionRate(), "Bullion Rate");
        adapter.addFragment(new YourOrder(), "Your Order");
        adapter.addFragment(new CoinRate(), "Coin Rate");
        adapter.addFragment(new CoinOrdes(), "Coin Orders");
        adapter.addFragment(new BankInfo(), "Bank Info");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);


    }

    private class GetOpenOrderDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            SoapObject request = new SoapObject("http://tempuri.org/", Constants.GetOpenOrderDetails);
            request.addProperty("Obj", ObjVariable);
            Log.e("request", request.toString());
            Log.e("ObjVariable", ObjVariable);
            try {
                regresponse = loadServiceTerminal(request, Constants.GetOpenOrderDetails);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }

            return regresponse;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            try {
                //Log.e("orderresponce222", s);


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        final JSONObject json;
                        try {
                            json = new JSONObject(s);
                            Constants.OpenOrder = json.getString("OpenOrder");


                            RemainGold = 0;
                            RemainSilver = 0;
                            double totalBuyGold = 0;
                            double totalBuySilver = 0;

                            JSONArray jArray = new JSONArray(Constants.OpenOrder);
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json_data = jArray.getJSONObject(i);

                                String Sorce = json_data.getString("Source").trim().toLowerCase();
                                String TradeType = json_data.getString("TradeType");
                                double Volume = json_data.getDouble("Volume");

                                Log.e("orderresponce222", Sorce);
                                if (Sorce.equals("gold") || Sorce.equals("goldnext")) {

                                    if (TradeType.equals("2") || TradeType.equals("4") || TradeType.equals("6")) {
                                        totalBuyGold = totalBuyGold - Volume;
                                    } else {
                                        totalBuyGold = totalBuyGold + Volume;
                                    }
//                                    int Valuemeee=json_data.getInt("Volume");
//                                    RemainGold=Valuemeee+RemainGold;
//                                    Log.e("orderresponce222", "fh");
                                } else {

                                    if (TradeType.equals("2") || TradeType.equals("4") || TradeType.equals("6")) {
                                        totalBuySilver = totalBuySilver - Volume;
                                    } else {
                                        totalBuySilver = totalBuySilver + Volume;
                                    }
//                                    int Valuemeee=json_data.getInt("Volume");
//                                    RemainSilver=Valuemeee+RemainSilver;
//                                    Log.e("orderresponce222", "fh");
                                }


                            }
                            DecimalFormat df = new DecimalFormat("#.00");
//                            tvMarqueeGlod.setText("" + "\"Gold Booking: " + df.format(totalBuyGold) + " gms\"" + "");
                            tvMarqueeGlod.setText("" + "\"Gold Booking: " + totalBuyGold + " gms\"" + "");
//                            tvMarqueeSlider.setText("" + "\"Silver Booking: " + df.format(totalBuySilver) + " kgs\"" + "");
                            tvMarqueeSlider.setText("" + "\"Silver Booking: " + totalBuySilver + " kgs\"" + "");

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


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("posss2", "" + position);

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.e("posss", "" + position);

            return mFragmentTitleList.get(position);
        }
    }

    //    private void setupTabIcons() {
//        try {
//
//            final View tabOne = LayoutInflater.from(activity).inflate(R.layout.custom_tab, null);
//            ImageView ivTabOne = tabOne.findViewById(R.id.ivTab);
//            ivTabOne.setImageResource(tabsIcon[0]);
//            TextView tvTab1 = tabOne.findViewById(R.id.tvTab);
////                tvTab1.setTextSize(8);
//            tvTab1.setText(getResources().getString(R.string.BullionRate));
//            Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(tabOne);
//
//            View tabTwo = LayoutInflater.from(activity).inflate(R.layout.custom_tab, null);
//            ImageView ivTabTwo = tabTwo.findViewById(R.id.ivTab);
//            ivTabTwo.setImageResource(tabsIcon[1]);
//            TextView tvTab2 = tabTwo.findViewById(R.id.tvTab);
//            tvTab2.setText(getResources().getString(R.string.YourOrder));
////            tvTab2.setTextSize(8);
//            Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(tabTwo);
//
//            View tabThree = LayoutInflater.from(activity).inflate(R.layout.custom_tab, null);
//            ImageView ivTabThree = tabThree.findViewById(R.id.ivTab);
//            ivTabThree.setImageResource(tabsIcon[2]);
//            TextView tvTab3 = tabThree.findViewById(R.id.tvTab);
//            tvTab3.setText(getResources().getString(R.string.CoinRate));
////            tvTab3.setTextSize(8);
//            Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(tabThree);
//
//            View tabFour = LayoutInflater.from(activity).inflate(R.layout.custom_tab, null);
//            ImageView ivTabFour = tabFour.findViewById(R.id.ivTab);
//            ivTabFour.setImageResource(tabsIcon[3]);
//            TextView tvTab4 = tabFour.findViewById(R.id.tvTab);
//            tvTab4.setText(getResources().getString(R.string.CoinOrders));
////            tvTab4.setTextSize(8);
//            Objects.requireNonNull(tabLayout.getTabAt(3)).setCustomView(tabFour);
//
//
//            View tabFour5 = LayoutInflater.from(activity).inflate(R.layout.custom_tab, null);
//            ImageView ivTabFour5 = tabFour5.findViewById(R.id.ivTab);
//            ivTabFour5.setImageResource(tabsIcon[4]);
//            TextView tvTab5 = tabFour5.findViewById(R.id.tvTab);
//            tvTab5.setText(getResources().getString(R.string.BankInfo));
////            tvTab5.setTextSize(8);
//            Objects.requireNonNull(tabLayout.getTabAt(4)).setCustomView(tabFour5);
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Bullion Rate");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.b2, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Your Order");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.b3, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Coin Rate");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.b4, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Coin Orders");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.b3, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFour4 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour4.setText("Bank Info");
        tabFour4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.b1, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFour4);


    }


    public void initDrawer() {
        LayoutInflater inflater = getLayoutInflater();
        listHeaderView = inflater.inflate(R.layout.nav_header, null, false);


        drawerListView.addHeaderView(listHeaderView);
        drawerListView.setAdapter(new NavigationDrawerAdapter(this));
        ivDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(Gravity.LEFT);

                Log.e("uiokiukj", "Open yess");

                UserName = Common.getPreferenceString(MainActivity.this, "UserName", "");
                Name = Common.getPreferenceString(MainActivity.this, "DisplayName", "");
                FristTimeMobile = Common.getPreferenceString(MainActivity.this, "FristTimeMobile", "");
                FrimName = Common.getPreferenceString(MainActivity.this, "Firmname", "");
                if (UserName.equals("") && FristTimeMobile.equals("")) {
                    tv_user_name.setText("Welcome Guest");
                    frim_name_display.setText("Welcome Guest");

                } else {
                    if (UserName.equals("")) {
                        tv_user_name.setText(FristTimeMobile);
                        frim_name_display.setText("");
                    } else {
                        tv_user_name.setText(Name);
                        frim_name_display.setText(FrimName);
                    }
                }
            }
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawer_layout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFragment(position);
            }
        });
    }

    private void selectItemFragment(int position) {

        switch (position) {
            case 1:

                viewPager.setCurrentItem(0);
                drawer_layout.closeDrawers();
                break;
            case 2:
                viewPager.setCurrentItem(1);
                drawer_layout.closeDrawers();
                break;
            case 3:
                viewPager.setCurrentItem(3);
                drawer_layout.closeDrawers();
                break;

            case 4:
                break;
        }


        drawerListView.setItemChecked(position, true);

//        drawerLayout.closeDrawer(drawerListView);
    }


    public void Logout() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Logout")
                .setMessage("Are you sure want to logout")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Common.setPreferenceString(MainActivity.this, "Access", "");
                        Common.setPreferenceString(MainActivity.this, "AccountID", "");
                        Common.setPreferenceString(MainActivity.this, "Balance", "");
                        Common.setPreferenceString(MainActivity.this, "City", "");
                        Common.setPreferenceString(MainActivity.this, "Email", "");
                        Common.setPreferenceString(MainActivity.this, "GroupID", "");
                        Common.setPreferenceString(MainActivity.this, "GroupName", "");
                        Common.setPreferenceString(MainActivity.this, "GST", "");
                        Common.setPreferenceString(MainActivity.this, "LoginId", "");
                        Common.setPreferenceString(MainActivity.this, "LoginId", "");
                        Common.setPreferenceString(MainActivity.this, "Name", "");
                        Common.setPreferenceString(MainActivity.this, "Number", "");
                        Common.setPreferenceString(MainActivity.this, "password", "");
                        Common.setPreferenceString(MainActivity.this, "Status", "");
                        Common.setPreferenceString(MainActivity.this, "status_code", "");
                        Common.setPreferenceString(MainActivity.this, "UserName", "");
                        Common.setPreferenceString(MainActivity.this, "TokenLogin", "");
                        Common.setPreferenceString(MainActivity.this, "Firmname", "");
                        Common.setPreferenceString(MainActivity.this, "DisplayName", "");
                        SuccesDialog("Success", "Successful");

                        startActivity(getIntent());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();


    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Close Application")
                .setMessage("Are you sure you want to close this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    public void GetInfoClient() {
        try {
            try {
                SSLContext mySSLContext = SSLContext.getInstance("TLS");
                try {
//                    mySSLContext.init(null, trustAllCerts, null);
//                    IO.setDefaultSSLContext(mySSLContext);
//                    //mSocket = IO.socket(URL);
//                    IO.Options opts = new IO.Options();
//
//                    opts.reconnection = true;
//                    opts.reconnectionDelay = 1000;
//                    opts.timeout = 50000;
//
//                    opts.transports = new String[]{WebSocket.NAME};
//                    mSocket = IO.socket(Constants.SocketUrl, opts);
                    mSocket = io.socket.client.IO.socket(Constants.SocketUrl);
                    mSocket.connect();
                    if (mSocket.connected()) {

                        mSocket.emit("Client", Constants.prjName).on("ClientHeaderDetails", new io.socket.emitter.Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                Log.e("HeaderDetailsHome===", "" + args[0]);
                                JSONArray resultArray = null;
                                try {
                                    JSONObject result = null;

                                    result = new JSONObject("" + args[0]);
                                    final String Data = result.getString("Data");


                                    try {
                                        resultArray = new JSONArray(Data);
                                        for (int i = 0; i < resultArray.length(); i++) {
                                            final JSONObject p = (JSONObject) resultArray.get(i);


                                            //Log.e("BullionID====",""+p.getString("BullionID"));
                                            // Log.e("BookingNo1",""+p.getString("BookingNo1"));

                                            String BookingNo1 = p.getString("BookingNo1");
                                            String BookingNo2 = p.getString("BookingNo2");
                                            String BookingNo3 = p.getString("BookingNo3");
                                            String BookingNo4 = p.getString("BookingNo4");
                                            String BookingNo5 = p.getString("BookingNo5");
                                            String BookingNo6 = p.getString("BookingNo6");
                                            String BookingNo7 = p.getString("BookingNo7");
                                            final String Marquee = p.getString("Marquee");
                                            final String Marquee2 = p.getString("Marquee2").trim();


                                            Common.setPreferenceString(MainActivity.this, "Marquee", Marquee);
                                            Common.setPreferenceString(MainActivity.this, "Marquee22", Marquee2);

                                            whatsapp_no1 = p.getString("whatsapp_no1");

                                            Common.setPreferenceString(MainActivity.this, "BookingNo1", BookingNo1);
                                            Common.setPreferenceString(MainActivity.this, "BookingNo2", BookingNo2);
                                            Common.setPreferenceString(MainActivity.this, "BookingNo3", BookingNo3);
                                            Common.setPreferenceString(MainActivity.this, "BookingNo4", BookingNo4);
                                            Common.setPreferenceString(MainActivity.this, "BookingNo5", BookingNo5);
                                            Common.setPreferenceString(MainActivity.this, "BookingNo6", BookingNo6);
                                            Common.setPreferenceString(MainActivity.this, "BookingNo7", BookingNo7);


                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    if (Marquee.equals("null") || Marquee.equals("")) {
                                                        ll_marqee_Top.setVisibility(View.GONE);
                                                    } else {
                                                        ll_marqee_Top.setVisibility(View.VISIBLE);
                                                        tvMarquee.setText(Marquee);
                                                        tvMarquee.startScroll();
                                                    }

                                                    if (Marquee2.equals("null") || Marquee2.equals("")) {
                                                        ll_marqee22.setVisibility(View.GONE);
                                                    } else {
                                                        ll_marqee22.setVisibility(View.VISIBLE);
                                                        tvMarquee22.setText(Marquee2);
                                                        tvMarquee22.setSelected(true);
                                                        tvMarquee22.startScroll();

                                                    }
                                                }
                                            });


                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });


                    }


                    mSocket.on(Socket.EVENT_CONNECT, new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.e("Infoget1==", "Conncet");


                            // Toast.makeText(MetalDeliveryLocation.this, "Connected!!",Toast.LENGTH_LONG).show();

                            mSocket.emit("Client", Constants.prjName).on("ClientHeaderDetails", new io.socket.emitter.Emitter.Listener() {
                                @Override
                                public void call(Object... args) {
                                    Log.e("HeaderDetailsHome===", "" + args[0]);
                                    JSONArray resultArray = null;
                                    try {
                                        JSONObject result = null;

                                        result = new JSONObject("" + args[0]);
                                        final String Data = result.getString("Data");


                                        try {
                                            resultArray = new JSONArray(Data);
                                            for (int i = 0; i < resultArray.length(); i++) {
                                                JSONObject p = (JSONObject) resultArray.get(i);

                                                //Log.e("BullionID====",""+p.getString("BullionID"));
                                                // Log.e("BookingNo1",""+p.getString("BookingNo1"));

                                                String BookingNo1 = p.getString("BookingNo1");
                                                String BookingNo2 = p.getString("BookingNo2");
                                                String BookingNo3 = p.getString("BookingNo3");
                                                String BookingNo4 = p.getString("BookingNo4");
                                                String BookingNo5 = p.getString("BookingNo5");
                                                String BookingNo6 = p.getString("BookingNo6");
                                                String BookingNo7 = p.getString("BookingNo7");
                                                final String Marquee = p.getString("Marquee");
                                                final String Marquee2 = p.getString("Marquee2");
                                                Common.setPreferenceString(MainActivity.this, "Marquee", Marquee);
                                                Constants.LowRate = p.getString("LowRate");
                                                Constants.HighRate = p.getString("HighRate");
                                                final String BannerApp1 = p.getString("BannerApp1");

                                                Log.e("ddddddddddd", "" + BannerApp1);
                                                Log.e("ddddddddddd", "" + Marquee);

                                                whatsapp_no1 = p.getString("whatsapp_no1");

                                                Common.setPreferenceString(MainActivity.this, "BookingNo1", BookingNo1);
                                                Common.setPreferenceString(MainActivity.this, "BookingNo2", BookingNo2);
                                                Common.setPreferenceString(MainActivity.this, "BookingNo3", BookingNo3);
                                                Common.setPreferenceString(MainActivity.this, "BookingNo4", BookingNo4);
                                                Common.setPreferenceString(MainActivity.this, "BookingNo5", BookingNo5);
                                                Common.setPreferenceString(MainActivity.this, "BookingNo6", BookingNo6);
                                                Common.setPreferenceString(MainActivity.this, "BookingNo7", BookingNo7);
                                                Common.setPreferenceString(MainActivity.this, "Marquee", Marquee);
                                                Common.setPreferenceString(MainActivity.this, "Marquee22", Marquee2);

                                                runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        if (Marquee.equals("null") || Marquee.equals("")) {
                                                            ll_marqee_Top.setVisibility(View.GONE);
                                                        } else {
                                                            ll_marqee_Top.setVisibility(View.VISIBLE);
                                                            tvMarquee.setText(Marquee);
                                                            tvMarquee.startScroll();
                                                        }
                                                    }
                                                });

                                                runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        if (Marquee2.equals("null") || Marquee2.equals("")) {
                                                            ll_marqee22.setVisibility(View.GONE);
                                                        } else {
                                                            ll_marqee22.setVisibility(View.VISIBLE);
                                                            tvMarquee22.setText(Marquee2);

                                                            tvMarquee22.setSelected(true);
                                                            tvMarquee22.startScroll();
                                                        }
                                                    }
                                                });

                                                runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        String dialogopen = Common.getPreferenceString(MainActivity.this, "dialogopen", "");
                                                        if (dialogopen.equals("dialogopen")) {
                                                            if (BannerApp1.equals("") || BannerApp1.equals("null")) {

                                                            } else {
                                                                DialogHeader(BannerApp1);
                                                            }
                                                        }
                                                    }
                                                });


                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            });


                        }
                    }).on(Socket.EVENT_CONNECT_ERROR, new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... arg0) {

                            Log.e("Infoget2==", arg0[0].toString());

                        }
                    }).on("something_changed", new io.socket.emitter.Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject obj = (JSONObject) args[0];
                            Log.e("Infoget3==", obj.toString());

                        }
                    });

                    mSocket.connect();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    public static void call(Context context, String phone_no) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phone_no));
        context.startActivity(callIntent);
    }

    public String splitContactNumber(String number) {

        String[] separated = number.split(":");

        number= separated[1];

        return number;
    }

    public void SuccesDialog(String Titile, String Message) {

        LayoutInflater li = LayoutInflater.from(MainActivity.this);

        final View confirmDialog = li.inflate(R.layout.dailog_success, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);


        TextView tv_title = confirmDialog.findViewById(R.id.tv_title);
        TextView tv_message = confirmDialog.findViewById(R.id.tv_message);
        TextView tv_ok = confirmDialog.findViewById(R.id.tv_ok);


        tv_title.setText(Html.fromHtml(Titile));
        tv_message.setText(Html.fromHtml(Message));


        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogcommna.dismiss();

            }
        });

        alert.setView(confirmDialog);

        dialogcommna = alert.create();

        dialogcommna.show();

        dialogcommna.setCanceledOnTouchOutside(true);


    }

//    private class CheckVersiton extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            SoapObject request = new SoapObject("http://tempuri.org/", "GetVersionAndroid");
//            request.addProperty("Obj","{\"ClientId\":2}");
//            Log.e("request", request.toString());
//            try {
//                regresponse = loadService2(request, "GetVersionAndroid");
//            } catch (IOException | XmlPullParserException e) {
//                e.printStackTrace();
//            }
//
//            return regresponse;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            try {
//                Log.e("versionresponce", s);
//                Log.e("CURRENT_VERSION_CODE", ""+CURRENT_VERSION_CODE);
//                play_store_version_code=s.trim();
//
//                try {
//                    if (play_store_version_code != null && !play_store_version_code.isEmpty()) {
//
//
//                        if (CURRENT_VERSION_CODE < Integer.parseInt(play_store_version_code)) {
//
//                            openAppUpdateDialog(MainActivity.this);
//
//
//                        } else {
//
//
//                        }
//                    } else {
//
//                        Log.e("ololo.", "elseee");
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            } catch (Exception e) {
//                Log.e("re_response", e.getMessage());
//                e.printStackTrace();
//            }
//        }
//    }


    public static String loadService2(SoapObject request, String method) throws IOException, XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/WebService.asmx");
        transport.call("http://tempuri.org/" + method, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        return response.toString();
    }

    @SuppressLint("SetTextI18n")
    public static void openAppUpdateDialog(final Activity activity) {
       AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.update_available, null);
        TextView update_txt = (TextView) dialogView.findViewById(R.id.update_txt);
        update_txt.setText("New version is available, please update now for exploring best features of" + " " + activity.getString(R.string.app_name));
        builder.setView(dialogView);
        final AlertDialog alt = builder.create();
        alt.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                activity.finish();
            }
        });
        alt.show();

        dialogView.findViewById(R.id.updateNow).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        alt.dismiss();
                        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
                        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        myAppLinkToMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try {
                            activity.startActivity(myAppLinkToMarket);
                            activity.finish();
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(activity.getApplicationContext(), "unable to find market app", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    BroadcastReceiver internetConnectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                boolean isInternetAvailable = intent.getBooleanExtra("ACTION", false);
                if (isInternetAvailable) {
                    txt_no_internet.setVisibility(View.GONE);

//                    viewPager = (ViewPager) findViewById(R.id.viewpager);
//                    setupViewPager(viewPager);
//                    viewPager.setCurrentItem(Constants.page);
//
//                    setupTabIcons();
//                    GetInfoClient();


                } else {
                    txt_no_internet.setVisibility(View.VISIBLE);
                    // startActivity(new Intent(MainActivity.this, MainActivity.class));


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkBroadcastForNougat();
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(internetConnectionReceiver, new IntentFilter(Constant.FILTER_INTERNET));
    }

    @Override
    protected void onStop() {
        super.onStop();
        registerNetworkBroadcastForNougat();
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(internetConnectionReceiver);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerNetworkBroadcastForNougat();
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(internetConnectionReceiver);
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(internetConnectionReceiver, new IntentFilter(Constant.FILTER_INTERNET));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private Handler handler;
    private Runnable runnable;


    @Override
    protected void onDestroy() {
        try {
            unregisterNetworkChanges();
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @SuppressLint("StaticFieldLeak")
    private void GetImagews() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                showProgress("");
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {

                String bankDetailsResponse = "";
                SoapObject request = new SoapObject("http://tempuri.org/", Constants.BankDetail);
                request.addProperty("ClientId", "1");
                try {
                    bankDetailsResponse = loadService(request, Constants.BankDetail);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return bankDetailsResponse;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {

                    Log.e("bankDetailsRespones", "" + response);


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }.execute();
    }

    public void DialogHeader(final String Url) {

        try {
            final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.full_scrren_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Common.setPreferenceString(MainActivity.this, "dialogopen", "dialogopenNot");


            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog.setCanceledOnTouchOutside(true);

            TextView skip = dialog.findViewById(R.id.skip);
            RelativeLayout rl_popup = dialog.findViewById(R.id.rl_popup);
            ImageView image_view = dialog.findViewById(R.id.image_view);

            RequestOptions options = new RequestOptions()
                    //.placeholder(R.drawable.logo)
                    .error(R.drawable.logo);

            Glide.with(this).load(Url).apply(options).into(image_view);
            rl_popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }};

    public void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0);
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }

    public static String loadService222222(SoapObject request, String method) throws IOException, XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE("https://vickygold.co.in/WebService/Terminal.asmx");
        transport.call("http://tempuri.org/" + method, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        return response.toString();
    }


}
