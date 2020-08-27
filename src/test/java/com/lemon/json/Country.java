package com.lemon.json;

public class Country {
    private String countryName;
    private String provinceName;
    private String cityName;

    public Country(){
    }

    public Country(String countryName,String provinceName,String cityName){
        this.countryName = countryName;
        this.provinceName = provinceName;
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
