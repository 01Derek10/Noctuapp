<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "noctua";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Comprobar conexión
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Obtener valores de POST
$username = $_POST['username'];

// Preparar y ejecutar la consulta de selección
$stmt = $conn->prepare("SELECT username, email, edad, apellidos, descripcion FROM users WHERE username = ?");
$stmt->bind_param("s", $username);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $userData = $result->fetch_assoc();
    echo json_encode(array("status" => "success", "data" => $userData));
} else {
    echo json_encode(array("status" => "error", "message" => "User not found"));
}

// Cerrar conexión
$stmt->close();
$conn->close();
?>
