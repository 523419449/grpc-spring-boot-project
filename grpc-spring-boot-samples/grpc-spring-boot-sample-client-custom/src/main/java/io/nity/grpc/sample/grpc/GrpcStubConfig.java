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

package io.nity.grpc.sample.grpc;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.manualflowcontrol.StreamingGreeterGrpc;
import io.nity.grpc.DisposableManagedChannel;
import io.nity.grpc.autoconfigure.GrpcClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * grpc存根配置类，完成自定义Channel和所有Stub的初始化
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(GrpcClientProperties.class)
public class GrpcStubConfig {

    @Autowired
    protected GrpcClientProperties clientProperties;

    @Bean
    public GreeterGrpc.GreeterBlockingStub getGreeterBlockingStub(Channel channel) {
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(channel);
        return blockingStub;
    }

    @Bean
    public StreamingGreeterGrpc.StreamingGreeterStub getStreamingGreeterStub(Channel channel) {
        StreamingGreeterGrpc.StreamingGreeterStub streamingGreeterStub = StreamingGreeterGrpc.newStub(channel);
        return streamingGreeterStub;
    }

    @Bean
    @ConditionalOnProperty(value = "grpc.client.model", havingValue = GrpcClientProperties.SERVER_MODEL_CUSTOM)
    public DisposableManagedChannel getChannel() {
        int port = clientProperties.getPort();
        ManagedChannel channel;
        String host = clientProperties.getHost();

        log.info("will create custom channel");
        log.info("creating channel on {}:{}", host, port);

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        DisposableManagedChannel disposableManagedChannel = new DisposableManagedChannel(channel);
        return disposableManagedChannel;
    }

}
