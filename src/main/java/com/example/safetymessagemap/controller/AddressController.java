package com.example.safetymessagemap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddressController {

    private final String CONFIRM_KEY = "devU01TX0FVVEgyMDI0MTAxODE0MjMzMTExNTE2ODk=";  // API 인증키

    @GetMapping("/jusoPopup")
    public String jusoPopup(Model model) {
        model.addAttribute("confmKey", CONFIRM_KEY);
        model.addAttribute("returnUrl", "/jusoPopup/callback");
        return "jusoPopup";
    }

    @GetMapping("/jusoPopup/callback")
    public String jusoCallBack() {
        return "callback";
    }
}