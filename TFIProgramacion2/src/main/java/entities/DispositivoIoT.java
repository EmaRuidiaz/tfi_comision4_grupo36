/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author ema_r
 */
public class DispositivoIoT {
    private Long id;
    private Boolean eliminado = false;
    private String serial; // UNIQUE, NOT NULL 
    private String modelo; // NOT NULL 
    private String ubicacion;
    private String firmwareVersion;
    private ConfiguracionRed configuracionRed;    
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }
    public String getSerial() { return serial; }
    public void setSerial(String serial) { this.serial = serial; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getFirmwareVersion() { return firmwareVersion; }
    public void setFirmwareVersion(String firmwareVersion) { this.firmwareVersion = firmwareVersion; }
    public ConfiguracionRed getConfiguracionRed() { return configuracionRed; }
    public void setConfiguracionRed(ConfiguracionRed configuracionRed) { this.configuracionRed = configuracionRed; }

    @Override
    public String toString() {
        return "DispositivoIoT [id=" + id + ", serial=" + serial + ", modelo=" + modelo + ", eliminado=" + eliminado + "]";
    }
}
