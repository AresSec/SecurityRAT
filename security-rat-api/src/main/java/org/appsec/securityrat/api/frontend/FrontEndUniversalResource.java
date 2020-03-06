package org.appsec.securityrat.api.frontend;

import java.util.*;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.dto.AlternativeInstance;
import org.appsec.securityrat.api.dto.OptColumnContent;
import org.appsec.securityrat.api.dto.ReqCategory;
import org.appsec.securityrat.api.dto.RequirementSkeleton;
import org.appsec.securityrat.api.dto.frontend.Category;
import org.appsec.securityrat.api.dto.frontend.CollectionCategory;
import org.appsec.securityrat.api.dto.frontend.OptionColumnAlternative;
import org.appsec.securityrat.api.dto.frontend.ProjectType;
import org.appsec.securityrat.api.dto.frontend.TagCategory;

@RestController
@RequestMapping("/frontend-api")
@Slf4j
public class FrontEndUniversalResource {
    @RequestMapping(value = "/collections",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollectionCategory> getCollections() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/projectTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ProjectType> getProjectTypes() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/tags",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagCategory> getTags() {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/requirementCategories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReqCategory> getAllActiveReqCategories() {
        log.warn("Not implemented");
        return null;
    }
    
    @RequestMapping(value = "/requirementSkeletons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequirementSkeleton> getAllActiveRequirements() {
        log.warn("Not implemented");
        return null;
    }
    
    @RequestMapping(value = "/alternativeinstances",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlternativeInstance> alternativeInstances() {
        log.warn("Not implemented");
        return null;
    }
    
    @RequestMapping(value = "/optColumnContents",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumnContent> getAll() {
        log.warn("Not implemented");
        return null;
    }
    
    @RequestMapping(value = "/requirementSkeletons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequirementSkeleton> getActiveRequirement(@PathVariable Long id) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value="/categoriesWithRequirements/filter",
    		method=RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Category> getCategoriesWithSkeletons(@RequestParam("collections") Long[] collectionIds, @RequestParam("projectTypes") Long[] projectTypeIds) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value="/numberOfRequirements/filter",
        method=RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public int getNumberOfRequirements(
        @RequestParam("collections") Long[] collectionIds,
        @RequestParam("projectTypes") Long[] projectTypeIds) {
        log.warn("Not implemented");
        return -1;
    }

@RequestMapping(value = "/alternativeInstances/filter",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<org.appsec.securityrat.api.dto.frontend.AlternativeInstance> getAlternativeInstancesForAlternativeSet(@RequestParam("alternativeSet") Long alternativeSetId) {
        log.warn("Not implemented");
        return null;
    }

    @RequestMapping(value = "/optionColumnsWithAlternativeSets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<OptionColumnAlternative> getOptionColumnsWithAlternativeSets() {
        log.warn("Not implemented");
        return null;
    }
}
