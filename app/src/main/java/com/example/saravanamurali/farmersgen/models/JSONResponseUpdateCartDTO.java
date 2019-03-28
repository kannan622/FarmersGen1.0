package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class JSONResponseUpdateCartDTO {

    @SerializedName("success")
    success updateSuccess;

    @SerializedName("product_code")
    String updateProductCode;

    @SerializedName("count")
    String updateCount;

    @SerializedName("total_price")
    String updateTotalPrice;

    @SerializedName("device_id")
    String updateDevice_ID;

    @SerializedName("grand_total")
    String grandTotal;


    public class success {

        @SerializedName("responsecode")
        String responseCode;

        @SerializedName("message")
        String message;

        public String getResponseCode() {
            return responseCode;
        }

        public String getMessage() {
            return message;
        }
    }

    public String getGrandTotal() {
        return grandTotal;
    }


    public success getUpdateSuccess() {
        return updateSuccess;
    }

    public String getUpdateProductCode() {
        return updateProductCode;
    }

    public String getUpdateCount() {
        return updateCount;
    }

    public String getUpdateTotalPrice() {
        return updateTotalPrice;
    }

    public String getUpdateDevice_ID() {
        return updateDevice_ID;
    }


}


