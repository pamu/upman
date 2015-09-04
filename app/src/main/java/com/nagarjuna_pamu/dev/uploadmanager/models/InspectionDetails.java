package com.nagarjuna_pamu.dev.uploadmanager.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pnagarjuna on 03/09/15.
 */
public class InspectionDetails implements LocalFilesItem {
    private String scrapeId;
    private String make;
    private String model;
    private String sellerName;
    private String sellerPhone;
    private String sellerAddress;


    public String getScrapeId() {
        return scrapeId;
    }

    public void setScrapeId(String scrapeId) {
        this.scrapeId = scrapeId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public InspectionDetails(final JSONObject jsonObject) {
        try {
            scrapeId = jsonObject.getString("scrape_id");
            make = jsonObject.getString("make");
            model = jsonObject.getString("model");
            sellerAddress = jsonObject.getString("seller_address");
            sellerName = jsonObject.getString("seller_name");
            sellerPhone= jsonObject.getString("seller_phone");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
