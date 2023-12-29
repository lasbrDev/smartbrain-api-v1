package br.com.lasbr.smartbrain.domain.service;

import br.com.lasbr.smartbrain.infra.exception.ClarifaiServiceException;
import com.clarifai.channel.ClarifaiChannel;
import com.clarifai.credentials.ClarifaiCallCredentials;
import com.clarifai.grpc.api.*;

    public class ClarifaiService {

        private static final String PAT = "YOUR_PAT_HERE";
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
                throw new ClarifaiServiceException("Erro ao chamar o serviço Clarifai", e);
            }
        }
    }
