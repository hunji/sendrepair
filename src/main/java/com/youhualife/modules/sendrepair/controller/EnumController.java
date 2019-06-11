package com.youhualife.modules.sendrepair.controller;

import com.youhualife.common.utils.R;
import com.youhualife.modules.sendrepair.entity.enums.OrderStatusTypeEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("enum")
public class EnumController {
    @ApiOperation("取得状态枚举")
    @GetMapping("/status")
    public R getGradeEnum(){
        HashMap<Integer, String> map = new HashMap<>(7);
        for (OrderStatusTypeEnum item : OrderStatusTypeEnum.values()){
            map.put(item.getCode(),item.getDesc());
        }
        return R.ok().put("statusInfo",map);
    }
}
