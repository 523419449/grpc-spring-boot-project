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

package io.nity.grpc.sample.web;

import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.nity.grpc.sample.grpc.BaseMsg;
import io.nity.grpc.sample.grpc.WechatMsg;
import io.nity.grpc.sample.grpc.WechatServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WechatController {

    @Autowired
    private WechatServiceGrpc.WechatServiceBlockingStub wechatServiceGrpc;

    @RequestMapping(value = {"/baseMsg"})
    public String baseMsg() {
        WechatMsg wechatMsg;
        BaseMsg baseMsg = BaseMsg.newBuilder().build();
        WechatMsg reqwechatMsg = WechatMsg.newBuilder()
                .setBaseMsg(baseMsg)
                .build();
        log.info("greet  sent request ...");
        wechatMsg = wechatServiceGrpc.helloWechat(reqwechatMsg);
        log.info("greet receive response ...");
        return  wechatMsg.getBaseMsg().toString();
    }

}
