import React, { useState, useEffect } from "react";
import api from "../api/api";

function Servicios() {
  const [servicios, setServicios] = useState([]);
  const [nombre, setNombre] = useState("");

  useEffect(() => {
    api.get("/servicios").then((res) => setServicios(res.data));
  }, []);

  const agregar = async (e) => {
    e.preventDefault();
    if (nombre.trim().length < 3) {
      alert("El nombre del servicio debe tener al menos 3 caracteres");
      return;
    }
    try {
      await api.post("/servicios", { nombre });
      alert("Servicio agregado");
      const res = await api.get("/servicios");
      setServicios(res.data);
      setNombre("");
    } catch (err) {
      alert("Error al agregar el servicio");
    }
  };

  return (
    <div>
      <h2>Gestión de Servicios</h2>
      <form onSubmit={agregar}>
        <input
          placeholder="Nombre del servicio"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          required
        />
        <button type="submit">Agregar</button>
      </form>
      <ul>
        {servicios.length > 0 ? (
          servicios.map((s) => <li key={s.id}>{s.nombre}</li>)
        ) : (
          <p>No hay servicios registrados.</p>
        )}
      </ul>
    </div>
  );
}

export default Servicios;