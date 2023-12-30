package br.com.lasbr.smartbrain.controller;

import br.com.lasbr.smartbrain.domain.service.ClarifaiService;
import br.com.lasbr.smartbrain.infra.exception.ClarifaiServiceException;
import com.clarifai.grpc.api.MultiOutputResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
    @RequestMapping("api/v1/detect-face")
    public class ClarifaiController {

        private final ClarifaiService service;

        @Autowired
        public ClarifaiController(ClarifaiService service) {
            this.service = service;
        }

        @PostMapping
        public ResponseEntity<?> detectFace(@RequestBody Map<String, String> request) {
            String imageUrl = request.get("imageUrl");
            try {
                MultiOutputResponse response = service.detectFace(imageUrl);
                return ResponseEntity.ok(response);
            } catch (ClarifaiServiceException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao chamar o serviço Clarifai" + e.getMessage());
            }
        }
    }
