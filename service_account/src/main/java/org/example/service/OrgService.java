package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Org;
import org.example.enums.AccountTypeEnum;
import org.example.mapper.OrgMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class OrgService {
    @Resource
    private OrgMapper orgMapper;

    @Resource
    private AccountService accountService;

    /**
     * 创建机构
     */
    @Transactional
    public void addOrg(Org org) {
        log.info("【创建机构开始】");
        accountService.checkUserName(org.getUserName());
        String accountId = accountService.createAccount(org, AccountTypeEnum.ORG.getCode());
        org.setAccountId(accountId);
//        BeanUtils.copyProperties(org,);
        orgMapper.insert(org);
        log.info("【创建机构结束】");
    }
}
