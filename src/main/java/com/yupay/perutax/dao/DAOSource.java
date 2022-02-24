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

package com.yupay.perutax.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

/**
 * The DAO source. It's singleton, app wide.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class DAOSource extends Thread {
    /**
     * The single instance, lazily initialized.
     */
    private static DAOSource INSTANCE;
    /**
     * The entity manager factory, initialized upon request.
     */
    private EntityManagerFactory emf;

    /**
     * The private initializer - singleton pattern.
     */
    private DAOSource() {
        Runtime.getRuntime().addShutdownHook(this);
    }

    /**
     * Static getter for the entity manager hold by the singleton.
     * Shorthand of {@link  #get()}.{@link #getEM()}
     *
     * @return the entity manager.
     */
    @NotNull
    public static EntityManager manager() {
        return Objects.requireNonNull(get().getEM());
    }

    /**
     * Static getter of the single instance.
     *
     * @return the single DAO source object.
     */
    @NotNull
    public static DAOSource get() {
        if (INSTANCE == null) {
            INSTANCE = new DAOSource();
        }
        return INSTANCE;
    }

    /**
     * Initializes the persistence (entity manager and its factory)
     * from an external .properties file. See in model-&gt;developer.properties
     * for a sample file and where to put it.
     *
     * @param settings the settings path.
     */
    public void initPersistence(@NotNull Path settings) {
        //Read properties from external file.
        var props = new Properties();
        try (var reader = Files
                .newBufferedReader(settings, StandardCharsets.UTF_8)) {
            props.load(reader);

            //Create entity manager and preserve factory for shutting down.
            emf = Persistence.createEntityManagerFactory("PUperutax", props);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read persistence .properties file.", e);
        }

    }

    /**
     * Creator of entity managers.
     *
     * @return the entity manager.
     */
    @Nullable
    public EntityManager getEM() {
        return emf.createEntityManager();
    }

    /**
     * Checks for entity manager factory status.
     * If it's initialized and open, will close.
     * Usually, this is invoked if user asks for a different
     * databse connection, so first you should stop persistence.
     * If user closes the app, there won't be any issue because
     * this is added as shutdown hook upon initialization.
     */
    public void stopPersistence() {
        if (emf != null && emf.isOpen()) emf.close();
        emf = null;
    }

    @Override
    public void run() {
        stopPersistence();
    }
}
