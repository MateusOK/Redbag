package br.com.redbag.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MACHO,
    FEMEA;

    @JsonCreator
    public static Gender fromString(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.toString().equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender: " + value);
    }
}