package com.github.caijh.framework.core.service.impl;

import com.github.caijh.framework.core.CallbackAction;
import com.github.caijh.framework.core.service.CallbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class DbCallbackServiceImpl implements CallbackService {

    @Override
    public void service(CallbackAction action) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    action.run(); // 事务提交后执行回调
                }
            });
        } else {
            action.run(); // 无事务时直接执行回调
        }
    }

}
