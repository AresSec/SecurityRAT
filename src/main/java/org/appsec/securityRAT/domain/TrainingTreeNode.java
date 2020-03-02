package org.appsec.securityrat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import org.appsec.securityrat.domain.enumeration.TrainingTreeNodeType;

/**
 * A TrainingTreeNode.
 */
@Entity
@Table(name = "training_tree_node")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "trainingtreenode")
public class TrainingTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "node_type")
    private TrainingTreeNodeType nodeType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("trainingTreeNodes")
    private TrainingTreeNode parentId;

    @ManyToOne
    @JsonIgnoreProperties("trainingTreeNodes")
    private Training trainingId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainingTreeNodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(TrainingTreeNodeType nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public TrainingTreeNode getParentId() {
        return parentId;
    }

    public void setParentId(TrainingTreeNode trainingTreeNode) {
        this.parentId = trainingTreeNode;
    }

    public Training getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Training training) {
        this.trainingId = training;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainingTreeNode)) {
            return false;
        }
        return id != null && id.equals(((TrainingTreeNode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TrainingTreeNode{" +
            "id=" + getId() +
            ", nodeType='" + getNodeType() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
