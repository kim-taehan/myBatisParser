package org.developx.mybatisParser.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.mapper.service.MapperService;
import org.developx.mybatisParser.domain.mapper.service.SqlService;
import org.developx.mybatisParser.web.data.MappersForm;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/mappers")
@RequiredArgsConstructor
public class MapperController {

    private final MapperService mapperService;

    private final SqlService sqlService;

    @GetMapping("")
    public String mappers(Model model, MappersForm mappersForm){

        mappersForm = mappersForm.fullName() == null ? MappersForm.init() : mappersForm;
        model.addAttribute("mappersForm", mappersForm);
        model.addAttribute("mappers", mapperService.findMappers(mappersForm.fullName(), mappersForm.tableName(), mappersForm.colName()));
        return "mappers/mappers";
    }

    @GetMapping("{mapperId}")
    public String mapper(Model model, @PathVariable Long mapperId){
        Mapper mapper = mapperService.findById(mapperId);
        model.addAttribute("mapper", mapper);
        model.addAttribute("sqls", sqlService.findSqls(mapper));
        return "mappers/mapper";
    }

}
