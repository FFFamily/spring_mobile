package org.example.controller;

import org.example.entity.Account;
import org.example.entity.CommonResponse;
import org.example.service.AccountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountService accountService;

    /**
     * 创建业务员
     */
    @PostMapping("/save")
    public CommonResponse<Void> addAccount(@RequestBody Account account) {
        accountService.addAccount(account);
        return CommonResponse.success();
    }

    @GetMapping("/findById/{accountId}")
    public CommonResponse<Account> findAccountById(@PathVariable String accountId) {
        return CommonResponse.success(accountService.findAccountById(accountId));
    }

    @PostMapping("/register")
    public CommonResponse<Void> register(@RequestBody Account account) {
        accountService.register(account);
        return CommonResponse.success();
    }


}
