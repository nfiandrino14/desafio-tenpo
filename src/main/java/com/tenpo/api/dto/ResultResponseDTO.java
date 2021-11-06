package com.tenpo.api.dto;

public class ResultResponseDTO {

    private Double result;

    public ResultResponseDTO() {
    }

    public ResultResponseDTO(Double result) {
        this.result = result;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultNumber{" + result + '}';
    }

}
