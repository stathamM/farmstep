package com.farmstep.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.farmstep.dto.RegionDto;
import com.farmstep.dto.ResourceLinkDto;
import com.farmstep.entity.RegionEntity;
import com.farmstep.entity.ResourceEntity;
import com.farmstep.entity.ResourceRegionRelationEntity;
import com.farmstep.repository.ResourceRegionRelationRepository;
import com.farmstep.repository.ResourceRepository;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceRegionRelationRepository resourceRegionRelationRepository;

    public ResourceService(
            ResourceRepository resourceRepository,
            ResourceRegionRelationRepository resourceRegionRelationRepository
    ) {
        this.resourceRepository = resourceRepository;
        this.resourceRegionRelationRepository = resourceRegionRelationRepository;
    }

    public List<ResourceLinkDto> findResourcesByDiagnosisType(String diagnosisType) {
        List<ResourceEntity> resources =
                resourceRepository.findByDiagnosisTypeAndIsActiveTrue(diagnosisType);

        List<ResourceLinkDto> result = new ArrayList<>();

        for (ResourceEntity resource : resources) {
            result.add(new ResourceLinkDto(
                    resource.getTitle(),
                    resource.getDescription(),
                    resource.getUrl(),
                    resource.getResourceType()
            ));
        }

        return result;
    }

    public List<RegionDto> findRegionsByDiagnosisType(String diagnosisType) {
        List<ResourceEntity> resources =
                resourceRepository.findByDiagnosisTypeAndIsActiveTrue(diagnosisType);

        List<RegionDto> result = new ArrayList<>();

        for (ResourceEntity resource : resources) {
            List<ResourceRegionRelationEntity> relations =
                    resourceRegionRelationRepository.findByResource(resource);

            for (ResourceRegionRelationEntity relation : relations) {
                RegionEntity region = relation.getRegion();
                result.add(new RegionDto(
                        region.getPrefectureName(),
                        region.getCityName(),
                        region.getDisplayName()
                ));
            }
        }

        return result;
    }
}