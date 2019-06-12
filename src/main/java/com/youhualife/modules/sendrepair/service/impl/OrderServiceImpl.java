package com.youhualife.modules.sendrepair.service.impl;

import cn.hutool.core.convert.Convert;
import com.youhualife.common.utils.Constant;
import com.youhualife.modules.sys.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youhualife.common.utils.PageUtils;
import com.youhualife.common.utils.Query;

import com.youhualife.modules.sendrepair.dao.OrderDao;
import com.youhualife.modules.sendrepair.entity.OrderEntity;
import com.youhualife.modules.sendrepair.service.OrderService;

import static com.youhualife.common.utils.ShiroUtils.getUserId;


/**
 * @author ZQ
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 查询送修单时根据角色去进行数据权限限定
        // 首先排除掉admin用户
        // 默认以下三个用户只分配一个角色
        // 1.动车段（角色）用户只能看到创建人是自己的数据 createBy
        // 2.技术科（角色）用户只能看到提交上来或者是审核过的数据 OrderStatusTypeEnum>=10
        // 3.智奇用户（角色）只能看到审核通过后的数据OrderStatusTypeEnum>=20
        if(getUserId() != Constant.SUPER_ADMIN){
            Long userId = getUserId();
            Integer roleId = Math.toIntExact(sysUserRoleService.queryRoleIdList(userId).get(0));
            switch(roleId){
                case 2:
                    params.put("createBy",userId);
                    break;
                case 3:
                    params.put("status",10);
                    break;
                case 4:
                    params.put("status",20);
                    break;
                default:
            }
        }
        Long createBy = Convert.toLong(params.get("createBy"),null);
        Integer status = Convert.toInt(params.get("status"),null);
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
                    .eq(createBy!=null ,"create_by",createBy)
                    .gt(status!=null,"status",status)
        );
        return new PageUtils(page);
    }

}