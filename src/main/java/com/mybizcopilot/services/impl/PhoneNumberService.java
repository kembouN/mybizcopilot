package com.mybizcopilot.services.impl;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public boolean isValidPhoneNumber(String phoneNumber, String countryCode) {
        try {
            PhoneNumber number = phoneUtil.parse(phoneNumber, countryCode);
            return phoneUtil.isValidNumber(number);
        }catch (NumberParseException e) {
            return false;
        }
    }

    public String formatPhoneNumber(String phoneNumber, String regionCode) throws NumberParseException {
            PhoneNumber number = phoneUtil.parse(phoneNumber, regionCode);
            return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
    }

    public String formatForDisplay(String phoneNumber, String code) throws NumberParseException {
            PhoneNumber number = phoneUtil.parse(phoneNumber, code);
            return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
    }

}
