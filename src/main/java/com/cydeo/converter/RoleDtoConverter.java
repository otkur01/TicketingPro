package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component

public class RoleDtoConverter implements Converter<String, RoleDTO> {

    RoleService roleService;
    ProjectService projectService;

    public RoleDtoConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(String source) {
        if (source == null || source.equals("")) {  //  Select  -> ""
            return null;
        }
        return roleService.findById(Long.parseLong(source));
    }

}
