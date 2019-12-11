package com.oo.businessplan.quzrtz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.quzrtz.mapper.SysTaskCronJobMapper;
import com.oo.businessplan.quzrtz.service.SysTaskCronJobService;
import com.oo.businessplan.quzrtz.pojo.SysTaskCronJob;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-12-11 17:49:54
 */
@Service("sysTaskCronJobService")
public class SysTaskCronJobServiceImpl extends BaseServiceImpl<SysTaskCronJob> implements SysTaskCronJobService {

    @Autowired
    SysTaskCronJobMapper sysTaskCronJobMapper;
}