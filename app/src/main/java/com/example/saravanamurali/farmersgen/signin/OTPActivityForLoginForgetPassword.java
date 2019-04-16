package com.example.saravanamurali.farmersgen.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.fragment.NewPassAndConfirmPass;
import com.example.saravanamurali.farmersgen.models.JSONOTPResponseFromOTPActivity;
import com.example.saravanamurali.farmersgen.models.JSONResponseToSendOTPFromForgetPasswordDTO;
import com.example.saravanamurali.farmersgen.models.OTPSendToMobileDTOFrom_FP;
import com.example.saravanamurali.farmersgen.models.OTPandMobileNoDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendMobileNoAndOTPForLoginForgetPassword;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendOTPToMFrom_FP;
import com.example.saravanamurali.farmersgen.signup.OTPActivity;
import com.example.saravanamurali.farmersgen.signup.OTPActivityForViewCart2;
import com.goodiebag.pinview.Pinview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivityForLoginForgetPassword extends AppCompatActivity {

    //Mobile Number from login Forget Password
    String mobileNumberForLoginForgetPassword;

    public Pinview pinview_AtLoginForgetPassword;
    private Button otpButton_AtLoginForgetPassword;
    private TextView mobileShow_ForgetPassword;

    private TextView timeShow_ForgetPassword;
    private TextView resendClick_ForgetPassword;


    private TextView errorText_AtLoginForgetPassword;
    String entered_OTP_AtLoginForgetPassword;

    long ms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfor_login_forget_password);

        Intent intent = getIntent();

         mobileNumberForLoginForgetPassword=intent.getStringExtra("MOBILENO_FOR_LOGIN_FORGET_PASSWORD");

        pinview_AtLoginForgetPassword = (Pinview) findViewById(R.id.otpPinViewForLoginForgetPassword);
        otpButton_AtLoginForgetPassword = (Button) findViewById(R.id.otpSubmitForLoginForgetPassword);
        errorText_AtLoginForgetPassword = (TextView) findViewById(R.id.otpErrorForLoginForgetPassword);

        mobileShow_ForgetPassword=(TextView)findViewById(R.id.otp_MobileNumber_AtForgetPassword);


        mobileShow_ForgetPassword.setText(mobileNumberForLoginForgetPassword);


        timeShow_ForgetPassword=(TextView)findViewById(R.id.timeShower_ForgetPassword);
        resendClick_ForgetPassword=(TextView)findViewById(R.id.reSend_ForgetPassword);

        getOTP();

        countDownTimerAtForgetPassword();



        otpButton_AtLoginForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(entered_OTP_AtLoginForgetPassword)) {
                    sendMobileNoandOTPForLoginForgetPassword();
                } else {
                    Toast.makeText(OTPActivityForLoginForgetPassword.this,"Please enter OTP",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void countDownTimerAtForgetPassword() {

        CountDownTimer countDownTimer=new CountDownTimer(120*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                ms=millisUntilFinished;

                timeShow_ForgetPassword.setText(""+millisUntilFinished/1000);


            }

            @Override
            public void onFinish() {
                otpButton_AtLoginForgetPassword.setVisibility(View.INVISIBLE);
                resendClick_ForgetPassword.setVisibility(View.VISIBLE);
                resendClick_ForgetPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        otpButton_AtLoginForgetPassword.setVisibility(View.VISIBLE);
                        resendClick_ForgetPassword.setVisibility(View.VISIBLE);
                        countDownTimerAtForgetPassword();
                        getOTP();

                        sendOTPForResendAtForgetPasswordActivity();



                    }
                });

            }
        }.start();
    }

    private void sendOTPForResendAtForgetPasswordActivity() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActivityForLoginForgetPassword.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api=APIClientToSendOTPToMFrom_FP.getAPIInterfaceTOSendOTPFrom_FP();

        OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_fp_Login=new OTPSendToMobileDTOFrom_FP(mobileNumberForLoginForgetPassword);

        Call<JSONResponseToSendOTPFromForgetPasswordDTO> call= api.getOTPTOForgetPassword(otpSendToMobileDTOFrom_fp_Login);

        call.enqueue(new Callback<JSONResponseToSendOTPFromForgetPasswordDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToSendOTPFromForgetPasswordDTO> call, Response<JSONResponseToSendOTPFromForgetPasswordDTO> response) {
                if(response.isSuccessful()){
                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponseToSendOTPFromForgetPasswordDTO> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });





    }

    private void getOTP() {

        pinview_AtLoginForgetPassword.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                entered_OTP_AtLoginForgetPassword =pinview.getValue();
            }
        });

    }

    private void sendMobileNoandOTPForLoginForgetPassword() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActivityForLoginForgetPassword.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api=APIClientToSendMobileNoAndOTPForLoginForgetPassword.getApiInterfaceToSendMobileNoAndOTPForLoginForgetPassword();

        OTPandMobileNoDTO otpAndMobileNoDTO = new OTPandMobileNoDTO(mobileNumberForLoginForgetPassword, entered_OTP_AtLoginForgetPassword);

        Call<JSONOTPResponseFromOTPActivity> call=api.sendMobileNoandOTPFromLoginForgetPasswordActivity(otpAndMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {

               /* if(response.isSuccessful()){

                    JSONOTPResponseFromOTPActivity jsonotpResponseFromOTPActivity = response.body();
*/
                    if(response.isSuccessful())  {

                        if(csprogress.isShowing()){
                            csprogress.dismiss();
                        }

                        //(jsonotpResponseFromOTPActivity.getStatus()=="200")
                        Intent newPassintent = new Intent(OTPActivityForLoginForgetPassword.this, NewPassAndConfirmPassForLoginForgetPassword.class);
                        newPassintent.putExtra("MOBILENO_FOR_NEW_PASS_AND_CONFIRM_PASSWORD", mobileNumberForLoginForgetPassword);
                        startActivity(newPassintent);
                        finish();
                    }
                    else  {
                        //if (jsonotpResponseFromOTPActivity.getStatus()=="500")

                        //Resend OTP

                        //sendMobileNoandOTPForLoginForgetPassword();

                        Toast.makeText(OTPActivityForLoginForgetPassword.this,"500 Eror",Toast.LENGTH_LONG).show();

                    }
                }


            @Override
            public void onFailure(Call<JSONOTPResponseFromOTPActivity> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });


    }
}
