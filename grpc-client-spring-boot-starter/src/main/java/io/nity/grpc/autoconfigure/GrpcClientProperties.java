/*
 * Copyright 2019 The nity.io gRPC Spring Boot Project Authors
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
 */

package io.nity.grpc.autoconfigure;

import io.grpc.Metadata;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "grpc.client", ignoreUnknownFields = true)
public class GrpcClientProperties {

    public static final int DEFAULT_SERVER_PORT = 50051;

    /**
     * fully-featured, high performance, useful in testing
     */
    public static final String SERVER_MODEL_IN_PROCESS = "inProcess";

    /**
     * plaintext without TLS.
     * While this is convenient for testing environments, users must be aware of the security risks of doing so for real production systems.
     */
    public static final String SERVER_MODEL_SIMPLE = "simple";

    /**
     * service with TLS, safely use to talk to external systems
     */
    public static final String SERVER_MODEL_TLS = "tls";

    /**
     * custom Channel
     */
    public static final String SERVER_MODEL_CUSTOM = "custom";

    /**
     * custom AppId
     */
    public static final String DEFAULT_APPID = "";
    /**
     * custom AppKey
     */
    public static final String DEFAULT_APPKEY = "";

    /**
     * custom MachineCode
     */
    public static final String DEFAULT_MACHINECODE = "";





    /**
     * gRPC running model, default simple
     */
    private String model = SERVER_MODEL_SIMPLE;

    /**
     * In process server name.
     */
    private String inProcessServerName;

    /**
     * gRPC stub host
     */
    private String host = "";

    /**
     * gRPC stub port
     */
    private int port = DEFAULT_SERVER_PORT;

    private String trustCertCollectionFilePath;
    private String clientCertChainFilePath;
    private String clientPrivateKeyFilePath;
    /**
     * gRPC stub appid
     */
    private String appid = DEFAULT_APPID;



    /**
     * gRPC stub appkey
     */
    private String appkey = DEFAULT_APPKEY;
    /**
     * gRPC stub metadata
     */
    private Metadata metadata ;

    /**
     * gRPC stub machineCode
     */
    private String machinecode = DEFAULT_MACHINECODE;
    public String getMachinecode() {
        return machinecode;
    }

    public void setMachinecode(String machinecode) {
        this.machinecode = machinecode;
    }


    public String getAppId() {
        return appid;
    }

    public void setAppId(String appid) {
        this.appid = appid;
    }

    public Metadata getMetadata(String appid,String appkey) {
        this.appid = appid;
        this.appkey = appkey;
        return getMetadata();
    }


    public Metadata getMetadata() {
        if(appid.equals("")  || appkey.equals("")) {
            return null;
        } else {
            metadata = new Metadata();
            metadata.put(Metadata.Key.of("appid", Metadata.ASCII_STRING_MARSHALLER), appid);
            metadata.put(Metadata.Key.of("appkey", Metadata.ASCII_STRING_MARSHALLER), appkey);
            return metadata;
        }
    }
    public void setMetadata(String appid,String appkey) {
        this.appid = appid;
        this.appkey = appkey;
        getMetadata();
    }
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getAppKey() {
        return appkey;
    }

    public void setAppKey(String appkey) {
        this.appkey = appkey;
    }
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTrustCertCollectionFilePath() {
        return trustCertCollectionFilePath;
    }

    public void setTrustCertCollectionFilePath(String trustCertCollectionFilePath) {
        this.trustCertCollectionFilePath = trustCertCollectionFilePath;
    }

    public String getClientCertChainFilePath() {
        return clientCertChainFilePath;
    }

    public void setClientCertChainFilePath(String clientCertChainFilePath) {
        this.clientCertChainFilePath = clientCertChainFilePath;
    }

    public String getClientPrivateKeyFilePath() {
        return clientPrivateKeyFilePath;
    }

    public void setClientPrivateKeyFilePath(String clientPrivateKeyFilePath) {
        this.clientPrivateKeyFilePath = clientPrivateKeyFilePath;
    }

    public String getInProcessServerName() {
        return inProcessServerName;
    }

    public void setInProcessServerName(String inProcessServerName) {
        this.inProcessServerName = inProcessServerName;
    }
}
