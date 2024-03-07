package com.example.springjwtauthexample.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateParser {
    public static LocalDate parse(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}

