<div align="center">
  <a href="public/SMT_UTN.png">
    <img src="https://i.imgur.com/rY92D2Q.png" alt="Logo de San Miguel de Tucumán y Universidad Tecnologica Nacional">
  </a>
<h3 align="center">Recolección Inteligente para un Tucumán Limpio (Aplicación Web)</h3>
<h4 align="center">Sistema de control de flotas para camiones recolectores de residuos sólidos urbanos en San Miguel de Tucumán</h4>
</div>

# Acerca del proyecto:
Este proyecto propone la implementación de un sistema de localización por GPS en tiempo real para controlar las operaciones de los camiones recolectores de basura de la ciudad. Diseñado para complementarse con el plan GIRSU, una estrategia de optimización de recursos para garantizar una gestión innovadora y eficiente de los residuos sólidos urbanos en la ciudad de San Miguel de Tucumán.

No solo buscamos mejorar la eficiencia y control en la recolección de residuos, sino también contribuir a la reducción de costos operativos y del impacto ambiental, alineándose con los objetivos de sostenibilidad de la ciudad; Al permitir a los ciudadanos estar informados sobre los horarios y rutas de recolección, se fomenta una mayor responsabilidad y participación comunitaria en la gestión de residuos.

# Objetivos
- **Integración con GIRSU:** Aportar una herramienta tecnológica que fortalece y complementa las iniciativas actuales del programa GIRSU, en pos de una ciudad más limpia y eficiente.
- **Mejora del servicio público:** Aumentar la calidad y la eficiencia del servicio de recolección de residuos, ofreciendo una respuesta más ágil a los desafíos operativos.
- **Generación de datos estratégicos:** Proveer un historial detallado de las operaciones de recolección para la toma de decisiones informadas y la planificación futura.
- **Participación ciudadana:** Fomentar la conciencia ambiental y la participación activa de los ciudadanos en la gestión de residuos, a través de la transparencia y la accesibilidad de la información.

# Alcance del proyecto
- **Implementación de Dispositivos GPS:** Instalación de dispositivos de rastreo GPS en los camiones recolectores de residuos sólidos urbanos.
- **Centralización de Datos:** Transmisión de la información de ubicación en tiempo real a un servidor centralizado, con almacenamiento seguro y accesible.
- **Acceso Público a la Información:** Desarrollo de una plataforma web interactiva que muestra la ubicación en tiempo real de los camiones recolectores y otros datos relevantes.
- **Gestión administrativa:** Creación de un panel de control para la administración de usuarios, camiones y rutas de recolección, además de acceder a un historial detallado de recorridos, velocidades, datos de conductores, paradas, demoras, y otros aspectos operativos.

# Esquema general
<img src="https://i.imgur.com/yFy4IQc.png" alt="Logo de San Miguel de Tucumán y Universidad Tecnologica Nacional">


# Tecnologías usadas en esta Aplicación Web:
![Java]( 	https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

![Vim](https://img.shields.io/badge/VIM-%2311AB00.svg?&style=for-the-badge&logo=vim&logoColor=white)

![WebStorm](https://img.shields.io/badge/webstorm-143?style=for-the-badge&logo=webstorm&logoColor=white&color=black) ![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)

![love](http://ForTheBadge.com/images/badges/built-with-love.svg)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

# Características
- [x] Actualización en tiempo real de las ubicaciones de los camiones a través de WebSocket
- [x] Operaciones CRUD para camiones
- [ ] Operaciones CRUD para usuarios
- [ ] Autenticación y autorización de usuarios
- [ ] Alertas y notificaciones para el estado de los camiones

<p align="right">(<a href="#readme-top">back to top</a>)</p>

# Cómo usar

Para obtener una copia local del proyecto, siga estos pasos:

## Prerrequisitos
* Java 21

# Instalación

1. Clonar el repositorio
   ```sh
   git clone https://github.com/ramos-adrian/girsu-gps-api
   ```

2. Crear un archivo `.env` en la raíz del proyecto y configurar las variables de entorno
   ```sh
    #.env
    CORS_ALLOWED_ORIGINS=http://localhost:3000
    WEBSOCKET_ALLOWED_ORIGINS=http://localhost:3000
   ```

4. Ejecutar la aplicación con Maven
   ```sh
   ./mvnw spring-boot:run
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

# Contacto
Ramos, Adrián David - adrian@ramos.ar

[![linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](www.linkedin.com/in/adrian-david-ramos) 