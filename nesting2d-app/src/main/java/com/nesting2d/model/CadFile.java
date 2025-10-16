package com.nesting2d.model;

import java.util.ArrayList;
import java.util.List;

public class CadFile {
    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;
    private List<CadEntity> entities;
    private CadDrawingInfo drawingInfo;
    
    public CadFile() {
        this.entities = new ArrayList<>();
    }
    
    public CadFile(String fileName, String filePath, String fileType, long fileSize) {
        this();
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
    
    // Getters and Setters
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFileType() {
        return fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    public long getFileSize() {
        return fileSize;
    }
    
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
    public List<CadEntity> getEntities() {
        return entities;
    }
    
    public void setEntities(List<CadEntity> entities) {
        this.entities = entities;
    }
    
    public void addEntity(CadEntity entity) {
        this.entities.add(entity);
    }
    
    public CadDrawingInfo getDrawingInfo() {
        return drawingInfo;
    }
    
    public void setDrawingInfo(CadDrawingInfo drawingInfo) {
        this.drawingInfo = drawingInfo;
    }
    
    public int getEntityCount() {
        return entities.size();
    }
    
    public int getEntityCountByType(String type) {
        return (int) entities.stream()
                .filter(entity -> entity.getType().equals(type))
                .count();
    }
}