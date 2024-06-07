<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "android";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Comprobar conexión
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Obtener valores de POST
$username = $_POST['username'];
$email = $_POST['email'];

// Preparar y ejecutar la consulta de actualización
$stmt = $conn->prepare("UPDATE users SET email = ? WHERE username = ?");
$stmt->bind_param("ssss", $email, $age, $description, $username);
$success = $stmt->execute();

// Verificar resultados
if ($success) {
    echo json_encode(array("status" => "success"));
} else {
    echo json_encode(array("status" => "error"));
}

// Cerrar conexión
$stmt->close();
$conn->close();
?>
