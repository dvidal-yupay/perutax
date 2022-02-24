package com.yupay.perutax.entities.functionals;

import com.yupay.perutax.entities.AccountNature;
import com.yupay.perutax.entities.CostGroup;
import com.yupay.perutax.entities.Currenci;
import com.yupay.perutax.entities.TaxAccount;

import java.util.function.Function;

/**
 * The tax account parser is a function to read a txt file line
 * and parse each part from that. The file is a csv separated by
 * semicolon (;) or pipe (|), expecting the following structure:
 * <pre>
 *     {id}|{name}|{nature}|{currency}|{groupCost}|{usable}
 * </pre>
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class TaxAccountParser implements Function<String, TaxAccount> {
    @Override
    public TaxAccount apply(String s) {
        if (s == null || s.isBlank()) return null;
        var str = s.strip();
        var parts = str.split("[|;]", -1);
        var r = new TaxAccount();
        r.setId(parts[0]);
        r.setName(parts[1]);
        r.setNature(AccountNature.valueOf(parts[2]));
        r.setCurrency(Currenci.valueOf(parts[3]));
        if (!parts[4].isBlank()) r.setGroupCost(CostGroup.valueOf(parts[4]));
        r.setUsable(Boolean.parseBoolean(parts[5]));
        r.setTrash(false);
        return r;
    }
}
