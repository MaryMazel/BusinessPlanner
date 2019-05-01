package com.example.businessplanner.presentation.utils;

import android.support.design.widget.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean validateFields(String name, TextInputLayout layoutName,
                                         String phone, TextInputLayout layoutPhone,
                                         String email, TextInputLayout layoutEmail,
                                         String profit, TextInputLayout layoutProfit) {
        List<Boolean> validFields = new ArrayList<>();
        validFields.add(validateName(name, layoutName));
        validFields.add(validatePhone(phone, layoutPhone));
        validFields.add(validateEmail(email, layoutEmail));
        validFields.add(validateProfit(profit, layoutProfit));

        for (Boolean validField : validFields) {
            if (!validField) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateName(String text, TextInputLayout layout) {
        if (text.equals("")) {
            layout.setError("Field can't be empty");
            return false;
        } else {
            layout.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean validatePhone(String text, TextInputLayout layout) {
        if (text.equals("")) {
            layout.setError("Field can't be empty");
            return false;
        } else {
            layout.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean validateEmail(String text, TextInputLayout layout) {
        if (text.equals("")) {
            return true;
        }
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            layout.setErrorEnabled(false);
            return true;
        } else {
            layout.setError("This is not email");
            return false;
        }
    }

    public static boolean validateProfit(String text, TextInputLayout layout) {
        try {
            if (!text.equals("")) {
                Long.parseLong(text);
                layout.setErrorEnabled(false);
            }
            return true;
        } catch (Exception e) {
            layout.setError("Input integer value");
            return false;
        }
    }

    public static long validateProfit(String text) {
        return Long.parseLong(text);
    }
}
