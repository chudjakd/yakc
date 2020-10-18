/*
 * Copyright 2020 Marc Nuri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created on 2020-10-18, 10:51
 */
package com.marcnuri.yakc.model.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class IntOrStringSerializer extends JsonSerializer<String> {

  @Override
  public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers)
    throws IOException {
    try {
      jsonGenerator.writeNumber(Long.parseLong(value));
    } catch (NumberFormatException ex) {
      jsonGenerator.writeString(value);
    }
  }
}