package com.blog.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        System.out.println(resourceName + " " + fieldName + " " + fieldValue);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Getters
    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }

    // Setters
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldValue(long fieldValue) {
        this.fieldValue = fieldValue;
    }

    // toString method
    @Override
    public String toString() {
        return "ResourceNotFoundException{" +
                "resourceName='" + resourceName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue=" + fieldValue +
                '}';
    }
}
