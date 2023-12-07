package org.developx.mybatisParser.domain.analysis.finder;

import java.io.File;
import java.util.Collection;

public interface DirectoryFinder {
    Collection<File> findDirectory(String fullPath);
}
