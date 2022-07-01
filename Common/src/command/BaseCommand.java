package command;

import serializable.Response;

public abstract class BaseCommand {
     protected boolean isValidAttrs = false;
     public Response response;
     public abstract void execute();
     protected BaseCommand(){
          response = new Response();
     }
}

