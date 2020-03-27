package deti.tqs.airq.entities;


// POJO
public class AirQuality
{

    private String city, pm10, co2, o3;

    public AirQuality(String city, String pm10, String co2, String o3) {
        this.city = city;
        this.pm10 = pm10;
        this.co2 = co2;
        this.o3 = o3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

}