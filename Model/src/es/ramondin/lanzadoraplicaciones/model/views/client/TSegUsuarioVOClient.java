package es.ramondin.lanzadoraplicaciones.model.views.client;

import es.ramondin.lanzadoraplicaciones.model.views.common.TSegUsuarioVO;

import java.math.BigDecimal;

import oracle.jbo.client.remote.ViewUsageImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Apr 04 08:22:45 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TSegUsuarioVOClient extends ViewUsageImpl implements TSegUsuarioVO {
    /**
     * This is the default constructor (do not remove).
     */
    public TSegUsuarioVOClient() {
    }


    public BigDecimal getIdUsuario(String codUsuario) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"getIdUsuario",new String [] {"java.lang.String"},new Object[] {codUsuario});
        return (BigDecimal)_ret;
    }

    public BigDecimal getIdGrupo(String codUsuario) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"getIdGrupo",new String [] {"java.lang.String"},new Object[] {codUsuario});
        return (BigDecimal)_ret;
    }

    public String getNombreUsuario(String codUsuario) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"getNombreUsuario",new String [] {"java.lang.String"},new Object[] {codUsuario});
        return (String)_ret;
    }
}
