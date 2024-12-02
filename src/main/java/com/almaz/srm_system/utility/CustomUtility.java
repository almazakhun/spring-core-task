package com.almaz.srm_system.utility;

import org.apache.commons.lang3.RandomStringUtils;

public class CustomUtility {
    public static String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
