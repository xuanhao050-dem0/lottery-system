package com.bit.lotterysystem.service;

import com.bit.lotterysystem.controller.param.CreateActivityParam;
import com.bit.lotterysystem.service.dto.CreateActivityDTO;
import org.springframework.stereotype.Service;

@Service
public interface ActivityService {
    CreateActivityDTO createActivity(CreateActivityParam param);
}
