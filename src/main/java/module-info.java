/**
 * Monolithic module for the application.
 *
 * @author InfoYupay SACS
 */
module perutax {
    /*===========================*
     * Persistence requirements. *
     *===========================*/
    requires jakarta.persistence;
    requires eclipselink;
    requires org.postgresql.jdbc;

    /*==============================================*
     * JavaFX dependencies                          *
     * These requires are strategically placed      *
     * in order to take advantage of the transitive *
     * dependencies of the JavaFX modules.          *
     *==============================================*/
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;

    /*=====================*
     * Reactive libraries. *
     *=====================*/
    requires reactor.core;
    requires reactorfx;

    /*====================*
     * Interop libraries. *
     *====================*/
    requires com.google.gson;

    /*==============================*
     * IntelliJ tools requirements. *
     *==============================*/
    requires org.jetbrains.annotations;

    /*===========================*
     * Export directives for JPA *
     *===========================*/
    exports com.yupay.perutax.entities to jakarta.persistence, eclipselink;

    /*=========================*
     * Open directives for JFX *
     *=========================*/
    opens com.yupay.perutax.forms to javafx.fxml, javafx.graphics;
    //opens com.yupay.perutax.forms.inner to javafx.fxml, javafx.graphics;

    /*============================*
     * General export directives. *
     *============================*/
    exports com.yupay.perutax;
}