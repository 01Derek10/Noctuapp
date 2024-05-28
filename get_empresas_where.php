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

// Obtener el nombre de la empresa desde la solicitud GET
$nombre_empresa = $_GET['nombre_empresa'] ?? '';

// Verificar si se proporcionó el nombre de la empresa
if(empty($nombre_empresa)) {
    echo json_encode(array("status" => "error", "message" => "No se proporcionó el nombre de la empresa."));
    exit();
}

// Consulta SQL para obtener todos los campos de la tabla "empresas" filtrados por el nombre
$sql = "SELECT * FROM empresas WHERE nombre = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $nombre_empresa);
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
    echo json_encode(array("status" => "error", "message" => "No se encontraron empresas con ese nombre."));
    exit();
}

// Cerrar conexión
$stmt->close();
$conn->close();

// Devolver el array con los resultados
echo json_encode($empresasArray);
?>
