package com.bustanil.controller;

import org.springframework.stereotype.Controller;

import javax.faces.bean.ViewScoped;

@Controller
@ViewScoped
public class HelloController {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
