package com.farmstep.dto;

public class ResourceLinkDto {

    private final String title;
    private final String description;
    private final String url;
    private final String resourceType;

    public ResourceLinkDto(String title, String description, String url, String resourceType) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.resourceType = resourceType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getResourceType() {
        return resourceType;
    }
}