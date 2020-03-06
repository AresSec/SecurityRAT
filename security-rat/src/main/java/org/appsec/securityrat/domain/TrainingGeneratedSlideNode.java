package org.appsec.securityrat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A TrainingGeneratedSlideNode.
 */
@Entity
@Table(name = "training_generated_slide_node")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "traininggeneratedslidenode")
public class TrainingGeneratedSlideNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("trainingGeneratedSlideNodes")
    private TrainingTreeNode node;

    @ManyToOne
    @JsonIgnoreProperties("trainingGeneratedSlideNodes")
    private OptColumn optColumn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainingTreeNode getNode() {
        return node;
    }

    public void setNode(TrainingTreeNode trainingTreeNode) {
        this.node = trainingTreeNode;
    }

    public OptColumn getOptColumn() {
        return optColumn;
    }

    public void setOptColumn(OptColumn optColumn) {
        this.optColumn = optColumn;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainingGeneratedSlideNode)) {
            return false;
        }
        return id != null && id.equals(((TrainingGeneratedSlideNode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TrainingGeneratedSlideNode{" +
            "id=" + getId() +
            "}";
    }
}
