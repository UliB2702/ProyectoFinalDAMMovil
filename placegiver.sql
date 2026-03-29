-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-03-2026 a las 00:29:52
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
-- Base de datos: `placegiver`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `nombre`) VALUES
(1, 'Desarrollo'),
(2, 'Propuestas de trabajo'),
(3, 'Preguntas generales');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajedm`
--

CREATE TABLE `mensajedm` (
  `id` int(11) NOT NULL,
  `texto` varchar(500) NOT NULL,
  `archivo` blob DEFAULT NULL,
  `idConversacion` int(11) NOT NULL,
  `fechaEnvio` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicaciones`
--

CREATE TABLE `publicaciones` (
  `id` int(11) NOT NULL,
  `texto` varchar(500) NOT NULL,
  `fechaPublicacion` datetime NOT NULL,
  `usuario` varchar(500) NOT NULL COMMENT 'id',
  `idCategoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `publicaciones`
--

INSERT INTO `publicaciones` (`id`, `texto`, `fechaPublicacion`, `usuario`, `idCategoria`) VALUES
(10, 'Hola, buen día', '2026-02-11 16:31:09', 'Iago', 1),
(16, 'Hola, como estan?', '2026-03-01 20:50:23', 'as', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seguidorxseguido`
--

CREATE TABLE `seguidorxseguido` (
  `seguidor` varchar(20) NOT NULL,
  `seguido` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `nombre` varchar(20) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `email` varchar(320) NOT NULL,
  `password` varchar(20) NOT NULL,
  `fechaCreacion` datetime NOT NULL,
  `fotoPerfil` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`nombre`, `descripcion`, `email`, `password`, `fechaCreacion`, `fotoPerfil`) VALUES
('as', 'Hola, soy el creador de esta aplicación. Ojala la disfruten y le den una oportunidad', 'ulibaamonde@gmail.com', 'as', '2026-02-04 00:00:00', NULL),
('des', 'des', 'ulibaamonde@gmail.com', 'des', '2026-02-22 19:17:34', NULL),
('eeeee', '', 'ulibaamonde', 'eeeee', '2026-02-10 12:57:02', NULL),
('Iago', '', 'iago@gmail.com', 'i', '2026-02-10 13:19:14', NULL),
('Julio', '', 'papa@gmail.com', 'a', '2026-02-10 13:17:20', NULL),
('os', '', 'ulibaamonde@gail.com', 'eee', '2026-02-10 12:54:17', NULL),
('Rodrigo', '', 'ulibaamonde', 'e', '2026-02-12 13:04:49', NULL),
('sa', '', 'ulibamonde@gmail.com', 'as', '2026-03-27 19:16:06', NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `mensajedm`
--
ALTER TABLE `mensajedm`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario` (`usuario`),
  ADD KEY `idCategoria` (`idCategoria`);

--
-- Indices de la tabla `seguidorxseguido`
--
ALTER TABLE `seguidorxseguido`
  ADD PRIMARY KEY (`seguidor`,`seguido`),
  ADD KEY `seguido` (`seguido`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`nombre`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `mensajedm`
--
ALTER TABLE `mensajedm`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  ADD CONSTRAINT `publicaciones_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `usuarios` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `publicaciones_ibfk_2` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `seguidorxseguido`
--
ALTER TABLE `seguidorxseguido`
  ADD CONSTRAINT `seguidorxseguido_ibfk_1` FOREIGN KEY (`seguidor`) REFERENCES `usuarios` (`nombre`),
  ADD CONSTRAINT `seguidorxseguido_ibfk_2` FOREIGN KEY (`seguido`) REFERENCES `usuarios` (`nombre`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
