package com.ninjabyte.puzzle.integration;

import org.springframework.integration.file.filters.ResettableFileListFilter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class MyCustomRemovalFilter implements ResettableFileListFilter<File> {


    @Override
    public boolean remove(File xmlFile) {

        if (xmlFile == null) {
            return true;
        }
        // TODO you own on removal logic

        return true;
    }

    @Override
    public List<File> filterFiles(File[] files) {

        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(files);
    }
}