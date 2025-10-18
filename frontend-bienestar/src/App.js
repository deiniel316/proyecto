import React, { useEffect, useState } from "react";

function App() {
  const [clientes, setClientes] = useState([]);
  const [error, setError] = useState(null);

  // Estados para todos los campos
  const [nombreCompleto, setNombreCompleto] = useState("");
  const [dpi, setDpi] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [telefono, setTelefono] = useState("");
  const [fechaNacimiento, setFechaNacimiento] = useState("");
  const [editId, setEditId] = useState(null);

  // ✅ Cargar lista de clientes
  const cargarClientes = () => {
    fetch("http://localhost:8080/clientes")
      .then((res) => {
        if (!res.ok) throw new Error("Error en la respuesta del servidor");
        return res.json();
      })
      .then((data) => setClientes(data))
      .catch((err) => setError(err.message));
  };

  useEffect(() => {
    cargarClientes();
  }, []);

  // ✅ Registrar o actualizar cliente
  const guardarCliente = (e) => {
    e.preventDefault();

    // Validar campos obligatorios
    if (!nombreCompleto || !dpi || !email || !password) {
      alert("Por favor completa todos los campos obligatorios (*)");
      return;
    }

    // Datos con los nombres que Java espera
    const cliente = { 
      nombreCompleto, 
      dpi, 
      email, 
      password, 
      telefono, 
      fechaNacimiento 
    };

    // Si editId tiene valor => PUT (editar)
    if (editId !== null) {
      fetch(`http://localhost:8080/clientes/${editId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cliente),
      })
        .then((res) => {
          if (!res.ok) throw new Error("Error al actualizar cliente");
          return res.text();
        })
        .then(() => {
          alert("✅ Cliente actualizado");
          resetFormulario();
          cargarClientes();
        })
        .catch((err) => alert("❌ " + err.message));
    } else {
      // POST (nuevo cliente)
      fetch("http://localhost:8080/clientes", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cliente),
      })
        .then((res) => {
          if (!res.ok) throw new Error("Error al registrar cliente");
          return res.text();
        })
        .then(() => {
          alert("✅ Cliente agregado");
          resetFormulario();
          cargarClientes();
        })
        .catch((err) => alert("❌ " + err.message));
    }
  };

  // ✅ Eliminar cliente
  const eliminarCliente = (id) => {
    if (window.confirm("¿Seguro que quieres eliminar este cliente?")) {
      fetch(`http://localhost:8080/clientes/${id}`, {
        method: "DELETE",
      })
        .then((res) => {
          if (!res.ok) throw new Error("Error al eliminar cliente");
          return res.text();
        })
        .then(() => {
          alert("🗑️ Cliente eliminado");
          cargarClientes();
        })
        .catch((err) => alert("❌ " + err.message));
    }
  };

  // ✅ Cargar datos al formulario para editar
  const editarCliente = (cliente) => {
    setEditId(cliente.id);
    setNombreCompleto(cliente.nombreCompleto || "");
    setDpi(cliente.dpi || "");
    setEmail(cliente.email || "");
    setPassword(cliente.password || "");
    setTelefono(cliente.telefono || "");
    setFechaNacimiento(cliente.fechaNacimiento || "");
  };

  // ✅ Limpiar formulario
  const resetFormulario = () => {
    setEditId(null);
    setNombreCompleto("");
    setDpi("");
    setEmail("");
    setPassword("");
    setTelefono("");
    setFechaNacimiento("");
  };

  return (
    <div style={{ padding: "20px", maxWidth: "600px" }}>
      <h1>🏥 Sistema de Clientes - Bienestar</h1>

      {error && <p style={{ color: "red" }}>⚠️ {error}</p>}

      {clientes.length === 0 && !error ? (
        <p>No hay clientes en el sistema.</p>
      ) : (
        <div>
          <h2>📋 Lista de Clientes</h2>
          <ul style={{ listStyle: "none", padding: 0 }}>
            {clientes.map((c) => (
              <li key={c.id} style={{ 
                padding: "10px", 
                border: "1px solid #ddd", 
                marginBottom: "8px",
                borderRadius: "5px"
              }}>
                <strong>{c.nombreCompleto}</strong><br />
                DPI: {c.dpi} | Email: {c.email} | Tel: {c.telefono}
                <div style={{ marginTop: "5px" }}>
                  <button 
                    onClick={() => editarCliente(c)}
                    style={{ marginRight: "5px" }}
                  >
                    ✏️ Editar
                  </button>
                  <button 
                    onClick={() => eliminarCliente(c.id)}
                    style={{ backgroundColor: "#ff4444", color: "white" }}
                  >
                    🗑️ Eliminar
                  </button>
                </div>
              </li>
            ))}
          </ul>
        </div>
      )}

      <hr />
      
      <h2>{editId ? "✏️ Editar Cliente" : "➕ Registrar Nuevo Cliente"}</h2>
      <form onSubmit={guardarCliente} style={{ display: "grid", gap: "10px" }}>
        <div>
          <label>Nombre Completo *:</label>
          <input
            type="text"
            placeholder="Ej: Juan Pérez"
            value={nombreCompleto}
            onChange={(e) => setNombreCompleto(e.target.value)}
            style={{ width: "100%", padding: "8px" }}
            required
          />
        </div>

        <div>
          <label>DPI * (mínimo 8 dígitos):</label>
          <input
            type="text"
            placeholder="Ej: 1234567890123"
            value={dpi}
            onChange={(e) => setDpi(e.target.value)}
            style={{ width: "100%", padding: "8px" }}
            required
          />
        </div>

        <div>
          <label>Email *:</label>
          <input
            type="email"
            placeholder="Ej: juan@example.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            style={{ width: "100%", padding: "8px" }}
            required
          />
        </div>

        <div>
          <label>Password * (mínimo 8 caracteres, letra y número):</label>
          <input
            type="password"
            placeholder="Ej: password123"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={{ width: "100%", padding: "8px" }}
            required
          />
        </div>

        <div>
          <label>Teléfono:</label>
          <input
            type="text"
            placeholder="Ej: 12345678"
            value={telefono}
            onChange={(e) => setTelefono(e.target.value)}
            style={{ width: "100%", padding: "8px" }}
          />
        </div>

        <div>
          <label>Fecha de Nacimiento:</label>
          <input
            type="date"
            value={fechaNacimiento}
            onChange={(e) => setFechaNacimiento(e.target.value)}
            style={{ width: "100%", padding: "8px" }}
          />
        </div>

        <div>
          <button 
            type="submit"
            style={{ 
              backgroundColor: "#4CAF50", 
              color: "white", 
              padding: "10px 15px",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer"
            }}
          >
            {editId ? "💾 Actualizar Cliente" : "✅ Agregar Cliente"}
          </button>
          
          {editId && (
            <button 
              type="button" 
              onClick={resetFormulario} 
              style={{ 
                marginLeft: "10px",
                padding: "10px 15px",
                border: "1px solid #ccc",
                borderRadius: "4px",
                cursor: "pointer"
              }}
            >
              ❌ Cancelar
            </button>
          )}
        </div>
      </form>

      <p style={{ fontSize: "12px", color: "#666", marginTop: "20px" }}>
        * Campos obligatorios
      </p>
    </div>
  );
}

export default App;