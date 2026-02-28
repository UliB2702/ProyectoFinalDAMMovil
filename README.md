# Diario de desarrollo

## 19 de enero

Estuve subiendo el proyecto a github con todo lo hecho de la versión de moviles hasta el momento. Incluyendo el inicio de la pantalla de Iniciar Sesión, Registro y Perfil de Usuario

## 28 de enero

Estuve implementando menus del estilo DrawerLayouts. Aunque les falten funcionalidad, los diseños de los menus estan mayormente hechos. Además de ir implementando Framents que haran el desarrollo mucho más sencillo

## 2 de Febrero

Avanzando con la conexion con el backend. Estoy empezando a pensar en cambiar que la clave primaria de usuario sea el nombre en lugar de un id

## 4 de Febrero

Al avanzar junto con la API, pude realizar un inicio de sesion funcional, en el cual, llame a la api y compruebe que los datos de un usuario existan

## 5 de Febrero

Junto a lo de ayer, ahora pude cambiar el menu DrawerLayout para que muestre el menu de sesion iniciada junto con los datos en el header del menu

## 9 de Febrero

Pude agregar la capacidad de navegar entre Fragments junto con que, al ir al Fragment de perfil de usuario, cargue los datos de la API y los muestra. Además, empece a crear la funcion para el Post de un usuario

## 10 de Febrero

Implementado el registro de usuario y capacidad de ver su propio perfil al precionar el boton "Mi cuenta" en el menu

## 11 de Febrero

Implementacion de la feed en el menu principal, permitiendo recargar el feed si se vuelve a abrir el menu del inicio. Iniciando la feed para cada usuario

## 12 de Febrero

Implementacion del feed de los posts de un usuario y quitando el menu de subir un post si el usuario no es el de la cuenta que se muestra

## 20 de Febrero

Pude arreglar un error que mostraba todos los posts que habian en la cuenta de un usuario en lugar de solo mostrar los del propio usuario

## 24 de Febrero

Pude agregar la capacidad de crear post, evitar que se cree un usuario con un nombre que ya existe y empezando a implementar el borrado de un post

## 28 de Febrero

Añadida la funcion de borrar post mientras sea del usuario con el que se iniciara sesion