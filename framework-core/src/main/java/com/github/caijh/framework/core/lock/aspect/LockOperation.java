package com.github.caijh.framework.core.lock.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockOperation {

    private String key;

    private int expired = -1;

}
