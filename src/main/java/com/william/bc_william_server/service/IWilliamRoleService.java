package com.william.bc_william_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.william.pojo.WilliamRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatisPlusGenerator
 * @since 2019-11-20
 */
public interface IWilliamRoleService extends IService<WilliamRole> {

    List<Integer> getUserRoleIdsByUid(String uid);

    List<Integer> getRoleMenuAndPIdsByRid(Integer currentRoleId);

    // 保存角色和菜单权限之间的关系
    void saveRolePermission(Integer rid, Integer[] ids);
}
