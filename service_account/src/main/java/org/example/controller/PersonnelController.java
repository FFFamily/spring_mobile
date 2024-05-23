package org.example.controller;

import org.example.entity.CommonResponse;
import org.example.entity.Personnel;
import org.example.service.PersonnelService;
import org.example.vo.PersonnelListParamVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/account/personnel")
public class PersonnelController {
    @Resource
    private PersonnelService personnelService;


    @PostMapping("/list")
    public CommonResponse<Object> accountList(@RequestBody PersonnelListParamVo vo) {
        return CommonResponse.success(personnelService.findAccountList(vo));
    }

    @PostMapping("/register")
    public CommonResponse<Void> register(@RequestBody Personnel personnel) {
        personnelService.register(personnel);
        return CommonResponse.success();
    }


}
