package ni.edu.ucem.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * La anotación @SpringBootApplication carga varias dependencias por defecto (tal como el contenedor de servlet 
 * envevido), activa el escaneo de componentes (@ComponentScan) y los EndPoints web (@EnableWebMvc), entre otras tareas.
 */
@SpringBootApplication
public class HotelWebApi
{
    public static void main( String[] args )
    {
        /*
         * Con Maven or Gradle, es posible empaquetar la aplicación en formato WAR, sin embargo, para iniciar 
         * rapidamente la aplicación durante el desarrollo, implementamos una clase java ejecutable.
         */
        SpringApplication.run(HotelWebApi.class, args);
    }
}
