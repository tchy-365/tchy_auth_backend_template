package com.william.bc_william_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.william.pojo.WilliamRole;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mybatisPlusGenerator
 * @since 2019-11-20
 */
public interface WilliamRoleMapper extends BaseMapper<WilliamRole> {

    List<Integer> getUserRoleIdsByUid(String uid);

    List<Integer> getRoleMenuAndPIdsByRid(Integer currentRoleId);
}
