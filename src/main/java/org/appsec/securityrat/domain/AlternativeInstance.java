package org.appsec.securityrat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A AlternativeInstance.
 */
@Entity
@Table(name = "alternative_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "alternativeinstance")
public class AlternativeInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    @Lob
    private String content;

    @ManyToOne
    @JsonIgnoreProperties("alternativeInstances")
    private AlternativeSet alternativeSet;

    @ManyToOne
    @JsonIgnoreProperties("alternativeInstances")
    private RequirementSkeleton requirementSkeleton;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AlternativeSet getAlternativeSet() {
        return alternativeSet;
    }

    public void setAlternativeSet(AlternativeSet alternativeSet) {
        this.alternativeSet = alternativeSet;
    }

    public RequirementSkeleton getRequirementSkeleton() {
        return requirementSkeleton;
    }

    public void setRequirementSkeleton(RequirementSkeleton requirementSkeleton) {
        this.requirementSkeleton = requirementSkeleton;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlternativeInstance)) {
            return false;
        }
        return id != null && id.equals(((AlternativeInstance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AlternativeInstance{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
