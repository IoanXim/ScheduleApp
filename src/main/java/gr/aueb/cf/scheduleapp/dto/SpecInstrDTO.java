package gr.aueb.cf.scheduleapp.dto;

public class SpecInstrDTO {
    private Long instrId;
    private Long specId;

    public SpecInstrDTO() {
    }

    public SpecInstrDTO(Long instrId, Long specId) {
        this.instrId = instrId;
        this.specId = specId;
    }

    public Long getInstrId() {
        return instrId;
    }

    public void setInstrId(Long instrId) {
        this.instrId = instrId;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }
}
