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
import com.yupay.perutax.dao.FolioContext;
import com.yupay.perutax.dao.PersonRole;
import com.yupay.perutax.entities.CostCenter;
import com.yupay.perutax.entities.Person;
import com.yupay.perutax.entities.TaxAccount;
import com.yupay.perutax.entities.TypeFolio;
import javafx.beans.value.ObservableValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * The searchable info class is a pretty wrapper
 * for the parameters needed to show a search result
 * dialog after performing the search in DAO layer.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public class SearchableInfo<T> {
    /**
     * A function to get a text input, run a query
     * and retrieve all results from database.
     */
    private Function<String, List<T>> query;
    /**
     * The headers texts to show in the selection dialog.
     */
    private String[] headers;
    /**
     * The columns widhts of info shown in the selection dialog.
     */
    private int[] widths;
    /**
     * Functions to map the observables in the selection dialog.
     */
    private Function<T, ObservableValue<String>>[] columnValues;
    /**
     * The text filtering predicate to allow user for finer control
     * of the search dialog.
     */
    private BiPredicate<String, T> filter;
    /**
     * A formatter to show the values in a table view cell.
     */
    private Function<T, String> formatter;

    /**
     * Static factory to create a searchable info for
     * TaxAccount entities.
     *
     * @return a new searchable info.
     */
    @Contract("->new")
    public static @NotNull SearchableInfo<TaxAccount> account() {
        return new SearchableInfo<TaxAccount>()
                .withColumnValues(
                        TaxAccount::idProperty,
                        TaxAccount::nameProperty,
                        x -> x.currencyProperty().asString())
                .withHeaders("Código", "Nombre", "Moneda")
                .withWidths(100, 350, 100)
                .withFilter((s, t)
                        -> t.getId().matches("^" + s + ".*")
                        || t.getName().matches("(?i).*" + s + ".*"))
                .withQuery(DAO.taxAccount().specialize()::search)
                .withFormatter(o -> o.getId() + " - " + o.getName());
    }

    /**
     * Static factory to create searchable information for cost centers.
     *
     * @return a new searchable info.
     */
    @Contract("->new")
    public static @NotNull SearchableInfo<CostCenter> costcenter() {
        return new SearchableInfo<CostCenter>()
                .withColumnValues(
                        CostCenter::idProperty,
                        CostCenter::titleProperty)
                .withHeaders("Código", "Descripción")
                .withWidths(100, 375)
                .withFilter((s, t)
                        -> t.getId().matches("^" + s + ".*")
                        || t.getTitle().matches("(?i).*" + s + ".*"))
                .withQuery(DAO.costcenter().specialize()::search)
                .withFormatter(o -> o.getId() + " - " + o.getTitle());
    }

    /**
     * Static factory to create searchable info for person entities.
     *
     * @param roles the person roles to allow in user search and selection.
     * @return a new searchable info object.
     */
    @Contract("_->new")
    public static @NotNull SearchableInfo<Person> person(
            @NotNull PersonRole @NotNull ... roles) {
        return new SearchableInfo<Person>()
                .withColumnValues(
                        p -> p.doiTypeProperty().asString(),
                        Person::doiNumProperty,
                        Person::fullNameProperty)
                .withHeaders("Tipo", "Documento", "Nombre")
                .withWidths(100, 150, 375)
                .withFilter((s, t)
                        -> t.getDoiNum().matches("^" + s + ".*")
                        || t.getFullName().matches("(?i).*" + s + ".*"))
                .withQuery(s -> DAO.person().specialize().search(s, roles));
    }

    /**
     * Searchable info to search for types of folio.
     *
     * @param contexts the folio type contexts to allow.
     * @return a new searchable info object.
     */
    @Contract("_->new")
    public static @NotNull SearchableInfo<TypeFolio> folioTypeAll(
            @NotNull FolioContext @NotNull ... contexts) {
        return new SearchableInfo<TypeFolio>()
                .withColumnValues(
                        TypeFolio::idProperty,
                        TypeFolio::titleProperty)
                .withHeaders("Código", "Nombre")
                .withWidths(150, 375)
                .withFilter((s, t)
                        -> t.getTitle().matches("(?i).*" + s + ".*"))
                .withQuery(s -> DAO.typeFolio().specialize().findByContext(contexts));
    }

    /**
     * Fluent setter - with.
     *
     * @param filter new value to set in {@link #filter}
     * @return this instance.
     */
    public final SearchableInfo<T> withFilter(BiPredicate<String, T> filter) {
        this.filter = filter;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #filter}
     */
    public final BiPredicate<String, T> getFilter() {
        return filter;
    }

    /**
     * Fluent setter - with.
     *
     * @param columnValues new value to set in {@link #columnValues}
     * @return this instance.
     */
    @SafeVarargs
    public final SearchableInfo<T> withColumnValues(@NotNull Function<T, ObservableValue<String>> @NotNull ... columnValues) {
        this.columnValues = columnValues;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #columnValues}
     */
    public final Function<T, ObservableValue<String>>[] getColumnValueAt() {
        return columnValues;
    }

    /**
     * Fluent setter - with.
     *
     * @param widths new value to set in {@link #widths}
     * @return this instance.
     */
    public final SearchableInfo<T> withWidths(int... widths) {
        this.widths = widths;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #widths}
     */
    public final int[] getWidths() {
        return widths;
    }

    /**
     * Fluent setter - with.
     *
     * @param headers new value to set in {@link #headers}
     * @return this instance.
     */
    public final SearchableInfo<T> withHeaders(@NotNull String @NotNull ... headers) {
        this.headers = headers;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #headers}
     */
    public final String[] getHeaders() {
        return headers;
    }

    /**
     * Fluent setter - with.
     *
     * @param query new value to set in {@link #query}
     * @return this instance.
     */
    public final SearchableInfo<T> withQuery(Function<String, List<T>> query) {
        this.query = query;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #query}
     */
    public final Function<String, List<T>> getQuery() {
        return query;
    }

    /**
     * Fluent setter - with.
     *
     * @param formatter new value to set in {@link #formatter}
     * @return this instance.
     */
    public final SearchableInfo<T> withFormatter(Function<T, String> formatter) {
        this.formatter = formatter;
        return this;
    }

    /**
     * Accessor - getter.
     *
     * @return value of {@link #formatter}
     */
    public final Function<T, String> getFormatter() {
        return formatter;
    }

}
