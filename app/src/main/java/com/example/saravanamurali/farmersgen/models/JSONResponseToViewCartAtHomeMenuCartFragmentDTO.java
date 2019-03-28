package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseToViewCartAtHomeMenuCartFragmentDTO {

    @SerializedName("records")
    public List<MenuCartFragmentViewCartDTO> viewCartListRecord;

    @SerializedName("grand_total")
    public String grandTotal;

    public List<MenuCartFragmentViewCartDTO> getViewCartListRecord() {
        return viewCartListRecord;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

}
