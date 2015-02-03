package controllers;

import play.mvc.BodyParser;
import play.mvc.BodyParser.Json;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class SecuredMobile extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
    	System.out.println("----------------Authentication need-----------------");
    	System.out.println(ctx.session().get("userName"));
        return ctx.session().get("userName");
    }
    
    @Override
    @BodyParser.Of(Json.class)
    public Result onUnauthorized(Context ctx) {
        return badRequest();
    }
    
    
    
}