package org.appsec.securityrat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A OptColumnContent.
 */
@Entity
@Table(name = "opt_column_content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "optcolumncontent")
public class OptColumnContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JsonIgnoreProperties("optColumnContents")
    private OptColumn optColumn;

    @ManyToOne
    @JsonIgnoreProperties("optColumnContents")
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

    public OptColumn getOptColumn() {
        return optColumn;
    }

    public void setOptColumn(OptColumn optColumn) {
        this.optColumn = optColumn;
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
        if (!(o instanceof OptColumnContent)) {
            return false;
        }
        return id != null && id.equals(((OptColumnContent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OptColumnContent{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
