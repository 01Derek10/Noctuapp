<?php
$hostname_localhost="localhost";
$database_localhost="NOMBREACAMBIAR";
$username_localhost="root";
$password_localhost="";

$json=array();
    $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
    $cosulta ="select nombre, contrasenia, email, from usuarios where nombre = {$_POST['nombre']} and contrasenia = {$_POST['contrasenia']}";
    $resultado=mysqli_query($conexion,$cosulta);
    while($registro=mysqli_fetch_assoc($resultado)){
        $json['User'][]=$registro;
    }
    mysqli_close($conexion);
    echo json_encode($json);

?>