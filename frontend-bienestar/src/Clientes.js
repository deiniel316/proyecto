import React, { useState, useEffect } from "react";
import api from "../api/api";

function Clientes() {
  const [clientes, setClientes] = useState([]);
  const [nuevo, setNuevo] = useState({ nombreCompleto: "", email: "", dpi: "" });

  useEffect(() => {
    api.get("/clientes").then((res) => setClientes(res.data));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!nuevo.email.includes("@")) {
      alert("El correo debe tener formato válido.");
      return;
    }
    try {
      await api.post("/clientes", nuevo);
      alert("Cliente registrado");
      const res = await api.get("/clientes");
      setClientes(res.data);
    } catch (err) {
      alert("Error registrando cliente");
    }
  };

  return (
    <div>
      <h2>Gestión de Clientes</h2>

      <form onSubmit={handleSubmit}>
        <input
          placeholder="Nombre completo"
          value={nuevo.nombreCompleto}
          onChange={(e) => setNuevo({ ...nuevo, nombreCompleto: e.target.value })}
          required
        />
        <input
          placeholder="DPI"
          value={nuevo.dpi}
          onChange={(e) => setNuevo({ ...nuevo, dpi: e.target.value })}
          required
        />
        <input
          type="email"
          placeholder="Correo"
          value={nuevo.email}
          onChange={(e) => setNuevo({ ...nuevo, email: e.target.value })}
          required
        />
        <button type="submit">Agregar Cliente</button>
      </form>

      <ul>
        {clientes.map((c) => (
          <li key={c.id}>{c.nombreCompleto} - {c.email}</li>
        ))}
      </ul>
    </div>
  );
}

export default Clientes;