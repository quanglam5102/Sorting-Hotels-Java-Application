/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lamhuynh
 */
public class Hotel {
    private String name;
    private int star;
    private double price;
    
    public Hotel(String name, int star, double price) {
        this.name = name;
        this.star = star;
        this.price = price;
    }
    
    //Method to get hotel name
    public String getName() {
        return name;
    }
    
    //Method to get hotel star
    public Integer getStar() {
        return star;
    }
    
    //Method to get hotel price
    public Double getPrice() {
        return price;
    }
    
}
