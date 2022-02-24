package com.yupay.perutax.entities;

import jakarta.persistence.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

/**
 * A cost center for financial computations.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "cost_center")
public class CostCenter {
    /**
     * The user-assigned ID of the cost center (3 cahracters).
     */
    private final StringProperty id =
            new SimpleStringProperty(this, "id");
    /**
     * The title of the cost center. Avoid repeating values.
     */
    private final StringProperty title =
            new SimpleStringProperty(this, "title");
    /**
     * The trash flag. If true, the cost center should be ignored.
     */
    private final BooleanProperty trash =
            new SimpleBooleanProperty(this, "trash");

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Column(name = "id", nullable = false, length = 3)
    public String getId() {
        return id.get();
    }

    /**
     * Accessor - setter.
     *
     * @param id value to set on {@link #id}
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #title}
     */
    @Basic
    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title.get();
    }

    /**
     * Accessor - setter.
     *
     * @param title value to set on {@link #title}
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #trash}
     */
    @Basic
    @Column(name = "trash", nullable = false)
    public boolean isTrash() {
        return trash.get();
    }

    /**
     * Accessor - setter.
     *
     * @param trash value to set on {@link #trash}
     */
    public void setTrash(boolean trash) {
        this.trash.set(trash);
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property {@link  #id}
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property {@link  #title}
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return property {@link  #trash}
     */
    public BooleanProperty trashProperty() {
        return trash;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CostCenter that && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
