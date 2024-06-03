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
$user = $_POST['username'];
$pass = $_POST['password'];

// Preparar y ejecutar la consulta
$stmt = $conn->prepare("SELECT * FROM users WHERE username = ? AND password = ?");
$stmt->bind_param("ss", $user, $pass);
$stmt->execute();
$result = $stmt->get_result();

// Verificar resultados
if ($result->num_rows > 0) {
    echo json_encode(array("status" => "success"));
} else {
    echo json_encode(array("status" => "error"));
}

// Cerrar conexión
$stmt->close();
$conn->close();
?>
