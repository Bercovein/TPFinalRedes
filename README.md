# TPFinalRedes (Bertolotti - Bercovsky)
Trabajo final sobre Sockets ( Servidor - Cliente ) 



Trabajo Práctico Final 
Sockets

Preguntas

1.¿Qué es un puerto?

Diversos programas TCP/IP pueden ejecutarse simultáneamente en Internet. Cada uno de estos programas funciona con un protocolo.
Por lo tanto, a cada una de estas aplicaciones puede serle asignada una dirección única en equipo, codificada en 16 bits llamada puerto a donde se dirigen los datos. Si se trata de una solicitud enviada a la aplicación, la aplicación se denomina aplicación servidor. Si se trata de una respuesta, entonces hablamos de una aplicación cliente. 

2.¿Como estan formados los endpoints?


3.¿Que es un socket?

Un socket es un proceso o hilo existente en la máquina cliente y en la máquina servidora, que sirve en última instancia para que el programa servidor y el cliente lean y escriban la información. Esta información será la transmitida por las diferentes capas de red. 

Un socket queda definido por un par de direcciones IP local y remota, un protocolo de transporte y un par de números de puerto local y remoto. Para que dos programas puedan comunicarse entre sí es necesario que se cumplan ciertos requisitos:

    Que un programa sea capaz de localizar al otro.
    Que ambos programas sean capaces de intercambiarse cualquier secuencia de octetos, es decir, datos relevantes a su finalidad.

Para ello son necesarios los tres recursos que originan el concepto de socket:

    Un protocolo de comunicaciones, que permite el intercambio de octetos.
    Un par de direcciones del Protocolo de Red (Dirección IP, si se utiliza el Protocolo TCP/IP), que identifica la computadora de origen y la remota.
    Un par de números de puerto, que identifica a un programa dentro de cada computadora.


4.¿A qué capa del modelo TPC/IP pertenecen los sockets? ¿Porque?


5.¿Cómo funciona el modelo cliente-servidor con TCP/IP Sockets?

El servidor debe negociar con su Sistema Operativo un puerto (casi siempre bien conocido) donde esperar las solicitudes. El servidor espera pasivamente las peticiones en un puerto bien conocido que ha sido reservado para el servicio que ofrece. El cliente también solicita, a su sistema operativo, un puerto no usado desde el cual enviar su solicitud y esperar respuesta. Un cliente ubica un puerto arbitrario, no utilizado y no reservado, para su comunicación.
En una interacción se necesita reservar solo uno de los dos puertos, asignados un identificador único de puerto para cada servicio, se facilita la construcción de clientes y servidores.
Los servidores por lo general son más difíciles de construir que los clientes pues aunque se implantan como programas de aplicación deben manejar peticiones concurrentes, así como reforzar todos los procedimientos de acceso y protección del sistema computacional en el que corren, y protegerse contra todos los errores posibles. El cliente y el servidor pueden interactuar en la misma máquina. 


6.¿Cuales son las causas comunes por la que la conexión entre cliente/servidor falle?



7.Diferencias entre sockets UDP y TCP

Las propiedades de un socket dependen de las características del protocolo en el que se implementan. Generalmente la comunicación con sockets se realiza mediante un protocolo de la familia TCP/IP (Protocolo de Control de Transmisión/Protocolo de Internet). Los dos más utilizados son: TCP (Protocolo de Control de Transmisión) y UDP (Protocolo de Datagrama de Usuario)

Cuando se implementan con el protocolo TCP, los sockets tienen las siguientes propiedades:

    Orientado a conexión.
    Se garantiza la transmisión de todos los octetos sin errores ni omisiones.
    Se garantiza que todo octeto llegará a su destino en el mismo orden en que se ha transmitido. Estas propiedades son muy importantes para garantizar la corrección de los programas que tratan la información.

El protocolo UDP es un protocolo no orientado a la conexión. Sólo se garantiza que si un mensaje llega, llegue bien. En ningún caso se garantiza que llegue o que lleguen todos los mensajes en el mismo orden que se mandaron. Esto lo hace adecuado para el envío de mensajes frecuentes pero no demasiado importantes, como por ejemplo, mensajes para los refrescos (actualizaciones) de un gráfico.. 


8.Diferencia entre sync & async sockets?

