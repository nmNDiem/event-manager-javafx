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
public class Event {
    private int id;
    private int categoryId;
    private String name;
    private int locationId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int availableTickets;
    private BigDecimal price;
    private String imageUrl;
    private String description;
    private boolean isActive;

    public Event() {
    }

    public Event(int id, int categoryId, String name, int locationId, 
            LocalDateTime startTime, LocalDateTime endTime, int availableTickets, 
            BigDecimal price, String imageUrl, String description, boolean isActive) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.locationId = locationId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableTickets = availableTickets;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.isActive = isActive;
    }
    
    public Event(int categoryId, String name, int locationId, 
            LocalDateTime startTime, LocalDateTime endTime, int availableTickets, 
            BigDecimal price, String imageUrl, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.locationId = locationId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableTickets = availableTickets;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
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
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the locationId
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the availableTickets
     */
    public int getAvailableTickets() {
        return availableTickets;
    }

    /**
     * @param availableTickets the availableTickets to set
     */
    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    
    
}
