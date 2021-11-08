package com.tenpo.api.dto;

import static java.lang.Double.compare;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResultResponseDTO other = (ResultResponseDTO) obj;

        return (compare(other.result, this.result) == 0);
    }
}
