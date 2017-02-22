package com.pawel.database_php;



  import com.google.gson.annotations.Expose;
  import com.google.gson.annotations.SerializedName;

  import java.util.ArrayList;
  import java.util.List;

public class DataBody {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("products")
    @Expose
    private ArrayList<Product> product ;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Product> getProduct() {
        return product;
    }

    public static class Product {


        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("description")
        @Expose
        private String description;

        public Product(String name, String price, String description) {
            this.name = name;
            this.price = price;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


    }


}

