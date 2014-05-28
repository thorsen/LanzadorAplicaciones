package es.ramondin.lanzadoraplicaciones.model.general;

import java.util.List;

public class TreeItem {
    private Integer idFuncion;
    private String text, urlDestino, version, nombreTaskFlow, nombreAM;
    private List<TreeItem> children;

    public TreeItem() {
        super();
    }

    public TreeItem(Integer idFuncion, String text, String urlDestino, String version, String nombreTaskFlow, String nombreAM) {
        super();
        this.idFuncion = idFuncion;
        this.text = text;
        this.urlDestino = urlDestino;
        this.version = version;
        this.nombreTaskFlow = nombreTaskFlow;
        this.nombreAM = nombreAM;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setUrlDestino(String urlDestino) {
        this.urlDestino = urlDestino;
    }

    public String getUrlDestino() {
        return urlDestino;
    }

    public void setChildren(List<TreeItem> children) {
        this.children = children;
    }

    public List<TreeItem> getChildren() {
        return children;
    }

    public void setIdFuncion(Integer idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Integer getIdFuncion() {
        return idFuncion;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setNombreTaskFlow(String nombreTaskflow) {
        this.nombreTaskFlow = nombreTaskflow;
    }

    public String getNombreTaskFlow() {
        return nombreTaskFlow;
    }

    public void setNombreAM(String nombreAM) {
        this.nombreAM = nombreAM;
    }

    public String getNombreAM() {
        return nombreAM;
    }
}
