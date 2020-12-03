package com.common.platform.sys.modular.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.platform.sys.modular.system.entity.OperationLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.platform.sys.modular.system.mapper.OperationLogMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 */

@Service
public class OperationLogService extends ServiceImpl<OperationLogMapper,OperationLog> {

    /**
     * 获取操作日志
     */
    public List<Map<String,Object>> getOperationLogs(Page page,String beginTime,String endTime,String logName,String logType){
        return this.baseMapper.getOperationLogs(page,beginTime,endTime,logName,logType);
    }
}
