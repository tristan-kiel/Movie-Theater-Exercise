package com.jpmc.theater;

import org.junit.jupiter.api.Test;

public class LocalDateProviderTests {
    @Test
    void makeSureCurrentDate() {
        System.out.println("Current Date Is - " + LocalDateProvider.singleton().currentDate()); 
    }
}
