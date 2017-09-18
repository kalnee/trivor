package org.kalnee.trivor.gateway.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.kalnee.trivor.gateway.domain.enumeration.TranscriptTypeEnum;

/**
 * A Transcript.
 */
@Entity
@Table(name = "transcript")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transcript implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TranscriptTypeEnum type;

    @Column(name = "imdb_id")
    private String imdbId;

    @NotNull
    @Column(name = "cover_url", nullable = false)
    private String coverUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Size(max = 1000)
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "genres")
    private String genres;

    @Size(max = 3)
    @Column(name = "country", length = 3)
    private String country;

    @Min(value = 0)
    @Column(name = "duration")
    private Integer duration;

    @Size(max = 2)
    @Column(name = "language", length = 2)
    private String language;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Transcript name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TranscriptTypeEnum getType() {
        return type;
    }

    public Transcript type(TranscriptTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(TranscriptTypeEnum type) {
        this.type = type;
    }

    public String getImdbId() {
        return imdbId;
    }

    public Transcript imdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public Transcript coverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Transcript createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public Transcript description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public Transcript year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGenres() {
        return genres;
    }

    public Transcript genres(String genres) {
        this.genres = genres;
        return this;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getCountry() {
        return country;
    }

    public Transcript country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getDuration() {
        return duration;
    }

    public Transcript duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public Transcript language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transcript transcript = (Transcript) o;
        if (transcript.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transcript.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transcript{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", imdbId='" + getImdbId() + "'" +
            ", coverUrl='" + getCoverUrl() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", description='" + getDescription() + "'" +
            ", year='" + getYear() + "'" +
            ", genres='" + getGenres() + "'" +
            ", country='" + getCountry() + "'" +
            ", duration='" + getDuration() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
