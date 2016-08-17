package mobello.amtrust.com.model;

import java.util.ArrayList;

/**
 * Created by Parthi on 22-Jul-16.
 */
public class RetailersPrice {

    public String status;
    public ArrayList<Retailer> retailers = new ArrayList<Retailer>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Retailer> getRetailers() {
        return retailers;
    }

    public void setRetailers(ArrayList<Retailer> retailers) {
        this.retailers = retailers;
    }

    public class Retailer {

        public String name;
        public Integer price;
        public String currency;
        public String currencySymbol;
        public String logo;
        public String website;
        public String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getCurrencySymbol() {
            return currencySymbol;
        }

        public void setCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
