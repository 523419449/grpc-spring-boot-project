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

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.Channel;
import io.grpc.Metadata;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.manualflowcontrol.StreamingGreeterGrpc;
import io.grpc.stub.MetadataUtils;
import io.nity.grpc.autoconfigure.GrpcClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * grpc存根配置类，完成所有Stub的初始化
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(GrpcClientProperties.class)
public class WechatGrpcStubConfig {
    private final GrpcClientProperties clientProperties;
    private final Metadata metadata = new Metadata();
    @Autowired
    public WechatGrpcStubConfig(GrpcClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }
    @Bean
    public WechatServiceGrpc.WechatServiceFutureStub wechatServiceFutureStub(Channel channel){
        WechatServiceGrpc.WechatServiceFutureStub FutureStub = WechatServiceGrpc.newFutureStub(channel);
        FutureStub = MetadataUtils.attachHeaders(FutureStub, metadata);





        return FutureStub;
    }
    @Bean
    public WechatServiceGrpc.WechatServiceBlockingStub getWechatServiceBlockingStub(Channel channel) {
        WechatServiceGrpc.WechatServiceBlockingStub blockingStub = WechatServiceGrpc.newBlockingStub(channel);
        blockingStub = MetadataUtils.attachHeaders(blockingStub, metadata);
        return blockingStub;
    }





    @Bean
    public WechatServiceGrpc.WechatServiceStub getStreamingGreeterStub(Channel channel) {
        WechatServiceGrpc.WechatServiceStub wechatServiceStub = WechatServiceGrpc.newStub(channel);
        wechatServiceStub = MetadataUtils.attachHeaders(wechatServiceStub, metadata);
        return wechatServiceStub;
    }
}
