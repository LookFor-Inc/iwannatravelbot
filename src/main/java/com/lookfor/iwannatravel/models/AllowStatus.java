package com.lookfor.iwannatravel.models;

public enum AllowStatus {
    YES,
    NO,
    PARTIAL;

    public static AllowStatus value(String value) {
        return valueOf(value.toUpperCase());
    }
}
