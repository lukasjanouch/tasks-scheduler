package cz.sentiscrape.tasksscheduler.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
//http://localhost:8080/swagger-ui/index.html
@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Lukáš Janouch");
        myContact.setEmail("lukasjanou8@seznam.cz");

        Info information = new Info()
                .title("Tasks Scheduler System API")
                .version("1.0")
                .description("This API exposes endpoints to schedule projects and their tasks.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
