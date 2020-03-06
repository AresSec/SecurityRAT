package org.appsec.securityrat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RequirementSkeleton.
 */
@Entity
@Table(name = "requirement_skeleton")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "requirementskeleton")
public class RequirementSkeleton implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "universal_id")
    private String universalId;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "show_order")
    private Integer showOrder;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "requirementSkeleton")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OptColumnContent> optColumnContents = new HashSet<>();

    @OneToMany(mappedBy = "requirementSkeleton")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AlternativeInstance> alternativeInstances = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("requirementSkeletons")
    private ReqCategory reqCategory;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "requirement_skeleton_tag_instance",
               joinColumns = @JoinColumn(name = "requirement_skeleton_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tag_instance_id", referencedColumnName = "id"))
    private Set<TagInstance> tagInstances = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "requirement_skeleton_collection_instance",
               joinColumns = @JoinColumn(name = "requirement_skeleton_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "collection_instance_id", referencedColumnName = "id"))
    private Set<CollectionInstance> collectionInstances = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "requirement_skeleton_project_type",
               joinColumns = @JoinColumn(name = "requirement_skeleton_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "project_type_id", referencedColumnName = "id"))
    private Set<ProjectType> projectTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversalId() {
        return universalId;
    }

    public void setUniversalId(String universalId) {
        this.universalId = universalId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<OptColumnContent> getOptColumnContents() {
        return optColumnContents;
    }

    public void setOptColumnContents(Set<OptColumnContent> optColumnContents) {
        this.optColumnContents = optColumnContents;
    }

    public Set<AlternativeInstance> getAlternativeInstances() {
        return alternativeInstances;
    }

    public void setAlternativeInstances(Set<AlternativeInstance> alternativeInstances) {
        this.alternativeInstances = alternativeInstances;
    }

    public ReqCategory getReqCategory() {
        return reqCategory;
    }

    public void setReqCategory(ReqCategory reqCategory) {
        this.reqCategory = reqCategory;
    }

    public Set<TagInstance> getTagInstances() {
        return tagInstances;
    }

    public void setTagInstances(Set<TagInstance> tagInstances) {
        this.tagInstances = tagInstances;
    }

    public Set<CollectionInstance> getCollectionInstances() {
        return collectionInstances;
    }

    public void setCollectionInstances(Set<CollectionInstance> collectionInstances) {
        this.collectionInstances = collectionInstances;
    }

    public Set<ProjectType> getProjectTypes() {
        return projectTypes;
    }

    public void setProjectTypes(Set<ProjectType> projectTypes) {
        this.projectTypes = projectTypes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequirementSkeleton)) {
            return false;
        }
        return id != null && id.equals(((RequirementSkeleton) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RequirementSkeleton{" +
            "id=" + getId() +
            ", universalId='" + getUniversalId() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            ", showOrder=" + getShowOrder() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
