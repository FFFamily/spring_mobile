package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Personnel;
import org.example.mapper.PersonnelMapper;
import org.example.vo.PersonnelListParamVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class PersonnelService {

    @Resource
    private PersonnelMapper personnelMapper;


    public Object findAccountList(PersonnelListParamVo vo) {
        log.info("查询用户列表：{}", vo);
        Page<Personnel> page = new Page<>(vo.getPage(), vo.getPageSize());
        return personnelMapper.selectPage(page, new QueryWrapper<>());
    }

    /**
     * 注册
     *
     * @param personnel
     */
    public void register(Personnel personnel) {
        log.info("【创建Personnel】");
        personnelMapper.insert(personnel);
    }
}
