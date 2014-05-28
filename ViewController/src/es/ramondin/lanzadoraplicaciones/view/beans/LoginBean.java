package es.ramondin.lanzadoraplicaciones.view.beans;

import es.ramondin.util.general.RmdMensaje;

import java.io.IOException;

import java.math.BigDecimal;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import javax.security.auth.Subject;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import weblogic.security.URLCallbackHandler;
import weblogic.security.services.Authentication;


public class LoginBean {
    private String usuario;
    private String password;

    private final String PARAM_RUTA_SERVIDOR = "RUTA_SERVIDOR";

    public LoginBean() {
    }

    public String doLogin() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();

        //Pasamos usuario  + " "  + IP para validar si el usuario puede acceder desde esa IP
        String usuaurioMasIP = usuario + " " + request.getRemoteAddr();
        byte[] pw = password.getBytes();

        try {
            Subject subject = Authentication.login(new URLCallbackHandler(usuaurioMasIP, pw));
            weblogic.servlet.security.ServletAuthentication.runAs(subject, request);
            HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
            HttpSession session = request.getSession();
            BindingContainer bindings = getBindings();

            estableceSeguridadAntigua(bindings, session, response);

            //Almacenamos el nombre del usuario para mostrarlo en el menú
            guardaNombreUsuario(bindings, session);
            //Almacenamos el grupo del usuario para desplegar el menú correspondiente
            guardaGrupoUsuario(bindings, session);
            //Almacenamos la ruta del servidor para carga de imágenes
            guardaRutaServidor(bindings, session);

            String loginUrl = "/adfAuthentication?success_url=/faces" + ctx.getViewRoot().getViewId();
            sendForward(request, response, loginUrl);
        } catch (FailedLoginException fle) {
            ResourceBundle res = ResourceBundle.getBundle("es.ramondin.lanzadoraplicaciones.view.resources.Idioma", ctx.getViewRoot().getLocale());

            RmdMensaje.muestraError(ctx, res.getString("ErrorLogin"), res.getString("CausaErrorLogin"));
        } catch (LoginException le) {
            ResourceBundle res = ResourceBundle.getBundle("es.ramondin.lanzadoraplicaciones.view.resources.Idioma", ctx.getViewRoot().getLocale());

            RmdMensaje.muestraError(ctx, res.getString("ErrorIPs"), res.getString("CausaErrorIPs"));
            //            reportUnexpectedLoginError("LoginException", le);
        }

        return null;
    }

    private void estableceSeguridadAntigua(BindingContainer bindings, HttpSession session, HttpServletResponse response) {
        String idSesion = session.getId();

        OperationBinding operation = bindings.getOperationBinding("existeIdSesion");
        operation.getParamsMap().put("idSesion", idSesion);
        Boolean crearRegistro = !((Boolean)operation.execute());

        actualizaTablaSesiones(bindings, idSesion, crearRegistro);

        if (crearRegistro)
            creaCookie(session, response);
    }

    private void guardaNombreUsuario(BindingContainer bindings, HttpSession session) {
        OperationBinding operation = bindings.getOperationBinding("getNombreUsuario");
        operation.getParamsMap().put("codUsuario", this.usuario);
        String nombreUsuario = (String)operation.execute();

        session.setAttribute("nombreUsuario", nombreUsuario);
    }

    private void guardaGrupoUsuario(BindingContainer bindings, HttpSession session) {
        OperationBinding operation = bindings.getOperationBinding("getIdGrupo");
        operation.getParamsMap().put("codUsuario", this.usuario);
        BigDecimal grupoUsuario = (BigDecimal)operation.execute();

        session.setAttribute("idGrupoUsuario", grupoUsuario);
    }

    private void guardaRutaServidor(BindingContainer bindings, HttpSession session) {
        OperationBinding operation = bindings.getOperationBinding("getValorParametro");
        operation.getParamsMap().put("parametro", PARAM_RUTA_SERVIDOR);
        String rutaServidor = (String)operation.execute();

        session.setAttribute("rutaServidor", rutaServidor);
    }

    private BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    private void actualizaTablaSesiones(BindingContainer bindings, String idSesion, Boolean crearRegistro) {
        OperationBinding operation = bindings.getOperationBinding("getIdUsuario");
        operation.getParamsMap().put("codUsuario", this.usuario);
        BigDecimal idUsuario = (BigDecimal)operation.execute();

        operation = bindings.getOperationBinding("actualizaTablaSesiones");
        operation.getParamsMap().put("crearRegistro", crearRegistro);
        operation.getParamsMap().put("idSesion", idSesion);
        operation.getParamsMap().put("idUsuario", idUsuario);
        operation.getParamsMap().put("codUsuario", this.usuario);
        operation.execute();

        operation = bindings.getOperationBinding("Commit");
        operation.execute();
    }

    private void creaCookie(HttpSession session, HttpServletResponse response) {
        Cookie cookie = new Cookie("RMDADFSESSIONID", session.getId());
        cookie.setPath("/");
        response.addCookie(cookie);

        cookie = new Cookie("RMDADFUSU", CodificaSimple(this.password, "psofndu"));
        cookie.setPath("/");
        response.addCookie(cookie);

        cookie = new Cookie("RMDADFNOMUSU", this.usuario);
        cookie.setPath("/");
        cookie.setMaxAge(100000);
        response.addCookie(cookie);
    }

    private String CodificaSimple(String cadena, String pw) {
        String cAdmitidosSimple =
            "0123OLPpoli456SXEDCRFVUJMIKkujmTGBYHNyhnt789QAZWgbrfvedcwsxqaz"; // Nos sirve para guardar el codigo encriptado como cokie , ya que en una cookie n se puede guardar simbolos como el punto y coma (;)
        String cTransformSimple = "POLIKUJMN4107HYTGBVFR2369plomkiujnbhygtEDCXSWQAZ85vfrcdexswzaq";
        String cPw = "psofndu";
        String cDevolver = "";

        if (cPw.equals(pw)) {
            int pos, newpos;
            char car;

            for (int i = 0; i < cadena.length(); i++) {
                try {
                    car = cadena.charAt(i);
                } catch (IndexOutOfBoundsException e) {
                    return cDevolver;
                }
                pos = cAdmitidosSimple.indexOf(car);
                newpos = (pos + i) % cAdmitidosSimple.length();
                cDevolver = cDevolver + cTransformSimple.charAt(newpos);
            }
        } else {
            cDevolver = "sd743jvc0kGUg87";
        }

        return cDevolver;
    }

    private void sendForward(HttpServletRequest request, HttpServletResponse response, String forwardUrl) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException se) {
            reportUnexpectedLoginError("ServletException", se);
        } catch (IOException ie) {
            reportUnexpectedLoginError("IOException", ie);
        }
        ctx.responseComplete();
    }

    private void reportUnexpectedLoginError(String errType, Exception e) {
        RmdMensaje.muestraExcepcion(FacesContext.getCurrentInstance(), e, "Unexpected error during login",
                                "Unexpected error during login (" + errType + "), please consult logs for detail");
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
