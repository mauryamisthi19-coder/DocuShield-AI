package com.docushield.document.dto;

public class DocumentAnalysisResponse {

    private String documentType;

    private String summary;

    private String sensitiveData;

    private String recommendation;

    public DocumentAnalysisResponse() {
    }

    public DocumentAnalysisResponse(String documentType,
                                    String summary,
                                    String sensitiveData,
                                    String recommendation) {

        this.documentType = documentType;
        this.summary = summary;
        this.sensitiveData = sensitiveData;
        this.recommendation = recommendation;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSensitiveData() {
        return sensitiveData;
    }

    public void setSensitiveData(String sensitiveData) {
        this.sensitiveData = sensitiveData;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}