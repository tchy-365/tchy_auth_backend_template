package com.william.bc_william_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.william.pojo.WilliamMenu;

import java.util.List;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author mybatisPlusGenerator
 * @since 2019-11-20
 */
public interface WilliamMenuMapper extends BaseMapper<WilliamMenu> {

    List<Integer> queryRolePermissionIdsByRid(String roleId);
}
