package com.nesting2d.service;

import com.nesting2d.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CadFileService {
    private static final Logger logger = LoggerFactory.getLogger(CadFileService.class);
    
    private DxfFileReader dxfReader;
    private DwgFileReader dwgReader;
    
    public CadFileService() {
        this.dxfReader = new DxfFileReader();
        this.dwgReader = new DwgFileReader();
    }
    
    public CadFile importCadFile(File file) {
        try {
            String fileName = file.getName();
            String fileType = getFileType(fileName);
            long fileSize = file.length();
            
            logger.info("Importing CAD file: {} (Type: {}, Size: {} bytes)", fileName, fileType, fileSize);
            
            CadFile cadFile = new CadFile(fileName, file.getAbsolutePath(), fileType, fileSize);
            
            if (fileType.equalsIgnoreCase("DXF")) {
                cadFile = dxfReader.readDxfFile(file, cadFile);
            } else if (fileType.equalsIgnoreCase("DWG")) {
                cadFile = dwgReader.readDwgFile(file, cadFile);
            } else {
                throw new UnsupportedOperationException("Unsupported file type: " + fileType);
            }
            
            logger.info("Successfully imported CAD file with {} entities", cadFile.getEntityCount());
            return cadFile;
            
        } catch (Exception e) {
            logger.error("Error importing CAD file: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to import CAD file", e);
        }
    }
    
    private String getFileType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase();
        if (extension.equals("DXF")) {
            return "DXF";
        } else if (extension.equals("DWG")) {
            return "DWG";
        } else {
            throw new IllegalArgumentException("Unsupported file extension: " + extension);
        }
    }
    
    public boolean isSupportedFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".dxf") || fileName.endsWith(".dwg");
    }
}