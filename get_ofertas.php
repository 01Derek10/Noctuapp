<?php
// Configuración de la base de datos
$servername = "localhost"; // Cambia esto si tu servidor de base de datos no está en el mismo servidor
$username = "tu_usuario"; // Sustituye 'tu_usuario' por tu nombre de usuario de la base de datos
$password = "tu_contraseña"; // Sustituye 'tu_contraseña' por tu contraseña de la base de datos
$dbname = "noctua"; // Nombre de la base de datos

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar conexión
if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}

// Consulta SQL para obtener todos los campos de la tabla "ofertas"
$sql = "SELECT * FROM ofertas";
$result = $conn->query($sql);

// Array para almacenar los resultados
$ofertasArray = array();

// Verificar si hay resultados y almacenarlos en el array
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $ofertasArray[] = $row;
    }
} else {
    echo json_encode(array("status" => "error", "message" => "No se encontraron ofertas."));
    exit();
}

// Cerrar conexión
$conn->close();

// Devolver el array con los resultados
echo json_encode($ofertasArray);
?>
