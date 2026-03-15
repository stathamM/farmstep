package com.farmstep.dto;

public class RegionDto {

    private final String prefectureName;
    private final String cityName;
    private final String displayName;

    public RegionDto(String prefectureName, String cityName, String displayName) {
        this.prefectureName = prefectureName;
        this.cityName = cityName;
        this.displayName = displayName;
    }

    public String getPrefectureName() {
        return prefectureName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDisplayName() {
        return displayName;
    }
}