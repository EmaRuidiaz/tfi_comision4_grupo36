/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author ema_r
 */
public class ConfiguracionRed {
    private Long id;
    private Boolean eliminado = false;
    private Boolean dhcpHabilitado; // NOT NULL
    private String ip;
    private String mascara;
    private String gateway;
    private String dnsPrimario;
    
    public Long getDispositivoIotId() { return id; }
    public void setDispositivoIotId(Long id) { this.id = id; }
    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }
    public Boolean getDhcpHabilitado() { return dhcpHabilitado; }
    public void setDhcpHabilitado(Boolean dhcpHabilitado) { this.dhcpHabilitado = dhcpHabilitado; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getMascara() { return mascara; }
    public void setMascara(String mascara) { this.mascara = mascara; }
    public String getGateway() { return gateway; }
    public void setGateway(String gateway) { this.gateway = gateway; }
    public String getDnsPrimario() { return dnsPrimario; }
    public void setDnsPrimario(String dnsPrimario) { this.dnsPrimario = dnsPrimario; }
    
    @Override
    public String toString() {
        return "ConfiguracionRed [id=" + id + ", dhcpHabilitado=" + dhcpHabilitado + ", ip=" + ip + ", eliminado=" + eliminado + "]";
    }
}
