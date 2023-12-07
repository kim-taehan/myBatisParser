package org.developx.mybatisParser.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.developx.mybatisParser.domain.analysis.finder.DirectoryFinder;
import org.developx.mybatisParser.domain.batch.BatchService;
import org.developx.mybatisParser.domain.snapshot.service.SnapshotService;
import org.developx.mybatisParser.global.properties.items.FileCheckProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;


@Controller
@Slf4j
@RequestMapping("/sample")
@RequiredArgsConstructor
public class HelloController {

    private final DirectoryFinder directoryFinder;
    private final SnapshotService snapshotService;
    private final BatchService batchService;

    @GetMapping("")
    public String call() throws IOException {


        // 동기로 처리
        Collection<File> xmlFiles = directoryFinder.findDirectory("D:\\000_source\\project\\mybatisParser\\samplesql");
        Long snapshot = snapshotService.createSnapshot(xmlFiles);

        // 비동기 처리 필요
        batchService.startBatch(snapshot);
        return "redirect:/";
    }
}
