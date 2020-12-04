package com.william.bc_william_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.william.pojo.WilliamMenu;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author mybatisPlusGenerator
 * @since 2019-11-20
 */
public interface IWilliamMenuService extends IService<WilliamMenu> {

    List<Integer> queryRolePermissionIdsByRid(String roleId);
}
