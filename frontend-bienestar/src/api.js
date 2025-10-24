import axios from "axios";

// ✅ Config base para llamadas al backend
const api = axios.create({
  baseURL: "http://localhost:8080", // tu backend Spring Boot
});

// ✅ Interceptor para incluir el token JWT (si hay login)
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;