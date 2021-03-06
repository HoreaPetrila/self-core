/**
 * Copyright (c) 2020, Self XDSD Contributors
 * All rights reserved.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"),
 * to read the Software only. Permission is hereby NOT GRANTED to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.selfxdsd.core.mock;

import com.selfxdsd.api.*;
import com.selfxdsd.api.storage.Storage;
import com.selfxdsd.core.managers.StoredProjectManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * In memory PMs for testing purposes.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 * @todo #69:20min Add to the ability for {@link ProjectManagers} to add a
 *  project manager `addProjectManager(...)` and then test that using
 *  {@link InMemoryProjectManagers#getById(int)} and
 *  {@link InMemoryProjectManagers#pick(String)}
 */
public final class InMemoryProjectManagers implements ProjectManagers {

    /**
     * Parent storage.
     */
    private final Storage storage;

    /**
     * PMs's "table".
     */
    private final Map<Integer, ProjectManager> pms = new HashMap<>();

    /**
     * Constructor.
     * @param storage Parent storage.
     */
    public InMemoryProjectManagers(final Storage storage) {
        this.storage = storage;
        this.pms.put(
            1,
            new StoredProjectManager(1, "github", "123token", storage)
        );
        this.pms.put(
            2,
            new StoredProjectManager(2, "gitlab", "123token", storage)
        );
    }

    @Override
    public Iterator<ProjectManager> iterator() {
        return this.pms.values().iterator();
    }

    @Override
    public ProjectManager getById(final int id) {
        return this.pms.get(id);
    }

    @Override
    public ProjectManager pick(final String provider) {
        return pms.values().stream()
            .filter(pm -> pm.provider().equals(provider))
            .findFirst()
            .orElse(null);
    }
}
