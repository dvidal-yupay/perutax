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

/**
 * This entity represents the class of purchase as specified
 * for purchases records in the SUNAT-PLE specification
 * (field #35 on book 050100, table #30) and is as follows
 * (translated from spanish):
 * <table style="border-spacing: 0; border-collapse: collapse">
 *     <tr style="border-spacing: 0; border-collapse: collapse;">
 *         <td style="border: 1px solid;">ID</td>
 *         <td style="border: 1px solid;">Title</td>
 *     </tr>
 *     <tr style="border-spacing: 0; border-collapse: collapse;">
 *         <td style="border: 1px solid;">1</td>
 *         <td style="border: 1px solid;">Commodities, materials, supplies, containers and packaging.</td>
 *     </tr>
 *     <tr style="border-spacing: 0; border-collapse: collapse;">
 *         <td style="border: 1px solid;">2</td>
 *         <td style="border: 1px solid;">Assets</td>
 *     </tr>
 *     <tr style="border-spacing: 0; border-collapse: collapse;">
 *         <td style="border: 1px solid;">3</td>
 *         <td style="border: 1px solid;">Assets others than stated in 1 and 2.</td>
 *     </tr>
 *     <tr style="border-spacing: 0; border-collapse: collapse;">
 *         <td style="border: 1px solid;">4</td>
 *         <td style="border: 1px solid;">Expenses for education, recreation, health,
 *         cultural, representation, training/lectures, travels,
 *         automotive maintenance and rewards.</td>
 *     </tr>
 *     <tr style="border-spacing: 0; border-collapse: collapse;">
 *         <td style="border: 1px solid;">5</td>
 *         <td style="border: 1px solid;">Expenses others than stated in 4.</td>
 *     </tr>
 * </table>
 */
@Entity
@Table(name = "folio_purchase_class", schema = "public")
public class FolioPurchaseClass {
    /**
     * The ID as specified in SUNAT-PLE tables.
     */
    private final IntegerProperty id =
            new SimpleIntegerProperty(this, "id");
    /**
     * The title to show to the user (unique).
     */
    private final StringProperty title =
            new SimpleStringProperty(this, "title");
    /**
     * The trash flag is used to know if the entity
     * may be used. Since this entity shouldn't be
     * deleted from database due relationships issues,
     * then this flag acts like a deleted indicator.
     */
    private final BooleanProperty trash =
            new SimpleBooleanProperty(this, "trash");

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #id}
     */
    @Id
    @Column(name = "id")
    public short getId() {
        return (short) id.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param id value to set into {@link #id}
     */
    public void setId(short id) {
        this.id.set(id);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #title}
     */
    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param title value to set into {@link #title}
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Public accessor - getter.
     *
     * @return value of {@link #trash}
     */
    @Basic
    @Column(name = "trash")
    public boolean isTrash() {
        return trash.get();
    }

    /**
     * Public accessor - setter.
     *
     * @param trash value to set into {@link #trash}
     */
    public void setTrash(boolean trash) {
        this.trash.set(trash);
    }

}
