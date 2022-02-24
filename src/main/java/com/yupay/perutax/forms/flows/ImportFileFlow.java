package com.yupay.perutax.forms.flows;

import com.yupay.perutax.dao.DAO;
import com.yupay.perutax.forms.ErrorAlert;
import com.yupay.perutax.forms.PeruTaxFXApp;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.yupay.perutax.forms.ErrorAlert.easy;

/**
 * Class to process csv files or other text files
 * and import the represented entities into database.
 *
 * @param <T> type erasure of imported entities.
 * @author InfoYupay SACS
 * @version 1.0
 */
public class ImportFileFlow<T> {
    /**
     * The filters to use in file chooser.
     */
    private final List<ExtensionFilter> filters = new ArrayList<>();
    /**
     * The entity class type.
     */
    private final Class<T> tClass;
    /**
     * The selected filter (from {@link #filters} list) index.
     */
    private int selectedFilter = 0;
    /**
     * The line parser is a function that converts an expected
     * line into an entity instance.
     */
    private Function<String, T> lineParser;
    /**
     * What to do if an error arises.
     */
    private Consumer<Throwable> onError;
    /**
     * Path reference to file when using drag-and-drop operations.
     * Also if user copy-pastes the source file in a textfield.
     */
    private Path dropFile;

    /**
     * Default constructor.
     *
     * @param tClass entity class.
     */
    private ImportFileFlow(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * Static factory of this class.
     *
     * @param entity the entity class.
     * @param <T>    type erasure of entity.
     * @return a new import file flow object.
     */
    @Contract("_->new")
    public static @NotNull <T> ImportFileFlow<T> forEntity(@NotNull Class<T> entity) {
        return new ImportFileFlow<>(entity);
    }

    /**
     * Fluent method to add an extension filter in the
     * filters list to show in file chooser to the user.
     *
     * @param filter the extension filter to add to list.
     * @return this instance.
     * @see #withDefaultFilters()
     */
    @Contract("_->this")
    public @NotNull ImportFileFlow<T> addFilter(@NotNull ExtensionFilter filter) {
        filters.add(filter);
        return this;
    }

    /**
     * Adds the default filters to use in the file chooser.
     * The default filters include: *.txt, *.csv and *.*
     * Also sets the default filter index to *.csv
     *
     * @return this instance.
     */
    @Contract("->this")
    public @NotNull ImportFileFlow<T> withDefaultFilters() {
        return addFilter(new ExtensionFilter("Todos los archivos (*.)", "*.*"))
                .addFilter(new ExtensionFilter(
                        "Archivo de texto plano (*.txt)",
                        "*.txt"))
                .addFilter(new ExtensionFilter(
                        "Archivo csv (*.csv)",
                        "*.csv"))
                .withSelectedFilter(2);
    }

    /**
     * Fluent setter for the selected filter index. When file chooser
     * will be selected, the selectedFilter index will be used for the
     * default user selection of the filter. The default value is 0, and
     * a standard index may be set using withDefaultFilters.
     *
     * @param selectedFilter the selected filter index.
     * @return this instance.
     * @see #withDefaultFilters()
     */
    @Contract("_->this")
    public @NotNull ImportFileFlow<T> withSelectedFilter(int selectedFilter) {
        this.selectedFilter = selectedFilter;
        return this;
    }

    /**
     * Fluent setter to set the mapping function to parse a text line
     * into an entity instance.
     *
     * @param lineParser the mapper function.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull ImportFileFlow<T> withLineParser(@NotNull Function<String, T> lineParser) {
        this.lineParser = lineParser;
        return this;
    }

    /**
     * What to do if an error arises. To use a default error handler,
     * you may invoke withErrorBriefing.
     *
     * @param onError the error handler.
     * @return this instance.
     * @see #withErrorBriefing(String)
     */
    @Contract("_->this")
    public @NotNull ImportFileFlow<T> withOnError(@NotNull Consumer<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    /**
     * Creates an ErrorAlert with given briefing text, and sets
     * as the error handler.
     *
     * @param briefing the error briefing text.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull ImportFileFlow<T> withErrorBriefing(@NotNull String briefing) {
        return withOnError(easy(briefing));
    }

    /**
     * Sets the path reference of a file to use instead of selected
     * by file chooser. If this is not null, FileChooser will be inhibited.
     *
     * @param dropFile the file to use as source.
     * @return this instance.
     */
    @Contract("_->this")
    public @NotNull ImportFileFlow<T> withDropFile(@NotNull Path dropFile) {
        this.dropFile = dropFile;
        return this;
    }

    /**
     * Delegated method to show the file chooser to the user.
     *
     * @return the user election (or empty if none).
     */
    private Optional<Path> chooseFile() {
        //If drop file is used, file chooser will be skipped.
        if (dropFile != null) return Optional.of(dropFile);
        var chooser = new FileChooser();
        chooser.getExtensionFilters().setAll(filters);
        chooser.setSelectedExtensionFilter(filters.get(selectedFilter));
        chooser.setTitle("Seleccionar Archivo de Origen");
        return Optional.ofNullable(chooser.showOpenDialog(PeruTaxFXApp.getPrimary()))
                .map(File::toPath);
    }

    /**
     * Delegated method to read each line of a choosen file, and
     * map it using the {@link  #lineParser}.
     *
     * @param choosen the choosen file.
     * @return stream of parsed entities.
     * @throws UncheckedIOException if IOException is caught.
     */
    private Stream<T> readSourceFile(@NotNull Path choosen) {
        try {
            return Files.readAllLines(choosen, StandardCharsets.UTF_8)
                    .stream()
                    .map(lineParser);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read choosen file source.", e);
        }
    }

    /**
     * Effectively runs/execute this flow.
     * The flow is thread-blocking.
     *
     * @return inserted elements.
     */
    @Blocking
    public @NotNull List<T> execute() {
        try {
            var ls = chooseFile().stream()
                    .flatMap(this::readSourceFile)
                    .filter(Objects::nonNull)
                    .toList();
            if (ls.isEmpty()) {
                throw new IllegalStateException("The file didn't contain valid items.");
            }
            return DAO.forEntity(tClass).insertMany(ls);
        } catch (Exception e) {
            onError.accept(e);
            return List.of();
        }
    }
}
