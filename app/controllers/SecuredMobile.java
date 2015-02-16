package controllers;

import play.mvc.BodyParser;
import play.mvc.BodyParser.Json;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
/**
 * Class used to manage 
 * @author NGUYEN Nhat Minh
 * @version 1.0.0
 * @category Controller
 * @see {@link Object}
 */
public class SecuredMobile extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
    	System.out.println("----------------Authentication need-----------------");
    	System.out.println(ctx.session().get("userName"));
        return ctx.session().get("userNameMobile");
    }
    
    @Override
    @BodyParser.Of(Json.class)
    public Result onUnauthorized(Context ctx) {
        return badRequest();
    }
    
    
    
}