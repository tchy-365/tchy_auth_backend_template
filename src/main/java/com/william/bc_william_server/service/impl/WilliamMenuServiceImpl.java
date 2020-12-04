package com.william.bc_william_server.service.impl;

import com.william.bc_william_server.mapper.WilliamMenuMapper;
import com.william.bc_william_server.service.IWilliamMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.william.pojo.WilliamMenu;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author mybatisPlusGenerator
 * @since 2019-11-20
 */
@Service
public class WilliamMenuServiceImpl extends ServiceImpl<WilliamMenuMapper, WilliamMenu> implements IWilliamMenuService {

    @Override
    public List<Integer> queryRolePermissionIdsByRid(String roleId) {
        return this.getBaseMapper().queryRolePermissionIdsByRid(roleId);
    }
}
