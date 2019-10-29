package com.example.android.blooddrop.Model;

public class DataDonor {
    private String DonorId;
    private String DonorName;
    private String DonorPhone;
    private String DonorAddress;
    private String DonorBloodType;

    public DataDonor() {

    }

    public DataDonor(String DonorId, String DonorName, String DonorPhone, String DonorAddress, String DonorBloodType) {
        this.DonorId = DonorId;
        this.DonorName = DonorName;
        this.DonorPhone = DonorPhone;
        this.DonorAddress = DonorAddress;
        this.DonorBloodType = DonorBloodType;
    }

    //getter
    public String getDonorId() {
        return DonorId;
    }

    //setter
    public void setDonorId(String DonorId) {
        this.DonorId = DonorId;
    }

    public String getDonorName() {
        return DonorName;
    }

    public void setDonorName(String DonorName) {
        this.DonorName = DonorName;
    }

    public String getDonorPhone() {
        return DonorPhone;
    }

    public void setDonorPhone(String DonorPhone) {
        this.DonorPhone = DonorPhone;
    }

    public String getDonorBloodType() {
        return DonorBloodType;
    }

    public void setDonorBloodType(String DonorBloodType) {
        this.DonorBloodType = DonorBloodType;
    }

    public String getDonorAddress() {
        return DonorAddress;
    }

    public void setDonorAddress(String DonorAddress) {
        this.DonorAddress = DonorAddress;
    }
}
