package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Account;
import org.example.enums.RoleTypeEnum;
import org.example.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class AccountService {

    @Resource
    private AccountMapper accountMapper;

    /**
     * 创建机构
     */
    public void addAccount(Account account) {
        log.info("【创建业务员】");
        accountMapper.insert(account);
    }

    public Account findAccountById(String accountId) {
        log.info("【查询业务员】{}", accountId);
        return accountMapper.selectById(accountId);
    }

    public void register(Account account) {
        log.info("注册用户");
        // 生成 account 信息
        // 相比原系统就是缺少了注册 coreAccountId 以及 生成 personnel
        // 分配权限
        // 默认一开始是 未认证用户
        account.setRoleType(RoleTypeEnum.USER.getCode());
        accountMapper.insert(account);
    }
}
