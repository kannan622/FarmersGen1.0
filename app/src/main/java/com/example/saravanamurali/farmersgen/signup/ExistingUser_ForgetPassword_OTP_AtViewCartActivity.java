package com.example.saravanamurali.farmersgen.signup;

import android.content.Intent;
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
import com.example.saravanamurali.farmersgen.models.OTPandMobileNoDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendMobileNoAndOTP;
import com.goodiebag.pinview.Pinview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExistingUser_ForgetPassword_OTP_AtViewCartActivity extends AppCompatActivity {

    public Pinview pinview_OTP;
    private Button otpButton_OTP;
    private TextView errorText_OTP;

    String entered_OTP_AtViewCart;

    String MobileNumberFrom_Existing_User_Forget_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_user__forget_password__otp__at_view_cart);

        Intent getMobileNumber=getIntent();
        MobileNumberFrom_Existing_User_Forget_Password=getMobileNumber.getStringExtra("MOBILE_NO_TO_CONFIRM_PASSWORD_FOR_EXISTING_USER");

        pinview_OTP = (Pinview) findViewById(R.id.existing_User_otpPinView_AtViewCart);
        otpButton_OTP=(Button)findViewById(R.id.existingUser_otpSubmit_ViewCart);
        errorText_OTP=(TextView)findViewById(R.id.existing_User_otpErroratVCart);

        pinview_OTP.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {

                entered_OTP_AtViewCart=pinview_OTP.getValue();

            }
        });

        otpButton_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(entered_OTP_AtViewCart)) {
                    sendMobileNoandOTPForConfirmPassword();
                } else {
                    Toast.makeText(ExistingUser_ForgetPassword_OTP_AtViewCartActivity.this,"Please enter OTP",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void sendMobileNoandOTPForConfirmPassword() {

        ApiInterface api = APIClientToSendMobileNoAndOTP.getApiInterfaceToSendMobileNoAndOTP();


        OTPandMobileNoDTO otPandMobileNoDTO = new OTPandMobileNoDTO(MobileNumberFrom_Existing_User_Forget_Password, entered_OTP_AtViewCart);

        Call<JSONOTPResponseFromOTPActivity> call = api.sendMobileNoandOTPFromOTPActivity(otPandMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(ExistingUser_ForgetPassword_OTP_AtViewCartActivity.this,"Here",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ExistingUser_ForgetPassword_OTP_AtViewCartActivity.this, Existing_User_NewPassAndConfirmPass_AtViewCartActivity.class);
                    intent.putExtra("MOBILENO_FOR_NEW_AND_CONFIRM_PASSWORD", MobileNumberFrom_Existing_User_Forget_Password);
                    startActivity(intent);
                } else {
                    // if (jsonotpResponseFromOTPActivity.getStatus() == 500) {

                    //Resend OTP

                }
            }




            @Override
            public void onFailure(Call<JSONOTPResponseFromOTPActivity> call, Throwable t) {

            }
        });

    }


}
