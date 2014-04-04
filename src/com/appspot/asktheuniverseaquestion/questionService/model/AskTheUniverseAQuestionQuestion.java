/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-04-01 18:14:47 UTC)
 * on 2014-04-04 at 08:50:14 UTC 
 * Modify at your own risk.
 */

package com.appspot.asktheuniverseaquestion.questionService.model;

/**
 * Model definition for AskTheUniverseAQuestionQuestion.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the questionService. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class AskTheUniverseAQuestionQuestion extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long day;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long month;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String question;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long year;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getDay() {
    return day;
  }

  /**
   * @param day day or {@code null} for none
   */
  public AskTheUniverseAQuestionQuestion setDay(java.lang.Long day) {
    this.day = day;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public AskTheUniverseAQuestionQuestion setId(java.lang.Double id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMonth() {
    return month;
  }

  /**
   * @param month month or {@code null} for none
   */
  public AskTheUniverseAQuestionQuestion setMonth(java.lang.Long month) {
    this.month = month;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getQuestion() {
    return question;
  }

  /**
   * @param question question or {@code null} for none
   */
  public AskTheUniverseAQuestionQuestion setQuestion(java.lang.String question) {
    this.question = question;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getYear() {
    return year;
  }

  /**
   * @param year year or {@code null} for none
   */
  public AskTheUniverseAQuestionQuestion setYear(java.lang.Long year) {
    this.year = year;
    return this;
  }

  @Override
  public AskTheUniverseAQuestionQuestion set(String fieldName, Object value) {
    return (AskTheUniverseAQuestionQuestion) super.set(fieldName, value);
  }

  @Override
  public AskTheUniverseAQuestionQuestion clone() {
    return (AskTheUniverseAQuestionQuestion) super.clone();
  }

}
