package com.config;

import ch.qos.logback.core.PropertyDefinerBase;

public class MyPropertyDefiner extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        return MyPropertyDefiner.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    }
}