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
$email = $_POST['email'];
$age = $_POST['age'];
$lastName = $_POST['lastName'];
$description = $_POST['description'];

// Preparar y ejecutar la consulta de actualización
$stmt = $conn->prepare("UPDATE users SET email = ?, edad = ?, apellidos = ?, descripcion = ? WHERE username = ?");
$stmt->bind_param("sisss", $email, $age, $lastName, $description, $username);
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
