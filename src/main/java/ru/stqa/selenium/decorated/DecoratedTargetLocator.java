/*
 * Copyright 2016 Alexei Barantsev
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package ru.stqa.selenium.decorated;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DecoratedTargetLocator extends AbstractDecoratedChild<WebDriver.TargetLocator,DecoratedWebDriver> implements WebDriver.TargetLocator {

  public DecoratedTargetLocator(final WebDriver.TargetLocator targetLocator, final DecoratedWebDriver driverWrapper) {
    super(targetLocator, driverWrapper);
  }

  @Override
  public WebDriver frame(int frameIndex) {
    getOriginal().frame(frameIndex);
    return activate(getTopmostDecorated());
  }

  @Override
  public WebDriver frame(String frameName) {
    getOriginal().frame(frameName);
    return activate(getTopmostDecorated());
  }

  @Override
  public WebDriver frame(WebElement frameElement) {
    getOriginal().frame(frameElement);
    return activate(getTopmostDecorated());
  }

  @Override
  public WebDriver parentFrame() {
    getOriginal().parentFrame();
    return activate(getTopmostDecorated());
  }

  @Override
  public WebDriver window(String windowName) {
    getOriginal().window(windowName);
    return activate(getTopmostDecorated());
  }

  @Override
  public WebDriver defaultContent() {
    getOriginal().defaultContent();
    return activate(getTopmostDecorated());
  }

  @Override
  public WebElement activeElement() {
    return getTopmostDecorated().activate(getTopmostDecorated().createDecorated(getOriginal().activeElement()));
  }

  @Override
  public Alert alert() {
    return activate(getTopmostDecorated().createDecorated(getOriginal().alert()));
  }
}