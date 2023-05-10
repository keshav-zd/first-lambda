package com.exam1;

public class VisitoreData
{
    private String branch;
    private String address;
    private String city;
    private int ZIP;
        private int jan;
    private int fab;
    private int mar;
    private int ytd;
    private String location;
    int may;
    int april;
    int june;
    int july;
    int august;
    int september;
    int october;
    int november;
    int december;
    int boundaries_zip_codes;
    int community_areas;
    int zip_code;
    int census_tracts;
    int wards;

    public VisitoreData(String branch, String address, String city, int zip, int jan, int fab, int mar,
                        int april, int may, int june, int july, int august,
                        int september, int october, int november, int december, int ytd,String location)
    {
         this.branch=branch;
         this.address=address;
         this.city=city;
        this.ZIP=zip;
        this.jan=jan;
        this.fab=fab;
        this.mar=mar;

        this.ytd= ytd;
        this.location= location;

    }

    public String getBranch() {
        return branch;
    }

    public String getCity() {
        return city;
    }

    public int getJan() {
        return jan;
    }

    public void setJan(int jan) {
        this.jan = jan;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getYtd() {
        return ytd;
    }

    public void setYtd(int ytd) {
        this.ytd = ytd;
    }


    public int getMar() {
        return mar;
    }

    public void setMar(int mar) {
        this.mar = mar;
    }

    public int getFab() {
        return fab;
    }

    public void setFab(int fab) {
        this.fab = fab;
    }

    public void setZIP(int ZIP) {
        this.ZIP = ZIP;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "CirculationData{" +
                "branch='" + branch + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", ZIP=" + ZIP +
                ", jan=" + jan +
                ", fab=" + fab +
                ", mar=" + mar +
                ", ytd=" + ytd +
                ", location=" + location +
                '}';
    }

    public int getZIP() {
        return ZIP;
    }

    public int getDecember() {
        return december;
    }


    public int getWards() {
        return wards;
    }

    public void setWards(int wards) {
        this.wards = wards;
    }



    public void setDecember(int december) {
        this.december = december;
    }


    public int getOctober() {
        return october;
    }

    public int getNovember() {
        return november;
    }

    public void setNovember(int november) {
        this.november = november;
    }

    public void setOctober(int october) {
        this.october = october;
    }

    public int getAugust() {
        return august;
    }

    public int getSeptember() {
        return september;
    }

    public void setSeptember(int september) {
        this.september = september;
    }

    public void setAugust(int august) {
        this.august = august;
    }

    public int getJuly() {
        return july;
    }

    public void setJuly(int july) {
        this.july = july;
    }

    public int getJune() {
        return june;
    }

    public void setJune(int june) {
        this.june = june;
    }

    public int getMay() {
        return may;
    }

    public int getApril() {
        return april;
    }

    public void setApril(int april) {
        this.april = april;
    }

    public void setMay(int may) {
        this.may = may;
    }
}
