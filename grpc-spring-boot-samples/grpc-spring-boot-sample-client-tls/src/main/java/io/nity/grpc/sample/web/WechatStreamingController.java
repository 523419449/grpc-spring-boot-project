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

import com.google.common.collect.Lists;
import io.grpc.examples.manualflowcontrol.StreamingGreeterGrpc;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import io.grpc.stub.StreamObserver;
import io.nity.grpc.sample.grpc.WechatMsg;
import io.nity.grpc.sample.grpc.WechatServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
public class WechatStreamingController {

    @Autowired
    private WechatServiceGrpc.WechatServiceStub wechatServiceStub;

    @RequestMapping(value = {"/wechatService"})
    public String WechatMsg() {
        WechatMsg.Builder wechatMsgBuilder = WechatMsg.newBuilder() ;
        log.info("greet sent  request ...");
        List<WechatMsg> names = Lists.newArrayList();
        names.add(wechatMsgBuilder.build());
        ClientResponseObserver<WechatMsg, WechatMsg> clientResponseObserver = new ClientResponseObserver<WechatMsg,WechatMsg>() {
                    ClientCallStreamObserver<WechatMsg> requestStream;
                    @Override
                    public void beforeStart(final ClientCallStreamObserver<WechatMsg> requestStream) {
                        this.requestStream = requestStream;
                        requestStream.disableAutoInboundFlowControl();
                        requestStream.setOnReadyHandler(new Runnable() {
                            Iterator<WechatMsg> iterator = names.iterator();
                            @Override
                            public void run() {
                                while (requestStream.isReady()) {
                                    if (iterator.hasNext()) {
                                        // Send more messages if there are more messages to send.
                                        WechatMsg name = iterator.next();
                                        log.info(" --> " + name);
                                        WechatMsg request =WechatMsg.newBuilder().setBaseMsg(name.getBaseMsg()).build();
                                        requestStream.onNext(request);
                                    } else {
                                        // Signal completion if there is nothing left to send.
                                        requestStream.onCompleted();
                                    }
                                }
                            }
                        });
                    }
                    @Override
                    public void onNext(WechatMsg value) {
                        log.info("<-- " + value.getBaseMsg());
                        // Signal the sender to send one message.
                        requestStream.request(1);
                    }
                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {
                        log.info("All Done");
                    }
                };
        StreamObserver<WechatMsg> helloRequestStreamObserver = wechatServiceStub.helloWechatStreaming(clientResponseObserver);

        return "done";
    }


}
