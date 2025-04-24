/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class Registration {
    private int id;
    private int eventId;
    private int userId;
    private LocalDateTime registrationTime;
    private BigDecimal paymentAmount;
    private String paymentStatus;

    public Registration() {
    }

    public Registration(int id, int eventId, int userId, LocalDateTime registrationTime, BigDecimal paymentAmount, String paymentStatus) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.registrationTime = registrationTime;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the eventId
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the registrationTime
     */
    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    /**
     * @param registrationTime the registrationTime to set
     */
    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    /**
     * @return the paymentAmount
     */
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * @param paymentAmount the paymentAmount to set
     */
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * @return the paymentStatus
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * @param paymentStatus the paymentStatus to set
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    
}
