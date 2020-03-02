package org.appsec.securityrat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProjectType.
 */
@Entity
@Table(name = "project_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "projecttype")
public class ProjectType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "show_order")
    private Integer showOrder;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_type_status_column",
               joinColumns = @JoinColumn(name = "project_type_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "status_column_id", referencedColumnName = "id"))
    private Set<StatusColumn> statusColumns = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_type_opt_column",
               joinColumns = @JoinColumn(name = "project_type_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "opt_column_id", referencedColumnName = "id"))
    private Set<OptColumn> optColumns = new HashSet<>();

    @ManyToMany(mappedBy = "projectTypes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<RequirementSkeleton> requirementSkeletons = new HashSet<>();

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

    public Set<StatusColumn> getStatusColumns() {
        return statusColumns;
    }

    public void setStatusColumns(Set<StatusColumn> statusColumns) {
        this.statusColumns = statusColumns;
    }

    public Set<OptColumn> getOptColumns() {
        return optColumns;
    }

    public void setOptColumns(Set<OptColumn> optColumns) {
        this.optColumns = optColumns;
    }

    public Set<RequirementSkeleton> getRequirementSkeletons() {
        return requirementSkeletons;
    }

    public void setRequirementSkeletons(Set<RequirementSkeleton> requirementSkeletons) {
        this.requirementSkeletons = requirementSkeletons;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectType)) {
            return false;
        }
        return id != null && id.equals(((ProjectType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProjectType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", showOrder=" + getShowOrder() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
