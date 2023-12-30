package br.com.lasbr.smartbrain.domain.service;

import br.com.lasbr.smartbrain.infra.exception.ClarifaiServiceException;
import com.clarifai.channel.ClarifaiChannel;
import com.clarifai.credentials.ClarifaiCallCredentials;
import com.clarifai.grpc.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

    @Service
    public class ClarifaiService {

        private static final Logger logger = LoggerFactory.getLogger(ClarifaiService.class);
        private static final String PAT = "11412dfb88e04deabfa9336c2156367f";
        private static final String USER_ID = "clarifai";
        private static final String APP_ID = "main";
        private static final String MODEL_ID = "general-image-detection";
        private static final String MODEL_VERSION_ID = "1580bb1932594c93b7e2e04456af7c6f";

        public MultiOutputResponse detectFace(String imageUrl) {
            try {
                V2Grpc.V2BlockingStub stub = V2Grpc.newBlockingStub(ClarifaiChannel.INSTANCE.getGrpcChannel())
                        .withCallCredentials(new ClarifaiCallCredentials(PAT));

                return stub.postModelOutputs(
                        PostModelOutputsRequest.newBuilder()
                                .setUserAppId(UserAppIDSet.newBuilder().setUserId(USER_ID).setAppId(APP_ID))
                                .setModelId(MODEL_ID)
                                .setVersionId(MODEL_VERSION_ID)
                                .addInputs(
                                        Input.newBuilder().setData(
                                                Data.newBuilder().setImage(
                                                        Image.newBuilder().setUrl(imageUrl)
                                                )
                                        )
                                )
                                .build()
                );
            } catch (Exception e) {
                logger.error("Erro ao chamar o serviço Clarifai", e);
                throw new ClarifaiServiceException("Erro ao chamar o serviço Clarifai", e);
            }
        }
    }
