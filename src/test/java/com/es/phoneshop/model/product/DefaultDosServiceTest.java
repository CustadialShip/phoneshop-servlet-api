package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.security.DefaultDosService;
import com.es.phoneshop.model.product.security.DosService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultDosServiceTest {
    public static final String DEFAULT_IP_1 = "1";
    public static final String DEFAULT_IP_2 = "2";
    public static final String DEFAULT_IP_3 = "3";
    private DosService dosFilter;


    @Before
    public void setup() {
        dosFilter = DefaultDosService.getInstance();
    }

    @Test
    public void testIsAllowedFalse() {
        for (int i = 0; i < 20; i++) {
            dosFilter.isAllowed(DEFAULT_IP_1);
        }
        assertFalse(dosFilter.isAllowed(DEFAULT_IP_1));
    }

    @Test
    public void testIsAllowedTrue() {
        for (int i = 0; i < 10; i++) {
            dosFilter.isAllowed(DEFAULT_IP_2);
        }
        assertTrue(dosFilter.isAllowed(DEFAULT_IP_2));
    }

    @Test
    public void testIsAllowedTrueDifferentIp() {
        for (int i = 0; i < 100; i++) {
            dosFilter.isAllowed("4");
        }
        assertTrue(dosFilter.isAllowed(DEFAULT_IP_3));
    }
}