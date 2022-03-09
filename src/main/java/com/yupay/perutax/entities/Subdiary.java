/*
 *  Perutax - Taxation software for Peru.
 *     Copyright (C) 2021-2022  Ingenieria Informatica Yupay SACS
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.yupay.perutax.entities;

import jakarta.persistence.*;
import javafx.beans.property.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The subdiary entity.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "subdiary", schema = "public")
public class Subdiary {
    /**
     * The code (ID) of the subdiary.
     */
    private final StringProperty id =
            new SimpleStringProperty(this, "id");
    /**
     * The title to show to user.
     */
    private final StringProperty title =
            new SimpleStringProperty(this, "title");
    /**
     * Trash flag. If true, this entity should be treated as if deleted.
     */
    private final BooleanProperty trash =
            new SimpleBooleanProperty(this, "trash");
    /**
     * The subdiary role defines the prefix used in correlatives
     * for those journal entries associated with this subdiary.
     */
    private final ObjectProperty<SubdiaryRole> role =
            new SimpleObjectProperty<>(this, "role");

    /**
     * Default empty constructor.
     */
    public Subdiary() {
    }

    /**
     * Constructor that copies fields from another instance.
     *
     * @param another the source instance.
     */
    public Subdiary(@NotNull Subdiary another) {
        setId(another.getId());
        setTitle(another.getTitle());
        setTrash(another.isTrash());
        setRole(another.getRole());
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #role}.get();
     */
    @Basic
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    public final SubdiaryRole getRole() {
        return role.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param role value to assign into {@link #role}.
     */
    public final void setRole(SubdiaryRole role) {
        this.role.set(role);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #role}.
     */
    public final ObjectProperty<SubdiaryRole> roleProperty() {
        return role;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Column(name = "id", nullable = false, length = 2)
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
     * Java FX accessor - property.
     *
     * @return the property {@link #id}
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * Java FX accessor - property.
     *
     * @return the property {@link #title}
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Java FX accessor - property.
     *
     * @return the property {@link #trash}
     */
    public BooleanProperty trashProperty() {
        return trash;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Subdiary subdiary && Objects.equals(getId(), subdiary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return getId() + " - " + getTitle();
    }
}
