package com.yupay.perutax.entities.functionals;

import com.yupay.perutax.entities.TypeFolio;

import java.util.function.Function;

import static java.lang.Boolean.parseBoolean;

/**
 * The type folio parser is a function to read a txt file line
 * and parse each part from that. The file is a csv separated by
 * semicolon (;), expecting the following structure:
 * <pre>
 *     {id};{title};{regexSerie};{regexNumber}:{ctxtPurchase};{ctxtSale};{ctxtForeign};{ctxtTaxCredit}
 * </pre>
 * Only ; should be amitted as a separator provided the nature of regular expressions.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class TypeFolioParser implements Function<String, TypeFolio> {
    @Override
    public TypeFolio apply(String s) {
        if (s == null || s.isBlank()) return null;
        var str = s.strip();
        var parts = str.split(";", -1);
        var r = new TypeFolio();
        r.setId(parts[0]);
        r.setTitle(parts[1].strip().toUpperCase());
        r.setRegexSerie(parts[2].strip());
        r.setRegexNumber(parts[3].strip());
        r.setCtxtPurchase(parseBoolean(parts[4].strip()));
        r.setCtxtSale(parseBoolean(parts[5].strip()));
        r.setCtxtForeign(parseBoolean(parts[6].strip()));
        r.setCtxtTaxCredit(parseBoolean(parts[7].strip()));
        r.setTrash(false);
        return r;
    }
}
