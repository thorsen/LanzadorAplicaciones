package es.ramondin.lanzadoraplicaciones.model.views;

import es.ramondin.modelo.seg.entities.TsegusuarioImpl;

import java.math.BigDecimal;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Apr 04 08:21:42 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TSegUsuarioVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CodUsuario {
            public Object get(TSegUsuarioVORowImpl obj) {
                return obj.getCodUsuario();
            }

            public void put(TSegUsuarioVORowImpl obj, Object value) {
                obj.setCodUsuario((String)value);
            }
        }
        ,
        IdGrupo {
            public Object get(TSegUsuarioVORowImpl obj) {
                return obj.getIdGrupo();
            }

            public void put(TSegUsuarioVORowImpl obj, Object value) {
                obj.setIdGrupo((BigDecimal)value);
            }
        }
        ,
        IdUsuario {
            public Object get(TSegUsuarioVORowImpl obj) {
                return obj.getIdUsuario();
            }

            public void put(TSegUsuarioVORowImpl obj, Object value) {
                obj.setIdUsuario((BigDecimal)value);
            }
        }
        ,
        NombreUsuario {
            public Object get(TSegUsuarioVORowImpl obj) {
                return obj.getNombreUsuario();
            }

            public void put(TSegUsuarioVORowImpl obj, Object value) {
                obj.setNombreUsuario((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(TSegUsuarioVORowImpl object);

        public abstract void put(TSegUsuarioVORowImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }

    public static final int CODUSUARIO = AttributesEnum.CodUsuario.index();
    public static final int IDGRUPO = AttributesEnum.IdGrupo.index();
    public static final int IDUSUARIO = AttributesEnum.IdUsuario.index();
    public static final int NOMBREUSUARIO = AttributesEnum.NombreUsuario.index();

    /**
     * This is the default constructor (do not remove).
     */
    public TSegUsuarioVORowImpl() {
    }

    /**
     * Gets TSegUsuario entity object.
     * @return the TSegUsuario
     */
    public TsegusuarioImpl getTSegUsuario() {
        return (TsegusuarioImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for COD_USUARIO using the alias name CodUsuario.
     * @return the COD_USUARIO
     */
    public String getCodUsuario() {
        return (String) getAttributeInternal(CODUSUARIO);
    }

    /**
     * Sets <code>value</code> as attribute value for COD_USUARIO using the alias name CodUsuario.
     * @param value value to set the COD_USUARIO
     */
    public void setCodUsuario(String value) {
        setAttributeInternal(CODUSUARIO, value);
    }

    /**
     * Gets the attribute value for ID_GRUPO using the alias name IdGrupo.
     * @return the ID_GRUPO
     */
    public BigDecimal getIdGrupo() {
        return (BigDecimal) getAttributeInternal(IDGRUPO);
    }

    /**
     * Sets <code>value</code> as attribute value for ID_GRUPO using the alias name IdGrupo.
     * @param value value to set the ID_GRUPO
     */
    public void setIdGrupo(BigDecimal value) {
        setAttributeInternal(IDGRUPO, value);
    }

    /**
     * Gets the attribute value for ID_USUARIO using the alias name IdUsuario.
     * @return the ID_USUARIO
     */
    public BigDecimal getIdUsuario() {
        return (BigDecimal) getAttributeInternal(IDUSUARIO);
    }

    /**
     * Sets <code>value</code> as attribute value for ID_USUARIO using the alias name IdUsuario.
     * @param value value to set the ID_USUARIO
     */
    public void setIdUsuario(BigDecimal value) {
        setAttributeInternal(IDUSUARIO, value);
    }

    /**
     * Gets the attribute value for NOMBRE_USUARIO using the alias name NombreUsuario.
     * @return the NOMBRE_USUARIO
     */
    public String getNombreUsuario() {
        return (String) getAttributeInternal(NOMBREUSUARIO);
    }

    /**
     * Sets <code>value</code> as attribute value for NOMBRE_USUARIO using the alias name NombreUsuario.
     * @param value value to set the NOMBRE_USUARIO
     */
    public void setNombreUsuario(String value) {
        setAttributeInternal(NOMBREUSUARIO, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}
