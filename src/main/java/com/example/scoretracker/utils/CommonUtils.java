package com.example.scoretracker.utils;

import com.example.scoretracker.common.Constant;
import com.example.scoretracker.exception.AppException;
import com.example.scoretracker.model.common.ErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;

public class CommonUtils {

    public static Date convertToDate(String dateString, String dateFormat) {
        if (dateString == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setLenient(true);
            return sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime convertToLocalDateTime(String date, String pattern) {
        try {
            if (date == null || StringUtils.isBlank(date)) return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(date, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertToString(Date date, String dateFormat) {
        try {
            if (date == null) {
                return null;
            }
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setLenient(true);
            return formatter.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertToString(LocalDateTime localDateTime, String pattern) {
        try {
            if (localDateTime == null) return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return localDateTime.format(formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean validateEmail(String emailStr){
        Matcher matcher = Constant.PATTERN.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public static String extractUsernameFromEmail(String email) {
        int atIndex = email.indexOf("@");
        String username;

        if (atIndex != -1) {
            username = email.substring(0, atIndex);
        } else {
            throw new AppException(new ErrorResponse("Email invalid"));
        }

        return username;
    }

    public static String alphaNumericString(int len) {
        String AB = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String encodeRandomPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(rawPassword);
    }


}
