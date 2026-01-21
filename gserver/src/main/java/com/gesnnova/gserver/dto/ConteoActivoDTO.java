package com.gesnnova.gserver.dto;

public class ConteoActivoDTO {

    private Long totalActivos;

    public ConteoActivoDTO() {
    }

    public ConteoActivoDTO(Long totalActivos) {
        this.totalActivos = totalActivos;
    }

    public Long getTotalActivos() {
        return totalActivos;
    }

    public void setTotalActivos(Long totalActivos) {
        this.totalActivos = totalActivos;
    }
}
