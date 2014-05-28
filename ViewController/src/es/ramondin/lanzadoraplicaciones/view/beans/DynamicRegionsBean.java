package es.ramondin.lanzadoraplicaciones.view.beans;

import es.ramondin.lanzadoraplicaciones.model.general.TreeItem;
import es.ramondin.utilidades.JSFUtils;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.layout.RichPanelAccordion;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.ClientListenerSet;
import oracle.adf.view.rich.event.SetPropertyListener;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;


public class DynamicRegionsBean {
    private String taskFlowId = "/WEB-INF/welcome.xml#welcome";
    private String nombreAM = "";
    private RichPopup _popup;
    private RichPanelAccordion menuPA;
    private AdfFacesContext adfFctx = AdfFacesContext.getCurrentInstance();
    private FacesContext fctx = FacesContext.getCurrentInstance();
    
    private static final String VERSION_11_TASKFLOWS = "11T";
    private static final String VERSION_11_ANTIGUAS = "11A";
    private static final String VERSION_10 = "10";
    
    private static final String TASKFLOW_ANTIGUAS = "antiguasTF";

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public String cargaAplicacionTaskflow() {
        String urlDestino = (String)JSFUtils.resolveExpression("#{pageFlowScope.urlDestino}");
        String version = (String)JSFUtils.resolveExpression("#{pageFlowScope.versionDestino}");
        String nombreTaskFlowDestino = (String)JSFUtils.resolveExpression("#{pageFlowScope.nombreTaskFlowDestino}");
        String nombreAMDestino = (String)JSFUtils.resolveExpression("#{pageFlowScope.nombreAMDestino}");
        
        String rutaTaskFlow = null;
        
        if (version.contentEquals(VERSION_11_TASKFLOWS))
            rutaTaskFlow = "/WEB-INF/" + nombreTaskFlowDestino + ".xml#" + nombreTaskFlowDestino;
        else {
            rutaTaskFlow = "/WEB-INF/" + TASKFLOW_ANTIGUAS + ".xml#" + TASKFLOW_ANTIGUAS;
        }
                
        if (this.pendingChangesExist()) {
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            this._popup.show(hints);
            this.adfFctx.getPageFlowScope().put("taskFlowId", rutaTaskFlow);
            this.adfFctx.getPageFlowScope().put("nombreAM", nombreAMDestino);
        } else {
            this.taskFlowId = rutaTaskFlow;
            this.nombreAM = nombreAMDestino;
        }
        
        if (!version.contentEquals(VERSION_11_TASKFLOWS)) {
            if (version.contentEquals(VERSION_10))
                urlDestino = urlDestino.replace("../..", "http://rmd5.ramondin.es");
            
            Map sessionState = this.fctx.getExternalContext().getSessionMap();
            if (sessionState == null)
                sessionState = new HashMap();
            
            sessionState.put("urlDestinoAntigua", urlDestino);
        }
        
        return null;
    }

    private boolean pendingChangesExist() {
        /** How to get the controller context
         *  The ControlleContext class provides per-request information about the controller state for
         *  a web application.
         */
        ControllerContext cctx = ControllerContext.getInstance();
        return cctx.getCurrentViewPort().getTaskFlowContext().isDataDirty();
    }

    private ApplicationModule getApplicationModule(String dataProvider) {
        Application app = this.fctx.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = this.fctx.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, dataProvider,
                                            Object.class);

        return (ApplicationModule)valueExp.getValue(elContext);
    }

    public void set_popup(RichPopup _popup) {
        this._popup = _popup;
    }

    public RichPopup get_popup() {
        return this._popup;
    }

    public void continueAction(ActionEvent actionEvent) {
        if (this.nombreAM != null && !this.nombreAM.isEmpty())
            this.getApplicationModule("#{data." + this.nombreAM + "DataControl.dataProvider}").getTransaction().rollback();

        /*
        if(this.taskFlowId.equals("/WEB-INF/departments-flow.xml#departments-flow")) {
            this.getApplicationModule("#{data.DepartmentsModuleDataControl.dataProvider}").getTransaction().rollback();
        }

        if(this.taskFlowId.equals("/WEB-INF/employees-flow.xml#employees-flow")) {
            this.getApplicationModule("#{data.EmployeesModuleDataControl.dataProvider}").getTransaction().rollback();
        }

        if(this.taskFlowId.equals("/WEB-INF/jobs-flow.xml#jobs-flow")) {
            this.getApplicationModule("#{data.JobsModuleDataControl.dataProvider}").getTransaction().rollback();
        }

        if(this.taskFlowId.equals("/WEB-INF/locations-flow.xml#locations-flow")) {
            this.getApplicationModule("#{data.LocationsModuleDataControl.dataProvider}").getTransaction().rollback();
        }
*/
        this.taskFlowId = (String)this.adfFctx.getPageFlowScope().get("taskFlowId");
        this.nombreAM = (String)this.adfFctx.getPageFlowScope().get("nombreAM");
    }

    public void setMenuPA(RichPanelAccordion menuPA) {
        this.menuPA = menuPA;

        if (this.menuPA != null && this.menuPA.getChildCount() == 0)
            cargaMenuPA();
    }

    public RichPanelAccordion getMenuPA() {
        return this.menuPA;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void cargaMenuPA() {
        BigDecimal idGrupoUsuario = (BigDecimal)((HttpServletRequest)this.fctx.getExternalContext().getRequest()).getSession().getAttribute("idGrupoUsuario");
        String idioma = this.fctx.getViewRoot().getLocale().getLanguage();
        
        OperationBinding operation =
            this.getBindings().getOperationBinding("getMenuCompleto");
        operation.getParamsMap().put("idGrupo", idGrupoUsuario.intValue());
        operation.getParamsMap().put("idioma", idioma);
        LinkedHashMap<TreeItem, LinkedHashMap<TreeItem, ArrayList<TreeItem>>> menuCompleto =
            (LinkedHashMap<TreeItem, LinkedHashMap<TreeItem, ArrayList<TreeItem>>>)operation.execute();

        Iterator itCategorias = menuCompleto.entrySet().iterator();
        
        while (itCategorias.hasNext()) {
            addCategoria((Map.Entry<TreeItem, LinkedHashMap<TreeItem, ArrayList<TreeItem>>>)itCategorias.next());
        }

        RequestContext.getCurrentInstance().addPartialTarget(this.menuPA);
    }

    public void addCategoria(Map.Entry<TreeItem, LinkedHashMap<TreeItem, ArrayList<TreeItem>>> categoria) {
        ExpressionFactory elFactory = this.fctx.getApplication().getExpressionFactory();
        ELContext elContext = this.fctx.getELContext();
        
        RichShowDetailItem rsdiCategoria = new RichShowDetailItem();
        rsdiCategoria.setText(categoria.getKey().getText());
        rsdiCategoria.setStretchChildren("first");
        
        RichTree rtCategoria = new RichTree();
        rtCategoria.setValue(new ChildPropertyTreeModel(creaMenu(categoria.getValue()), "children"));
        rtCategoria.setVar("node");
        rtCategoria.setRowSelection("single");
        rtCategoria.setStyleClass("AFStretchWidth");
        
        UIXSwitcher switcher = new UIXSwitcher();
        ValueExpression ve = elFactory.createValueExpression(elContext, "#{node.children == null ? 'leaf' : 'notLeaf'}", String.class);
        switcher.setValueExpression("facetName", ve);
        
        RichCommandLink rclFuncion = new RichCommandLink();
        ValueExpression veText = elFactory.createValueExpression(elContext, "#{node.text}", String.class);
        rclFuncion.setValueExpression("text", veText);

        MethodExpression methodEx = elFactory.createMethodExpression(elContext, "#{pageFlowScope.DynamicRegionsBean.cargaAplicacionTaskflow}", String.class, new Class[] {});
        rclFuncion.setActionExpression(methodEx);
        
        SetPropertyListener splTaskflow = new SetPropertyListener(ActionEvent.class.getName());
        ValueExpression veFrom = elFactory.createValueExpression(elContext, "#{node.urlDestino}", String.class);
        splTaskflow.setValueExpression("from", veFrom);
        ValueExpression veTo = elFactory.createValueExpression(elContext, "#{pageFlowScope.urlDestino}", String.class);
        splTaskflow.setValueExpression("to", veTo);
        rclFuncion.addActionListener(splTaskflow);
        
        SetPropertyListener splVersion = new SetPropertyListener(ActionEvent.class.getName());
        veFrom = elFactory.createValueExpression(elContext, "#{node.version}", String.class);
        splVersion.setValueExpression("from", veFrom);
        veTo = elFactory.createValueExpression(elContext, "#{pageFlowScope.versionDestino}", String.class);
        splVersion.setValueExpression("to", veTo);
        rclFuncion.addActionListener(splVersion);
        
        SetPropertyListener splNombreTF = new SetPropertyListener(ActionEvent.class.getName());
        veFrom = elFactory.createValueExpression(elContext, "#{node.nombreTaskFlow}", String.class);
        splNombreTF.setValueExpression("from", veFrom);
        veTo = elFactory.createValueExpression(elContext, "#{pageFlowScope.nombreTaskFlowDestino}", String.class);
        splNombreTF.setValueExpression("to", veTo);
        rclFuncion.addActionListener(splNombreTF);
        
        SetPropertyListener splNombreAM = new SetPropertyListener(ActionEvent.class.getName());
        veFrom = elFactory.createValueExpression(elContext, "#{node.nombreAM}", String.class);
        splNombreAM.setValueExpression("from", veFrom);
        veTo = elFactory.createValueExpression(elContext, "#{pageFlowScope.nombreAMDestino}", String.class);
        splNombreAM.setValueExpression("to", veTo);
        rclFuncion.addActionListener(splNombreAM);
        
        switcher.getFacets().put("leaf", rclFuncion);
        
        RichOutputText rotArbol = new RichOutputText();
        rotArbol.setValueExpression("value", veText);
        rotArbol.setValueExpression("shortDesc", veText);
        switcher.getFacets().put("notLeaf", rotArbol);

        rtCategoria.getFacets().put("nodeStamp", switcher);
        
        //Añadimos un clickListener para desplegar nodos al hacer click sobre ellos
        ClientListenerSet clsNodo = new ClientListenerSet();
        clsNodo.addListener("click", "expandDiscloseNode");
        
        rtCategoria.setClientListeners(clsNodo);

        rsdiCategoria.getChildren().add(rtCategoria);

        this.menuPA.getChildren().add(rsdiCategoria);
    }

    private ArrayList<TreeItem> creaMenu(LinkedHashMap<TreeItem, ArrayList<TreeItem>> arboles) {
        ArrayList<TreeItem> root = new ArrayList<TreeItem>();
        
        Iterator it = arboles.entrySet().iterator();
        Map.Entry<TreeItem, ArrayList<TreeItem>> entryArboles;
        TreeItem nodo;
        
        while (it.hasNext()) {
            entryArboles = (Map.Entry<TreeItem, ArrayList<TreeItem>>)it.next();
            
            nodo = entryArboles.getKey();
            root.add(nodo);
            
            if (entryArboles.getValue() != null) {
                nodo.setChildren(entryArboles.getValue());
            }            
        }

        return root;
    }
}
