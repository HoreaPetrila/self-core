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
package com.selfxdsd.core.projects;

import com.selfxdsd.api.*;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for {@link PmProjects}.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
public final class PmProjectsTestCase {

    /**
     * Registering a new Repo within a PM's Projects is
     * not allowed.
     */
    @Test(expected = IllegalStateException.class)
    public void registerIsForbidden() {
        final Projects projects = new PmProjects(1, new ArrayList<>());
        projects.register(
            Mockito.mock(Repo.class), Mockito.mock(ProjectManager.class)
        );
    }

    /**
     * Method assignedTo() returns itself if the id matches.
     */
    @Test
    public void assignedToReturnsItself() {
        final Projects projects = new PmProjects(1, new ArrayList<>());
        MatcherAssert.assertThat(
            projects.assignedTo(1), Matchers.is(projects)
        );
    }

    /**
     * Method assignedTo() throws an exception if the specified id
     * is the one of a different PM.
     */
    @Test (expected = IllegalStateException.class)
    public void assignedToComplainsOnDifferendId() {
        final Projects projects = new PmProjects(1, new ArrayList<>());
        projects.assignedTo(2);
    }

    /**
     * PmProjects can be iterated.
     */
    @Test
    public void iterateWorks() {
        final List<Project> list = new ArrayList<>();
        list.add(Mockito.mock(Project.class));
        list.add(Mockito.mock(Project.class));
        list.add(Mockito.mock(Project.class));
        final Projects projects = new PmProjects(1, list);
        MatcherAssert.assertThat(projects, Matchers.iterableWithSize(3));
    }

    /**
     * Method ownedBy(User) returns the User's projects.
     */
    @Test
    public void ownedByWorks() {
        final List<Project> list = new ArrayList<>();
        list.add(this.projectOwnedBy("mihai", "github"));
        list.add(this.projectOwnedBy("mihai", "github"));
        list.add(this.projectOwnedBy("vlad", "github"));
        list.add(this.projectOwnedBy("mihai", "gitlab"));
        list.add(this.projectOwnedBy("mihai", "github"));
        final Projects projects = new PmProjects(1, list);
        MatcherAssert.assertThat(
            projects.ownedBy(
                this.mockUser("mihai", "github")
            ),
            Matchers.iterableWithSize(3)
        );
    }

    /**
     * Mock a Project owned by a User.
     * @param username User's name.
     * @param providerName Provider's name.
     * @return Project.
     */
    private Project projectOwnedBy(
        final String username, final String providerName
    ) {
        final Project project = Mockito.mock(Project.class);
        final User owner = this.mockUser(username, providerName);
        Mockito.when(project.owner()).thenReturn(owner);

        return project;
    }

    /**
     * Mock a User.
     * @param username Username.
     * @param providerName Name of the provider.
     * @return User.
     */
    private User mockUser(final String username, final String providerName) {
        final User user = Mockito.mock(User.class);
        Mockito.when(user.username()).thenReturn(username);

        final Provider provider = Mockito.mock(Provider.class);
        Mockito.when(provider.name()).thenReturn(providerName);

        Mockito.when(user.provider()).thenReturn(provider);
        return user;
    }
}
