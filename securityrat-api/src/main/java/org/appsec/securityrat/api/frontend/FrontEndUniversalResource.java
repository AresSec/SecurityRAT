package org.appsec.securityrat.api.frontend;

import java.util.*;
import java.util.stream.Collectors;
import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.api.FrontendAlternativeInstanceProvider;
import org.appsec.securityrat.api.FrontendCategoryProvider;
import org.appsec.securityrat.api.FrontendDtoProvider;
import org.appsec.securityrat.api.IdentifiableDtoProvider;
import org.appsec.securityrat.api.dto.rest.AlternativeInstance;
import org.appsec.securityrat.api.dto.rest.OptColumnContent;
import org.appsec.securityrat.api.dto.rest.ReqCategory;
import org.appsec.securityrat.api.dto.rest.RequirementSkeleton;
import org.appsec.securityrat.api.dto.frontend.Category;
import org.appsec.securityrat.api.dto.frontend.CollectionCategory;
import org.appsec.securityrat.api.dto.frontend.OptionColumnAlternative;
import org.appsec.securityrat.api.dto.frontend.ProjectType;
import org.appsec.securityrat.api.dto.frontend.TagCategory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/frontend-api")
@Slf4j
public class FrontEndUniversalResource {
    // TODO: Frontend needs cleanup
    
    private IdentifiableDtoProvider<Long, AlternativeInstance>
            alternativeInstanceProvider;
    
    @Inject
    private IdentifiableDtoProvider<Long, OptColumnContent>
            optColumnContentProvider;
    
    @Inject
    private IdentifiableDtoProvider<Long, RequirementSkeleton>
            requirementSkeletonProvider;
    
    @Inject
    private IdentifiableDtoProvider<Long, ReqCategory> reqCategoryProvider;
    
    @Inject
    private FrontendDtoProvider<CollectionCategory>
            frontendCollectionCategoryProvider;
    
    @Inject
    private FrontendDtoProvider<ProjectType> frontendProjectTypeProvider;
    
    @Inject
    private FrontendDtoProvider<TagCategory> frontendTagCategoryProvider;
    
    @Inject
    private FrontendDtoProvider<OptionColumnAlternative>
            frontendOptionColumnAlternativeProvider;
    
    @Inject
    private FrontendCategoryProvider frontendCategoryProvider;
    
    @Inject
    private FrontendAlternativeInstanceProvider
            frontendAlternativeInstanceProvider;
    
    @RequestMapping(value = "/collections",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CollectionCategory> getCollections() {
        log.debug("REST request to get all collection catetories and their "
                + "collection instances");
        
        return this.frontendCollectionCategoryProvider.findAll();
    }

    @RequestMapping(value = "/projectTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ProjectType> getProjectTypes() {
        log.debug("REST request to get project types");
        
        return new HashSet<>(this.frontendProjectTypeProvider.findAll());
    }

    @RequestMapping(value = "/tags",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagCategory> getTags() {
        log.debug("REST request to get all tag catetories and their tag "
                + "instances");
        
        return this.frontendTagCategoryProvider.findAll();
    }

    @RequestMapping(value = "/requirementCategories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReqCategory> getAllActiveReqCategories() {
        log.debug("REST request to get all ReqCategories");
        
        return this.reqCategoryProvider.findAll()
                .stream()
                .filter(e -> e.getActive())
                .collect(Collectors.toList());
    }
    
    @RequestMapping(value = "/requirementSkeletons",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequirementSkeleton> getAllActiveRequirements() {
        log.debug("REST request to get all Requirements");
        
        return this.requirementSkeletonProvider.findAll()
                .stream()
                .filter(e -> e.getActive())
                .collect(Collectors.toList());
    }
    
    @RequestMapping(value = "/alternativeinstances",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlternativeInstance> alternativeInstances() {
        log.debug("REST request to get all active alternative instances");
        
        return this.alternativeInstanceProvider.findAll()
                .stream()
                .filter(e -> e.getAlternativeSet().getActive())
                .collect(Collectors.toList());
    }
    
    @RequestMapping(value = "/optColumnContents",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OptColumnContent> getAll() {
        log.debug("REST request to get all active OptColumnContents");
        
        return this.optColumnContentProvider.findAll()
                .stream()
                .filter(e -> e.getOptColumn().getActive())
                .collect(Collectors.toList());
    }
    
    @RequestMapping(value = "/requirementSkeletons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequirementSkeleton> getActiveRequirement(
            @PathVariable Long id) {
        log.debug("REST request requirement {}", id);
        
        return this.requirementSkeletonProvider.findById(id)
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value="/categoriesWithRequirements/filter",
    		method=RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Category> getCategoriesWithSkeletons(
            @RequestParam("collections") Long[] collectionIds,
            @RequestParam("projectTypes") Long[] projectTypeIds) {
        return new HashSet<>(
                this.frontendCategoryProvider.findEagerlyCategoriesWithRequirements(
                        collectionIds, projectTypeIds));
    }

    @RequestMapping(value="/numberOfRequirements/filter",
        method=RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public int getNumberOfRequirements(
        @RequestParam("collections") Long[] collectionIds,
        @RequestParam("projectTypes") Long[] projectTypeIds) {
        return this.getCategoriesWithSkeletons(collectionIds, projectTypeIds)
                .stream()
                .mapToInt(e -> e.getRequirements().size())
                .sum();
    }

    @RequestMapping(value = "/alternativeInstances/filter",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<org.appsec.securityrat.api.dto.frontend.AlternativeInstance> getAlternativeInstancesForAlternativeSet(
            @RequestParam("alternativeSet") Long alternativeSetId) {
        return new HashSet<>(
                this.frontendAlternativeInstanceProvider.getActiveAlternativeInstancesForAlternativeSet(
                        alternativeSetId));
    }

    @RequestMapping(value = "/optionColumnsWithAlternativeSets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<OptionColumnAlternative> getOptionColumnsWithAlternativeSets() {
        return new HashSet<>(
                this.frontendOptionColumnAlternativeProvider.findAll());
    }
}
