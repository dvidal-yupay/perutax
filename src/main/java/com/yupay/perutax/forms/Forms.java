package com.yupay.perutax.forms;

import com.yupay.perutax.dao.FolioContext;
import com.yupay.perutax.forms.inner.SearchableInfo;
import javafx.fxml.FXMLLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.function.Supplier;

/**
 * Static factory for all app forms.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class Forms {
    /**
     * Enforce static factory initialization forbidden.
     *
     * @throws IllegalAccessException always.
     */
    @Contract("->fail")
    private Forms() throws IllegalAccessException {
        throw new IllegalAccessException("Don't instanciate a static factory.");
    }

    /**
     * Utility method to get FXML url reference.
     *
     * @param resource the resource to get (Fxml form).
     * @return the URL pointing to an fxml form.
     */
    @Nullable
    private static URL getFXML(String resource) {
        return Forms.class.getResource(resource);
    }

    /**
     * Utility method to load the controller using a fxml loader.
     *
     * @param resource the resource (Fxml file) to load.
     * @param factory  the factory for controller instances.
     * @param <T>      type erausre of controller class.
     * @return loaded controller class.
     */
    @NotNull
    private static <T> T load(
            @NotNull String resource,
            @NotNull Supplier<T> factory) {
        try {
            var loader = new FXMLLoader(getFXML(resource));
            loader.setControllerFactory(c -> c.cast(factory.get()));
            loader.load();
            return loader.getController();
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read form " + resource, e);
        }
    }

    /**
     * Static factory for an application.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static PeruTaxFX application() {
        return load("application.fxml", PeruTaxFX::new);
    }

    /**
     * Static factory for a country-card.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static CountryCard countryCard() {
        return load("country-card.fxml", CountryCard::new);
    }

    /**
     * Static factory for a country-view.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static CountryView countryView() {
        return load("country-view.fxml", CountryView::new);
    }

    /**
     * Static factory for a tdoi-card.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static TDoiCard tDoiCard() {
        return load("tdoi-card.fxml", TDoiCard::new);
    }

    /**
     * Static factory for a tdoi-view.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static TDoiView tDoiView() {
        return load("tdoi-view.fxml", TDoiView::new);
    }

    /**
     * Static factory for a tdoi-card.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static PersonCard personCard() {
        return load("person-card.fxml", PersonCard::new);
    }

    /**
     * Static factory for a tdoi-view.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static PersonView personView() {
        return load("person-view.fxml", PersonView::new);
    }

    /**
     * Static factory for a taxperiod.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static TaxPeriodForm periodForm() {
        return load("taxperiod.fxml", TaxPeriodForm::new);
    }

    /**
     * Static factory for a xrate.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static XRateForm xrate() {
        return load("xrate.fxml", XRateForm::new);
    }

    /**
     * Static factory for a costcenter.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static CostCenterForm costcenter() {
        return load("costcenter.fxml", CostCenterForm::new);
    }

    /**
     * Static factory for a correlative.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static CorrelativeForm correlative() {
        return load("correlative.fxml", CorrelativeForm::new);
    }

    /**
     * Static factory for a taxaccount-view.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static TaxAccountView taxAccountView() {
        return load("taxaccount-view.fxml", TaxAccountView::new);
    }

    /**
     * Static factory for a taxaccount-card.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static TaxAccountCard taxAccountCard() {
        return load("taxaccount-card.fxml", TaxAccountCard::new);
    }

    /**
     * Static factory for a tfolio-card.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static TypeFolioCard typeFolioCard() {
        return load("tfolio-card.fxml", TypeFolioCard::new);
    }

    /**
     * Static factory for a tfolio-view.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static TypeFolioView typeFolioView() {
        return load("tfolio-view.fxml", TypeFolioView::new);
    }

    /**
     * Static factory for a subdiary-card.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static SubdiaryCard subdiaryCard() {
        return load("subdiary-card.fxml", SubdiaryCard::new);
    }

    /**
     * Static factory for a subdiary-view.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static SubdiaryView subdiaryView() {
        return load("subdiary-view.fxml", SubdiaryView::new);
    }

    /**
     * Static factory for a journal-card.fxml form.
     *
     * @return the controller class.
     */
    @NotNull
    public static JournalCard journalCard() {
        return load("journal-card.fxml", JournalCard::new);
    }

    /**
     * Static factory for a search.fxml form.
     *
     * @param info the search information object.
     * @param <T>  type erasure of searchable entity.
     * @return the controller class.
     */
    @NotNull
    public static <T> SearchDialog<T> search(SearchableInfo<T> info) {
        return load("search.fxml", () -> new SearchDialog<>(info));
    }


    /**
     * Static factory for a folio-info.fxml form.
     *
     * @param contexts the approved folio contexts.
     * @return the controller class.
     */
    @NotNull
    public static JournalDtFolioDialog folioInfo(@NotNull FolioContext @NotNull ... contexts) {
        return load("folio-info.fxml", () -> new JournalDtFolioDialog(contexts));
    }

}
