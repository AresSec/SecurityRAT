package org.appsec.securityrat.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Training.
 */
@Entity
@Table(name = "training")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "training")
public class Training extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "all_requirements_selected")
    private Boolean allRequirementsSelected;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "training_opt_column",
               joinColumns = @JoinColumn(name = "training_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "opt_column_id", referencedColumnName = "id"))
    private Set<OptColumn> optColumns = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "training_collection",
               joinColumns = @JoinColumn(name = "training_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"))
    private Set<CollectionInstance> collections = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "training_project_type",
               joinColumns = @JoinColumn(name = "training_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "project_type_id", referencedColumnName = "id"))
    private Set<ProjectType> projectTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isAllRequirementsSelected() {
        return allRequirementsSelected;
    }

    public void setAllRequirementsSelected(Boolean allRequirementsSelected) {
        this.allRequirementsSelected = allRequirementsSelected;
    }

    public Set<OptColumn> getOptColumns() {
        return optColumns;
    }

    public void setOptColumns(Set<OptColumn> optColumns) {
        this.optColumns = optColumns;
    }

    public Set<CollectionInstance> getCollections() {
        return collections;
    }

    public void setCollections(Set<CollectionInstance> collectionInstances) {
        this.collections = collectionInstances;
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
        if (!(o instanceof Training)) {
            return false;
        }
        return id != null && id.equals(((Training) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Training{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", allRequirementsSelected='" + isAllRequirementsSelected() + "'" +
            "}";
    }
}
