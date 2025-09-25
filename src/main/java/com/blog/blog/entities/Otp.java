package com.blog.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String otp;
    private Date expiry;

    public Otp(String email, String otp, Date expiry) {
        this.email = email;
        this.otp = otp;
        this.expiry = expiry;
    }

    public Otp() {
        this.expiry = new Date(System.currentTimeMillis() + 1000 * 60 * 5); // 5 minutes
    }
}
