/*
 * Copyright (C) 2007 Google Inc.
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

import com.google.common.collect.testing.Helpers;
import com.google.common.collect.testing.features.CollectionFeature;
import static com.google.common.collect.testing.features.CollectionFeature.ALLOWS_NULL_VALUES;
import static com.google.common.collect.testing.features.CollectionFeature.SUPPORTS_ADD;
import com.google.common.collect.testing.features.CollectionSize;
import static com.google.common.collect.testing.features.CollectionSize.ZERO;

import java.lang.reflect.Method;
import java.util.List;

/**
 * A generic JUnit test which tests {@code add(Object)} operations on a list.
 * Can't be invoked directly; please see
 * {@link com.google.common.collect.testing.ListTestSuiteBuilder}.
 *
 * @author Chris Povirk
 */
@SuppressWarnings("unchecked") // too many "unchecked generic array creations"
public class ListAddTester<E> extends AbstractListTester<E> {
  @CollectionFeature.Require(SUPPORTS_ADD)
  @CollectionSize.Require(absent = ZERO)
  public void testAdd_supportedPresent() {
    assertTrue("add(present) should return true", getList().add(samples.e0));
    expectAdded(samples.e0);
  }

  @CollectionFeature.Require(absent = SUPPORTS_ADD)
  @CollectionSize.Require(absent = ZERO)
  /*
   * absent = ZERO isn't required, since unmodList.add() must
   * throw regardless, but it keeps the method name accurate.
   */
  public void testAdd_unsupportedPresent() {
    try {
      getList().add(samples.e0);
      fail("add(present) should throw");
    } catch (UnsupportedOperationException expected) {
    }
  }

  @CollectionFeature.Require(value = {SUPPORTS_ADD, ALLOWS_NULL_VALUES})
  @CollectionSize.Require(absent = ZERO)
  public void testAdd_supportedNullPresent() {
    E[] array = createArrayWithNullElement();
    collection = getSubjectGenerator().create(array);
    assertTrue("add(nullPresent) should return true", getList().add(null));

    List<E> expected = Helpers.copyToList(array);
    expected.add(null);
    expectContents(expected);
  }

  /**
   * Returns the {@link Method} instance for
   * {@link #testAdd_supportedNullPresent()} so that tests can suppress it. See
   * {@link CollectionAddTester#getAddNullSupportedMethod()} for details.
   */
  public static Method getAddSupportedNullPresentMethod() {
    try {
      return ListAddTester.class.getMethod("testAdd_supportedNullPresent");
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }
}
