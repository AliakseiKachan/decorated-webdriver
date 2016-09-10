/*
 * Copyright 2016 Alexei Barantsev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.stqa.selenium.wrapper;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class DecoratedTest {

  static class Fixture<T> {

    AbstractDecorated<T> deco;
    Topmost topmost;

    public Fixture(T original) {
      topmost = mock(Topmost.class);
      deco = new AbstractDecorated<T>(original) {
        @Override
        public Topmost getTopmostDecorated() {
          return topmost;
        }
      };
    }
  }

  @Test
  public void testConstructor() {
    Object obj = new Object();
    Fixture<Object> fixture = new Fixture<Object>(obj);
    assertThat(fixture.deco.getOriginal(), sameInstance(obj));
  }

  @Test
  public void testSetOriginal() {
    Fixture<String> fixture = new Fixture<String>("test");
    fixture.deco.setOriginal("diff");
    assertThat(fixture.deco.getOriginal(), equalTo("diff"));
  }

  @Test
  public void testToString() {
    Fixture<String> fixture = new Fixture<String>("test");
    assertThat(fixture.deco.toString(), equalTo("Decorated {test}"));
  }

  @Test
  public void testEquals() {
    Fixture<String> fixture1 = new Fixture<String>("test");
    Fixture<String> fixture2 = new Fixture<String>("test");
    assertThat(fixture1.deco, equalTo(fixture2.deco));
  }

  @Test
  public void testEqualToOriginal() {
    Fixture<String> fixture = new Fixture<String>("test");
    assertEquals(fixture.deco, "test");
  }

  @Test
  public void testHashCode() {
    Fixture<String> fixture = new Fixture<String>("test");
    assertEquals(fixture.deco.hashCode(), "test".hashCode());
  }

  @Test
  public void testDelegatesToTopmost() throws Throwable {
    WebElement mockedElement = mock(WebElement.class);
    when(mockedElement.isDisplayed()).thenReturn(true);
    Fixture<WebElement> fixture = new Fixture<WebElement>(mockedElement);
    WebElement decorated = new Decorator<WebElement>().activate(fixture.deco);

    //ArgumentCaptor<Decorated> tBefore = ArgumentCaptor.forClass(Decorated.class);
    //ArgumentCaptor<Decorated> tCall = ArgumentCaptor.forClass(Decorated.class);
    //ArgumentCaptor<Decorated> tAfter = ArgumentCaptor.forClass(Decorated.class);
    //ArgumentCaptor<Method> mBefore = ArgumentCaptor.forClass(Method.class);
    //ArgumentCaptor<Method> mCall = ArgumentCaptor.forClass(Method.class);
    //ArgumentCaptor<Method> mAfter = ArgumentCaptor.forClass(Method.class);

    //decorated.isDisplayed();

    //verify(fixture.topmost, times(1)).beforeMethodGlobal(tBefore.capture(), mBefore.capture(), any(Object[].class));
    //verify(fixture.topmost, times(1)).callMethodGlobal(tCall.capture(), mCall.capture(), any(Object[].class));
    //verify(fixture.topmost, times(1)).afterMethodGlobal(tAfter.capture(), mAfter.capture(), anyObject(), any(Object[].class));
  }

}
