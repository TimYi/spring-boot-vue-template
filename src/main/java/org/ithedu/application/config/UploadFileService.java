/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.application.config;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadFileService {

	@Value("${my.file.location:#{null}}")
	private String outerFileLocation;

	public File getLocalFile(String relativePath) {
		if(relativePath.startsWith("/")) {
			relativePath = relativePath.replaceFirst("/", "");
		}
		return new File(FilenameUtils.concat(outerFileLocation, relativePath));
	}
}
