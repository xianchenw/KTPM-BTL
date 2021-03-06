/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

/**
 *
 * @author HIEN
 */
public class ReceiptDetail {
    private int productId;
    private int receiptId;
    private double quantity;

    public ReceiptDetail(){
        
    }
    
    public ReceiptDetail(int productId, int receiptId, double quantity){
        this.productId = productId;
        this.receiptId = receiptId;
        this.quantity = quantity;
    }
    
    public ReceiptDetail(int productId, int receiptId){
        this.productId = productId;
        this.receiptId = receiptId;
    }
    
    /**
     * @return the receiptId
     */
    public int getReceiptId() {
        return receiptId;
    }

    /**
     * @param receiptId the receiptId to set
     */
    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @return the quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    
}
