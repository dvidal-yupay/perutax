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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.eclipse.persistence.annotations.UuidGenerator;

import java.util.Objects;

/**
 * This table stores a folio documentation.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
@Entity
@Table(schema = "public", name = "folio_file")
public class FolioFile {
    /**
     * The ID of this object.
     */
    private final StringProperty id =
            new SimpleStringProperty(this, "id");
    /**
     * PDF representation of folio.
     */
    private final ObjectProperty<byte[]> pdf =
            new SimpleObjectProperty<>(this, "pdf");
    /**
     * XML file (for electronic - UBL compliant folios).
     */
    private final StringProperty xml =
            new SimpleStringProperty(this, "xml");
    /**
     * XML reception certificate (for electronic - SUNAT/PSE/OSE compliants).
     */
    private final StringProperty cdr =
            new SimpleStringProperty(this, "cdr");

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #id}.get();
     */
    @Id
    @Column(length = 36, name = "id")
    @UuidGenerator(name = "uuid_foliofile")
    @GeneratedValue(generator = "uuid_foliofile")
    public final String getId() {
        return id.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param id value to assign into {@link #id}.
     */
    public final void setId(String id) {
        this.id.set(id);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #id}.
     */
    public final StringProperty idProperty() {
        return id;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #pdf}.get();
     */
    @Basic
    @Column(nullable = false, name = "pdf")
    public final byte[] getPdf() {
        return pdf.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param pdf value to assign into {@link #pdf}.
     */
    public final void setPdf(byte[] pdf) {
        this.pdf.set(pdf);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #pdf}.
     */
    public final ObjectProperty<byte[]> pdfProperty() {
        return pdf;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #xml}.get();
     */
    @Basic
    @Column(name = "xml")
    public final String getXml() {
        return xml.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param xml value to assign into {@link #xml}.
     */
    public final void setXml(String xml) {
        this.xml.set(xml);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #xml}.
     */
    public final StringProperty xmlProperty() {
        return xml;
    }

    /**
     * FX Accessor - getter.
     *
     * @return value of {@link #cdr}.get();
     */
    @Basic
    @Column(name = "cdr")
    public final String getCdr() {
        return cdr.get();
    }

    /**
     * FX Accessor - setter.
     *
     * @param cdr value to assign into {@link #cdr}.
     */
    public final void setCdr(String cdr) {
        this.cdr.set(cdr);
    }

    /**
     * FX Accessor - property.
     *
     * @return property {@link #cdr}.
     */
    public final StringProperty cdrProperty() {
        return cdr;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FolioFile folioFile &&
                Objects.equals(getId(), folioFile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
