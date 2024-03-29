/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.collect.testing.testers;

import com.google.common.collect.testing.AbstractCollectionTester;
import com.google.common.collect.testing.Helpers;

import java.util.Collection;
import java.util.List;

/**
 * Base class for list testers.
 *
 * @author George van den Driessche
 */
public class AbstractListTester<E> extends AbstractCollectionTester<E> {
  /*
   * Previously we had a field named list that was initialized to the value of
   * collection in setUp(), but that caused problems when a tester changed the
   * value of list or collection but not both.
   */
  protected final List<E> getList() {
    return (List<E>) collection;
  }

  /**
   * {@inheritDoc}
   * <p>
   * The {@code AbstractListTester} implementation overrides
   * {@link AbstractCollectionTester#expectContents(Collection)} to verify that
   * the order of the elements in the list under test matches what is expected.
   */
  @Override protected void expectContents(Collection<E> expectedCollection) {
    List<E> expected = Helpers.copyToList(expectedCollection);
    String context =
        String.format("expected collection %s; actual collection %s: ",
            expected, collection);
    assertEquals("size mismatch: " + context,
        expected.size(), getList().size());

    for (int i = 0; i < expected.size(); i++) {
      String indexContext = "mismatch at index " + i + ": " + context;
      assertEquals(indexContext, expected.get(i), getList().get(i));
    }
  }
}
