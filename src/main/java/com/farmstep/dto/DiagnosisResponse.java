package com.farmstep.dto;

import java.util.List;

public class DiagnosisResponse {

    private Long diagnosisResultId;

    private String type;
    private String message;
    private String nextAction;
    private String funding;
    private String recommendedStyle;
    private String caution;
    private String regionHint;

    private List<ResourceLinkDto> resources;
    private List<RegionDto> regions;

    public DiagnosisResponse(
            Long diagnosisResultId,
            String type,
            String message,
            String nextAction,
            String funding,
            String recommendedStyle,
            String caution,
            String regionHint,
            List<ResourceLinkDto> resources,
            List<RegionDto> regions
    ) {
        this.diagnosisResultId = diagnosisResultId;
        this.type = type;
        this.message = message;
        this.nextAction = nextAction;
        this.funding = funding;
        this.recommendedStyle = recommendedStyle;
        this.caution = caution;
        this.regionHint = regionHint;
        this.resources = resources;
        this.regions = regions;
    }

    public Long getDiagnosisResultId() {
        return diagnosisResultId;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getNextAction() {
        return nextAction;
    }

    public String getFunding() {
        return funding;
    }

    public String getRecommendedStyle() {
        return recommendedStyle;
    }

    public String getCaution() {
        return caution;
    }

    public String getRegionHint() {
        return regionHint;
    }

    public List<ResourceLinkDto> getResources() {
        return resources;
    }

    public List<RegionDto> getRegions() {
        return regions;
    }
}