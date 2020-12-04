package com.william.bc_william_server.service.impl;

import com.william.bc_william_server.mapper.WilliamRoleMapper;
import com.william.bc_william_server.service.IWilliamRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.william.pojo.WilliamRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mybatisPlusGenerator
 * @since 2019-11-20
 */
@Service
public class WilliamRoleServiceImpl extends ServiceImpl<WilliamRoleMapper, WilliamRole> implements IWilliamRoleService {


    /**
     * 查询用户角色
     * @author     xinchuang
     * @param uid :
     * @return : java.util.List<java.lang.String>
     */
    @Override
    public List<Integer> getUserRoleIdsByUid(String uid) {
        return this.getBaseMapper().getUserRoleIdsByUid(uid);
    }

    /**
     * 查询菜单和权限
     * @author     xinchuang
     * @param currentRoleId :
     * @return : java.util.List<java.lang.String>
     */
    @Override
    public List<Integer> getRoleMenuAndPIdsByRid(Integer currentRoleId) {
        return this.getBaseMapper().getRoleMenuAndPIdsByRid(currentRoleId);
    }

    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {

    }
}
