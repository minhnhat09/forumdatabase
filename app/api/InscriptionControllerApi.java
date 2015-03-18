package api;

import models.Demand;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

public class InscriptionControllerApi extends Controller{
	
	public static Result demandCodeMobile(){
		JsonNode json = request().body().asJson();
		if(json == null){
			return badRequest();
		}else{
			System.out.println("Demande code");
			String userName     = json.get("username").asText();
			String ipn 			= json.get("ipn").asText();
			String service		= json.get("service").asText();
			String email		= json.get("email").asText();
			String motif		= json.get("motif").asText();
			
			Demand demand = new Demand();
			demand.name = userName;
			demand.ipn = ipn;
			//Serivce missing
			demand.email = email;
			demand.motif = motif;
			Ebean.save(demand);
			return ok();
		}
	}
	
}
