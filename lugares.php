<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "noctua";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar conexión
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Consultar la base de datos
$sql = "SELECT id, nombre, ubicacion, map, tags, descripcion FROM empresas";
$result = $conn->query($sql);

$lugares = array();

if ($result->num_rows > 0) {
    // Salida de datos de cada fila
    while($row = $result->fetch_assoc()) {
        $lugares[] = $row;
    }
} else {
    echo "0 results";
}
$conn->close();

header('Content-Type: application/json');
echo json_encode($lugares);
?>
