-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-05-2024 a las 09:23:39
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `noctua`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresas`
--

CREATE TABLE `empresas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `ubicacion` varchar(200) NOT NULL,
  `map` varchar(200) NOT NULL,
  `tags` varchar(200) NOT NULL,
  `ofertas` int(11) NOT NULL,
  `Descripción` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empresas`
--

INSERT INTO `empresas` (`id`, `nombre`, `ubicacion`, `map`, `tags`, `ofertas`, `Descripción`) VALUES
(1, 'Sala Gold', 'Calle Luis de Velázquez, 5', 'https://maps.app.goo.gl/MPBoxgwgHZKMkCsc9', 'Reggeton Trap', 0, 'Cócteles, champán, fiestas temáticas y DJs en una elegante discoteca con elaborados juegos de luces.'),
(2, 'Theatro Club', 'Calle Lazcano, 5', 'https://maps.app.goo.gl/BNxoF7DixUbwTNgw8', 'House, Retro', 0, 'Noches de temática reggae, house y retro en un animado club nocturno, con extravagantes espectáculos de drag queens.'),
(3, 'Andén', 'Plaza de Uncibay, 8', 'https://maps.app.goo.gl/wyNtrFmhRPponuT1A', 'Reggeton', 0, 'Amplia discoteca de varias salas con láseres, zonas vip y 4 bares, que abre hasta el amanecer.'),
(4, 'Discoteca Liceo', 'Calle Beatas, 21', 'https://maps.app.goo.gl/RtEFCJr5TVVm2jL36', 'Pop', 1, 'Espectáculo en vivo y cursos de baile acompañados por vinos y platos malagueños en un palacete del s. XIX.'),
(5, 'Sala Wenge', 'Calle Santa Lucía, 11', 'https://maps.app.goo.gl/LBKCQ2q4LmyZ8UoD7', 'Trap', 0, 'AMAZONIA by Wenge es una discoteca de renovada decoración y magnifica ambientación, que se mantiene llena cada día de la semana desde los últimos 11 años.'),
(6, 'Velvet Club', 'Calle Convalecientes, 11', 'https://maps.app.goo.gl/p2pEJmAjqSk951fC6', 'Directo', 0, 'Actuaciones en directo de grupos musicales en un animado pub que abre de martes a sábado hasta tarde.'),
(7, 'Bubbles Lounge Club', 'Calle Mártires, 14', 'https://maps.app.goo.gl/oQPv6yiyT3uiRTEN8', '', 1, 'Sesiones DJ y fiestas temáticas en moderna discoteca con amplia pista central, dos barras y dos reservados.'),
(8, 'Antigua Casa de Guardia', 'Alameda Principal, 18', 'https://maps.app.goo.gl/gXgvqyC9FdqCFvc4A', 'Tradicional', 0, 'Taberna tradicional, con paredes recubiertas de barricas, que sirve vinos dulces de la región y tapas.'),
(9, 'ZZ Pub', 'Calle Tejón y Rodríguez, 6', 'https://maps.app.goo.gl/JUKhstsC8dUwk6iM8', 'rock, soul, blues, indie', 1, 'Recinto de conciertos sencillo, donde cada noche hay actuaciones de bandas de rock, soul, blues y música indie.'),
(10, 'Clarence Jazz Club', 'Calle Cañon,5 ', 'https://maps.app.goo.gl/hoU6yBZ7BVPzhwoA7', 'Jazz', 0, 'Clarence Jazz Club nació en 2013 en el corazón de Málaga, a los pies de la Catedral, en un pequeño local, con una decoración con aire neoyorquino, comenzó su andadura como un Club de Jazz de referenci'),
(11, 'Sala Premier', 'Calle Molina Lario, 2', 'https://maps.app.goo.gl/nDRWyBbsh9Ds2FFr9', '', 1, 'Juegos de mesa, cócteles y variedad de cervezas en original pub de dos plantas decorado con motivos de cine.'),
(12, 'Malafama', 'Calle Comedias, 15', 'https://maps.app.goo.gl/RERZEDBsHuzYpW6m9', '', 1, 'Sesiones DJ y fiestas temáticas en un disco bar con moderna iluminación LED de colores y cuatro barras.'),
(13, 'Sala White', 'Calle José Denis Belgrano, 3', 'https://maps.app.goo.gl/6bK74WLRMncLJZWR8', 'Cerrado', 0, 'N/A'),
(14, 'The Hall', 'Calle Héroe de Sostoa, 65', 'https://maps.app.goo.gl/cZpBkwb3kzboK7Wm8', 'Jazz', 1, 'Nuestra sala vive conciertos, teatro, música en vivo, baile… un torrente de sensaciones que son alimento para el espíritu.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ofertas`
--

CREATE TABLE `ofertas` (
  `id` int(11) NOT NULL,
  `idEmpresa` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `enlace` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ofertas`
--

INSERT INTO `ofertas` (`id`, `idEmpresa`, `descripcion`, `enlace`) VALUES
(1, 4, '3x2 En bebidas antes de las 01:00', 'https://www.instagram.com/liceomalaga/'),
(2, 7, 'Consumición Gratis comprando la Entrada hasta el 30/05/2024', 'https://www.instagram.com/bubblesclub.malaga/?hl=es'),
(3, 14, 'Concierto de Jazz en Directo, entradas limitadas.', 'http://www.thehall.in'),
(4, 9, 'Noche Indie, con la entrada regalamos un chupito.', 'https://www.zzpub.es'),
(5, 11, 'Duplicamos tu botella de reservado, solo el 31/05/2024.', 'https://salapremier.com/salas/sala-premier-centro/'),
(7, 12, 'Oferta de 3 consumiciones a 10€ al comprar tu entrada.', 'https://www.instagram.com/malafama.malaga/');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` text NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`) VALUES
(1, 'ivan', '4321', 'ivan@gmail.com');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `empresas`
--
ALTER TABLE `empresas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `ofertas`
--
ALTER TABLE `ofertas`
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `fk_empresas_ofertas` (`idEmpresa`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `empresas`
--
ALTER TABLE `empresas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `ofertas`
--
ALTER TABLE `ofertas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ofertas`
--
ALTER TABLE `ofertas`
  ADD CONSTRAINT `fk_empresas_ofertas` FOREIGN KEY (`idEmpresa`) REFERENCES `empresas` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
