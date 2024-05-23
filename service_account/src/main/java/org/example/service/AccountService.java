package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Account;
import org.example.mapper.AccountMapper;
import org.example.vo.AccountListParamVo;
import org.springframework.beans.BeanUtils;
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

    public Account findAccountIdByUserName(String userName) {
        log.info("【查询业务员】{}", userName);
        return accountMapper.selectOne(new QueryWrapper<>());
    }

    public void register(Account account) {
        log.info("注册用户");
        // 生成 account 信息
        // 相比原系统就是缺少了注册 coreAccountId 以及 生成 personnel
        // 分配权限
        // 默认一开始是 未认证用户
//        account.setRoleType(RoleTypeEnum.USER.getCode());
        accountMapper.insert(account);
    }

    public Object findAccountList(AccountListParamVo vo) {
        log.info("查询用户列表：{}", vo);
        Page<Account> page = new Page<>(vo.getPage(), vo.getPageSize());
        return accountMapper.selectPage(page, new QueryWrapper<>());
    }


    public void checkUserName(String userName) {
        Account oldAccount = accountMapper.selectOne(new LambdaQueryWrapper<Account>().eq(Account::getUserName, userName));
        if (oldAccount != null) {
            throw new RuntimeException("当前账户已存在");
        }
    }

    public String createAccount(Account account, Integer accountType) {
        Account realAccount = new Account();
        BeanUtils.copyProperties(account, realAccount);
        realAccount.setAccountType(accountType);
        accountMapper.insert(realAccount);
        return realAccount.getId();
    }
}
