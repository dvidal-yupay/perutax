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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This entity represents a type of Document of Id.
 * A type of DOI may represent a passport, an id card,
 * or another accepted by Peruvian regulations.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "tdoi", schema = "public")
public class TypeDOI {
    /**
     * The primary ID, that matches a txation ID of 1 character.
     */
    private final StringProperty id =
            new SimpleStringProperty(this, "id");
    /**
     * The title to show in User interactions.
     */
    private final StringProperty title =
            new SimpleStringProperty(this, "title");
    /**
     * The regex to check a doi num validity for this type.
     */
    private final StringProperty regex =
            new SimpleStringProperty(this, "regex");
    /**
     * Trash flag. If true, item should be disregarded.
     */
    private final BooleanProperty trash =
            new SimpleBooleanProperty(this, "trash");

    /**
     * Default Constructor.
     */
    public TypeDOI() {
    }

    /**
     * Constructor that copies another instance.
     * The result is an object which is not
     * the same as another, but equals.
     *
     * @param another the instance to be copied.
     */
    public TypeDOI(@NotNull TypeDOI another) {
        setId(another.getId());
        setRegex(another.getRegex());
        setTitle(another.getTitle());
        setTrash(another.isTrash());
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Column(name = "id", nullable = false, length = 1)
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
    @Column(name = "title", nullable = false, length = 10)
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
     * @return value of {@link #regex}
     */
    @Basic
    @Column(name = "regex", nullable = false)
    public String getRegex() {
        return regex.get();
    }

    /**
     * Accessor - setter.
     *
     * @param regex value to set on {@link #regex}
     */
    public void setRegex(String regex) {
        this.regex.set(regex);
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
     * FXML accessor - property.
     *
     * @return the property {@link  #id}
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * FXML accessor - property.
     *
     * @return the property {@link  #title}
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * FXML accessor - property.
     *
     * @return the property {@link  #regex}
     */
    public StringProperty regexProperty() {
        return regex;
    }

    /**
     * FXML accessor - property.
     *
     * @return the property {@link  #trash}
     */
    public BooleanProperty trashProperty() {
        return trash;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TypeDOI typeDOI
                && Objects.equals(getId(), typeDOI.getId());
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
