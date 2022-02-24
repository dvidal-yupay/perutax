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
