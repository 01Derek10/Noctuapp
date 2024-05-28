<?php
// Configuración de la base de datos
$servername = "localhost"; // Cambia esto si tu servidor de base de datos no está en el mismo servidor
$username = "root"; // Sustituye 'tu_usuario' por tu nombre de usuario de la base de datos
$password = ""; // Sustituye 'tu_contraseña' por tu contraseña de la base de datos
$dbname = "noctua"; // Nombre de la base de datos

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar conexión
if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}

// Obtener los tags desde la solicitud GET
$tags = $_GET['tags'] ?? '';

// Verificar si se proporcionaron los tags
if(empty($tags)) {
    echo json_encode(array("status" => "error", "message" => "No se proporcionaron los tags."));
    exit();
}

// Crear la consulta SQL para buscar empresas por tags
$sql = "SELECT * FROM empresas WHERE FIND_IN_SET('$tags', tags) > 0";
$result = $conn->query($sql);

// Array para almacenar los resultados
$empresasArray = array();

// Verificar si hay resultados y almacenarlos en el array
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $empresasArray[] = $row;
    }
} else {
    echo json_encode(array("status" => "error", "message" => "No se encontraron empresas con los tags proporcionados."));
    exit();
}

// Cerrar conexión
$conn->close();

// Devolver el array con los resultados
echo json_encode($empresasArray);
?>
