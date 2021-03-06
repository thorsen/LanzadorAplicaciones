package es.ramondin.lanzadoraplicaciones.model.views;

import es.ramondin.modelo.cfg.entities.TparametrosImpl;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Apr 04 14:59:21 CEST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TParametrosVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Param {
            public Object get(TParametrosVORowImpl obj) {
                return obj.getParam();
            }

            public void put(TParametrosVORowImpl obj, Object value) {
                obj.setParam((String)value);
            }
        }
        ,
        Valor {
            public Object get(TParametrosVORowImpl obj) {
                return obj.getValor();
            }

            public void put(TParametrosVORowImpl obj, Object value) {
                obj.setValor((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(TParametrosVORowImpl object);

        public abstract void put(TParametrosVORowImpl object, Object value);

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
    public static final int PARAM = AttributesEnum.Param.index();
    public static final int VALOR = AttributesEnum.Valor.index();

    /**
     * This is the default constructor (do not remove).
     */
    public TParametrosVORowImpl() {
    }

    /**
     * Gets TParametros entity object.
     * @return the TParametros
     */
    public TparametrosImpl getTParametros() {
        return (TparametrosImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for PARAM using the alias name Param.
     * @return the PARAM
     */
    public String getParam() {
        return (String) getAttributeInternal(PARAM);
    }

    /**
     * Sets <code>value</code> as attribute value for PARAM using the alias name Param.
     * @param value value to set the PARAM
     */
    public void setParam(String value) {
        setAttributeInternal(PARAM, value);
    }

    /**
     * Gets the attribute value for VALOR using the alias name Valor.
     * @return the VALOR
     */
    public String getValor() {
        return (String) getAttributeInternal(VALOR);
    }

    /**
     * Sets <code>value</code> as attribute value for VALOR using the alias name Valor.
     * @param value value to set the VALOR
     */
    public void setValor(String value) {
        setAttributeInternal(VALOR, value);
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
