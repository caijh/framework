package com.github.caijh.framework.core.async.impl;


import com.github.caijh.framework.core.async.AsyncAction;
import com.github.caijh.framework.core.async.AsyncActionService;
import jakarta.inject.Inject;
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
