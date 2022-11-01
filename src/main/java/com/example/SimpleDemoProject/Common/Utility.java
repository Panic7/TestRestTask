package com.example.SimpleDemoProject.Common;

import java.time.LocalDate;
import java.time.Period;


public class Utility {

    public static int getAgeByDateOfBirth(LocalDate birth) {
        return Period.between(birth, LocalDate.now()).getYears();
    }

}
