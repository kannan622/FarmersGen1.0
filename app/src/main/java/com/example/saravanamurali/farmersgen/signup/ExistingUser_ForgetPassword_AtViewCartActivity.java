package com.example.saravanamurali.farmersgen.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.fragment.ViewCartActivity;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToSendOTPFromForgetPasswordDTO;
import com.example.saravanamurali.farmersgen.models.OTPSendToMobileDTOFrom_FP;
import com.example.saravanamurali.farmersgen.models.SignInDTO;
import com.example.saravanamurali.farmersgen.models.SignedInJSONResponse;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToLogin;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendOTPToMFrom_FP;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExistingUser_ForgetPassword_AtViewCartActivity extends AppCompatActivity {
    private TextInputLayout password_ViewCart;

    private TextView I_forgotPasswordTextAtViewCart;
    private Button nextViewCart;

    String confirmPassword_AtViewCartActivity = "";


    String forgetPasswordMobileNumberAtViewCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_user__forget_password__at_view_cart);

        Fabric.with(this, new Crashlytics());

        Intent intent = getIntent();

        forgetPasswordMobileNumberAtViewCart = intent.getStringExtra("EXISTING_USER_MOBILENO_AT_VIEWCART");

        password_ViewCart = (TextInputLayout) findViewById(R.id.forgotPassword_AtViewCartActivity);

        I_forgotPasswordTextAtViewCart = (TextView) findViewById(R.id.forgotMyPassword_AtiviewCart);
        nextViewCart = (Button) findViewById(R.id.nextButton_AtViewCart);


    }

    public void I_Forget_My_Password_At_ViewCart(View view) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ExistingUser_ForgetPassword_AtViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.setCancelable(false);
        csprogress.setCanceledOnTouchOutside(false);
        csprogress.show();


        ApiInterface api=APIClientToSendOTPToMFrom_FP.getAPIInterfaceTOSendOTPFrom_FP();

        OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_fp=new OTPSendToMobileDTOFrom_FP(forgetPasswordMobileNumberAtViewCart);

        Call<JSONResponseToSendOTPFromForgetPasswordDTO> call= api.getOTPTOForgetPassword(otpSendToMobileDTOFrom_fp);

        call.enqueue(new Callback<JSONResponseToSendOTPFromForgetPasswordDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToSendOTPFromForgetPasswordDTO> call, Response<JSONResponseToSendOTPFromForgetPasswordDTO> response) {
                if(response.isSuccessful()){

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }
                    Toast.makeText(ExistingUser_ForgetPassword_AtViewCartActivity.this,"i forget my password Clicked",Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(ExistingUser_ForgetPassword_AtViewCartActivity.this,ExistingUser_ForgetPassword_OTP_AtViewCartActivity.class);
                    intent.putExtra("MOBILE_NO_TO_CONFIRM_PASSWORD_FOR_EXISTING_USER",forgetPasswordMobileNumberAtViewCart);
                    startActivity(intent);
                    finish();
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

    public void forgetPassword_AtViewCart(View view) {

        if (!validatePassword_AtViewCart()) {
            return;
        } else {

            login_ViewCartActivity();


        }
    }

    private void login_ViewCartActivity() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ExistingUser_ForgetPassword_AtViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.setCancelable(false);
        csprogress.setCanceledOnTouchOutside(false);
        csprogress.show();


        ApiInterface api = APIClientToLogin.getApiInterfaceToLogin();

        SignInDTO signInDTO = new SignInDTO(forgetPasswordMobileNumberAtViewCart, confirmPassword_AtViewCartActivity);

        Call<SignedInJSONResponse> call = api.getLoginUser(signInDTO);

        call.enqueue(new Callback<SignedInJSONResponse>() {
            @Override
            public void onResponse(Call<SignedInJSONResponse> call, Response<SignedInJSONResponse> response) {

                if (response.isSuccessful()) {

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    SignedInJSONResponse signedInJSONResponse = response.body();


                    if (signedInJSONResponse.getUser_ID() != null) {


                        SharedPreferences current_User = getSharedPreferences("CURRENT_USER", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = current_User.edit();
                        editor.putString("CURRENTUSER", signedInJSONResponse.getUser_ID());
                        editor.commit();


                        Intent viewCartActivity = new Intent(ExistingUser_ForgetPassword_AtViewCartActivity.this, ViewCartActivity.class);
                        viewCartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(viewCartActivity);
                        finish();
                    }

                }
            }

            @Override
            public void onFailure(Call<SignedInJSONResponse> call, Throwable t) {
                Toast.makeText(ExistingUser_ForgetPassword_AtViewCartActivity.this, "You have Entered Wrong Password", Toast.LENGTH_LONG).show();

            }
        });

    }

    private boolean validatePassword_AtViewCart() {
        boolean status = false;
        String m_Password = password_ViewCart.getEditText().getText().toString().trim();

        if (m_Password.isEmpty()) {
            password_ViewCart.setError("Password Field Cant be Empty");
            status = false;
        } else if (m_Password.length() <= 4) {
            password_ViewCart.setError("Please Enter More than 4 character");
            status = false;
        } else if (!m_Password.isEmpty() && m_Password.length() >= 5) {
            password_ViewCart.setError("");
            status = true;

            confirmPassword_AtViewCartActivity = m_Password;

        }
        return status;
    }

}
