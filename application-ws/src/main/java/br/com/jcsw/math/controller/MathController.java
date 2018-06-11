package br.com.jcsw.math.controller;

import br.com.jcsw.math.MathService;
import br.com.jcsw.math.OperationRequest;
import br.com.jcsw.math.OperationResponse;
import br.com.jcsw.math.aop.LogExecutionInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "Math API")
@RestController
public class MathController {

  @Autowired
  private MathService mathService;

  @GetMapping(path = "/")
  public String index() {
    return "Welcome to Math API";
  }

  @PostMapping(path = "/math/calculate")
  @LogExecutionInfo
  public ResponseEntity calculate(@RequestBody OperationRequest operationRequest) {
    OperationResponse operationResponse = mathService.executeMathOperation(operationRequest);
    return ResponseEntity.ok(operationResponse);
  }

}
