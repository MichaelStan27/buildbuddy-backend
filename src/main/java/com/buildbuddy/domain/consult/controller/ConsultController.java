package com.buildbuddy.domain.consult.controller;

import com.buildbuddy.domain.consult.dto.param.ChatReqParam;
import com.buildbuddy.domain.consult.dto.param.ConsultTransactionReqParam;
import com.buildbuddy.domain.consult.dto.param.ConsultantReqParam;
import com.buildbuddy.domain.consult.dto.param.RoomChatReqParam;
import com.buildbuddy.domain.consult.dto.request.TransactionReqDto;
import com.buildbuddy.domain.consult.dto.response.ConsultantSchema;
import com.buildbuddy.domain.consult.service.ConsultService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/consult")
@Validated
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    @GetMapping("/get-consultant")
    public ResponseEntity<Object> get(ConsultantReqParam param, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<ConsultantSchema> response = consultService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/get-transaction")
    public ResponseEntity<Object> getConsultTransaction(ConsultTransactionReqParam param, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = consultService.getTransaction(param);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/get-room-chat")
    public ResponseEntity<Object> transaction(RoomChatReqParam param, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = consultService.getRoomChat(param);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/get-chat")
    public ResponseEntity<Object> getChat(ChatReqParam param, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = consultService.getChat(param);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/auto-complete")
    public ResponseEntity<Object> autoComplete(HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = consultService.autoComplete();

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/transaction")
    public ResponseEntity<Object> transaction(@RequestBody TransactionReqDto body, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = consultService.transaction(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
