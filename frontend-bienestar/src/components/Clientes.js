import React, { useState } from "react";

export default function RegistroCliente() {
  const [form, setForm] = useState({
    nombreCompleto: "",
    identificadorUnico: "",
    fechaNacimiento: "",
    telefono: "",
    email: "",
    password: "",
  });

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch("http://localhost:8081/api/clientes/registrar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    })
      .then((res) => res.json())
      .then(() => alert("Cliente registrado exitosamente"))
      .catch(() => alert("Error al registrar cliente"));
  };

  return (
    <form onSubmit={handleSubmit} style={{ margin: "20px" }}>
      <h2>Registrar Cliente</h2>
      {Object.keys(form).map((key) => (
        <input
          key={key}
          name={key}
          placeholder={key}
          type={key === "password" ? "password" : "text"}
          value={form[key]}
          onChange={handleChange}
          style={{ display: "block", margin: "10px 0" }}
          required
        />
      ))}
      <button type="submit">Registrar</button>
    </form>
  );
}