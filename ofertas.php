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


// Preparar y ejecutar la consulta
$stmt = $conn->prepare("SELECT ofertas.id, empresas.nombre, ofertas.descripcion, ofertas.enlace FROM ofertas join empresas on idEmpresa = empresas.id");

$stmt->execute();
$result = $stmt->get_result();

$ofertas = array();

// Verificar resultados
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $ofertas[] = $row;
    }
} else {
    echo "0 result";
}

// Cerrar conexión
$stmt->close();
$conn->close();

header('Content-Type: application/json');
echo json_encode($ofertas);
?>