package com.yupay.perutax.entities;

import jakarta.persistence.*;
import javafx.beans.property.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * Journal detail's person entity.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(name = "journal_dt_person")
public class JournalDtPerson {
    /**
     * The ID identifying the journal DT object.
     */
    private final LongProperty id =
            new SimpleLongProperty(this, "id");
    /**
     * The type of DOI of the person (this is a snapshot).
     */
    private final StringProperty doiType =
            new SimpleStringProperty(this, "doiType");
    /**
     * The DOI number of the person. (Snapshot)
     */
    private final StringProperty doiNum =
            new SimpleStringProperty(this, "doiNum");
    /**
     * The full name of the person. (Snapshot)
     */
    private final StringProperty fullName =
            new SimpleStringProperty(this, "fullName");
    /**
     * The referenced person. (Realtime data).
     */
    private final ObjectProperty<Person> reference =
            new SimpleObjectProperty<>(this, "reference");
    /**
     * The journal detail line owning this entity.
     */
    private JournalDt owner;

    /**
     * Empty constructor for entities.
     */
    public JournalDtPerson() {
    }

    /**
     * Constructor to copy person data.
     *
     * @param person the person to asign.
     */
    public JournalDtPerson(@NotNull Person person) {
        setReference(person);
        setFullName(person.getFullName());
        setDoiNum(person.getDoiNum());
        setDoiType(person.getDoiType().getId());
    }

    /**
     * Craetes a function to format this entity in a string.
     *
     * @return the function.
     */
    @Contract("->new")
    public static @NotNull Function<JournalDtPerson, String> formatter() {
        return x -> x == null ? ""
                : x.getDoiType() + "-" +
                x.getDoiNum() + "-" +
                x.getFullName();
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id.get();
    }

    /**
     * Accessor - setter.
     *
     * @param id value to set on {@link #id}
     */
    public void setId(long id) {
        this.id.set(id);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #doiType}
     */
    @Basic
    @Column(name = "doi_type", nullable = false, length = 1)
    public String getDoiType() {
        return doiType.get();
    }

    /**
     * Accessor - setter.
     *
     * @param doiType value to set on {@link #doiType}
     */
    public void setDoiType(String doiType) {
        this.doiType.set(doiType);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #doiNum}
     */
    @Basic
    @Column(name = "doi_num", nullable = false, length = 15)
    public String getDoiNum() {
        return doiNum.get();
    }

    /**
     * Accessor - setter.
     *
     * @param doiNum value to set on {@link #doiNum}
     */
    public void setDoiNum(String doiNum) {
        this.doiNum.set(doiNum);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #fullName}
     */
    @Basic
    @Column(name = "full_name", nullable = false)
    public String getFullName() {
        return fullName.get();
    }

    /**
     * Accessor - setter.
     *
     * @param fullName value to set on {@link #fullName}
     */
    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #reference}
     */
    @ManyToOne
    @JoinColumn(name = "person", referencedColumnName = "id", nullable = false)
    public Person getReference() {
        return reference.get();
    }

    /**
     * Accessor - setter.
     *
     * @param personByPerson value to set on {@link #reference}
     */
    public void setReference(Person personByPerson) {
        this.reference.set(personByPerson);
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #owner}
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    public JournalDt getOwner() {
        return owner;
    }

    /**
     * Accessor - setter.
     *
     * @param owner value to set on {@link #owner}
     */
    public void setOwner(JournalDt owner) {
        this.owner = owner;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #id}
     */
    public LongProperty idProperty() {
        return id;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #doiType}
     */
    public StringProperty doiTypeProperty() {
        return doiType;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #doiNum}
     */
    public StringProperty doiNumProperty() {
        return doiNum;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #fullName}
     */
    public StringProperty fullNameProperty() {
        return fullName;
    }

    /**
     * JavaFX accessor - property.
     *
     * @return the property {@link  #reference}
     */
    public ObjectProperty<Person> referenceProperty() {
        return reference;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof JournalDtPerson that && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
