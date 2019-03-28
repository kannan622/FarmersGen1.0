package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ApplyCouponDTO {

    @SerializedName("")
    String user_ID;
    @SerializedName("")
    String coupon_Code;

    @SerializedName("")
    String coupon_ID;

    public ApplyCouponDTO(String user_ID, String coupon_Code, String coupon_ID) {
        this.user_ID = user_ID;
        this.coupon_Code = coupon_Code;
        this.coupon_ID = coupon_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public void setCoupon_Code(String coupon_Code) {
        this.coupon_Code = coupon_Code;
    }

    public void setCoupon_ID(String coupon_ID) {
        this.coupon_ID = coupon_ID;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public String getCoupon_Code() {
        return coupon_Code;
    }

    public String getCoupon_ID() {
        return coupon_ID;
    }
}
