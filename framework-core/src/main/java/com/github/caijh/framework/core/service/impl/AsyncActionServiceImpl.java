package com.github.caijh.framework.core.service.impl;

import javax.inject.Inject;

import com.github.caijh.framework.core.AsyncAction;
import com.github.caijh.framework.core.service.AsyncActionService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class AsyncActionServiceImpl implements AsyncActionService {

    @Inject
    private TaskExecutor taskExecutor;

    @Override
    public void service(AsyncAction action) {
        taskExecutor.execute(action);
    }

}
