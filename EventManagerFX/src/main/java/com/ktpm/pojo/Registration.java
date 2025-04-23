/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.pojo;

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
    private String status;

    public Registration() {
    }

    public Registration(int id, int eventId, int userId, LocalDateTime registrationTime, String status) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.registrationTime = registrationTime;
        this.status = status;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
