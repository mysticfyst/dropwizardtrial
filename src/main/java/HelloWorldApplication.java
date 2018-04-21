import filters.RunBeforeOthers;
import health.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resources.HelloWorldResource;

public class HelloWorldApplication extends Application<HelloWorldConfig> {

    public static void main(String[] args) throws Exception{
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName(){
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfig> bootstrap){

    }

    @Override
    public void run(HelloWorldConfig helloWorldConfig, Environment environment) throws Exception {
        final HelloWorldResource resource = new HelloWorldResource(
                helloWorldConfig.getTemplate(),
                helloWorldConfig.getDefaultName()
        );
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(helloWorldConfig.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(RunBeforeOthers.class);
        environment.jersey().register(resource);
    }
}
