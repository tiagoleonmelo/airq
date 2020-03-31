package deti.tqs.airq.entities;


// POJO
public class AirQuality
{

    private String city, pm10, co, o3, aqi;

    public AirQuality(String city, String pm10, String co, String o3, String aqi) {
        this.city = city;
        this.pm10 = pm10;
        this.co = co;
        this.o3 = o3;
        this.aqi = aqi;
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

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    @Override
    public String toString() {
        return "AirQuality [aqi=" + aqi + ", city=" + city + ", co=" + co + ", o3=" + o3 + ", pm10="
                + pm10 + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aqi == null) ? 0 : aqi.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((co == null) ? 0 : co.hashCode());
        result = prime * result + ((o3 == null) ? 0 : o3.hashCode());
        result = prime * result + ((pm10 == null) ? 0 : pm10.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AirQuality other = (AirQuality) obj;
        if (aqi == null) {
            if (other.aqi != null)
                return false;
        } else if (!aqi.equals(other.aqi))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (co == null) {
            if (other.co != null)
                return false;
        } else if (!co.equals(other.co))
            return false;
        if (o3 == null) {
            if (other.o3 != null)
                return false;
        } else if (!o3.equals(other.o3))
            return false;
        if (pm10 == null) {
            if (other.pm10 != null)
                return false;
        } else if (!pm10.equals(other.pm10))
            return false;
        return true;
    }

}