package br.com.jcsw.math.controller;

import br.com.jcsw.math.MathService;
import br.com.jcsw.math.OperationRequest;
import br.com.jcsw.math.OperationResponse;
import br.com.jcsw.math.aop.LogExecutionInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "Math API")
@RestController
public class MathController {

  @Autowired
  private MathService mathService;

  @PostMapping(path = "/math/operation", consumes = MediaType.APPLICATION_JSON_VALUE)
  @LogExecutionInfo
  public ResponseEntity executeMathOperation(@RequestBody OperationRequest operationRequest) {
    OperationResponse operationResponse = mathService.executeMathOperation(operationRequest);
    return ResponseEntity.ok(operationResponse);
  }

}