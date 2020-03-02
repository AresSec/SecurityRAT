package org.appsec.securityrat.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A SlideTemplate.
 */
@Entity
@Table(name = "slide_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "slidetemplate")
public class SlideTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SlideTemplate)) {
            return false;
        }
        return id != null && id.equals(((SlideTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SlideTemplate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
