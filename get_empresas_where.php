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

// Obtener los nombres desde la solicitud POST
$nombres = $_POST['nombres'] ?? '';

// Verificar si se proporcionaron los nombres
if(empty($nombres)) {
    echo json_encode(array("status" => "error", "message" => "No se proporcionaron los nombres."));
    exit();
}

// Convertir los nombres en un array
$nombresArray = json_decode($nombres, true);
if (!is_array($nombresArray)) {
    echo json_encode(array("status" => "error", "message" => "Formato incorrecto de nombres."));
    exit();
}

// Crear la consulta SQL para buscar empresas por nombres
$placeholders = implode(',', array_fill(0, count($nombresArray), '?'));
$sql = "SELECT * FROM empresas WHERE nombre IN ($placeholders)";
$stmt = $conn->prepare($sql);

// Vincular los parámetros
$types = str_repeat('s', count($nombresArray));
$stmt->bind_param($types, ...$nombresArray);
$stmt->execute();
$result = $stmt->get_result();

// Array para almacenar los resultados
$empresasArray = array();

// Verificar si hay resultados y almacenarlos en el array
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $empresasArray[] = $row;
    }
} else {
    echo json_encode(array("status" => "error", "message" => "No se encontraron empresas con los nombres proporcionados."));
    exit();
}

// Cerrar conexión
$stmt->close();
$conn->close();

// Devolver el array con los resultados
echo json_encode($empresasArray);
?>
