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

package com.yupay.perutax.forms.inner;

import com.yupay.perutax.dao.DAO;
import com.yupay.perutax.entities.JournalDtFolio;
import javafx.util.StringConverter;

/**
 * A string converter to easily show or parse a
 * JournalDtFolio object in format folioType-folioSerie-folioNum.
 * This will try to fetch a folio type from the database.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class JournalFolioInfoConverter extends StringConverter<JournalDtFolio> {
    @Override
    public String toString(JournalDtFolio object) {
        return object == null
                ? ""
                : String.join("-",
                object.getFolioType().getId(),
                object.getFolioSerie(),
                object.getFolioNum());
    }

    @Override
    public JournalDtFolio fromString(String string) {
        try {
            if (string == null) return null;
            if (string.matches("\\d{2}-\\p{Alnum}*-\\p{Alnum}+")) {
                var parts = string.split("-", -1);
                var r = new JournalDtFolio();
                r.setFolioType(DAO.typeFolio().fetch(parts[0]));
                r.setFolioSerie(parts[1]);
                r.setFolioNum(parts[2]);
                return r;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();//TODO: remove before distro
            return null;
        }
    }
}
