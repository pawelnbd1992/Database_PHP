package com.pawel.database_php;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 2017-03-10.
 */
public class SaveAdapter {
    private List<DataBody.Product> products ;

    public List<DataBody.Product> getProducts() {
        return products;
    }

    public void setProducts(List<DataBody.Product> products) {
        this.products = products;
    }
}
