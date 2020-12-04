package com.william.bc_william_server.controller;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.william.bc_william_server.service.IWilliamDeptService;
import com.william.constant.RespCodeAndMsg;
import com.william.pojo.DataGridView;
import com.william.pojo.Result;
import com.william.pojo.TreeNode;
import com.william.pojo.WilliamDept;
import com.william.pojo.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatisPlusGenerator
 * @since 2019-11-22
 */
@RestController
@RequestMapping("/william-dept")
public class WilliamDeptController {

    @Autowired
    private IWilliamDeptService deptService;



    /**
     * 加载部门管理左边的部门树的json
     */
    @PostMapping("loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(WilliamDept williamDept) {
        List<WilliamDept> list = this.deptService.list();
        List<TreeNode> treeNodes=new ArrayList<>();
        for (WilliamDept williamDepts : list) {
            Boolean spread = williamDepts.getOpen()==1;
            treeNodes.add(new TreeNode(williamDepts.getId(), String.valueOf(williamDepts.getPid()), williamDepts.getTitle(), spread));
        }
        System.out.println(JSONUtil.toJsonStr(new DataGridView(treeNodes)));
        return new DataGridView(treeNodes);
    }

    /**
     * 查询
     */
    @RequestMapping("loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo) {
        IPage<WilliamDept> page=new Page<>(deptVo.getPage(), deptVo.getLimit());
        QueryWrapper<WilliamDept> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()), "title", deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()), "address", deptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()), "remark", deptVo.getRemark());
        queryWrapper.eq(deptVo.getId()!=null, "id", deptVo.getId()).or().eq(deptVo.getId()!=null,"pid", deptVo.getId());
        queryWrapper.orderByAsc("seq");
        this.deptService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 加载最大的排序码
     * @param deptVo
     * @return
     */
    @RequestMapping("loadDeptMaxOrderNum")
    public Map<String,Object> loadDeptMaxOrderNum(){
        Map<String, Object> map=new HashMap<String, Object>();
        QueryWrapper<WilliamDept> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<WilliamDept> page=new Page<>(1, 1);
        List<WilliamDept> list = this.deptService.page(page, queryWrapper).getRecords();
        if(list.size()>0) {
            map.put("value", list.get(0).getSeq() + 1 );
        }else {
            map.put("value", 1);
        }
        return map;
    }


    /**
     * 添加
     * @param deptVo
     * @return
     */
    @RequestMapping("addDept")
    public Result addDept(DeptVo deptVo) {
        deptVo.setCreateTime(new Date());
        this.deptService.save(deptVo);
        return Result.getResult(RespCodeAndMsg.ADD_SUCCESS);
    }


    /**
     * 修改
     * @param deptVo
     * @return
     */
    @RequestMapping("updateDept")
    public Result updateDept(DeptVo deptVo) {
        this.deptService.updateById(deptVo);
        return Result.getResult(RespCodeAndMsg.UPDATE_SUCCESS);
    }


    /**
     * 查询当前的ID的部门有没有子部门
     */
    @RequestMapping("checkDeptHasChildrenNode")
    public Map<String,Object> checkDeptHasChildrenNode(DeptVo deptVo){
        Map<String, Object> map=new HashMap<String, Object>();
        QueryWrapper<WilliamDept> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid", deptVo.getId());
        List<WilliamDept> list = this.deptService.list(queryWrapper);
        if(list.size()>0) {
            map.put("value", true);
        }else {
            map.put("value", false);
        }
        return map;
    }

    /**
     * 删除
     * @param deptVo
     * @return
     */
    @RequestMapping("deleteDept")
    public Result deleteDept(DeptVo deptVo) {
        this.deptService.removeById(deptVo.getId());
        return Result.getResult(RespCodeAndMsg.UPDATE_SUCCESS);
    }

}
