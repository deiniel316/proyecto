import React, { useEffect, useState } from "react";

function App() {
  const [clientes, setClientes] = useState([]);
  const [error, setError] = useState(null);
  const [nombre, setNombre] = useState("");
  const [dpi, setDpi] = useState("");
  const [editId, setEditId] = useState(null);

  // === Cargar lista de clientes ===
  const cargarClientes = () => {
    fetch("http://localhost:8080/clientes")
      .then((res) => {
        if (!res.ok) {
          throw new Error("Error en la respuesta del servidor");
        }
        return res.json();
      })
      .then((data) => setClientes(data))
      .catch((err) => {
        console.error("Error al cargar clientes:", err);
        setError(err.message);
      });
  };

  useEffect(() => {
    cargarClientes();
  }, []);

  // === Registrar o actualizar cliente ===
  const guardarCliente = (e) => {
    e.preventDefault();

    if (!/^[0-9]+$/.test(dpi)) {
      alert(" El DPI debe contener solo números");
      return;
    }

    if (nombre.length < 3) {
      alert(" El nombre debe tener al menos 3 caracteres");
      return;
    }

    const metodo = editId ? "PUT" : "POST";
    const url = editId
      ? `http://localhost:8080/clientes/${editId}`
      : "http://localhost:8080/clientes";

    fetch(url, {
      method: metodo,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nombre, dpi }),
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error("Error al guardar cliente");
        }
        return res.text();
      })
      .then(() => {
        alert(editId ? " Cliente actualizado" : "Cliente registrado");
        setNombre("");
        setDpi("");
        setEditId(null);
        cargarClientes();
      })
      .catch((err) => {
        alert("Error: " + err.message);
      });
  };

  // === Eliminar cliente ===
  const eliminarCliente = (id) => {
    if (!window.confirm("¿Seguro que deseas eliminar este cliente?")) return;

    fetch(`http://localhost:8080/clientes/${id}`, { method: "DELETE" })
      .then((res) => {
        if (!res.ok) {
          throw new Error("Error al eliminar cliente");
        }
        return res.text();
      })
      .then(() => {
        alert(" Cliente eliminado");
        cargarClientes();
      })
      .catch((err) => alert(" Error: " + err.message));
  };

  // === Cargar datos para editar ===
  const editarCliente = (cliente) => {
    setNombre(cliente.nombre);
    setDpi(cliente.dpi);
    setEditId(cliente.id);
  };

  return (
    <div style={{ padding: "20px", maxWidth: "600px", margin: "auto" }}>
      <h1>Lista de clientes</h1>

      {error && <p style={{ color: "red" }}> {error}</p>}

      {clientes.length === 0 && !error ? (
        <p>No hay clientes en el sistema.</p>
      ) : (
        <ul>
          {clientes.map((c) => (
            <li key={c.id} style={{ marginBottom: "10px" }}>
              <b>{c.nombre}</b> - {c.dpi}
              <button
                onClick={() => editarCliente(c)}
                style={{ marginLeft: "10px" }}
              >
                 Editar
              </button>
              <button
                onClick={() => eliminarCliente(c.id)}
                style={{ marginLeft: "10px", color: "red" }}
              >
                 Eliminar
              </button>
            </li>
          ))}
        </ul>
      )}

      <hr />
      <h2>{editId ? "Editar cliente" : "Registrar nuevo cliente"}</h2>
      <form onSubmit={guardarCliente}>
        <input
          type="text"
          placeholder="Nombre"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          style={{ marginRight: "10px" }}
        />
        <input
          type="text"
          placeholder="DPI"
          value={dpi}
          onChange={(e) => setDpi(e.target.value)}
          style={{ marginRight: "10px" }}
        />
        <button type="submit">{editId ? "Actualizar" : "Agregar"}</button>
        {editId && (
          <button
            type="button"
            onClick={() => {
              setNombre("");
              setDpi("");
              setEditId(null);
            }}
            style={{ marginLeft: "10px" }}
          >
            Cancelar
          </button>
        )}
      </form>
    </div>
  );
}

export default App;