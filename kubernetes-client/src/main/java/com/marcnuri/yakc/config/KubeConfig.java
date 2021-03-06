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
 * Created on 2020-04-17, 4:16
 */
package com.marcnuri.yakc.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by Marc Nuri on 2020-04-17.
 */
@SuppressWarnings("WeakerAccess")
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KubeConfig {
  @JsonProperty("current-context")
  private String currentContext;
  private List<NamedCluster> clusters;
  private List<NamedContext> contexts;
  private List<NamedExtension> extensions;
  private List<NamedAuthInfo> users;
  private Map<String, Object> preferences;

  @NoArgsConstructor
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class NamedCluster {
    private String name;
    private Cluster cluster;
  }
  @NoArgsConstructor
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Cluster {
    @JsonProperty("certificate-authority")
    private String certificateAuthority;
    @JsonProperty("certificate-authority-data")
    private String certificateAuthorityData;
    @JsonProperty("insecure-skip-tls-verify")
    private Boolean insecureSkipTlsVerify;
    private String server;
    private List<NamedExtension> extensions;
  }
  @NoArgsConstructor
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class NamedContext {
    private String name;
    private Context context;
  }
  @NoArgsConstructor
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Context {
    private String cluster;
    private String namespace;
    private String user;
    private List<NamedExtension> extensions;
  }
  @NoArgsConstructor
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class NamedExtension {
    private String name;
    private AuthInfo user;
    private Map<String, Object> extension;
  }
  @NoArgsConstructor
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class AuthInfo {
    private String as;
    @JsonProperty("as-groups")
    private Map<String, Object> asGroups;
    @JsonProperty("as-user-extra")
    private Map<String, Object> asUserExtra;
    @JsonProperty("auth-provider")
    private Map<String, Object> authProvider;
    @JsonProperty("client-certificate")
    private String clientCertificate;
    @JsonProperty("client-certificate-data")
    private String clientCertificateData;
    @JsonProperty("client-key")
    private String clientKey;
    @JsonProperty("client-key-data")
    private String clientKeyData;
    private Map<String, Object> exec;
    private List<NamedExtension> extensions;
    private String password;
    private String token;
    private String tokenFile;
    private String username;
  }
  @NoArgsConstructor
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class NamedAuthInfo {
    private String name;
    private AuthInfo user;
    private Map<String, Object> extension;
  }
}
