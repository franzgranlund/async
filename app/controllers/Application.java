package controllers;

import play.libs.Akka;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.ExecutionContext;
import utils.LongRunningProcess;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        LongRunningProcess.run(10000L);
        return ok(index.render("Your new application is ready."));
    }

    public static F.Promise<Result> async() {
        F.Promise<Integer> integerPromise = F.Promise.promise(() ->
                LongRunningProcess.run(10000L));

        return integerPromise.map(i -> ok());
    }

    public static F.Promise<Result> async2() {
        F.Promise<Integer> integerPromise = F.Promise.promise(() ->
                LongRunningProcess.run(10000L));

        F.Promise<Integer> integerPromise2 = F.Promise.promise(() ->
                LongRunningProcess.run(10000L));

        return integerPromise.flatMap(i ->
                        integerPromise2.map(x -> ok())
        );
    }

    public static F.Promise<Result> async3() {
        ExecutionContext myExecutionContext = Akka.system().dispatchers().lookup("play.akka.actor.my-context");

        F.Promise<Integer> integerPromise = F.Promise.promise(() ->
                LongRunningProcess.run(10000L)
        , myExecutionContext);

        F.Promise<Integer> integerPromise2 = F.Promise.promise(() ->
                LongRunningProcess.run(10000L)
        , myExecutionContext);

        return integerPromise.flatMap(i -> integerPromise2.map(x -> ok()));
    }
}
