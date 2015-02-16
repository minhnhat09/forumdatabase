package controllers;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
/**
 * Class used to manage security
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link Object}
 */
public class Secured extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
    	
        return ctx.session().get("userName");
    }
    
    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.login());
    }
    
    
    
}