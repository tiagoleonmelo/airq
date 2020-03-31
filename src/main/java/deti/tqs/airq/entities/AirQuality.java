package deti.tqs.airq.entities;

import java.util.HashMap;

// POJO
public class AirQuality
{

    private String country, city;
    private HashMap<String, String> attributes;

    public AirQuality(String country, String city) {
        this.country = country;
        this.city = city;
        this.attributes = new HashMap<String, String>();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
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
        if (attributes == null) {
            if (other.attributes != null)
                return false;
        } else if (!attributes.equals(other.attributes))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AirQuality [attributes=" + attributes + ", city=" + city + ", country=" + country + "]";
    }


    // Since we are using a HashMap for scalability purposes, we need a method
    // to add attributes
    public AirQuality putAttr(String key, String value)
    {
        this.attributes.put(key, value);
        return this;
    }


}